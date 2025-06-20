package com.pjatk.QuizzApp.Game;

import com.pjatk.QuizzApp.Game.Memory.RoomQuestion;
import com.pjatk.QuizzApp.Game.Memory.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Controller
@RequiredArgsConstructor
public class RoomSocketController {

    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    private final Map<String, ScheduledFuture<?>> timers = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    @MessageMapping("/room/{roomId}")
    public void handleMessage(@DestinationVariable String roomId, @Payload Map<String, Object> message) {
        String type = (String) message.get("type");
        Room room = roomService.getRoom(roomId);
        if (room == null) return;

        switch (type) {
            case "join" -> joinRoom(roomId, (String) message.get("playerName"));
            case "start" -> startQuiz(roomId);
            case "answer" -> {
                String playerName = String.valueOf(message.get("playerName"));
                Object rawAnswer = message.get("answerIndex");
                int answerIndex = (rawAnswer != null && !"null".equals(rawAnswer)) ? Integer.parseInt(rawAnswer.toString()) : -1;
                handleAnswer(roomId, playerName, answerIndex);
            }
        }
    }

    private void joinRoom(String roomId, String playerName) {
        Room room = roomService.getRoom(roomId);
        room.addPlayer(playerName);
        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/players", room.getPlayers());
    }

    private void startQuiz(String roomId) {
        Room room = roomService.getRoom(roomId);
        if (room == null || room.isStarted()) return;

        room.setStarted(true);
        room.setRoomQuestions(loadQuestions(roomId));

        List<Map<String, Object>> initialScores = new ArrayList<>();
        for (String player : room.getPlayers()) {
            room.getPlayerScores().putIfAbsent(player, 0);
            initialScores.add(Map.of("player", player, "score", 0));
        }

        messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of(
                "type", "quiz-start",
                "players", initialScores
        ));

        scheduler.schedule(() -> sendQuestion(roomId), 1, TimeUnit.SECONDS);
    }

    private void sendQuestion(String roomId) {
        Room room = roomService.getRoom(roomId);
        RoomQuestion currentQuestion = room.getCurrentQuestion();

        if (currentQuestion != null) {
            room.clearAnswers();

            messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of(
                    "type", "question",
                    "question", currentQuestion.getQuestion(),
                    "answers", currentQuestion.getAnswers(),
                    "duration", currentQuestion.getDuration()
            ));

            ScheduledFuture<?> timer = scheduler.schedule(() -> {
                revealAnswers(roomId, true);
            }, currentQuestion.getDuration(), TimeUnit.SECONDS);

            timers.put(roomId, timer);
        } else {
            messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of("type", "quiz-end"));
        }
    }

    private synchronized void handleAnswer(String roomId, String playerName, int answerIndex) {
        Room room = roomService.getRoom(roomId);
        if (room.getPlayerAnswers().containsKey(playerName)) return; // ignore duplicates

        room.getPlayerAnswers().put(playerName, answerIndex);

        if (room.getPlayerAnswers().size() == room.getPlayers().size()) {
            ScheduledFuture<?> timer = timers.remove(roomId);
            if (timer != null) timer.cancel(false);
            revealAnswers(roomId, false);
        }
    }

    private void revealAnswers(String roomId, boolean timeoutTriggered) {
        Room room = roomService.getRoom(roomId);
        RoomQuestion current = room.getCurrentQuestion();
        if (current == null) return;

        int correctIndex = current.getCorrectAnswerIndex();
        Map<String, Integer> scores = room.getPlayerScores();
        List<Map<String, Object>> results = new ArrayList<>();

        for (String player : room.getPlayers()) {
            int answer = room.getPlayerAnswers().getOrDefault(player, -1);
            boolean correct = answer == correctIndex;
            if (correct) {
                scores.put(player, scores.getOrDefault(player, 0) + 1);
            }

            results.add(Map.of(
                    "player", player,
                    "score", scores.getOrDefault(player, 0),
                    "correct", correct
            ));
        }

        messagingTemplate.convertAndSend("/topic/room/" + roomId, Map.of(
                "type", "reveal",
                "correctAnswer", correctIndex,
                "results", results
        ));

        room.clearAnswers();
        room.moveToNextQuestion();

        scheduler.schedule(() -> sendQuestion(roomId), 3, TimeUnit.SECONDS);
    }

    private List<RoomQuestion> loadQuestions(String roomId) {
        Room room = roomService.getRoom(roomId);
        return roomService.loadRoomQuestionsFromDB(room.getQuizId());
    }
}
