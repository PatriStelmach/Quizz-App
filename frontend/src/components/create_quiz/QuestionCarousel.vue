<script setup lang="ts">
import { watchOnce } from '@vueuse/core'
import { ref } from 'vue'
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
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import { Carousel, type CarouselApi, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from '@/components/ui/carousel'
import questionStore from '@/store/question.store.ts'
import { Button } from '@/components/ui/button'
import { QuestionTypes } from '@/composables/question.types.ts'
import { Textarea } from '@/components/ui/textarea'
import type { QuestionDto } from '@/types/question.dto.ts'
import { z } from 'zod'
import { useForm, useFieldArray } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'

const useQuestionTypes = QuestionTypes()
const useQuestionStore = questionStore()
const questionsAmount = useQuestionStore.questions.length
const emblaMainApi = ref<CarouselApi>()
const emblaThumbnailApi = ref<CarouselApi>()
const selectedIndex = ref(0)
const questionType = ref<string | null>(null)

const questions = useQuestionStore.questions

const AnswerSchema = z.object
({
  answerText: z.string().min(1, "Answer cannot be empty"),
  correct: z.boolean(),
})

const QuestionSchema = z.discriminatedUnion("questionType", [
  z.object
  ({
    questionType: z.literal("open"),
    question: z.string().min(10, "Question is required"),
    answers: z.array(AnswerSchema).min(1, "Answer required"),
  }),
  z.object
  ({
    questionType: z.literal("singleChoice"),
    question: z.string().min(10, "Question is required"),
    answers: z.array(AnswerSchema).min(4, "Four answers required"),
  }),
  z.object
  ({
    questionType: z.literal("multipleChoice"),
    question: z.string().min(10, "Question is required"),
    answers: z.array(AnswerSchema).min(4, "Four answers required"),
  }),
])

const { handleSubmit, errors, values } = useForm({
  validationSchema: toTypedSchema(QuestionSchema),
  initialValues: {
    question: '',
    questionType: QuestionType.SingleChoice,
    answers: [
      { answerText: '', correct: false },
      { answerText: '', correct: false },
    ],
  },
})

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

const addQuestion = (questionDto: QuestionDto) =>
{
  useQuestionStore.addQuestion(questionDto)
}

</script>

<template>
  <div class="relative mx-auto max-w-xl grid justify-center text-center">
    <Carousel
      class="relative w-full  my-10 max-w-md mx-auto"
      @init-api="(val) => emblaMainApi = val"
    >
      <CarouselContent>
        <CarouselItem v-for="(_, index) in questionsAmount" :key="index">
          <div class="p-1 ">
            <Card
              v-bind="questions[index]"
                class="w-full border-primary shadow-secondary shadow-md max-w-md mx-auto">
              <CardHeader>
                <CardTitle>Add new question</CardTitle>
                <CardDescription>Create questions for your quiz</CardDescription>
              </CardHeader>
              <CardContent>
                <form>
                  <div class="grid items-center w-full gap-4">
                    <div class="flex flex-col space-y-1.5">
                      <Label for="name">Question</Label>
                      <Input id="name" placeholder="What you will be asking" />
                    </div>
                    <div class="flex flex-col space-y-1.5">

                      <Label for="questionType">Question type</Label>
                      <Select v-model="questionType">
                        <SelectTrigger  id="questionType">
                          <SelectValue  placeholder="Select" />
                        </SelectTrigger>
                        <SelectContent  position="popper">
                          <SelectItem
                            v-for="type in useQuestionTypes"
                            :key="type.value"
                            :value="type.value"> {{ type.label }}</SelectItem>
                        </SelectContent>
                      </Select>
                      <div v-if="questionType === 'singleChoice' && questions[index]" class="mt-4 space-y-4">
                        <Label for="answers">Answers</Label>
                        <Input v-model="questions[index].answers[0].answerText"></Input>
                        <Input v-model="questions[index].answers[1].answerText"></Input>
                        <Input v-model="questions[index].answers[2].answerText"></Input>
                        <Input v-model="questions[index].answers[3].answerText"></Input>
                      </div>

                      <div v-if="questionType === 'multipleChoice' && questions[index]" class="mt-4 space-y-4">
                        <Label for="answers">Answers</Label>
                        <Input v-model="questions[index].answers[0].answerText"></Input>
                        <Input v-model="questions[index].answers[1].answerText"></Input>
                        <Input v-model="questions[index].answers[2].answerText"></Input>
                        <Input v-model="questions[index].answers[3].answerText"></Input>
                      </div>

                      <div v-if="questionType === 'open' && questions[index]" class="mt-4 space-y-4">
                        <Label for="answers">Answers</Label>
                        <Textarea v-model="questions[index].answers[0].answerText"></Textarea>
                      </div>

                    </div>
                  </div>
                </form>
              </CardContent>
              <CardFooter class="flex justify-between px-6 pb-6">
                <Button class="hover:bg-destructive" variant="outline">
                  Cancel
                </Button>
                <Button
                @click="questions"
                >Save question</Button>
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
    <button class="cursor-pointer mt-5 text-white transition duration-300 ease-in-out
    bg-gradient-to-r from-sky-500 via-sky-600 to-sky-700 hover:brightness-110 hover:scale-105 focus:shadow-md shadow-primary
     focus:outline-none focus:ring-cyan-300 dark:focus:ring-cyan-800 font-medium rounded-lg text-sm mr-0 px-5 py-2.5 text-center me-2 mb-2">
      Save all questions and create your quizuś!
    </button>
  </div>
</template>
