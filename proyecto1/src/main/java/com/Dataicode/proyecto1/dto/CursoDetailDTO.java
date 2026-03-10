package com.Dataicode.proyecto1.dto;

import java.util.List;

public class CursoDetailDTO {

    private Long id;
    private String nombre;
    private String categoria;
    private String nivel;
    private String fechaInicio;
    private String fechaFin;
    private int plazas;
    private boolean activo;
    private int plazasOcupadas;
    private int plazasRestantes;
    private List<String> alumnosInscritos;
    
    public CursoDetailDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public int getPlazas() { return plazas; }
    public void setPlazas(int plazas) { this.plazas = plazas; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    public int getPlazasOcupadas() {
        return plazasOcupadas;
    }

    public void setPlazasOcupadas(int plazasOcupadas) {
        this.plazasOcupadas = plazasOcupadas;
    }

    public int getPlazasRestantes() {
        return plazasRestantes;
    }

    public void setPlazasRestantes(int plazasRestantes) {
        this.plazasRestantes = plazasRestantes;
    }

	public List<String> getAlumnosInscritos() {
		return alumnosInscritos;
	}

	public void setAlumnosInscritos(List<String> alumnosInscritos) {
		this.alumnosInscritos = alumnosInscritos;
	}


}