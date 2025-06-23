import axios from 'axios';
import { exec } from 'child_process';
import fs from 'fs';
import { promisify } from 'util';
import path from 'path';
import tmp from 'tmp';
import { fileURLToPath } from 'url';

const execAsync = promisify(exec);
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

export const scoreProblem = async (req, res) => {
  console.log("문제 채점 요청", req.body);

  const { submissionId, userId, baekjoonId, language, sourceCode } = req.body;

  try {
    const tcDir = path.join(__dirname, '..', 'testcases', String(baekjoonId));
    const input = fs.readFileSync(path.join(tcDir, 'input1.txt'), 'utf-8').trim();
    const expected = fs.readFileSync(path.join(tcDir, 'output1.txt'), 'utf-8').trim();

    let actual = '';
    let start = 0, end = 0;

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
      const cppFile = path.join(tmpDir.name, 'main.cpp');
      const exeFile = path.join(tmpDir.name, 'main.out');

      fs.writeFileSync(cppFile, sourceCode);
      await execAsync(`g++ ${cppFile} -o ${exeFile}`);

      start = Date.now();
      const { stdout } = await execAsync(`${exeFile}`, { input, timeout: 3000 });
      end = Date.now();
      actual = stdout.trim();

    } else if (language === 'java') {
      const tmpDir = tmp.dirSync();
      const javaFile = path.join(tmpDir.name, 'Main.java');

      fs.writeFileSync(javaFile, sourceCode);
      await execAsync(`javac ${javaFile}`);

      start = Date.now();
      const { stdout } = await execAsync(`java -cp ${tmpDir.name} Main`, { input, timeout: 3000 });
      end = Date.now();
      actual = stdout.trim();
    }

    const isCorrect = actual === expected;
    const resultPayload = {
      result: isCorrect ? 'correct' : 'wrong',
      executionTime: (end - start) / 1000,
      language,
    };

    // Spring 서버로 결과 전송 (주석 처리 해제 시 사용)
    // await axios.patch(`http://localhost:8080/api/submissions/${submissionId}`, resultPayload);

    res.status(200).json({ message: '채점 완료', result: resultPayload });

  } catch (err) {
    console.error("채점 오류 전체 객체:", JSON.stringify(err, null, 2));
    res.status(500).json({
      error: "채점 중 오류 발생",
      detail: err.stderr || err.message || err.stdout || "No detail available"
    });
  }
};
