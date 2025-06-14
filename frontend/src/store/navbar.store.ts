import { defineStore } from 'pinia'
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'

export const useNavbarStore = defineStore('navbar', () => {
  const route = useRoute()

  // Stan aktywnej ścieżki
  const activeRoute = ref<string>('')

  // Stany dla poszczególnych zakładek
  const isHome = ref(false)
  const isTopPlayers = ref(false)
  const isCreateQuiz = ref(false)
  const isLogging = ref(false)

  // Funkcja aktualizująca stan na podstawie ścieżki
  function updateRouteState(path: string) {
    activeRoute.value = path
    isHome.value = path === '/home'
    isTopPlayers.value = path === '/top-players'
    isCreateQuiz.value = path === '/create-quiz'
    isLogging.value = path === '/login'
  }

  // Automatyczna aktualizacja przy zmianie route
  watch(() => route.path, (newPath) => {
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
