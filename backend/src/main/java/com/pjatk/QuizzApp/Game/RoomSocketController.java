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

    @MessageMapping("/room/{roomId}/join")
    public void joinRoom(@DestinationVariable String roomId, @Payload String playerName) {
        Room room = roomService.getRoom(roomId);
        if (room != null && !room.getPlayers().contains(playerName)) {
            room.getPlayers().add(playerName);
            messagingTemplate.convertAndSend("/topic/room/" + roomId + "/players", room.getPlayers());
        }
    }
    @MessageMapping("/room/{roomId}/start")
    public void startQuiz(@DestinationVariable String roomId) {
        Room room = roomService.getRoom(roomId);
        if (room != null && !room.isStarted()) {
            room.setStarted(true);
            messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of(
                    "type", "quiz-start"
            ));

            // You can also preload first question here if needed:
            // messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of("type", "quiz-question", "question", firstQuestion));
        }
    }

    @MessageMapping("/room/{roomId}")
    public void handleMessage(@DestinationVariable String roomId, @Payload Map<String, Object> message) {
        String type = (String) message.get("type");

        if ("join".equals(type)) {
            String playerName = (String) message.get("playerName");
            joinRoom(roomId, playerName); // reuse method you already wrote
        }

        if ("start".equals(type)) {
            startQuiz(roomId);
        }

        // You can add "answer-submitted" etc. here too
    }
}
