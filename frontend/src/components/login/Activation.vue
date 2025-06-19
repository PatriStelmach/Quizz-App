<script setup lang="ts">

import { defineComponent, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useForm } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'
import * as z from 'zod'
import AuthService from '@/services/auth.service.ts'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
import { FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage} from '@/components/ui/form'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import useAuthStore from '@/store/useAuthStore.ts'

const authStore = useAuthStore()
const router = useRouter()
const isShowing = ref(false)

onMounted(() =>
{
  setTimeout(async () =>
  {
    isShowing.value = true
  }, 60)
})

const formSchema = toTypedSchema(z.object({
  token: z.string().min(6, "Token must be 6 numbers long").max(6, "Token must be 6 numbers long"),
}))

const form = useForm({
  validationSchema: formSchema,
})

const onSubmit = form.handleSubmit((values) =>
{
  setTimeout( () =>
  {
    isShowing.value = false;
  }, 50);
  authStore.activate(values.token)
  setTimeout( () =>
  {
    router.push('/home');
  }, 300);

})

const exitAlert = () =>
{
  setTimeout( () =>
  {
    isShowing.value = false;
  }, 50);
  setTimeout( () =>
  {
    router.push('/home');
  }, 300);
}


</script>

<template>
  <Transition
    appear
    enter-active-class="transition duration-500 ease-out"
    enter-from-class="opacity-0 scale-95 translate-y-4"
    enter-to-class="opacity-100 scale-100 translate-y-0"
    leave-active-class="transition duration-200 ease-in"
    leave-from-class="opacity-100 scale-100 translate-y-0"
    leave-to-class="opacity-0 scale-95 translate-y-4">
    <div v-if="isShowing" class="fixed inset-0 z-50 flex items-center justify-center">
      <Alert class="relative w-full max-w-md h-72 shadow-xl shadow-primary flex flex-col">
        <Button
          class="absolute top-2 right-2 cursor-pointer"
          @click="exitAlert">
          X
        </Button>
        <AlertTitle class="text-xl mt-4">Confirm your email</AlertTitle>
        <AlertDescription class="text-center mb-2 mt-2">
          Paste your activation code
        </AlertDescription>
        <form @submit="onSubmit">
          <FormField v-slot="{ componentField }" name="token">
            <FormItem>
              <FormControl>
                <Input class="mb-2" type="text" placeholder="verification token " v-bind="componentField" />
              </FormControl>
              <FormMessage />
            </FormItem>
          </FormField>
          <Button
            class="cursor-pointer"
            type="submit">
            Submit
          </Button>
        </form>
      </Alert>
    </div>
  </Transition>
</template>



