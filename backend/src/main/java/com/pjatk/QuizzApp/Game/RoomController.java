package com.pjatk.QuizzApp.Game;

import com.pjatk.QuizzApp.Game.Memory.Room;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/rooms")
@AllArgsConstructor
public class RoomController
{

    private final RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<Room> createRoom(@RequestParam Integer quizId) throws AccessDeniedException
    {
        return ResponseEntity.ok(roomService.createRoom(quizId));
    }

    @GetMapping("/get")
    public ResponseEntity<Room> getRoom(@RequestParam String roomId)
    {
        Room room = roomService.getRoom(roomId);
        return room != null ? ResponseEntity.ok(room) : ResponseEntity.notFound().build();
    }
}
