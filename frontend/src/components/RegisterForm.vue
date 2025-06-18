<script setup lang="ts">
import {useForm } from 'vee-validate'

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
import authStore from '@/store/auth.store.ts'
import type { UserRegister } from '@/types/user.register.ts'
import { useRouter } from 'vue-router'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'


const router = useRouter()
const isAfterSubmit = ref(false)
const isFormShowing = ref(false)
const isFormHiding = ref(false)
const isAlertShowing = ref(false)
const isAlertHiding = ref(false)
const useAuthStore = authStore()

onMounted(() =>
{
  setTimeout(() =>
  {
    isFormShowing.value = true;
  }, 60)
});


const form  = useForm<UserRegister & { passwordConfirm: string }>({
  validationSchema: yup.object({
    username: yup.string()
      .required('Username is required')
      .min(6, 'Username must be at least 6 characters')
      .max(50, 'Username must be at most 50 characters'),
    email: yup.string().required('Email is required').email('Invalid email'),
    password: yup.string()
      .required('Password is required')
      .min(8, 'Password must be at least 6 characters'), passwordConfirm: yup
      .string()
      .required('Password do not match!')
      .min(8, 'Password must be at least 6 characters')
      .oneOf([yup.ref('password')] , 'Passwords must match'),
  })
})

const onSubmit = form.handleSubmit(async (values) =>
{
  //eslint-disable-next-line @typescript-eslint/no-unused-vars
  const { passwordConfirm, ...registerData } = values;
  await useAuthStore.register(registerData)
  console.log('Form submitted with values: ', values)

  isAlertShowing.value = true;
  isAfterSubmit.value = true;
})

const exitAlert = () =>
{

  setTimeout( () =>
  {
    isAfterSubmit.value = false;
  }, 200);

  isAlertHiding.value = true;

  setTimeout(() =>
  {
    router.push('/login');
  }, 200);
}

</script>

<template>
  <Teleport to="main">
  <form
    class="p-10 fixed top-1/5 left-1/2 w-100 mt-20 grid justify-center rounded-md transform -translate-x-1/2 -translate-y-1/2 border border-gray-300 shadow-md transition-all duration-500 ease-in-out"
    :class="{
      'opacity-0 scale-95 translate-y-4': isFormHiding || !isFormShowing,
      'opacity-100 scale-100 translate-y-0': !isFormHiding && isFormShowing,
      'border-none blur': isAfterSubmit
    }"
    @submit.prevent="onSubmit"
  >
    <FormField v-slot="{ componentField }" name="email">
      <FormItem class="mb-5 w-2xs ">
        <FormLabel>E-mail</FormLabel>
        <FormControl>
          <Input type="text" placeholder="email@example.com" v-bind="componentField" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>



    <FormField v-slot="{ componentField }" name="username">
      <FormItem  class="mb-5 w-2xs ">
        <FormLabel>Username</FormLabel>
        <FormControl>
          <Input type="text" placeholder="username" v-bind="componentField" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="password">
      <FormItem  class="mb-5 w-2xs ">
        <FormLabel>Password</FormLabel>
        <FormControl>
          <Input type="password" autocomplete="new-password" placeholder="password" v-bind="componentField" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="passwordConfirm">
      <FormItem  class="mb-5 w-2xs ">
        <FormLabel>Confirm password</FormLabel>
        <FormControl>
          <Input type="password" autocomplete="new-password" placeholder="confirm password" v-bind="componentField" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>



    <Button class="cursor-pointer" type="submit">
      Submit
    </Button>
  </form>

  <Transition
    appear
    enter-active-class="transition duration-500 ease-out"
    enter-from-class="opacity-0 scale-95 translate-y-4"
    enter-to-class="opacity-100 scale-100 translate-y-0"
    leave-active-class="transition duration-300 ease-in"
    leave-from-class="opacity-100 scale-100 translate-y-0"
    leave-to-class="opacity-0 scale-95 translate-y-4">
  <div v-if="isAfterSubmit"

    class="fixed inset-0 z-50 flex items-center w-xl m-auto"
    >

    <Alert class="relative w-full h-48 shadow-xl shadow-primary">
      <Button
        class="absolute top-2 right-2 cursor-pointer"
        @click="exitAlert">
        X
      </Button>
      <AlertTitle class="text-xl">Confirm your email</AlertTitle>
      <AlertDescription class="justify-center">
        An confirmation e-mail has been sent to your adress. Confirm your e-mail and log in.
      </AlertDescription>
    </Alert>
  </div>
  </Transition>
</Teleport>
</template>
