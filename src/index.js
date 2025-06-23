import express from 'express'   
import bodyParser from 'body-parser';
import judgeRouter from './router/judge.route.js'; // 라우터 모듈
const app = express()
const port = 3000

app.use(bodyParser.json()); // JSON 파싱 미들웨어

app.use('/', judgeRouter); //라우터 등록

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})