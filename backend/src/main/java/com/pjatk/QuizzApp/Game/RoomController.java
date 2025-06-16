package com.pjatk.QuizzApp.Game;

import com.pjatk.QuizzApp.Game.Memory.Room;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/rooms")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;


    @PostMapping("/create")
    public ResponseEntity<Room> createRoom() throws AccessDeniedException {
        Room room = roomService.createRoom();
        return ResponseEntity.ok(room);
    }

    @GetMapping("/get")
    public ResponseEntity<Room> getRoom(@RequestParam String roomId) {
          Room room = roomService.getRoom(roomId);
          if (room == null) {
              return ResponseEntity.notFound().build();
            }
        return ResponseEntity.ok(room);
    }

}
