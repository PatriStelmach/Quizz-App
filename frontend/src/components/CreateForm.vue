<script setup lang="ts">
import { useForm } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'
import * as z from 'zod'

import { Button } from '@/components/ui/button'
import {
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form'
import {
  NumberField,
  NumberFieldContent,
  NumberFieldDecrement,
  NumberFieldIncrement,
  NumberFieldInput,
} from '@/components/ui/number-field'

import { cn } from '@/lib/utils'
import { Input } from '@/components/ui/input'
import { Check, ChevronsUpDown, Search } from 'lucide-vue-next'
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

const router = useRouter()
const isHiding = ref(false)
const isShowing = ref(false)
const isLoading = ref(false)

const categories = Object.entries(Category)
const difficulties = Object.entries(Diff)
const categoryKeys = Object.keys(Category) as [keyof typeof Category]
const difficultyKeys = Object.keys(Diff) as [keyof typeof Diff]

console.log(categories)

onMounted(() =>
{
  setTimeout(() =>
  {
    isShowing.value = true
  }, 60)
})


const formSchema =
  toTypedSchema(z.object({
    title: z.string().min(6, 'Title must have at least 6 characters').max(50),
    description: z.string().min(15, 'Description must have at least 15 characters').max(500),
    category: z.enum(categoryKeys,
      {
        required_error: "Choose category"
      }),
    difficulty: z.enum(difficultyKeys,
      {
        required_error: "Choose difficulty level"
      }),
    timeLimit: z.coerce.number().min(1, 'Time limit must be between 1 and 60 minutes').max(60),
  }))

const form = useForm({
  validationSchema: formSchema,
})

const onSubmit = form.handleSubmit(async (values) =>
{
  try
  {
    isLoading.value = true
    console.log('Form submitted with values: ', values)

    isHiding.value = true

    setTimeout(async () =>
    {
      await router.push('/home')
    }, 200)
  } catch (error)
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
      }
      else
      {
        errorMessage.value = 'An unexpected error occurred'
      }
    }, 200)
  }
})
</script>

<template>

  <form
    @submit="onSubmit"
    class="p-10 relative mx-auto max-w-2xl grid justify-center rounded-md border border-gray-300 shadow-md transition-all duration-500 ease-in-out"
    :class="{
      'opacity-0 scale-95 translate-y-4': isHiding || !isShowing,
      'opacity-100 scale-100 translate-y-0': !isHiding && isShowing,
    }"
  >
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
      <FormItem class="mb-5 ">
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
                  class="pl-9 focus-visible:ring-0  border-b rounded-none h-10"
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
                  :value="key"
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
                <RadioGroupItem class="cursor-pointer" :value="key" />
              </FormControl>
              <FormLabel class="mb-0 cursor-pointer">{{ label }}</FormLabel>
            </FormItem>
          </RadioGroup>
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="timeLimit">
      <FormItem class="mb-5">
        <FormControl>
          <NumberField
            class="mb-4 w-48"
            v-bind="componentField"
            id="number"
            :default-value="2"
            :format-options="{
      signDisplay: 'exceptZero',
      minimumFractionDigits: 1,
    }"
          >
            <Label for="number">Time limit (in minutes)</Label>
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



    <Button type="submit"> Submit </Button>
  </form>
</template>
