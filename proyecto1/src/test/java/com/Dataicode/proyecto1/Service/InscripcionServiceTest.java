package com.Dataicode.proyecto1.Service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Dataicode.proyecto1.entity.Alumno;
import com.Dataicode.proyecto1.entity.Curso;
import com.Dataicode.proyecto1.exception.BusinessException;
import com.Dataicode.proyecto1.repository.AlumnoRepository;
import com.Dataicode.proyecto1.repository.CursoRepository;
import com.Dataicode.proyecto1.repository.InscripcionRepository;
import com.Dataicode.proyecto1.service.InscripcionService;

class InscripcionServiceTest {

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private CursoRepository cursoRepository;

    private InscripcionService inscripcionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inscripcionService = new InscripcionService(inscripcionRepository, alumnoRepository, cursoRepository);
    }

    @Test
    void inscribir_debeLanzarBusinessException_siLaInscripcionEstaDuplicada() {
       
        Alumno alumno = new Alumno();
        alumno.setId(1L);
        alumno.setNombre("Alfonso");
        alumno.setApellidos("Perez");
        alumno.setEmail("alfonso@test.com");
        alumno.setFechaAlta(LocalDate.now());
        alumno.setActivo(true);

        Curso curso = new Curso();
        curso.setId(2L);
        curso.setNombre("Java");
        curso.setCategoria("Programacion");
        curso.setNivel("Basico");
        curso.setFechaInicio(LocalDate.now());
        curso.setFechaFin(LocalDate.now().plusDays(10));
        curso.setPlazas(20);
        curso.setActivo(true);

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));
        when(cursoRepository.findById(2L)).thenReturn(Optional.of(curso));
        when(inscripcionRepository.existsByAlumnoIdAndCursoIdAndEstado(1L, 2L, "ACTIVA")).thenReturn(true);

        assertThrows(BusinessException.class, () -> {
            inscripcionService.inscribir(1L, 2L, "Prueba");
        });
    }
}