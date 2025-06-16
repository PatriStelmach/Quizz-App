import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

let stompClient: Client | null = null;

export function connectWebSocket(roomId: string, onMessage: (body: any) => void) {
  const socket = new SockJS('http://localhost:8080/quiz-ws');

  stompClient = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000,
    onConnect: () => {
      console.log('WebSocket connected');
      stompClient?.subscribe(`/topic/room/${roomId}`, (message: IMessage) => {
        const payload = JSON.parse(message.body);
        onMessage(payload);
      });
    },
    onStompError: (frame) => {
      console.error('WebSocket error:', frame);
    },
  });

  stompClient.activate();
}

export function sendRoomMessage(roomId: string, data: any) {
  stompClient?.publish({
    destination: `/app/room/${roomId}`,
    body: JSON.stringify(data),
  });
}

export function disconnectWebSocket() {
  stompClient?.deactivate();
}
