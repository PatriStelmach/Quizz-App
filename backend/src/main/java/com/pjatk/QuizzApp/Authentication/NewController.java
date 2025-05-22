package com.pjatk.QuizzApp.Authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/secured")
@RestController
public class NewController
{
    @GetMapping("/home")
    ResponseEntity<String> home()
    {
        return ResponseEntity.ok("Secured Home Page");
    }
}
