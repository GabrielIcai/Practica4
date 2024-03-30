package edu.comillas.icai.gitt.pat.spring.Practica4PAT.errores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ManejadorErroresGlobales {
    @ResponseBody
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity errorLanzado(ResponseStatusException ex) {
        return new ResponseEntity<>(ex.getStatusCode());
    }
}

