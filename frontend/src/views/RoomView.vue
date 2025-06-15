<script setup lang="ts">
import { useRoute } from 'vue-router'
import QuizRoom from '@/components/QuizRoom.vue'

const route = useRoute()
const roomId = route.params.id as string
</script>

<template>
  <QuizRoom :roomId="roomId" />
</template>
