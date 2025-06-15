<script setup lang="ts">
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card'
import { ref, computed, onMounted } from 'vue'
import { Input } from '@/components/ui/input'


type SolvedQuiz =
  {
  id: number
  title: string
  date: string
}

const allQuizzes = ref<SolvedQuiz[]>([])
const searchQuery = ref('')

const filteredQuizzes = computed(() =>
  allQuizzes.value.filter(quiz =>
    quiz.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
)

const fetchSolvedQuizzes = async () =>
{
  allQuizzes.value = [
    { id: 1, title: 'JavaScript Basics', date: '2024-04-01' },
    { id: 2, title: 'Vue Fundamentals', date: '2024-04-05' },
    { id: 3, title: 'TypeScript Intro', date: '2024-04-10' }
  ]
}
const solvedQuizzes = ref<number>(0)

const fetchSolvedQuizzesAmount = async () =>
{
  solvedQuizzes.value = 12
}
onMounted(fetchSolvedQuizzes)
onMounted(fetchSolvedQuizzesAmount)

</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-background px-4">
    <div class="w-full max-w-xl space-y-8">

      <!-- Statystyka -->
      <Card class="w-full shadow-md border border-muted rounded-2xl p-4">
        <CardHeader>
          <CardTitle class="text-xl font-semibold text-center">Rozwiązane quizy</CardTitle>
        </CardHeader>
        <CardContent>
          <div class="text-5xl font-bold text-primary text-center">{{ solvedQuizzes }}</div>
          <p class="text-sm text-muted-foreground mt-2 text-center">
            Łączna liczba quizów rozwiązanych przez Ciebie
          </p>
        </CardContent>
      </Card>

      <!-- Wyszukiwarka -->
      <div class="w-full space-y-4">
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

    </div>
  </div>
</template>


