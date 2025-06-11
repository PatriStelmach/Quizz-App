package com.pjatk.QuizzApp.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomException> UserNotFoundHandler()
    {
        CustomException customException = new CustomException();
        customException.setCode(404);
        customException.setMessage("User not found");
        return ResponseEntity.status(404).body(customException);
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<CustomException> QuizNotFoundHandler()
    {
        CustomException customException = new CustomException();
        customException.setCode(404);
        customException.setMessage("Quiz not found");
        return ResponseEntity.status(404).body(customException);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<CustomException> TokenNotFoundHandler()
    {
        CustomException customException = new CustomException();
        customException.setCode(404);
        customException.setMessage("Token not found");
        return ResponseEntity.status(404).body(customException);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<CustomException> UserExistsHandler()
    {
        CustomException customException = new CustomException();
        customException.setCode(409);
        customException.setMessage("User with given credentials already exists");
        return ResponseEntity.status(409).body(customException);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<CustomException> WrongPasswordHandler()
    {
        CustomException customException = new CustomException();
        customException.setCode(401);
        customException.setMessage("Wrong password!");
        return ResponseEntity.status(401).body(customException);
    }
}

