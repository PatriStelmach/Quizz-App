package com.pjatk.QuizzApp.Game.Memory;

import com.pjatk.QuizzApp.Quiz.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class Room {
    private String id;
    private String ownerName;
    private List<String> players = new CopyOnWriteArrayList<>();
    private boolean started = false;

    public Room(String id, String ownerName) {
        this.id = id;
        this.ownerName = ownerName;
    }
}
