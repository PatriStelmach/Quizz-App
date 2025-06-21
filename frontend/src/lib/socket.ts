import SockJS from 'sockjs-client';
import { Client, Frame, over } from 'webstomp-client';

let stompClient: Client | null = null;

export const connectSocket = (onConnected: () => void, onMessage: (msg: any) => void, roomId: string) =>
{
  const socket = new SockJS('http://localhost:10000/quiz-ws');
  stompClient = over(socket);

  stompClient.connect({}, (frame?: Frame) =>
  {
    if (!frame) return;
    console.log('Connected: ' + frame);
    stompClient?.subscribe(`/topic/room/${roomId}/players`, message =>
    {
      onMessage(JSON.parse(message.body));
    });
    stompClient?.subscribe(`/topic/room/${roomId}`, message =>
    {
      const body = JSON.parse(message.body);
      onMessage(body);
    });
    onConnected();
  });
};
export const sendRoomMessage = (roomId: string, body: any) =>
{
  if (stompClient && stompClient.connected) {
    stompClient.send(`/app/room/${roomId}`, JSON.stringify(body), {});
  }
};
