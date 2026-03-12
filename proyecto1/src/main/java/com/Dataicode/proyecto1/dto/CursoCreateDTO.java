package com.Dataicode.proyecto1.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CursoCreateDTO {

    private String nombre;
    private String categoria;
    private String nivel;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int plazas;
    private boolean activo;

}