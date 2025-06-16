<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import SockJS from 'sockjs-client/dist/sockjs';
import { Client, type Frame } from '@stomp/stompjs'


const props = defineProps<{ roomId: string }>()

const playerName = ref('')
const answer = ref('')
const messages = ref<{ playerName: string; answer: string }[]>([])

const phase = ref<'ready' | 'countdown' | 'question' | 'summary'>('ready')
const countdown = ref(3)
const currentQuestionIndex = ref(0)
const questions = ref<{ question: string; options: string[] }[]>([
  { question: 'What is 2 + 2?', options: ['3', '4', '5', '6'] },
  { question: 'What is the capital of France?', options: ['Berlin', 'London', 'Paris', 'Rome'] }
])
const selectedAnswer = ref('')



const stompClient = new Client({
  webSocketFactory: () => new SockJS('http://localhost:10000/ws'),
})

onMounted(() => {
  stompClient.onConnect = () => {
    stompClient.subscribe(`/topic/room/${props.roomId}`, (frame: Frame) => {
      const body = JSON.parse(frame.body) as { playerName: string; answer: string }
      messages.value.push(body)
    })
  }
  stompClient.activate()
})

function startCountdown() {
  phase.value = 'countdown'
  countdown.value = 3
  const interval = setInterval(() => {
    countdown.value--
    if (countdown.value === 0) {
      clearInterval(interval)
      phase.value = 'question'
    }
  }, 1000)
}



function sendAnswer() {
  if (!selectedAnswer.value) return
  const msg = {
    playerName: playerName.value || 'Guest',
    answer: selectedAnswer.value,
  }
  stompClient.publish({
    destination: `/app/room/${props.roomId}/answer`,
    body: JSON.stringify(msg),
  })

  if (currentQuestionIndex.value < questions.value.length - 1) {
    currentQuestionIndex.value++
    selectedAnswer.value = ''
  } else {
    phase.value = 'summary'
  }
}
</script>

<template>
  <div class="quiz-room">
    <div v-if="phase === 'ready'">
      <h2>Ready?</h2>
      <input v-model="playerName" placeholder="Enter your name" />
      <button @click="startCountdown">I'm Ready</button>
    </div>

    <div v-else-if="phase === 'countdown'">
      <h2>Starting in {{ countdown }}...</h2>
    </div>

    <div v-else-if="phase === 'question'">
      <h2>Question {{ currentQuestionIndex + 1 }}</h2>
      <div>
        <strong>{{ questions[currentQuestionIndex].question }}</strong>
        <ul>
          <li v-for="option in questions[currentQuestionIndex].options" :key="option">
            <label>
              <input
                type="radio"
                :value="option"
                v-model="selectedAnswer"
                :disabled="!!selectedAnswer"
              />
              {{ option }}
            </label>
          </li>
        </ul>
        <button @click="sendAnswer" :disabled="!selectedAnswer">Submit Answer</button>
      </div>
    </div>

    <div v-else-if="phase === 'summary'">
      <h2>Quiz Finished!</h2>
      <!-- Placeholder for summary, scores, etc. -->
      <ul>
        <li v-for="m in messages" :key="m.playerName + m.answer">
          {{ m.playerName }}: {{ m.answer }}
        </li>
      </ul>
      <button @click="phase = 'ready'; currentQuestionIndex = 0; selectedAnswer = '';">Restart</button>
    </div>
  </div>
</template>

<style scoped>
.quiz-room {
  max-width: 400px;
  margin: auto;
  padding: 1em;
}
</style>
