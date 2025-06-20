package com.pjatk.QuizzApp.Game.Memory;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Room {
    private String id;
    private String ownerName;
    private List<String> players = new CopyOnWriteArrayList<>();
    private Integer quizId;
    private boolean started = false;

    private List<RoomQuestion> roomQuestions;
    private AtomicInteger currentQuestionIndex = new AtomicInteger(0);

    //thread-safe map
    private Map<String, Integer> playerAnswers = new ConcurrentHashMap<>();

    private final Map<String, Integer> playerScores = new ConcurrentHashMap<>();

    public Room(String id, String ownerName, Integer quizId) {
        this.id = id;
        this.ownerName = ownerName;
        this.quizId = quizId;
    }

    public RoomQuestion getCurrentQuestion() {
        if (roomQuestions != null && currentQuestionIndex.get() < roomQuestions.size()) {
            return roomQuestions.get(currentQuestionIndex.get());
        }
        return null;
    }

    public void moveToNextQuestion() {
        currentQuestionIndex.incrementAndGet();
    }

    public void clearAnswers() {
        playerAnswers.clear();
    }

    public void addPlayer(String playerName) {
        if (!players.contains(playerName)) {
            players.add(playerName);
            playerScores.putIfAbsent(playerName, 0);
        }
    }

    public boolean hasNextQuestion() {
        return roomQuestions != null && currentQuestionIndex.get() < roomQuestions.size();
    }
}
