package edu.comillas.icai.gitt.pat.spring.Practica4PAT.Excepciones;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ExcepcionPedidoIncorrecto extends RuntimeException {
    private List<FieldError> errores;
    public ExcepcionPedidoIncorrecto(BindingResult result) {
        this.errores = result.getFieldErrors();
    }
    public List<FieldError> getErrores() {
        return errores;
    }
}

