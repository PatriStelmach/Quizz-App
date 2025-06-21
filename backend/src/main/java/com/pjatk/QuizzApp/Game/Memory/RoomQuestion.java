package com.pjatk.QuizzApp.Game.Memory;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoomQuestion
{
    private String question;
    private List<String> answers;
    private int correctAnswerIndex;
    private int duration;
}