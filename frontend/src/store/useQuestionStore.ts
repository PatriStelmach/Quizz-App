import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { AnswerDto } from '@/types/answer.dto'
import type { QuestionDto} from '@/types/question.dto.ts'

const useQuestionStore = defineStore('question', () =>
{

  const questions = ref<QuestionDto[]>([])
  const quizId = ref<number>(0)
  const setQuizId = (id: number) =>
  {
    quizId.value = id;
  }
  const setQuestions = (questionsAmount: number, idQuiz: number) =>
  {
    const createEmptyQuestion = (index: number): QuestionDto =>
      ({
        id: index,
        questionText: '',
        answers: [
          { id: 0, answerText: '', correct: false },
          { id: 1, answerText: '', correct: false },
          { id: 2, answerText: '', correct: false },
          { id: 3, answerText: '', correct: false },
        ],
        quizId: idQuiz
    })

    questions.value = Array.from({ length: questionsAmount }, (_, index) =>
      createEmptyQuestion(index),
    )
    return questions.value
  }

  const updateQuestion = (question: QuestionDto, id: number) =>
  {
    try
    {
      questions.value = questions.value.map((q) =>
        q.id === id ? question : q,)
    }

    catch(error)
    {
      throw error;
    }

  }

  const clearQuestions = () =>
  {
    try
    {
      questions.value = []
      quizId.value = 0;
    }
    catch (error)
    {
      throw error;
    }

  }


  return {
    questions,
    setQuestions,
    updateQuestion,
    clearQuestions,
    setQuizId,
    quizId,
  }
})

export default useQuestionStore
