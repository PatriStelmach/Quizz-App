<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { Input } from '@/components/ui/input'
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card'

// Przykładowe dane — w prawdziwej aplikacji pobierz z API
type SolvedQuiz = {
  id: number
  title: string
  date: string
}

const allQuizzes = ref<SolvedQuiz[]>([])
const searchQuery = ref('')

// Filtrowanie quizów na podstawie zapytania
const filteredQuizzes = computed(() =>
  allQuizzes.value.filter(quiz =>
    quiz.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
)

const fetchSolvedQuizzes = async () => {
  // W prawdziwej aplikacji pobierz dane z API
  allQuizzes.value = [
    { id: 1, title: 'JavaScript Basics', date: '2024-04-01' },
    { id: 2, title: 'Vue Fundamentals', date: '2024-04-05' },
    { id: 3, title: 'TypeScript Intro', date: '2024-04-10' }
  ]
}

onMounted(fetchSolvedQuizzes)
</script>

<template>
  <div class="max-w-xl mx-auto mt-10 space-y-4">
    <Input
      v-model="searchQuery"
      placeholder="Szukaj quizu..."
      class="w-full"
    />

    <Card v-if="filteredQuizzes.length">
      <CardHeader>
        <CardTitle class="text-lg">Wyniki wyszukiwania</CardTitle>
      </CardHeader>
      <CardContent class="space-y-2">
        <div
          v-for="quiz in filteredQuizzes"
          :key="quiz.id"
          class="p-3 border rounded-lg hover:bg-muted transition-colors"
        >
          <div class="font-medium">{{ quiz.title }}</div>
          <div class="text-sm text-muted-foreground">Rozwiązano: {{ quiz.date }}</div>
        </div>
      </CardContent>
    </Card>

    <div v-else class="text-center text-muted-foreground">
      Brak pasujących quizów.
    </div>
  </div>
</template>

<style scoped></style>
