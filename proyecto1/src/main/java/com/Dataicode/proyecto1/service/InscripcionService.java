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
import com.Dataicode.proyecto1.exception.BusinessException;
import com.Dataicode.proyecto1.exception.NotFoundException;
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
 
            Alumno alumno = alumnoRepository.findById(alumnoId)
                    .orElseThrow(() -> new NotFoundException("Alumno no encontrado con id: " + alumnoId));

            Curso curso = cursoRepository.findById(cursoId)
                    .orElseThrow(() -> new NotFoundException("Curso no encontrado con id: " + cursoId));

            if (!alumno.isActivo()) {
                logger.warn("Alumno inactivo. alumnoId={}", alumnoId);
                throw new BusinessException("El alumno está inactivo");
            }

            if (!curso.isActivo()) {
                logger.warn("Curso inactivo. cursoId={}", cursoId);
                throw new BusinessException("El curso está inactivo");
            }

            boolean duplicada = inscripcionRepository.existsByAlumnoIdAndCursoIdAndEstado(alumnoId, cursoId, "ACTIVA");
            if (duplicada) {
                logger.warn("Inscripción duplicada. alumnoId={} cursoId={}", alumnoId, cursoId);
                throw new BusinessException("El alumno ya está inscrito en este curso");
            }

            long plazasOcupadas = inscripcionRepository.countByCursoIdAndEstado(cursoId, "ACTIVA");
            if (plazasOcupadas >= curso.getPlazas()) {
                logger.warn("Curso sin plazas disponibles. cursoId={}", cursoId);
                throw new BusinessException("No quedan plazas disponibles en el curso");
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

        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new NotFoundException("Inscripción no encontrada con id: " + inscripcionId));

        inscripcion.setEstado("CANCELADA");
        inscripcionRepository.save(inscripcion);

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