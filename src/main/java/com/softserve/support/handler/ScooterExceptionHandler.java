package com.softserve.support.handler;

import com.softserve.support.exceptions.ScooterNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ScooterExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ScooterNotFoundException.class})
    public ResponseEntity<String> noScooterFound(Exception e) {
        return ResponseEntity.status(404).body("Scooter with requested id not found");
    }

}
