package edu.comillas.icai.gitt.pat.spring.Practica4PAT.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Pedido(@NotBlank String numero, @NotNull String Modelo, @NotBlank String Color, String Extra1, String Extra2 ){ }
