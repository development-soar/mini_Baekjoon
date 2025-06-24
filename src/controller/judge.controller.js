import axios from 'axios';
import { exec } from 'child_process';
import fs from 'fs';
import { promisify } from 'util';
import path from 'path';
import tmp from 'tmp';
import { fileURLToPath } from 'url';
import { spawn } from 'child_process';


const execAsync = promisify(exec);
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

export const scoreProblem = async (req, res) => {
  console.log("문제 채점 요청", req.body);

  const { submissionId, userId, baekjoonId, language, sourceCode } = req.body;

  let actual = '';
  let start = 0, end = 0;
  let restoredCode = '', cppFile = '', exeFile = '';

  try {
    const tcDir = path.join(__dirname, '..', 'testcases', String(baekjoonId));
    let input = fs.readFileSync(path.join(tcDir, 'input1.txt'), 'utf-8')
      .replace(/\r\n/g, '\n').trim() + '\n';
    const expected = fs.readFileSync(path.join(tcDir, 'output1.txt'), 'utf-8')
      .replace(/\r\n/g, '\n').trim();

    console.log('[디버그] input 내용:', JSON.stringify(input));
    console.log("예상 출력:", expected);

    if (language === 'python' || language === 'python3') {
      const tmpFile = tmp.fileSync({ postfix: '.py' });
      fs.writeFileSync(tmpFile.name, sourceCode);

      const pythonCmd = language === 'python' ? 'python' : 'python3';
      start = Date.now();
      const { stdout } = await execAsync(`${pythonCmd} ${tmpFile.name}`, { input, timeout: 3000 });
      end = Date.now();
      actual = stdout.trim();

    } else if (language === 'cpp') {
      const tmpDir = tmp.dirSync();

      restoredCode = sourceCode
        .replace(/\\n/g, '\n')
        .replace(/\\"/g, '"')
        .replace(/\\\\/g, '\\');

      cppFile = path.join(tmpDir.name, 'main.cpp');
      exeFile = path.join(tmpDir.name, 'main.out');

      fs.writeFileSync(cppFile, restoredCode);
      fs.writeFileSync(path.join(__dirname, 'debug_main.cpp'), restoredCode);
      console.log('[디버그] 복원된 코드:\n', restoredCode);

      // 컴파일
      try {
        await execAsync(`g++ ${cppFile} -o ${exeFile}`);
      } catch (compileErr) {
        throw new Error('컴파일 오류: ' + compileErr.stderr);
      }

      // 실행
      start = Date.now();
      const { stdout } = await execAsync(`${exeFile}`, { input, timeout: 3000 });
      end = Date.now();
      actual = stdout.trim();

    } else if (language === 'java') {
      const tmpDir = tmp.dirSync();
      const javaFile = path.join(tmpDir.name, 'Main.java');

      fs.writeFileSync(javaFile, sourceCode);
      try {
        await execAsync(`javac ${javaFile}`);
      } catch (compileErr) {
        throw new Error('Java 컴파일 오류: ' + compileErr.stderr);
      }

      start = Date.now();

  actual = await new Promise((resolve, reject) => {
    const child = spawn('java', ['-cp', tmpDir.name, 'Main']);
    let output = '';
    let error = '';

    child.stdout.on('data', data => output += data.toString());
    child.stderr.on('data', data => error += data.toString());

    child.on('error', reject);
    child.on('close', code => {
      if (code !== 0) {
        return reject(new Error(`Java 프로세스 종료 코드 ${code}. stderr: ${error}`));
      }
      resolve(output.trim());
    });

    child.stdin.write(input);
    child.stdin.end();
  });

  end = Date.now();
}

    // 결과 비교
    const normalize = s => s.trim().replace(/\r\n/g, '\n');
    const isCorrect = normalize(actual) === normalize(expected);

    const resultPayload = {
      result: isCorrect ? 'correct' : 'wrong',
      executionTime: (end - start) / 1000,
      language,
    };

    res.status(200).json({ message: '채점 완료', result: resultPayload });

  } catch (err) {
    console.error("채점 오류 전체 객체:", JSON.stringify(err, null, 2));
    res.status(500).json({
      error: "채점 중 오류 발생",
      detail: err.stderr || err.message || err.stdout || "No detail available"
    });
  }
};
