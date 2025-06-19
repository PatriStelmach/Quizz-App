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

  function updateRouteState(path: string)
  {
    activeRoute.value = path
    isHome.value = path === '/home'
    isTopPlayers.value = path === '/top-players'
    isCreateQuiz.value = path === '/create-quiz'
    isLogging.value = path === '/login'
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
    updateRouteState
  }
})
