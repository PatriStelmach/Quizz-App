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

// Sorting state
const sortKey = ref<'title' | 'category' | 'diff'>( 'title' )
const sortAsc = ref(true)

// Compute sorted quizzes
const sortedQuizzes = computed(() => {
  const list = result.value?.allQuizzes ?? []
  return [...list].sort((a, b) => {
    const aVal = String(a[sortKey.value] ?? '').toLowerCase()
    const bVal = String(b[sortKey.value] ?? '').toLowerCase()
    if (aVal < bVal) return sortAsc.value ? -1 : 1
    if (aVal > bVal) return sortAsc.value ? 1 : -1
    return 0
  })
})

// Progress simulation
const progress = ref(0)
onMounted(() => {
  loading.value = true
  progress.value = 20
  setTimeout(() => { progress.value = 40 }, 300)
  setTimeout(() => { progress.value = 60 }, 500)
  setTimeout(() => { progress.value = 100 }, 700)
  setTimeout(() => { loading.value = false }, 900)
})

const createRoom = async (quizId: string) => {
  const token = authStore.token
  if (!token) {
    alert('Please log in first.')
    return
  }
  try {
    const response = await axios.post(
      `http://localhost:10000/rooms/create?quizId=${quizId}`, {},
      { headers: { Authorization: `Bearer ${token}` } }
    )
    await router.push({ name: 'room', params: { roomId: response.data.id } })
  } catch (e) {
    console.error(e)
    alert('Failed to create room.')
  }
}

// Change sort
function setSort(key: 'title' | 'category' | 'diff') {
  if (sortKey.value === key) {
    sortAsc.value = !sortAsc.value
  } else {
    sortKey.value = key
    sortAsc.value = true
  }
}
</script>

<template>
  <div class="mx-auto overflow-x-auto w-6xl">
    <TableCaption class="w-full text-center text-4xl text-primary border-b pb-6 mb-4">
      Available Quizzes
    </TableCaption>

    <Table>
      <TableHeader>
        <TableRow class="hover:bg-accent cursor-default">
          <TableHead @click="setSort('title')" class="cursor-pointer">
            Title <span v-if="sortKey === 'title'">{{ sortAsc ? '▲' : '▼' }}</span>
          </TableHead>
          <TableHead @click="setSort('category')" class="cursor-pointer">
            Category <span v-if="sortKey === 'category'">{{ sortAsc ? '▲' : '▼' }}</span>
          </TableHead>
          <TableHead @click="setSort('diff')" class="cursor-pointer">
            Difficulty <span v-if="sortKey === 'diff'">{{ sortAsc ? '▲' : '▼' }}</span>
          </TableHead>
          <TableHead>Max Points</TableHead>
          <TableHead>Time Limit</TableHead>
        </TableRow>
      </TableHeader>

      <TableBody v-if="!loading && !error">
        <TableRow
          v-for="quiz in sortedQuizzes"
          :key="quiz.id"
          @click="createRoom(quiz.id)"
          class="hover:bg-muted cursor-pointer"
        >
          <TableCell>{{ quiz.title }}</TableCell>
          <TableCell>{{ quiz.category }}</TableCell>
          <TableCell>{{ quiz.diff }}</TableCell>
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
