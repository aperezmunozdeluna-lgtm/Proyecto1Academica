package com.Dataicode.proyecto1.dto;

import java.time.LocalDateTime;

public class InscripcionListDTO {

    private Long id;
    private String alumnoNombreCompleto;
    private String cursoNombre;
    private LocalDateTime fechaInscripcion;
    private String estado;
    private String observaciones;

    public InscripcionListDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlumnoNombreCompleto() {
        return alumnoNombreCompleto;
    }

    public void setAlumnoNombreCompleto(String alumnoNombreCompleto) {
        this.alumnoNombreCompleto = alumnoNombreCompleto;
    }

    public String getCursoNombre() {
        return cursoNombre;
    }

    public void setCursoNombre(String cursoNombre) {
        this.cursoNombre = cursoNombre;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}