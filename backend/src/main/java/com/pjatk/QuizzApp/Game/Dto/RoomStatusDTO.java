package com.pjatk.QuizzApp.Game.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomStatusDTO {
    private String type;
    private boolean started;
}
