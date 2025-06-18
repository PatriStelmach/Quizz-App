package com.pjatk.QuizzApp.Game.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRoomDTO {
    private String questionId;
    private String questionText;
    private List<String> options;
    private int timeLimitSeconds;

}
