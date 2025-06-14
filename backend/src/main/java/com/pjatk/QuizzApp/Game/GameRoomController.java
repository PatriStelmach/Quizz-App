package com.pjatk.QuizzApp.Game;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class GameRoomController {


    @MessageMapping("/room/{roomId}/answer")
    @SendTo("/topic/room/{roomId}")
    public AnswerMessage handleAnswer(
            @DestinationVariable String roomId,
            AnswerMessage message
    ) {
        //logic
        return message;
    }
}