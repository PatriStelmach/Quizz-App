package ex.QuizzApp.Exceptions;

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
}

