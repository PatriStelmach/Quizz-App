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
import { useRouter } from 'vue-router'
import axios from 'axios'
import useAuthStore from '@/store/useAuthStore.ts'

const router = useRouter()
const authStore = useAuthStore()
const { result, loading, error } = useQuery<AllQuizzesQuery>(AllQuizzesDocument)
const sortKey = ref<'title' | 'category' | 'diff' | 'maxPoints' | 'timeLimit'>('title')
const sortAsc = ref(true)

const sortedQuizzes = computed(() =>
{
  const list = result.value?.allQuizzes ?? []
  return [...list].sort((a, b) =>
  {
    const aVal = a[sortKey.value]
    const bVal = b[sortKey.value]
    const aStr = typeof aVal === 'number' ? aVal : String(aVal ?? '').toLowerCase()
    const bStr = typeof bVal === 'number' ? bVal : String(bVal ?? '').toLowerCase()
    if (aStr < bStr) return sortAsc.value ? -1 : 1
    if (aStr > bStr) return sortAsc.value ? 1 : -1
    return 0
  })
})

const progress = ref(0)
onMounted(() =>
{
  loading.value = true
  progress.value = 20
  setTimeout(() => { progress.value = 40 }, 300)
  setTimeout(() => { progress.value = 60 }, 500)
  setTimeout(() => { progress.value = 100 }, 700)
  setTimeout(() => { loading.value = false }, 900)
})

const createRoom = async (quizId: string) => {
  const token = authStore.token
  if (!token)
  {
    alert('Please log in first.')
    return
  }
  try
  {
    const response = await axios.post(
      `http://localhost:10000/rooms/create?quizId=${quizId}`, {},

      {
        headers:
          {
            Authorization: `Bearer ${token}`
          }
      }
    )
    await router.push({ name: 'room', params: { roomId: response.data.id } })
  } catch (e)
  {
    console.error(e)
    alert('Failed to create room.')
  }
}

function setSort(key: 'title' | 'category' | 'diff' | 'maxPoints' | 'timeLimit')
{
  if (sortKey.value === key)
  {
    sortAsc.value = !sortAsc.value
  } else {
    sortKey.value = key
    sortAsc.value = true
  }
}

function difficultyClass(diff: string)
{
  const d = diff.toLowerCase()
  if (d === 'easy') return 'text-green-500 font-semibold'
  if (d === 'medium') return 'text-yellow-500 font-semibold'
  if (d === 'hard') return 'text-orange-500 font-semibold'
  if (d === 'expert') return 'text-red-500 font-semibold'
  return 'text-gray-500'
}
</script>

<template>
  <div class="mx-auto   overflow-visible w-6xl text-center">
    <h1 class="pt-6 text-center mx-auto w-full  text-4xl text-primary border-b pb-6 mb-4">Available Quizzusie</h1>

    <Table class="mb-20">
      <TableHeader>
        <TableRow class="hover:bg-secondary/20 cursor-pointer text-foreground">
          <TableHead @click="setSort('title')" class="cursor-pointer px-20 text-foreground hover:text-rose-400">
            Title <span v-if="sortKey === 'title'">{{ sortAsc ? '▲' : '▼' }}</span>
          </TableHead>
          <TableHead @click="setSort('category')" class="cursor-pointer text-foreground hover:text-rose-400">
            Category <span v-if="sortKey === 'category'">{{ sortAsc ? '▲' : '▼' }}</span>
          </TableHead>
          <TableHead @click="setSort('diff')" class="cursor-pointer text-foreground hover:text-rose-400">
            Difficulty <span v-if="sortKey === 'diff'">{{ sortAsc ? '▲' : '▼' }}</span>
          </TableHead>
          <TableHead @click="setSort('maxPoints')" class="cursor-pointer  text-foreground hover:text-rose-400">
            Max Points <span v-if="sortKey === 'maxPoints'">{{ sortAsc ? '▲' : '▼' }}</span>
          </TableHead>
          <TableHead @click="setSort('timeLimit')" class="cursor-pointer text-foreground hover:text-rose-400">
            Time Limit <span v-if="sortKey === 'timeLimit'">{{ sortAsc ? '▲' : '▼' }}</span>
          </TableHead>
        </TableRow>
      </TableHeader>

      <TableBody v-if="!loading && !error">
        <TableRow
          v-for="quiz in sortedQuizzes"
          :key="quiz.id"
          @click="createRoom(quiz.id)"
          class="hover:bg-primary/40 cursor-pointer transform transition-transform duration-200 hover:scale-105"
        >
          <TableCell class="px-20 text-xl">{{ quiz.title }}</TableCell>
          <TableCell>{{ quiz.category }}</TableCell>
          <TableCell :class="difficultyClass(quiz.diff)">{{ quiz.diff }}</TableCell>
          <TableCell>{{ quiz.maxPoints }}</TableCell>
          <TableCell>{{ quiz.timeLimit }}</TableCell>
        </TableRow>
      </TableBody>
    </Table>

    <div v-if="loading" class="py-4 text-center">
      <Progress v-model="progress" class="w-3/5 mx-auto mb-2" />
      <p class="text-gray-500">Loading quizzes...</p>
    </div>
    <div v-if="error" class="py-4 text-center text-red-500">
      <p>Error loading quizzes.</p>
    </div>
  </div>
</template>
