package com.pjatk.QuizzApp.Game.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerMessage {
    private String playerName;
    private String questionId;
    private String answer;
}
