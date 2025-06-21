import { defineStore } from 'pinia'
import { Diff } from '@/generated/graphql.ts'
import { ref } from 'vue'

const useGameStore = defineStore('game',
  () =>
  {
    const quizDiff = ref<Diff | null>(null)


    function setDiff(diffValue: string)
    {
      const matchedKey = Object.entries(Diff).find(([, v]) => v === diffValue)?.[0] as keyof typeof Diff | undefined

      if (!matchedKey) {
        console.warn('Nieprawidłowy diff:', diffValue)
        return
      }

      quizDiff.value = Diff[matchedKey]
    }

    const clearDiff = () =>
    {
      quizDiff.value = Diff.Easy;
    }

    const getPoints = () =>
    {
      switch (quizDiff.value) {
        case Diff.Easy:
          return 2
        case Diff.Medium:
          return 3
        case Diff.Hard:
          return 4
        case Diff.Expert:
          return 5
      }
    }

    return{ clearDiff, setDiff, getPoints};
  })

  export default useGameStore
