package com.Dataicode.proyecto1.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class InscripcionListDTO {

    private Long id;
    private String alumnoNombreCompleto;
    private String cursoNombre;
    private LocalDateTime fechaInscripcion;
    private String estado;
    private String observaciones;

}