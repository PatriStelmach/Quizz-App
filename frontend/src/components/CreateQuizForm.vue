<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import { Textarea } from '@/components/ui/textarea'
import { Label } from '@/components/ui/label'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'
import { Upload } from 'lucide-vue-next' // ikonka, opcjonalnie

type QuestionType = 'open' | 'choice' | 'truefalse'

type QuizQuestion = {
  type: QuestionType
  text: string
  options: string[] // dla choice i truefalse
  correctAnswer: string
  image?: File | null
}

const questions = ref<QuizQuestion[]>([])
const currentIndex = ref(0)

const currentQuestion = reactive<QuizQuestion>({
  type: 'open',
  text: '',
  options: [],
  correctAnswer: '',
  image: null
})

const isLastStep = computed(() => currentIndex.value >= questions.value.length)

function handleAddOption() {
  if (currentQuestion.options.length < 4) {
    currentQuestion.options.push('')
  }
}

function handleRemoveOption(index: number) {
  currentQuestion.options.splice(index, 1)
}

function handleNextQuestion() {
  questions.value.push({ ...currentQuestion })
  resetCurrentQuestion()
  currentIndex.value++
}

function resetCurrentQuestion() {
  currentQuestion.type = 'open'
  currentQuestion.text = ''
  currentQuestion.options = []
  currentQuestion.correctAnswer = ''
  currentQuestion.image = null
}

function handleSubmitQuiz() {
  console.log('Quiz:', questions.value)
  // TODO: Wyślij dane do API
}
</script>

<template>
  <div class="max-w-xl mx-auto mt-10">
    <!-- Dodawanie pytania -->
    <Card v-if="!isLastStep">
      <CardHeader>
        <CardTitle>Dodaj pytanie {{ currentIndex + 1 }}</CardTitle>
      </CardHeader>
      <CardContent class="space-y-4">

        <!-- Typ pytania -->
        <div>
          <Label>Typ pytania</Label>
          <Select v-model="currentQuestion.type">
            <SelectTrigger>
              <SelectValue placeholder="Wybierz typ" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="open">Otwarte</SelectItem>
              <SelectItem value="choice">Zamknięte (2-4 odpowiedzi)</SelectItem>
              <SelectItem value="truefalse">Prawda / Fałsz</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <!-- Treść pytania -->
        <div>
          <Label>Treść pytania</Label>
          <Textarea v-model="currentQuestion.text" placeholder="Wpisz pytanie..." />
        </div>

        <!-- Obrazek -->
        <div>
          <Label>Obrazek (opcjonalny)</Label>
          <Input type="file" @change="e => currentQuestion.image = (e.target as HTMLInputElement).files?.[0] ?? null" />
        </div>

        <!-- Odpowiedzi -->
        <div v-if="currentQuestion.type === 'choice'" class="space-y-2">
          <Label>Odpowiedzi</Label>
          <div v-for="(option, index) in currentQuestion.options" :key="index" class="flex gap-2 items-center">
            <Input v-model="currentQuestion.options[index]" placeholder="Odpowiedź" />
            <Button type="button" size="icon" variant="destructive" @click="handleRemoveOption(index)">×</Button>
          </div>
          <Button type="button" variant="outline" class="mt-2" @click="handleAddOption" :disabled="currentQuestion.options.length >= 4">
            Dodaj odpowiedź
          </Button>
        </div>

        <!-- Prawda / Fałsz -->
        <div v-else-if="currentQuestion.type === 'truefalse'" class="space-y-2">
          <Label>Poprawna odpowiedź</Label>
          <Select v-model="currentQuestion.correctAnswer">
            <SelectTrigger>
              <SelectValue placeholder="Wybierz" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="true">Prawda</SelectItem>
              <SelectItem value="false">Fałsz</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <!-- Odpowiedź otwarta -->
        <div v-else-if="currentQuestion.type === 'open'">
          <Label>Poprawna odpowiedź</Label>
          <Input v-model="currentQuestion.correctAnswer" placeholder="Poprawna odpowiedź" />
        </div>

        <div class="flex justify-end mt-4">
          <Button type="button" @click="handleNextQuestion">
            Dodaj pytanie
          </Button>
        </div>
      </CardContent>
    </Card>

    <!-- Podsumowanie -->
    <Card v-else>
      <CardHeader>
        <CardTitle>Quiz gotowy!</CardTitle>
      </CardHeader>
      <CardContent class="space-y-4">
        <p>Dodano {{ questions.length }} pytań.</p>
        <Button @click="handleSubmitQuiz">
          Zapisz quiz
        </Button>
      </CardContent>
    </Card>
  </div>
</template>
