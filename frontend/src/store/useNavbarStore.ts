import { defineStore } from 'pinia'
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'

export const useNavbarStore = defineStore('navbar', () =>
{
  const route = useRoute()

  const activeRoute = ref<string>('')

  const isHome = ref(false)
  const isTopPlayers = ref(false)
  const isCreateQuiz = ref(false)
  const isLogging = ref(false)
  const isAllQuizes = ref(false)
  const isCreateRoom = ref(false)

  function updateRouteState(path: string)
  {
    activeRoute.value = path
    isHome.value = path === '/home'
    isTopPlayers.value = path === '/top-players'
    isCreateQuiz.value = path === '/create-quiz'
    isLogging.value = path === '/login'
    isAllQuizes.value = path === '/all-quizes'
    isCreateRoom.value = path.startsWith('/room-view')
  }

  watch(() => route.path, (newPath) =>
  {
    updateRouteState(newPath)
  }, { immediate: true })

  return {
    activeRoute,
    isHome,
    isTopPlayers,
    isCreateQuiz,
    isLogging,
    updateRouteState,
    isAllQuizes,
    isCreateRoom,
  }
})
