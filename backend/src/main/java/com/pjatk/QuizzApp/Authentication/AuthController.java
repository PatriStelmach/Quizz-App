package com.pjatk.QuizzApp.Authentication;

import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.pjatk.QuizzApp.Exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController
{
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException
    {
        authService.register(request);
        return ResponseEntity.accepted().body("User registered successfully. Check your email for verification link.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthRequest request)
    {

        try
        {
        return ResponseEntity.ok(authService.authenticate(request));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(420).body("Wrong credentials");
        }
    }


    @GetMapping("/activate-acc")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) throws MessagingException
    {
        try
        {
            authService.activateAccount(token);
            return ResponseEntity.ok().body(authService.loginAfterActivate(token));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(690).body("Wrong credentials");
        }
    }


    @GetMapping("/test")
    public ResponseEntity<String> test()
    {
        return ResponseEntity.ok("działa");
    }

}
