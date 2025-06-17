package com.pjatk.QuizzApp.Game.Memory;

import lombok.Data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Room {
    private String id;
    private String ownerName;
    private List<String> players = new CopyOnWriteArrayList<>();
    private boolean started = false;

    private List<RoomQuestion> roomQuestions;
    private AtomicInteger currentQuestionIndex = new AtomicInteger(0);

    public Room(String id, String ownerName) {
        this.id = id;
        this.ownerName = ownerName;
    }
    public RoomQuestion getCurrentQuestion() {
        if (roomQuestions == null || currentQuestionIndex.get() >= roomQuestions.size()) return null;
        return roomQuestions.get(currentQuestionIndex.get());
    }

    public RoomQuestion nextQuestion() {
        if (roomQuestions == null || currentQuestionIndex.incrementAndGet() >= roomQuestions.size()) return null;
        return roomQuestions.get(currentQuestionIndex.get());
    }

    public void setRoomQuestions(List<RoomQuestion> roomQuestions) {
        this.roomQuestions = roomQuestions;
        this.currentQuestionIndex.set(0);
    }

}
