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
        Room room = roomService.getRoom(roomId);
        switch (type) {
            case "join" -> {
                String playerName = (String) message.get("playerName");
                System.out.println("handle Message playerName value: " + playerName);
                joinRoom(roomId, playerName);
            }
            case "start" -> {
                System.out.println("START message received for room: " + roomId);
                startQuiz(roomId);
                // future cases: "answer", "leave", etc.
            }
            case "answer" -> {
                String playerName = String.valueOf(message.get("playerName"));
                Object rawAnswer = message.get("answerIndex");

                Integer answerIndex = null;
                if (rawAnswer != null && !"null".equals(rawAnswer)) {
                    answerIndex = (rawAnswer instanceof Integer)
                            ? (Integer) rawAnswer
                            : Integer.parseInt(rawAnswer.toString());
                }
                System.out.println("All player answers: " + room.getPlayerAnswers());
                handleAnswer(roomId, playerName, answerIndex);
            }

        }
    }

    private void handleAnswer(String roomId, String playerName, Integer answerIndex) {
        Room room = roomService.getRoom(roomId);

        room.getPlayerAnswers().put(playerName, answerIndex != null ? answerIndex : -1); //  set -1 for: no answer



        // Check if all players answered(time out or send)
        if (room.getPlayerAnswers().size() == room.getPlayers().size()) {
            RoomQuestion current = room.getCurrentQuestion();
            if (current != null) {
                messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of(
                        "type", "reveal",
                        "correctAnswer", current.getCorrectAnswerIndex()
                ));
            }
            System.out.println("Saved answer for " + playerName + ": " + answerIndex);
            // Clear answers for next round
            room.getPlayerAnswers().clear();

            // Move to next question after delay
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    room.moveToNextQuestion();
                    sendQuestion(roomId);
                } catch (InterruptedException ignored) {}
            }).start();
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

            // send msg & front route to /game/:roomId
            messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of("type", "quiz-start"));

            // INJECT QUESTIONS FOR NOW
            if (room.getRoomQuestions() == null || room.getRoomQuestions().isEmpty()) {
                room.setRoomQuestions(loadQuestions()); // fallback (temporary)
            }

            // delay and send first question
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    sendQuestion(roomId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void sendQuestion(String roomId) {
        Room room = roomService.getRoom(roomId);
        RoomQuestion q = room.getCurrentQuestion();

        if (q != null) {
            room.getPlayerAnswers().clear(); // clear before accepting new answers
            messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of(
                    "type", "question",
                    "question", q.getQuestion(),
                    "answers", q.getAnswers(),
                    "duration", q.getDuration()
            ));
            startQuestionTimer(roomId, q.getDuration());
        } else {
            messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of(
                    "type", "quiz-end"
            ));
        }
    }
    private List<RoomQuestion> loadQuestions() {
        return List.of(
                new RoomQuestion("What is the capital of France (q1)?",
                        List.of("Paris", "Berlin", "Madrid", "Rome"), 0, 15),
                new RoomQuestion("2 + 2 = ? (q2)",
                        List.of("3", "4", "5", "6"), 1, 10),
                new RoomQuestion("What color is the sky? (q3)",
                        List.of("Green", "Blue", "Red", "Yellow"), 1, 12)
        );

    }

    private void startQuestionTimer(String roomId, int seconds) {
        new Thread(() -> {
            try {
                Thread.sleep(seconds * 1000L);

                Room room = roomService.getRoom(roomId);

                // Reveal only if not already done
                if (room.getPlayerAnswers().size() < room.getPlayers().size()) {
                    RoomQuestion current = room.getCurrentQuestion();
                    if (current != null) {
                        messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of(
                                "type", "reveal",
                                "correctAnswer", current.getCorrectAnswerIndex()
                        ));
                    }

                    room.getPlayerAnswers().clear();
                    Thread.sleep(3000);
                    room.moveToNextQuestion();
                    sendQuestion(roomId);
                }

            } catch (InterruptedException ignored) {
            }
        }).start();
    }
}
