<script setup lang="ts">
import { useForm } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'
import * as z from 'zod'

import { Button } from '@/components/ui/button'
import { FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form'
import {
  NumberField,
  NumberFieldContent,
  NumberFieldDecrement,
  NumberFieldIncrement,
  NumberFieldInput,
} from '@/components/ui/number-field'

import { cn } from '@/lib/utils.ts'
import { Input } from '@/components/ui/input'
import { AlertCircle, Check, ChevronsUpDown, Search } from 'lucide-vue-next'
import { onMounted, ref } from 'vue'
import { Category, Diff } from '@/generated/graphql.ts'
import {
  Combobox,
  ComboboxAnchor,
  ComboboxEmpty,
  ComboboxGroup,
  ComboboxInput,
  ComboboxItem,
  ComboboxItemIndicator,
  ComboboxList,
  ComboboxTrigger,
} from '@/components/ui/combobox'
import { RadioGroup, RadioGroupItem } from '@/components/ui/radio-group'
import { Label } from '@/components/ui/label'
import { useRouter } from 'vue-router'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
import axios from 'axios'
import useAuthStore from '@/store/useAuthStore.ts'
import useQuestionStore from '@/store/useQuestionStore.ts'
import { QuestionDto } from '@/generated/graphql.ts'

const questionStore = useQuestionStore()
const authStore = useAuthStore()
const router = useRouter()
const isHiding = ref(false)
const isShowing = ref(false)
const isLoading = ref(false)
const errorMessage = ref('')

const categories = Object.entries(Category)
const difficulties = Object.entries(Diff)
const categoryValues = Object.values(Category) as [Category]
const difficultyValues = Object.values(Diff) as [Diff]

console.log(authStore.token)

onMounted(() => {
  setTimeout(() => {
    isShowing.value = true
  }, 60)
})

const formSchema = toTypedSchema(
  z.object({
    title: z.string().min(6, 'Title must have at least 6 characters').max(50),
    description: z.string().min(15, 'Description must have at least 15 characters').max(500),
    category: z.enum(categoryValues, {
      required_error: 'Choose category',
    }),
    difficulty: z.enum(difficultyValues, {
      required_error: 'Choose difficulty level',
    }),
    timeLimit: z.coerce
      .number({
        invalid_type_error: 'Time limit must be a number',
        required_error: 'Time limit is required',
      })
      .min(1, 'Time limit must be at least 1 minute'),
    questionsAmount: z.coerce
      .number({
        invalid_type_error: 'Questions amount must be a number',
        required_error: 'Questions amount is required',
      })
      .min(2, 'Quiz must have at least 2 questions')
      .max(50, 'Quiz must have at most 50 questions'),
  }),
)

const form = useForm({
  validationSchema: formSchema,
})

const onSubmit = form.handleSubmit(async (values) => {
  try {
    const points = () =>
    {
      switch (values.difficulty)
      {
        case Diff.Easy:
          return values.questionsAmount * 2
        case Diff.Medium:
          return values.questionsAmount * 3
        case Diff.Hard:
          return values.questionsAmount * 4
        case Diff.Expert:
          return values.questionsAmount * 5
      }
    }

    await axios
      .post(
        'http://localhost:10000/quiz/create',
        {
          title: values.title,
          description: values.description,
          category: values.category.toUpperCase(),
          diff: values.difficulty,
          timeLimit: values.timeLimit,
          maxPoints: points()
        },
        {
          headers: {
            Authorization: `Bearer ${authStore.token}`,
          },
        },
      )
      .then(async (result) =>
      {
        if (result.status === 201)
        {
          questionStore.setQuestions(values.questionsAmount, result.data)
          questionStore.setQuizId(result.data)
          setTimeout(async () =>
          {
            await router.push({
              name: 'create-questions-and-answers',
              params: { quizId: result.data },
            })
          }, 100)
        }
      })

    isLoading.value = true
    console.log('Form submitted with values: ', values)

    isHiding.value = true
  } catch (error) {
    setTimeout(async () => {
      isLoading.value = false
      if (error instanceof Error) {
        errorMessage.value = error.message
        if (error.message.includes('500')) {
          errorMessage.value = 'Invalid Quiz data'
        } else if (error.message.includes('401') || error.message.includes('403')) {
          errorMessage.value = 'You have to be loged in to create a quiz'
        }
      } else {
        errorMessage.value = 'An unexpected error occurred'
      }
    }, 200)
  }
})
</script>

<template>
  <form
    @submit="onSubmit"
    class="p-10 mb-30  bg-card text-card-foreground shadow-xl shadow-secondary relative mx-auto max-w-2xl grid justify-center rounded-md border border-primary transition-all duration-500 ease-in-out"
    :class="{
      'opacity-0 scale-95 translate-y-4': isHiding || !isShowing,
      'opacity-100 scale-100 translate-y-0': !isHiding && isShowing,
    }"
  >
    <Alert v-if="errorMessage" variant="destructive">
      <AlertCircle class="w-4 h-4" />
      <AlertTitle class=""> Error! </AlertTitle>
      <AlertDescription>
        {{ errorMessage }}
      </AlertDescription>
    </Alert>
    <h1 class="text-center mb-6 text-3xl border-b pb-2">Create a new quiz</h1>
    <FormField v-slot="{ componentField }" name="title">
      <FormItem class="mb-5">
        <FormLabel class="justify-center">Quiz Title</FormLabel>
        <FormControl>
          <Input type="text" placeholder="Title" v-bind="componentField" class="mb-4 w-120 h-12" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="description">
      <FormItem class="mb-5">
        <FormLabel class="justify-center">Quiz Description</FormLabel>
        <FormControl>
          <Input
            type="text"
            placeholder="description"
            v-bind="componentField"
            class="mb-4 w-120 h-12"
          />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="category">
      <FormItem class="mb-5">
        <FormLabel>Category</FormLabel>
        <FormControl>
          <Combobox class="w-120 h-12" v-bind="componentField" by="label">
            <ComboboxAnchor as-child>
              <ComboboxTrigger as-child>
                <Button variant="outline" class="justify-between cursor-pointer">
                  {{ componentField.modelValue ?? 'Select category' }}

                  <ChevronsUpDown class="ml-2 h-4 w-4 shrink-0 opacity-50" />
                </Button>
              </ComboboxTrigger>
            </ComboboxAnchor>

            <ComboboxList>
              <div class="relative w-full max-w-sm items-center">
                <ComboboxInput
                  class="pl-9 focus-visible:ring-0 border-b rounded-none h-10"
                  placeholder="Select category..."
                />
                <span class="absolute start-0 inset-y-0 flex items-center justify-center px-3">
                  <Search class="size-4 text-muted-foreground" />
                </span>
              </div>
              <ComboboxEmpty> No category found. </ComboboxEmpty>
              <ComboboxGroup>
                <ComboboxItem
                  class="cursor-pointer"
                  v-bind="componentField"
                  v-for="[key, label] in categories"
                  :key="key"
                  :value="label"
                >
                  {{ label }}

                  <ComboboxItemIndicator>
                    <Check :class="cn('ml-auto h-4 w-4')" />
                  </ComboboxItemIndicator>
                </ComboboxItem>
              </ComboboxGroup>
            </ComboboxList>
          </Combobox>
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="difficulty">
      <FormItem class="mb-5">
        <FormLabel>Difficulty Level</FormLabel>
        <FormControl>
          <RadioGroup class="mb-4 mt-2 flex flex-col space-y-1" v-bind="componentField">
            <FormItem
              v-for="[key, label] in difficulties"
              :key="key"
              class="flex items-center space-y-0 gap-x-3"
            >
              <FormControl>
                <RadioGroupItem class="cursor-pointer" :value="label" />
              </FormControl>
              <FormLabel class="mb-0 cursor-pointer">{{ label }}</FormLabel>
            </FormItem>
          </RadioGroup>
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <div class="justify-between flex">
      <FormField v-slot="{ componentField }" name="timeLimit">
        <FormItem class="mb-5">
          <FormControl>
            <NumberField
              class="mb-4 w-48"
              :value="componentField.modelValue"
              @update:modelValue="(val) => componentField.onChange(Number(val))"
              id="number"
              :default-value="2"
              :format-options="{
                signDisplay: 'exceptZero',
                minimumFractionDigits: 1,
              }"
            >
              <FormLabel for="number">Time limit (in minutes)</FormLabel>
              <NumberFieldContent>
                <NumberFieldDecrement />
                <NumberFieldInput />
                <NumberFieldIncrement />
              </NumberFieldContent>
            </NumberField>
          </FormControl>
          <FormMessage />
        </FormItem>
      </FormField>

      <FormField v-slot="{ componentField }" name="questionsAmount">
        <FormItem class="mb-5">
          <FormControl>
            <NumberField
              class="mb-4 w-48"
              :value="componentField.modelValue"
              @update:modelValue="(val) => componentField.onChange(Number(val))"
              id="number"
              :default-value="2"
              :format-options="{
                signDisplay: 'exceptZero',
                minimumFractionDigits: 2,
              }"
            >
              <FormLabel for="number">Questions amount</FormLabel>
              <NumberFieldContent>
                <NumberFieldDecrement />
                <NumberFieldInput />
                <NumberFieldIncrement />
              </NumberFieldContent>
            </NumberField>
          </FormControl>
          <FormMessage />
        </FormItem>
      </FormField>
    </div>

    <Button class="cursor-pointer mb-5 hover:bg-secondary" type="submit"> Submit </Button>
  </form>
</template>
