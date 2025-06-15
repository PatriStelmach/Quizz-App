<template>
  <h1 class = "text-center text-3xl text-pink-500">
    HOOOOOOOOOOOOOOMEEEEEEEE
  </h1>
      <button @click="goToQuiz">Go to Quiz Room[TEST]</button>

</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

function goToQuiz() {
  router.push({ name: 'room-view', params: { id: '123' } })
}


</script>
