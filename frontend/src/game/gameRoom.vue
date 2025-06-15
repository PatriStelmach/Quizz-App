<script setup lang="ts">
import { ref, onMounted } from 'vue'
import SockJS from 'sockjs-client'
import { Client, Frame } from '@stomp/stompjs'

const roomId = 'test-room'
const playerName = ref('')
const answer = ref('')
const messages = ref<{ playerName: string; answer: string }[]>([])

// 1) Create the STOMP client as an ES module
const stompClient = new Client({
  webSocketFactory: () => new SockJS('http://localhost:10000/ws'),
  // debug: console.debug,
})

onMounted(() => {
  // 2) When connected, subscribe
  stompClient.onConnect = () => {
    stompClient.subscribe(
      `/topic/room/${roomId}`,
      (frame: Frame) => {
        // 3) Handle incoming frames
        const body = JSON.parse(frame.body) as {
          playerName: string
          answer: string
        }
        messages.value.push(body)
      }
    )
  }

  // 4) Kick off the connection
  stompClient.activate()
})

function sendAnswer() {
  const msg = {
    playerName: playerName.value,
    answer: answer.value,
  }
  // 5) Use `publish`, not `send`
  stompClient.publish({
    destination: `/app/room/${roomId}/answer`,
    body: JSON.stringify(msg),
  })
}
</script>

<template>
  <div>
    <input v-model="playerName" placeholder="Name" />
    <input v-model="answer" placeholder="Answer" />
    <button @click="sendAnswer">Send</button>
    <ul>
      <li v-for="m in messages" :key="m.playerName + m.answer">
        {{ m.playerName }}: {{ m.answer }}
      </li>
    </ul>
  </div>
</template>
