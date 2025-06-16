package com.pjatk.QuizzApp.Game;

import com.pjatk.QuizzApp.Game.Memory.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

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
}