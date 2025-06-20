<script setup lang="ts">
import { watchOnce } from '@vueuse/core'
import { ref, watchEffect } from 'vue'
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Progress } from '@/components/ui/progress'

import {
  Carousel,
  type CarouselApi,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from '@/components/ui/carousel'
import useQuestionStore from '@/store/useQuestionStore.ts'
import { Button } from '@/components/ui/button'
import { QuestionType } from '@/generated/graphql.ts'
import type { QuestionDto } from '@/types/question.dto.ts'
import { z } from 'zod'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
import { RadioGroup, RadioGroupItem } from '@/components/ui/radio-group'
import { storeToRefs } from 'pinia'
import axios from 'axios'
import useAuthStore from '@/store/useAuthStore.ts'
import router from '@/router'

const authStore = useAuthStore()
const questionStore = useQuestionStore()
const questionsAmount = questionStore.questions.length
const emblaMainApi = ref<CarouselApi>()
const emblaThumbnailApi = ref<CarouselApi>()
const selectedIndex = ref(0)
const isLoading = ref(false)
const isHidden = ref(false)
const showNotAllAdded = ref(false)
const errorMessage = ref<string | null>(null)
const questionUpdate = ref<boolean[]>(Array(questionsAmount).fill(false))
const showAdded = ref(false)
const { questions } = storeToRefs(questionStore)


const AnswerSchema = z.object
({
  answerText: z.string().min(1, "Answer cannot be empty"),
  correct: z.boolean(),
})

const QuestionSchema = z.object({
  questionText: z.string().min(5, "Question must be at least 10 characters"),
  answers: z.array(AnswerSchema)
    .min(1, "At least 1 answer required")
    .max(4, "At most 4 answers allowed"),
})

const addQuestion = (questionDto: QuestionDto) =>
{
  const result = QuestionSchema.safeParse(questionDto)

  if (!result.success)
  {
    const errors = result.error.errors

    const uniqueMessages = Array.from(new Set(errors.map(e => e.message)))
    errorMessage.value = uniqueMessages.join('\n')

    return false
  }
  else
  {
    setTimeout(() =>
    {
      errorMessage.value = null
      questionUpdate.value[questionDto.id] = true
      questionStore.updateQuestion(questionDto, questionDto.id)
      showAdded.value = true
      console.log(questionStore.questions)
    }, 100)

    return true
  }
}

const submitQuestions = async () =>
{
  try
  {


    if (questionUpdate.value.includes(false))
    {
      showNotAllAdded.value = true
      return
    }
    isLoading.value = true
    for (const q of questions.value)
    {
      await axios.post('http://localhost:10000/question/create',
        {
          question: q.questionText,
          answers: q.answers,
          quizId: q.quizId,
        },

        {
          headers:
            {
              Authorization: `Bearer ${authStore.token}`
            }
        }
      ).then(async (result) =>
      {
        if (result.status === 201)
        {
          setTimeout(async () =>
          {
            isHidden.value = true
            await router.push({name: 'home'})
          }, 1000)
        }

      })

    }
  }

  catch (error)
  {
    setTimeout(async () =>
    {
      isLoading.value = false
      if (error instanceof Error)
      {
        errorMessage.value = error.message
        if (error.message.includes('500'))
        {
          errorMessage.value = 'Invalid Quiz data'
        }
        else if (error.message.includes('401') || error.message.includes('403'))
        {
          errorMessage.value = 'You have to be loged in to create a quiz'
        }

      }

      else
      {
        errorMessage.value = 'An unexpected error occurred'
      }
    }, 200)
  }
}

const setSelectedAnswer = (questionIndex: number, answerIndex: string) =>
{
  const question = questions.value[questionIndex]
  if (!question) return

  question.answers.forEach((answer, index) => {
    answer.correct = String(index) === answerIndex
  })
}

const getSelectedAnswer = (questionIndex: number): string =>
{
  const question = questions.value[questionIndex]
  if (!question) return ''

  const correctIndex = question.answers.findIndex(a => a.correct)
  return correctIndex !== -1 ? String(correctIndex) : ''
}

const exitAlert = () =>
{
  setTimeout( () =>
  {
    errorMessage.value = null;
  }, 200);
}

function onSelect()
{
  if (!emblaMainApi.value || !emblaThumbnailApi.value)
    return
  selectedIndex.value = emblaMainApi.value.selectedScrollSnap()
  emblaThumbnailApi.value.scrollTo(emblaMainApi.value.selectedScrollSnap())
}

function onThumbClick(index: number)
{
  if (!emblaMainApi.value || !emblaThumbnailApi.value)
    return
  emblaMainApi.value.scrollTo(index)
}

watchOnce(emblaMainApi, (emblaMainApi) =>
{
  if (!emblaMainApi)
    return

  onSelect()
  emblaMainApi.on('select', onSelect)
  emblaMainApi.on('reInit', onSelect)
})
const progress = ref(0)
watchEffect((onCleanup) => {
  const timer1 = setTimeout(() => progress.value = 33, 300)
  const timer2 = setTimeout(() => progress.value = 66, 600)
  const timer3 = setTimeout(() => progress.value = 100, 900)

  onCleanup(() => {
    clearTimeout(timer1)
    clearTimeout(timer2)
    clearTimeout(timer3)
  })

  const finishTimer = setTimeout(() => {
    isLoading.value = false
  }, 1200)

  onCleanup(() => clearTimeout(finishTimer))
})
</script>

<template>
  <div class="relative mx-auto max-w-xl grid justify-center text-center">
    <div
      v-if="isLoading"
      class="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm"
    >
      <div class="bg-zinc-900 p-8 rounded-2xl shadow-xl w-80 text-center space-y-4">
        <h3 class="text-xl font-bold text-primary">Creating your Quiz...</h3>
        <Progress
          :model-value="progress"
          class="w-full h-2"
        />
        <p class="text-sm text-muted-foreground">
          Please wait while we save your questions.
        </p>
        <p class="text-sm font-medium text-primary">{{ progress }}%</p>
      </div>
    </div>


    <Carousel
      class="relative w-full transition-all duration-500  my-10 max-w-md mx-auto"
      :class="{
      'transition-all duration-500 border-none blur': errorMessage != null
    }"
      @init-api="(val) => emblaMainApi = val"
    >
      <CarouselContent>
        <CarouselItem v-for="(_, questionIndex) in questionsAmount" :key="questionIndex">
          <div class="p-1 ">
            <Card
              v-bind="questions[questionIndex]"
              class="w-full BG-Rborder-primary shadow-secondary shadow-md max-w-md mx-auto">
              <CardHeader>
                <CardTitle>Add new question</CardTitle>
                <CardDescription>Create questions for your quiz</CardDescription>
              </CardHeader>
              <CardContent>
                <form @submit.prevent="() => addQuestion(questions[questionIndex])">
                  <div class="grid items-center w-full gap-4">
                    <div class="flex flex-col space-y-1.5">
                      <Label class="justify-center" for="name">Question</Label>
                      <Input
                        id="name"
                        v-model="questions[questionIndex].questionText"
                        placeholder="Enter your question"
                      />
                    </div>
                    <div class="flex flex-col space-y-1.5">

                      <RadioGroup
                        v-if="questions[questionIndex]"
                        :model-value="getSelectedAnswer(questionIndex)"
                        @update:model-value="(value) => setSelectedAnswer(questionIndex, value)"
                        class="mt-4 space-y-4"
                      >
                        <div class="flex">
                          <Label class="mr-30.5">Correct</Label>
                          <Label>Answers</Label>
                        </div>

                        <div
                          v-for="(answer, answerIndex) in questions[questionIndex].answers"
                          :key="answerIndex"
                          class="flex items-centergap-2"
                        >
                          <RadioGroupItem
                            :value="String(answerIndex)"
                            :id="`answer-${questionIndex}-${answerIndex}`"
                            class="w-8 h-8  cursor-pointer"
                          />

                          <Input
                            v-model="questions[questionIndex].answers[answerIndex].answerText"
                            placeholder="Enter answer"
                            class=" ml-10 "

                          />
                        </div>
                      </RadioGroup>

                    </div>
                  </div>
                </form>
              </CardContent>
              <CardFooter class="flex justify-between px-6 pb-6">
                <Button class="hover:bg-destructive" variant="outline">
                  Cancel
                </Button>
                <Button @click="() => addQuestion(questions[questionIndex])">
                  Save question
                </Button>
              </CardFooter>
            </Card>
          </div>
        </CarouselItem>
      </CarouselContent>
      <CarouselPrevious class="w-12 h-12 hover:bg-primary cursor-pointer" />
      <CarouselNext class="w-12 h-12 hover:bg-primary cursor-pointer" />
    </Carousel>

    <Carousel
      class="relative w-full  pr-2"
      @init-api="(val) => emblaThumbnailApi = val"
    >
      <CarouselContent class="flex gap-1 ml-0">
        <CarouselItem v-for="(_, index) in questionsAmount" :key="index" class=" pl-0 basis-1/4 cursor-pointer" @click="onThumbClick(index)">
          <div class="p-1" :class="index === selectedIndex ? '' : 'opacity-50'">
            <Card class="hover:bg-primary cursor-pointer transition duration-300 ease-in-out">
              <CardContent class=" flex aspect-square items-center justify-center p-6">
                <span class="text-4xl font-semibold">{{ index + 1 }}</span>
              </CardContent>
            </Card>
          </div>
        </CarouselItem>
      </CarouselContent>
    </Carousel>
    <button
      @click="submitQuestions()"
      :disabled="isLoading"
      class="cursor-pointer mt-5 text-white transition duration-300 ease-in-out
    bg-gradient-to-r from-sky-500 via-sky-600 to-sky-700 hover:brightness-110 hover:scale-105 focus:shadow-md shadow-primary
     focus:outline-none focus:ring-cyan-300 dark:focus:ring-cyan-800 font-medium rounded-lg text-sm mr-0 px-5 py-2.5 text-center me-2 mb-2
     disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:scale-100">
      {{ isLoading ? 'Creating Quiz...' : 'Save all questions and create your Quizzuś!' }}
    </button>
  </div>
  <Transition
    appear
    enter-active-class="transition duration-500 ease-out"
    enter-from-class="opacity-0 scale-95 translate-y-4"
    enter-to-class="opacity-100 scale-100 translate-y-0"
    leave-active-class="transition duration-300 ease-in"
    leave-from-class="opacity-100 scale-100 translate-y-0"
    leave-to-class="opacity-0 scale-95 translate-y-4">
    <div v-if="errorMessage != null"

         class="fixed inset-0 z-50 flex items-center w-xl m-auto"
    >

      <Alert class="relative w-full h-48 shadow-xl shadow-primary">
        <Button
          class="absolute top-2 right-2 cursor-pointer"
          @click="exitAlert">
          X
        </Button>
        <AlertTitle class="text-xl">Question is incorrect</AlertTitle>
        <AlertDescription class="justify-center">
          {{errorMessage}}
        </AlertDescription>
      </Alert>
    </div>
  </Transition>
  <Transition
    appear
    enter-active-class="transition duration-500 ease-out"
    enter-from-class="opacity-0 scale-95 translate-y-4"
    enter-to-class="opacity-100 scale-100 translate-y-0"
    leave-active-class="transition duration-300 ease-in"
    leave-from-class="opacity-100 scale-100 translate-y-0"
    leave-to-class="opacity-0 scale-95 translate-y-4">
    <div v-if="showAdded"

         class="fixed inset-0 z-50 flex items-center w-xl m-auto"
    >

      <Alert class="relative w-full h-48 shadow-xl shadow-primary">
        <Button
          class="left-20 right-20 absolute bottom-4 cursor-pointer"
          @click="showAdded=false">
          Continue
        </Button>
        <AlertTitle class="text-xl">Question saved!</AlertTitle>
        <AlertDescription class="justify-center">
          Your question has been saved, click continue to add another questions.
        </AlertDescription>
      </Alert>
    </div>
  </Transition>
  <Transition
    appear
    enter-active-class="transition duration-500 ease-out"
    enter-from-class="opacity-0 scale-95 translate-y-4"
    enter-to-class="opacity-100 scale-100 translate-y-0"
    leave-active-class="transition duration-300 ease-in"
    leave-from-class="opacity-100 scale-100 translate-y-0"
    leave-to-class="opacity-0 scale-95 translate-y-4">
    <div v-if="showAdded"

         class="fixed inset-0 z-50 flex items-center w-xl m-auto"
    >

      <Alert class="relative w-full h-48 shadow-xl shadow-primary">
        <Button
          class="left-20 right-20 absolute bottom-4 cursor-pointer"
          @click="showAdded=false">
          Continue
        </Button>
        <AlertTitle class="text-xl">Question saved!</AlertTitle>
        <AlertDescription class="justify-center">
          Your question has been saved, click continue to add another questions.
        </AlertDescription>
      </Alert>
    </div>
  </Transition>
  <Transition
    appear
    enter-active-class="transition duration-500 ease-out"
    enter-from-class="opacity-0 scale-95 translate-y-4"
    enter-to-class="opacity-100 scale-100 translate-y-0"
    leave-active-class="transition duration-300 ease-in"
    leave-from-class="opacity-100 scale-100 translate-y-0"
    leave-to-class="opacity-0 scale-95 translate-y-4">
    <div v-if="showNotAllAdded"

         class="fixed inset-0 z-50 flex items-center w-xl m-auto"
    >

      <Alert class="relative w-full h-48 shadow-xl shadow-primary">
        <Button
          class="left-20 right-20 absolute bottom-4 cursor-pointer"
          @click="showNotAllAdded=false">
          Continue
        </Button>
        <AlertTitle class="text-xl">Blank questions!</AlertTitle>
        <AlertDescription class="justify-center">
          You have to fill all questions before saving a quiz!
        </AlertDescription>
      </Alert>
    </div>
  </Transition>
</template>
