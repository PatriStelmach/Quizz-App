<script setup lang="ts">
import { useField, useForm } from 'vee-validate'
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
import { Input } from '@/components/ui/input'
import { onMounted, ref } from 'vue'
import * as yup from 'yup'

const isShowing = ref(false)
const isHiding = ref(false)

onMounted(() =>
{
  setTimeout(() => {
    isShowing.value = true;
  }, 60)
});

// const formSchema = toTypedSchema(z.object({
//   username: z.string().min(2).max(50),
//   password: z.string().min(6).max(50),
//   email: z.string().min(1, { message: 'Email is required' }).email({ message: 'Invalid email' })
//
// }))

const { value: emailValue, errorMessage: emailErrorMessage } = useField('email');

const { handleSubmit } = useForm({
  validationSchema: yup.object({
    username: yup.string()
      .required('Username is required')
      .min(6, 'Username must be at least 6 characters')
      .max(50, 'Username must be at most 50 characters'),
    email: yup.string().required('Email is required').email('Invalid email'),
    password: yup.string()
      .required('Password is required')
      .min(6, 'Password must be at least 6 characters'), passwordConfirm: yup
      .string()
      .required('Password do not match!')
      .min(6, 'Password must be at least 6 characters')
      .oneOf([yup.ref('password')] , 'Passwords must match'),
    birthDate: yup.date().required('Birth date is required'),
  }),
})

const onSubmit = handleSubmit(values => {
  alert(JSON.stringify(values, null, 2));
});
</script>

<template>
  <form
    class="p-10 mt-20 w-92 grid justify-center rounded-md m-auto border border-gray-300 shadow-md transition-all duration-500 ease-in-out"
    :class="{
      'opacity-0 scale-95 translate-y-4': isHiding || !isShowing,
      'opacity-100 scale-100 translate-y-0': !isHiding && isShowing
    }"
    @submit="onSubmit"
  >
    <FormField v-slot="{ componentField }" name="email">
      <FormItem class="mb-5">
        <FormLabel>E-mail</FormLabel>
        <FormControl>
          <Input type="text" v-model="emailValue" placeholder="email@example.com" v-bind="componentField" />
          <span> {{ emailErrorMessage}}</span>
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="username">
      <FormItem  class="mb-5">
        <FormLabel>Username</FormLabel>
        <FormControl>
          <Input type="text" placeholder="username" v-bind="componentField" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="password">
      <FormItem  class="mb-5">
        <FormLabel>Password</FormLabel>
        <FormControl>
          <Input type="password" autocomplete="new-password" placeholder="password" v-bind="componentField" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="passwordConfirm">
      <FormItem  class="mb-5">
        <FormLabel>Confirm password</FormLabel>
        <FormControl>
          <Input type="password" autocomplete="new-password" placeholder="confirm password" v-bind="componentField" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="birth-date">
      <FormItem  class="mb-5">
        <FormLabel>Birth Date</FormLabel>
        <FormControl>
          <Input type="date" placeholder="DD-MM-YYYY" v-bind="componentField" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <Button class="cursor-pointer" type="submit">
      Submit
    </Button>
  </form>
</template>
