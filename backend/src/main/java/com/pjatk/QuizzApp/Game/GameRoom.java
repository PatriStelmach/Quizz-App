package com.pjatk.QuizzApp.Game;

import com.pjatk.QuizzApp.Quiz.Quiz;

import java.util.ArrayList;
import java.util.List;


public class GameRoom {
    private String roomId;
    private Quiz quiz;
    private List<GamePlayer> players = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private boolean started = false;
}