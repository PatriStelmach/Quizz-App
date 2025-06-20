<script setup lang="ts">
import { computed } from 'vue'
import { useQuery } from '@vue/apollo-composable'
import { AllQuizzesDocument, type AllQuizzesQuery } from '@/generated/graphql.ts'

const { result, loading, error } = useQuery<AllQuizzesQuery>(AllQuizzesDocument)
const quizzes = computed(() => result.value?.allQuizzes ?? [])
</script>

<template>
  <div class="overflow-x-auto w-full">
    <table class="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
      <thead class="bg-gray-50 dark:bg-gray-800">
      <tr>
        <th class="px-6 py-3 text-left text-xs font-medium uppercase text-gray-500 dark:text-gray-400">Title</th>
        <th class="px-6 py-3 text-left text-xs font-medium uppercase text-gray-500 dark:text-gray-400">Category</th>
        <th class="px-6 py-3 text-left text-xs font-medium uppercase text-gray-500 dark:text-gray-400">Difficulty</th>
        <th class="px-6 py-3 text-right text-xs font-medium uppercase text-gray-500 dark:text-gray-400">Max Points</th>
        <th class="px-6 py-3 text-right text-xs font-medium uppercase text-gray-500 dark:text-gray-400">Time Limit</th>
      </tr>
      </thead>
      <tbody v-if="!loading && !error" class="bg-white dark:bg-gray-900 divide-y divide-gray-200 dark:divide-gray-700">
      <tr v-for="quiz in quizzes" :key="quiz.id">
        <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-gray-100">{{ quiz.title }}</td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700 dark:text-gray-300">{{ quiz.category }}</td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700 dark:text-gray-300">{{ quiz.diff }}</td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700 dark:text-gray-300 text-right">{{ quiz.maxPoints }}</td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700 dark:text-gray-300 text-right">
          {{ quiz.timeLimit }}
        </td>
      </tr>
      </tbody>
    </table>

    <div v-if="loading" class="py-4 text-center">
      <p class="text-gray-500 dark:text-gray-400">Loading quizzes...</p>
    </div>
    <div v-if="error" class="py-4 text-center text-red-500">
      <p>Error loading quizzes.</p>
    </div>
  </div>
</template>


