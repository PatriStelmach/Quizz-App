package com.pjatk.QuizzApp.Game;

import com.pjatk.QuizzApp.Game.Memory.RoomQuestion;
import com.pjatk.QuizzApp.Game.Memory.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
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

            // send "quiz-start" first
            messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of("type", "quiz-start"));

            // simulate question (replace with real quiz logic)
            String question = "What is the capital of France?";
            List<String> answers = List.of("Paris", "London", "Berlin", "Madrid");

            messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of(
                    "type", "question",
                    "question", question,
                    "answers", answers
            ));
        }
    }
//    @MessageMapping("/room/{roomId}/start")
//    public void startQuiz(@DestinationVariable String roomId) {
//        Room room = roomService.getRoom(roomId);
//        if (room != null && !room.isStarted()) {
//            room.setStarted(true);
//
//            // Step 1: Load questions
//            List<RoomQuestion> questions = loadQuestions();
//            room.setRoomQuestions(questions);
//
//            // Step 2: Send 'get ready' message
//            messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of(
//                    "type", "get-ready"
//            ));
//
//            // Step 3: Wait 2 seconds, then send first question
//            new Thread(() -> {
//                try {
//                    Thread.sleep(2000);
//                    sendQuestion(roomId);
//                } catch (InterruptedException ignored) {}
//            }).start();
//        }
//    }

    private void sendQuestion(String roomId) {
        Room room = roomService.getRoom(roomId);
        RoomQuestion q = room.getCurrentQuestion();

        if (q != null) {
            messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of(
                    "type", "question",
                    "question", q.getQuestion(),
                    "answers", q.getAnswers()
            ));
        } else {
            messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of(
                    "type", "quiz-end"
            ));
        }
    }
    private List<RoomQuestion> loadQuestions() {
        return List.of(
                new RoomQuestion("What is the capital of France?",
                        List.of("Paris", "Berlin", "Madrid", "Rome"), 0),
                new RoomQuestion("2 + 2 = ?",
                        List.of("3", "4", "5", "6"), 1),
                new RoomQuestion("What color is the sky?",
                        List.of("Green", "Blue", "Red", "Yellow"), 1)
        );
    }

}
