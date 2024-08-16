package com.naveen.project1.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> MymethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        Map<String,String> response=new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String filedname=((FieldError)err).getField();
            String message = err.getDefaultMessage();
            response.put(filedname,message);
        });
        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> myResourceNotFoundException(ResourceNotFoundException e) {
        String message = e.getMessage();
        //APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> myAPIException(APIException e)
    {
        String message=e.getMessage();
        return new ResponseEntity<String>(message,HttpStatus.BAD_REQUEST);
    }

}
