<script setup lang="ts">
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card'
import { ref, computed, onMounted } from 'vue'
import { Input } from '@/components/ui/input'
import { useQuery } from '@vue/apollo-composable'
import { AllQuizzesDocument, type AllQuizzesQuery } from '@/generated/graphql.ts'
import { DonutChart } from '@/components/ui/chart-donut'

const { result, loading, error } = useQuery<AllQuizzesQuery>(AllQuizzesDocument)
const quizzes = computed(() => result.value?.allQuizzes ?? [])

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
    { id: 1, title: 'JavaScript Basics', score: 10 },
    { id: 2, title: 'Vue Fundamentals', score: 77 },
    { id: 3, title: 'TypeScript Intro', score: 66 }
  ]
}



const data = [
  {
    name: 'Jan',
    total: Math.floor(Math.random() * 2000) + 500,
    predicted: Math.floor(Math.random() * 2000) + 500,
  },
  {
    name: 'Feb',
    total: Math.floor(Math.random() * 2000) + 500,
    predicted: Math.floor(Math.random() * 2000) + 500,
  },
  {
    name: 'Mar',
    total: Math.floor(Math.random() * 2000) + 500,
    predicted: Math.floor(Math.random() * 2000) + 500,
  },
  {
    name: 'Apr',
    total: Math.floor(Math.random() * 2000) + 500,
    predicted: Math.floor(Math.random() * 2000) + 500,
  },
  {
    name: 'May',
    total: Math.floor(Math.random() * 2000) + 500,
    predicted: Math.floor(Math.random() * 2000) + 500,
  },
  {
    name: 'Jun',
    total: Math.floor(Math.random() * 2000) + 500,
    predicted: Math.floor(Math.random() * 2000) + 500,
  },
]

function valueFormatter(tick: number | Date) {
  return typeof tick === 'number'
    ? `$ ${new Intl.NumberFormat('us').format(tick).toString()}`
    : ''
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
  <div class=" mx-auto w-4xl flex items-center justify-center bg-background px-4">
    <div class="w-full max-w-xl space-y-8">
      <div>
        <Card class="w-full shadow-md border border-muted rounded-2xl p-4">
          <CardHeader>
            <CardTitle class="text-xl font-semibold text-center">Solved quizzusie</CardTitle>
          </CardHeader>
          <CardContent>
            <div class="text-5xl font-bold text-primary text-center mb-10">{{ solvedQuizzes }}</div>

          </CardContent>
        </Card>

        <DonutChart
          index="name"
          :category="'total'"
          :data="data"
          :value-formatter="valueFormatter"
        />
      </div>


      <div class="w-full space-y-4">
        <Input
          v-model="searchQuery"
          placeholder="Search your solved quizes."
          class="w-full"
        />

        <Card v-if="filteredQuizzes.length">
          <CardHeader>
            <CardTitle class="text-lg">Search results</CardTitle>
          </CardHeader>
          <CardContent class="space-y-2">
            <div
              v-for="quiz in filteredQuizzes"
              :key="quiz.id"
              class="p-3 border rounded-lg hover:bg-muted transition-colors"
            >
              <div class="font-medium">{{ quiz.title }}</div>
              <div class="text-sm text-muted-foreground">Score: {{ quiz.score }}</div>
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


