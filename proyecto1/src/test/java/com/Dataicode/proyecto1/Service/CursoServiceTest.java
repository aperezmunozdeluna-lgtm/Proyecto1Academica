package com.Dataicode.proyecto1.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Dataicode.proyecto1.entity.Curso;
import com.Dataicode.proyecto1.repository.CursoRepository;
import com.Dataicode.proyecto1.repository.InscripcionRepository;
import com.Dataicode.proyecto1.service.CursoService;

class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private InscripcionRepository inscripcionRepository;

    private CursoService cursoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cursoService = new CursoService(cursoRepository, inscripcionRepository);
    }

    @Test
    void desactivar_debePonerActivoFalse() {
        
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Java");
        curso.setCategoria("Programacion");
        curso.setNivel("Basico");
        curso.setFechaInicio(LocalDate.now());
        curso.setFechaFin(LocalDate.now().plusDays(10));
        curso.setPlazas(20);
        curso.setActivo(true);

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        cursoService.desactivar(1L);

        assertFalse(curso.isActivo());
        verify(cursoRepository).save(curso);
    }
}