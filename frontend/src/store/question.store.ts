import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { AnswerDto } from '@/types/answer.dto'
import { QuestionType } from '@/generated/graphql.ts'
import type { QuestionDto} from '@/types/question.dto.ts'

const questionStore = defineStore('question', () =>
{

  const questions = ref<QuestionDto[]>([])
  const setQuestions = (questionsAmount: number, idQuiz: number) =>
  {
    const createEmptyQuestion = (index: number): QuestionDto =>
      ({
        id: index,
        questionType: QuestionType.SingleChoice,
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

  const addQuestion = (newQuestion: QuestionDto) =>
  {
    try {
      questions.value = [...questions.value, newQuestion]
    } catch (e) {
      throw e
    }
    return newQuestion
  }

  const addAnswer = (questionId: number, newAnswer: AnswerDto) =>
  {
    try
    {
      questions.value = questions.value.map((q) =>
        q.id === questionId ? { ...q, answers: [...(q.answers ?? []), newAnswer] } : q,
      )
    } catch (e) {
      throw e
    }
    return newAnswer
  }

  const updateAnswer = (questionId: number, answerId: number, updatedAnswer: AnswerDto) =>
  {
    try
    {
      questions.value = questions.value.map((q) =>
        q.id === questionId
          ? ({
              ...q,
              answers: q.answers?.map((a) =>
                a?.id === answerId ? { ...a, ...updatedAnswer } : a,
              ),
            } as QuestionDto)
          : q,
      )
    } catch (e) {

      throw e
    }
    return updatedAnswer
  }

  return {
    questions,
    setQuestions,
    addAnswer,
    updateAnswer,
    addQuestion,
  }
})

export default questionStore
