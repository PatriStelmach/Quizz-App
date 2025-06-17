package com.pjatk.QuizzApp.Game.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoomPlayersDTO {
    private List<String> players;
}
