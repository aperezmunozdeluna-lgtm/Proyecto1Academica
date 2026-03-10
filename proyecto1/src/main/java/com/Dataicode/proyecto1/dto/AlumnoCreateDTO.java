package com.Dataicode.proyecto1.dto;

public class AlumnoCreateDTO {

    private String nombre;
    private String apellidos;
    private String email;
    private boolean activo;

    public AlumnoCreateDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}