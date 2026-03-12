package com.Dataicode.proyecto1.dto;

import lombok.Data;

@Data
public class AlumnoCreateDTO {

    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private boolean activo;

}