<script setup lang="ts">
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card'
import { ref, computed, onMounted } from 'vue'
import { Input } from '@/components/ui/input'
import { Chart, Grid, Bar, Tooltip } from 'vue3-charts'
import axios from 'axios'
import useAuthStore from '@/store/useAuthStore.ts'
import type { SolvedDto } from '@/types/solved.quiz.dto.ts'
import type { DiffAverage } from '@/types/diff.average.ts'
import { Diff } from '@/generated/graphql.ts'

const authStore = useAuthStore()
const allQuizzes = ref<(SolvedDto & { percentage: number })[]>([])
const solvedQuizzes = ref<number>(0)

const averageByDiff = computed(() => {
  const groups: Record<Diff, { totalScore: number; totalMax: number }> = {
    [Diff.Easy]: { totalScore: 0, totalMax: 0 },
    [Diff.Medium]: { totalScore: 0, totalMax: 0 },
    [Diff.Hard]: { totalScore: 0, totalMax: 0 },
    [Diff.Expert]: { totalScore: 0, totalMax: 0 },
  }

  for (const quiz of allQuizzes.value) {
    const key = quiz.diff as Diff
    groups[key].totalScore += quiz.score
    groups[key].totalMax += quiz.maxPoints
  }

  return Object.entries(groups).map(([diff, { totalScore, totalMax }]) =>
  {
    const percent = totalMax === 0 ? 0 : Math.round((totalScore / totalMax) * 100)

    return {
      diff: diff as Diff,
      averagePercentage: percent,
    }
  })
})

// Konfiguracja dla Vue3Charts
const chartData = computed(() => {
  return averageByDiff.value.map(item => ({
    name: item.diff,
    percentage: item.averagePercentage
  }))
})

const margin = ref({
  left: 60,
  top: 20,
  right: 20,
  bottom: 40
})

const direction = ref('horizontal')

const axis = ref({
  primary: {
    type: 'band'
  },
  secondary: {
    domain: [0, 100],
    type: 'linear',
    ticks: 5
  }
})

const solvedQuizes = async () =>
{
  try
  {
    const response = await axios.get<SolvedDto[]>(
      `http://localhost:10000/quiz/get-solved/${authStore.username}`,
      {
        headers:
          {
            Authorization: `Bearer ${authStore.token}`,
          },
      }
    )

    const data = response.data

    allQuizzes.value = data.map((entry) =>
      ({
        category: entry.category,
        diff: entry.diff,
        title: entry.title,
        maxPoints: entry.maxPoints,
        score: entry.score,
        percentage: Math.round((entry.score / entry.maxPoints) * 100)
      }))

    solvedQuizzes.value = allQuizzes.value.length
  }
  catch (error)
  {
    console.error(error)
  }
}

onMounted(()=>
{
  setTimeout(() =>
  {
    solvedQuizes()
  }, 400)
})

const searchQuery = ref('')

const filteredQuizzes = computed(() =>
  allQuizzes.value.filter(quiz =>
    quiz.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
)

console.log(authStore.token)
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

        <Card class="w-full mt-6">
          <CardHeader>
            <CardTitle class="text-lg text-center">Average Score by Difficulty</CardTitle>
          </CardHeader>
          <CardContent>
            <Chart
              :size="{ width: 500, height: 300 }"
              :data="chartData"
              :margin="margin"
            >
              <template #layers>
                <Grid strokeDasharray="2,2" />
                <Bar
                  :dataKeys="['name', 'percentage']"
                  :barStyle="{ fill: '#3b82f6', opacity: 0.8 }"
                />
              </template>
              <template #widgets>
                <Tooltip
                  borderColor="#3b82f6"
                  :config="{
                    percentage: { color: '#3b82f6', name: 'Score' }
                  }"
                />
              </template>
            </Chart>
          </CardContent>
        </Card>
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
              :key="quiz.title"
              class="p-3 border rounded-lg hover:bg-muted transition-colors"
            >
              <div class="font-medium">{{ quiz.title }}</div>
              <div class="text-sm text-muted-foreground">Score: {{ quiz.score }}</div>
              <div class="text-sm text-muted-foreground">Max points: {{ quiz.maxPoints }}</div>
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
