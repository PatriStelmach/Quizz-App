<script setup lang="ts">
import { onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import useAuthStore from '@/store/useAuthStore.ts'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'


const authStore = useAuthStore();
const route = useRoute()
const router = useRouter()

onMounted(async () =>
{
  const token = route.query.token as string;
  if (token)
  {
    try {
      await authStore.googleLogin(token);
      await router.replace({ path: '/dashboard' });
      console.log(token);
    } catch (err) {
      console.error('Google login failed:', err);
    }
  }
});


</script>

<template>
  <Card class="min-h-[700px] shadow-2xl border border-secondary shadow-primary
   flex flex-col mx-auto rounded-xl items-center mt-40 justify-center p-8 space-y-12">

    <h1 class="text-6xl font-extrabold text-primary drop-shadow-[0_4px_3px_rgba(59,130,246,0.5)]">
      Welcome to Quizzuś
    </h1>

    <Card class="max-w-3xl w-full">
      <CardHeader>
        <CardTitle class="text-4xl drop-shadow-[0_4px_3px_rgba(59,130,246,0.5)] text-primary text-center">
          How to Use the App</CardTitle>
      </CardHeader>
      <CardContent class="space-y-4">
        <ol class="list-decimal list-inside space-y-2 text-lg">
          <li>Log in using Google or your account credentials.</li>
          <li>Click on a quiz to start a new room or join an existing one.</li>
          <li>Answer questions within the time limit to earn points.</li>
          <li>Enjoy and compete with friends!</li>
          <li>Track your score on the live scoreboard.</li>
          <li>Browse solved quizzes on the dashboard.</li>
        </ol>
      <div class="flex justify-between">
        <Button class="mt-6" @click="router.push('/dashboard')">
          Go to Dashboard
        </Button>

        <Button class="mt-6" @click="router.push('/all-quizes')">
          Play some Quizzuś 😎
        </Button>
      </div>
      </CardContent>
    </Card>
  </Card>
</template>

