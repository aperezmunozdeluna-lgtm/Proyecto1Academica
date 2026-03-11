package com.Dataicode.proyecto1.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Dataicode.proyecto1.dto.AlumnoCreateDTO;
import com.Dataicode.proyecto1.entity.Alumno;
import com.Dataicode.proyecto1.repository.AlumnoRepository;
import com.Dataicode.proyecto1.repository.InscripcionRepository;
import com.Dataicode.proyecto1.service.AlumnoService;

class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private InscripcionRepository inscripcionRepository;

    private AlumnoService alumnoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alumnoService = new AlumnoService(alumnoRepository, inscripcionRepository);
    }

    @Test
    void crearDesdeDTO_debeGuardarAlumnoConFechaAlta() {
      
        AlumnoCreateDTO dto = new AlumnoCreateDTO();
        dto.setNombre("Alfonso");
        dto.setApellidos("Perez");
        dto.setEmail("alfonso@test.com");
        dto.setActivo(true);


        alumnoService.crearDesdeDTO(dto);

        ArgumentCaptor<Alumno> captor = ArgumentCaptor.forClass(Alumno.class);
        verify(alumnoRepository).save(captor.capture());

        Alumno alumnoGuardado = captor.getValue();

        assertEquals("Alfonso", alumnoGuardado.getNombre());
        assertEquals("Perez", alumnoGuardado.getApellidos());
        assertEquals("alfonso@test.com", alumnoGuardado.getEmail());
        assertTrue(alumnoGuardado.isActivo());
        assertEquals(LocalDate.now(), alumnoGuardado.getFechaAlta());
    }
}