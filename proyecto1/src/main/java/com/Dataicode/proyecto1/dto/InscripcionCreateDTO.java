package com.Dataicode.proyecto1.dto;

public class InscripcionCreateDTO {

    private Long alumnoId;
    private Long cursoId;
    private String observaciones;

    public InscripcionCreateDTO() {}

    public Long getAlumnoId() { return alumnoId; }
    public void setAlumnoId(Long alumnoId) { this.alumnoId = alumnoId; }

    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}