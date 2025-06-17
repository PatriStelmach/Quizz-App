package com.pjatk.QuizzApp.Game;

import com.pjatk.QuizzApp.Game.Memory.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;


@Controller
@RequiredArgsConstructor
public class RoomSocketController {

    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/room/{roomId}")
    public void handleMessage(@DestinationVariable String roomId, @Payload Map<String, Object> message) {
        String type = (String) message.get("type");

        switch (type) {
            case "join" -> {
                String playerName = (String) message.get("playerName");
                System.out.println("handle Message playerName value: " + playerName);
                joinRoom(roomId, playerName);
            }
            case "start" -> startQuiz(roomId);
            // future cases: "answer", "leave", etc.
        }
    }

    private void joinRoom(String roomId, String playerName) {
        Room room = roomService.getRoom(roomId);
        System.out.println("join room - room: " + room);
        System.out.println("playerName in method joinRoom: " + playerName);

            if (!room.getPlayers().contains(playerName)) {
                room.getPlayers().add(playerName);
                messagingTemplate.convertAndSend("/topic/room/" + roomId + "/players", room.getPlayers());
            }
        }
    @MessageMapping("/room/{roomId}/start")
    public void startQuiz(@DestinationVariable String roomId) {
        Room room = roomService.getRoom(roomId);
        if (room != null && !room.isStarted()) {
            room.setStarted(true);
            messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of("type", "quiz-start"));
        }
    }
}
