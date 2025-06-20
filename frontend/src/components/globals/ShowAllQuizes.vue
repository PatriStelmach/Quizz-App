<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useQuery } from '@vue/apollo-composable'
import { AllQuizzesDocument, type AllQuizzesQuery } from '@/generated/graphql.ts'
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table'
import { Progress } from '@/components/ui/progress'

const { result, loading, error } = useQuery<AllQuizzesQuery>(AllQuizzesDocument)
const quizzes = computed(() => result.value?.allQuizzes ?? [])

// Progress state
const progress = ref(0)

onMounted(() =>
{
  loading.value = true
  progress.value = 20

  setTimeout(() => {
    progress.value = 500
    loading.value = false
  }, 500)

})

//trudnosc, popularnosc, oceny, zapisywani
</script>

<template>
  <div class="mx-auto overflow-x-auto w-6xl">
    <TableCaption class="w-6xl text-center text-4xl text-color-primary border-b pb-10 mb-4 border-primary">
      Available Quizzusie
    </TableCaption>
    <Table>
      <TableHeader>
        <TableRow class="hover:bg-accent-500 cursor-default">
          <TableHead>Title</TableHead>
          <TableHead>Category</TableHead>
          <TableHead>Difficulty</TableHead>
          <TableHead>Max Points</TableHead>
          <TableHead>Time Limit</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody v-if="!loading && !error">
        <TableRow v-for="quiz in quizzes" :key="quiz.id">
          <TableCell>{{ quiz.title }}</TableCell>
          <TableCell>{{ quiz.category }}</TableCell>
          <TableCell>{{ quiz.diff }}</TableCell>
          <TableCell>{{ quiz.maxPoints }}</TableCell>
          <TableCell>{{ quiz.timeLimit }}</TableCell>
        </TableRow>
      </TableBody>
    </Table>

    <div v-if="loading" class="py-4 text-center">
      <!-- Shadcn Progress Bar -->
      <Progress v-model="progress" class="w-3/5 mx-auto mb-2" />
      <p class="text-gray-500 dark:text-gray-400">Loading quizzes...</p>
    </div>

    <div v-if="error" class="py-4 text-center text-red-500">
      <p>Error loading quizzes.</p>
    </div>
  </div>
</template>
