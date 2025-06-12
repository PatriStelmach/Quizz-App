<script setup lang="ts">
import { toTypedSchema } from '@vee-validate/zod'
import * as z from 'zod'
import { useForm } from 'vee-validate'
import {
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage
} from '@/components/ui/form'
import { Button } from "@/components/ui/button";
import { useRouter } from "vue-router";
import authStore from '@/store/auth.store.ts'

const router = useRouter()
const useAuthStore = authStore()
const formSchema = toTypedSchema(z.object({
  username: z.string().min(2, "Username must be at least 2 characters").max(50),
  password: z.string().min(6, "Password must be at least 6 characters")
}))

const form = useForm({
  validationSchema: formSchema,
})

const onSubmit = form.handleSubmit(async (values) => {
  await useAuthStore.login(values)
  console.log('Form submitted with values: ', values)
  await router.push('/home')
})
</script>

<template>
  <form class="p-10 w-92  grid justify-center rounded-md  m-auto" @submit="onSubmit">
    <FormField v-slot="{ componentField }" name="username">
      <FormItem class="w-2xs mb-5">
        <FormDescription class="text-2xl text-center mb-5">Login Form</FormDescription>
        <FormLabel class="w-2xs mb-5">Username</FormLabel>
        <FormControl>
          <input
            class="w-2xs h-10 mb-5 pl-2 bg-white text-black rounded-md"
            placeholder="username"
            v-bind="componentField"
          />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="password">
      <FormItem class="w-2xs mb-5">
        <FormLabel class="w-2xs mb-5">Password</FormLabel>
        <FormControl>
          <input
            type="password"
            class="w-2xs h-10 mb-5 pl-2 bg-white text-black rounded-md"
            placeholder="password"
            v-bind="componentField"
          />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <Button class="cursor-pointer w-2xs" type="submit">
      Sign in
    </Button>
  </form>
</template>
