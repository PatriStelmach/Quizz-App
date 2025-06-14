package com.pjatk.QuizzApp.Game;

public class AnswerMessage {
    private String playerName;
    private String answer;

    public AnswerMessage() {}
    public AnswerMessage(String playerName, String answer) {
        this.playerName = playerName;
        this.answer = answer;
    }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
}
