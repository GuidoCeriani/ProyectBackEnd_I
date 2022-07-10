package com.dh.integradora.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

    private static final Logger logger = LogManager.getLogger(GlobalExceptions.class);

    //para no tener que hacer try catch en cada metodo que llama a un servicio
    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<String> procesarErrores(ResourceNotFoundException ex){
        logger.error(ex.getMessage());
        return new ResponseEntity("Error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> procesarErrores(BadRequestException ex){
        logger.error(ex.getMessage());
        return new ResponseEntity("Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}