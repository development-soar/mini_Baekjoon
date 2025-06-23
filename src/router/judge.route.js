import express from 'express';
import { scoreProblem } from '../controller/judge.controller.js'; 

const router = express.Router();

router.post("/api/judge", scoreProblem);

export default router;