package com.pjatk.QuizzApp.User;

import com.pjatk.QuizzApp.User.DTO.ApiResponse;
import com.pjatk.QuizzApp.User.DTO.UserDTO;
import com.pjatk.QuizzApp.User.DTO.PasswordChangeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController
{
    private final UserService userService;
   private final Mapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@Valid @PathVariable int id)
    {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@Valid @PathVariable String username)
    {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok().body(mapper.toDto(user));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@Valid @PathVariable String email)
    {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok().body(mapper.toDto(user));
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers()
    {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users.stream()
                .map(mapper::toDto)
                .toList());
    }

    @GetMapping("/avatar/{username}")
    public ResponseEntity<?> getAvatar(@PathVariable String username) throws IOException
    {
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpg")
                .body(userService.getAvatar(username));
    }

    @PatchMapping("/change-username")
    public ResponseEntity<String> changeUsername(@RequestParam String newUsername)
    {
        String username = userService.getLoggedUsername();
        return ResponseEntity.ok().body(userService.changeUsername(username, newUsername));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody PasswordChangeRequest request)
    {
        String username = userService.getLoggedUsername();
        userService.changePassword(username, request.getPassword(), request.getNewPassword());
        return ResponseEntity.ok().body(new ApiResponse<>("success", "password changed", null));
    }

    @PatchMapping("/change-avatar")
    public ResponseEntity<ApiResponse<Void>> changeAvatar(@Valid @RequestPart MultipartFile avatar) throws IOException
    {
        String username = userService.getLoggedUsername();
        userService.changeAvatar(username, avatar);
        return ResponseEntity.ok().body(new ApiResponse<>("success", "Avatar updated", null));
    }

    @PatchMapping("/change-bio")
    public ResponseEntity<ApiResponse<String>> changeBio(@Valid @RequestParam String bio)
    {
        String username = userService.getLoggedUsername();
        userService.changeBio(username, bio);
        return ResponseEntity.ok().body(new ApiResponse<>("success", "Bio updated", bio));
    }

    @PatchMapping("/delete-role")
    public ResponseEntity<ApiResponse<String>> deleteRole(@Valid @RequestParam String roleName)
    {
        String username = userService.getLoggedUsername();
        userService.deleteRole(username, roleName);
        return ResponseEntity.ok().body(new ApiResponse<>("success", "role: " + roleName, "deleted"));
    }

    @PatchMapping("/add-role")
    public ResponseEntity<ApiResponse<Void>> addRole()
    {
        return null;
    }



}
