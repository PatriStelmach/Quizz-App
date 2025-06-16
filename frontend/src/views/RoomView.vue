<script setup lang="ts">
import { useRoute } from 'vue-router'
import QuizRoom from '@/components/QuizRoom.vue'


</script>

<template>
  <QuizRoom :roomId="roomId" />
</template>
