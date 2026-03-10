package com.Dataicode.proyecto1.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.Dataicode.proyecto1.dto.InscripcionListDTO;
import com.Dataicode.proyecto1.entity.Alumno;
import com.Dataicode.proyecto1.entity.Curso;
import com.Dataicode.proyecto1.entity.Inscripcion;
import com.Dataicode.proyecto1.repository.AlumnoRepository;
import com.Dataicode.proyecto1.repository.CursoRepository;
import com.Dataicode.proyecto1.repository.InscripcionRepository;

@Service
public class InscripcionService {

    private static final Logger logger = LoggerFactory.getLogger(InscripcionService.class);

    private final InscripcionRepository inscripcionRepository;
    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository,
                              AlumnoRepository alumnoRepository,
                              CursoRepository cursoRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.alumnoRepository = alumnoRepository;
        this.cursoRepository = cursoRepository;
        
    }

    public List<Inscripcion> listarTodas() {
        logger.info("Listando todas las inscripciones");
        return inscripcionRepository.findAll();
    }

    public List<Alumno> alumnosActivos() {
        return alumnoRepository.findByActivoTrue();
    }

    public List<Curso> cursosActivosConPlazas() {
        List<Curso> activos = cursoRepository.findByActivoTrue();

        return activos.stream()
                .filter(c -> plazasRestantes(c.getId()) > 0)
                .collect(Collectors.toList());
    }

    public long plazasRestantes(Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId).orElse(null);
        if (curso == null) return 0;
        
        logger.info("Calculando plazas restantes del curso id={}", cursoId);
        long ocupadas = inscripcionRepository.countByCursoIdAndEstado(cursoId, "ACTIVA");
        return (long) curso.getPlazas() - ocupadas;
    }

    
    public void inscribir(Long alumnoId, Long cursoId, String observaciones) {
        logger.info("Intentando inscribir alumnoId={} en cursoId={}", alumnoId, cursoId);
        logger.warn("Alumno o curso no encontrado. alumnoId={} cursoId={}", alumnoId, cursoId);
        logger.warn("Alumno o curso inactivo. alumnoId={} cursoId={}", alumnoId, cursoId);
        logger.warn("Inscripción duplicada. alumnoId={} cursoId={}", alumnoId, cursoId);
        logger.warn("Curso sin plazas disponibles. cursoId={}", cursoId);
        logger.info("Inscripción creada correctamente. alumnoId={} cursoId={}", alumnoId, cursoId);
        
        Alumno alumno = alumnoRepository.findById(alumnoId).orElse(null);
        Curso curso = cursoRepository.findById(cursoId).orElse(null);

        if (alumno == null || curso == null) {
            logger.warn("No se puede inscribir: alumno o curso no existe. alumnoId={} cursoId={}", alumnoId, cursoId);
            return;
        }

      
        if (!alumno.isActivo() || !curso.isActivo()) {
            logger.warn("No se puede inscribir: alumno o curso inactivo. alumnoId={} cursoId={}", alumnoId, cursoId);
            return;
        }


        boolean duplicada = inscripcionRepository.existsByAlumnoIdAndCursoIdAndEstado(alumnoId, cursoId, "ACTIVA");
        if (duplicada) {
            logger.warn("Inscripción duplicada (ya existe ACTIVA). alumnoId={} cursoId={}", alumnoId, cursoId);
            return;
        }

       
        long restantes = plazasRestantes(cursoId);
        if (restantes <= 0) {
            logger.warn("Curso sin plazas. cursoId={}", cursoId);
            return;
        }

        Inscripcion i = new Inscripcion();
        i.setAlumno(alumno);
        i.setCurso(curso);
        i.setFechaInscripcion(LocalDateTime.now());
        i.setEstado("ACTIVA");
        i.setObservaciones(observaciones);

        inscripcionRepository.save(i);
        logger.info("Inscripción creada correctamente. alumnoId={} cursoId={}", alumnoId, cursoId);
    }

    public void cancelar(Long inscripcionId) {
        logger.info("Cancelando inscripción id={}", inscripcionId);

        Inscripcion ins = inscripcionRepository.findById(inscripcionId).orElse(null);
        if (ins == null) {
            logger.warn("No existe la inscripción id={}", inscripcionId);
            return;
        }

        ins.setEstado("CANCELADA");
        inscripcionRepository.save(ins);

        logger.info("Inscripción cancelada correctamente id={}", inscripcionId);
    }
    public List<Inscripcion> listarPorEstado(String estado) {
        logger.info("Listando inscripciones por estado: {}", estado);
        return inscripcionRepository.findByEstado(estado);
    }
    public List<InscripcionListDTO> listarTodasDTO() {
        logger.info("Listando todas las inscripciones en DTO");

        return inscripcionRepository.findAll().stream().map(i -> {
            InscripcionListDTO dto = new InscripcionListDTO();
            dto.setId(i.getId());
            dto.setAlumnoNombreCompleto(i.getAlumno().getNombre() + " " + i.getAlumno().getApellidos());
            dto.setCursoNombre(i.getCurso().getNombre());
            dto.setFechaInscripcion(i.getFechaInscripcion());
            dto.setEstado(i.getEstado());
            dto.setObservaciones(i.getObservaciones());
            return dto;
        }).collect(java.util.stream.Collectors.toList());
    }
}