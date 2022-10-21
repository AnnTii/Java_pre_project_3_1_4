package spring_boot_security.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spring_boot_security.controller.HtmlController;

import java.time.LocalDateTime;

@ControllerAdvice(basePackageClasses = HtmlController.class)
public class HtmlControllerExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),"Error, user with this id not found");
        return new  ResponseEntity<>(userErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(RuntimeException e) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new  ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
