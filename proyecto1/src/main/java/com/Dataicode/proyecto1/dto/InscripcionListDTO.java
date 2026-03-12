package com.Dataicode.proyecto1.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class InscripcionListDTO {

    private Long id;
    private String alumnoNombre;
    private String cursoNombre;
    private LocalDate fechaInscripcion;

}