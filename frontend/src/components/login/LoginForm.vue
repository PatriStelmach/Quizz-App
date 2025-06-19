<script setup lang="ts">
import { toTypedSchema } from '@vee-validate/zod'
import * as z from 'zod'
import { useForm } from 'vee-validate'
import { ref } from 'vue'
import {
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage
} from '@/components/ui/form'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
import { Button } from "@/components/ui/button";
import { useRouter } from "vue-router";
import useAuthStore from '@/store/useAuthStore.ts'
import { AlertCircle } from 'lucide-vue-next'
import { onMounted } from 'vue'
import { Input } from '@/components/ui/input'

const router = useRouter()
const authStore = useAuthStore()
const isHiding = ref(false)
const isLoading = ref(false)
const isShowing = ref(false)
const isRegistering = ref(false)
const isGoogleing = ref(false)
const errorMessage = ref('')

onMounted(() =>
{
  setTimeout(() => {
    isShowing.value = true;
  }, 60)
});

const formSchema = toTypedSchema(z.object({
  username: z.string().min(2, "Username must be at least 2 characters").max(50),
  password: z.string().min(8, "Password must be at least 8 characters")
}))

const form = useForm({
  validationSchema: formSchema,
})

const onSubmit = form.handleSubmit(async (values) =>
{
  try
  {
    isLoading.value = true
    await authStore.login(values)
    console.log('Form submitted with values: ', values)

    isHiding.value = true

    setTimeout(async () =>
    {
      await router.push('/home')
    }, 100)

  }
  catch (error)
  {
    setTimeout(async () =>
    {


    isLoading.value = false
    if (error instanceof Error)
    {
      errorMessage.value = error.message
      if(error.message.includes('420'))
      {
        errorMessage.value = 'Invalid username or password'
      }
    }
    else {
      errorMessage.value = 'An unexpected error occurred'
    }
    }, 100)
  }
})
const onGoogleLogin = async () =>
{
  isGoogleing.value = true;
  setTimeout(async () =>
  {
   window.location.href = 'http://localhost:10000/oauth2/authorization/google';
    isGoogleing.value = false;
  }, 100);

}

const onRegister = async () =>
{
  isRegistering.value = true;
  isHiding.value = true;
  setTimeout(async () =>
  {
    await router.push('/register');
    isRegistering.value = false;
  }, 100);
};
</script>

<template>
  <Teleport to="main">
  <form
    class="p-10 fixed top-1/5 left-1/2 w-100 mt-20 grid justify-center rounded-md transform -translate-x-1/2 -translate-y-1/2 border border-secondary shadow-xl shadow-secondary transition-all duration-500 ease-in-out"
    :class="{
      'opacity-0 scale-95 translate-y-4': isHiding || !isShowing,
      'opacity-100 scale-100 translate-y-0': !isHiding && isShowing

    }"
    @submit.prevent="onSubmit"
  >
    <Alert v-if="errorMessage" variant="destructive">
      <AlertCircle class="w-4 h-4"/>
      <AlertTitle class="">
        Error!
      </AlertTitle>
      <AlertDescription >
        {{ errorMessage }}
      </AlertDescription>
    </Alert>
    <FormField v-slot="{ componentField }" name="username">
      <FormItem class="w-2xs mb-5">
        <FormDescription class="text-3xl mt-2 text-center mb-5 text-accent-foreground py-2 ">Sign in</FormDescription>
        <FormLabel class="w-2xs mb-2">Username</FormLabel>
        <FormControl>
          <Input
            placeholder="username"
            v-bind="componentField"
            :disabled="isLoading"
          />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="password">
      <FormItem class="w-2xs mb-5">
        <FormLabel class="w-2xs mb-2">Password</FormLabel>
        <FormControl>
          <Input
            type="password"
            placeholder="password"
            v-bind="componentField"
            :disabled="isLoading"
          />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <Button
      class="cursor-pointer mb-5 w-2xs transition duration-300 ease-in-out hover:bg-accent hover:text-primary-foreground disabled:opacity-50 disabled:cursor-not-allowed"
      type="submit"
      :disabled="isLoading"
    >
      <span v-if="!isLoading">Sign in</span>
      <span v-else class="flex items-center justify-center">
        <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-current" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
          <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
          <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
        </svg>
        Signing in...
      </span>
    </Button>



    <Button
      class="cursor-pointer mb-5 w-2xs transition duration-300 ease-in-out hover:bg-accent hover:text-primary-foreground disabled:opacity-50 disabled:cursor-not-allowed"
      :disabled="isRegistering"
       @click="onRegister"
    >
      <span v-if="!isRegistering">Sign up</span>
      <span v-else class="flex items-center justify-center">
        <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-current" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
          <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
          <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
        </svg>
        Loading...
      </span>
    </Button>

    <Button variant="ghost"
            :disabled="isGoogleing"
            @click ="onGoogleLogin"
            class="cursor-pointer w-2xs transition duration-300 ease-in-out hover:bg-accent hover:text-primary-foreground disabled:opacity-50 disabled:cursor-not-allowed">
      <img src="../../images/google-oauth.png"
           alt="profile icon"
           class="w-7 h-7"/>
      <span v-if="!isGoogleing">Login with Google</span>
    </Button>
  </form>
  </Teleport>
</template>
