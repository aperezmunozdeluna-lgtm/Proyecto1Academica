package com.Dataicode.proyecto1.service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.Dataicode.proyecto1.repository.InscripcionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.Dataicode.proyecto1.exception.NotFoundException;
import com.Dataicode.proyecto1.dto.AlumnoCreateDTO;
import com.Dataicode.proyecto1.dto.AlumnoDetailDTO;
import com.Dataicode.proyecto1.dto.AlumnoListDTO;
import com.Dataicode.proyecto1.dto.AlumnoUpdateDTO;
import com.Dataicode.proyecto1.entity.Alumno;
import com.Dataicode.proyecto1.entity.Inscripcion;
import com.Dataicode.proyecto1.repository.AlumnoRepository;

@Service
public class AlumnoService {


    private static final Logger logger = LoggerFactory.getLogger(AlumnoService.class);

   
    private final AlumnoRepository alumnoRepository;
    private final InscripcionRepository inscripcionRepository;
    
    public AlumnoService(AlumnoRepository alumnoRepository, InscripcionRepository inscripcionRepository) {
        this.alumnoRepository = alumnoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    
    public List<Alumno> listarTodos() {
        logger.info("Listando todos los alumnos");
        return alumnoRepository.findAll();
    }

    
    public Alumno crear(Alumno alumno) {
        logger.info("Creando alumno con email: {}", alumno.getEmail());

     
        alumno.setFechaAlta(LocalDate.now());
        alumno.setActivo(true);

        return alumnoRepository.save(alumno);
    }
    public void desactivar(Long id) {
        logger.info("Desactivando alumno con id: {}", id);
        logger.warn("Intento de desactivar alumno no existente id={}", id);
        logger.info("Alumno desactivado correctamente id={}", id);
        Alumno alumno = alumnoRepository.findById(id).orElse(null);

        if (alumno != null) {
            alumno.setActivo(false);
            alumnoRepository.save(alumno);
            logger.info("Alumno desactivado correctamente id: {}", id);
        } else {
            logger.warn("Intento de desactivar alumno que no existe. id: {}", id);
        }
        
    }
    public Alumno buscarPorId(Long id) {
        logger.info("Buscando alumno por id: {}", id);
        logger.warn("Alumno no encontrado con id={}", id);
        return alumnoRepository.findById(id).orElse(null);
        
    }

    public void actualizar(Long id, Alumno datosFormulario) {
        logger.info("Actualizando alumno id: {}", id);

        Alumno alumnoBD = alumnoRepository.findById(id).orElse(null);

        if (alumnoBD != null) {
          
            alumnoBD.setNombre(datosFormulario.getNombre());
            alumnoBD.setApellidos(datosFormulario.getApellidos());
            alumnoBD.setEmail(datosFormulario.getEmail());
            alumnoBD.setTelefono(datosFormulario.getTelefono());

            alumnoRepository.save(alumnoBD);
            logger.info("Alumno actualizado correctamente id: {}", id);
        } else {
            logger.warn("Intento de actualizar alumno que no existe. id: {}", id);
        }
    }
    public List<Alumno> buscarPorNombre(String nombre) {
        logger.info("Buscando alumnos por nombre que contenga: {}", nombre);
        return alumnoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    public List<Inscripcion> inscripcionesActivas(Long alumnoId) {
        logger.info("Buscando inscripciones ACTIVAS del alumno id={}", alumnoId);
        return inscripcionRepository.findByAlumnoIdAndEstado(alumnoId, "ACTIVA");
    }
    public void crearDesdeDTO(AlumnoCreateDTO dto) {

        logger.info("Creando alumno desde DTO con email: {}", dto.getEmail());
        logger.warn("No se ha podido crear el alumno con email={}", dto.getEmail());
        
        Alumno a = new Alumno();
        a.setNombre(dto.getNombre());
        a.setApellidos(dto.getApellidos());
        a.setEmail(dto.getEmail());
        a.setActivo(dto.isActivo());

        a.setFechaAlta(LocalDate.now()); 

        alumnoRepository.save(a);
    }

    public void actualizarDesdeDTO(Long id, AlumnoUpdateDTO dto) {

    logger.info("Actualizando alumno id={}", id);
    logger.warn("Intento de actualizar alumno no existente id={}", id);
    
    Alumno a = alumnoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Alumno no encontrado con id: " + id));

    a.setNombre(dto.getNombre());
    a.setApellidos(dto.getApellidos());
    a.setEmail(dto.getEmail());
    a.setTelefono(dto.getTelefono());
    a.setActivo(dto.isActivo());

   
    alumnoRepository.save(a);
    }
    public List<AlumnoListDTO> listarTodosDTO() {

        logger.info("Listando todos los alumnos en DTO");

        return alumnoRepository.findAll().stream().map(a -> {
            AlumnoListDTO dto = new AlumnoListDTO();
            dto.setId(a.getId());
            dto.setNombre(a.getNombre());
            dto.setApellidos(a.getApellidos());
            dto.setEmail(a.getEmail());
            dto.setActivo(a.isActivo());
            return dto;
        }).collect(Collectors.toList());
    }
    public AlumnoDetailDTO detalleDTO(Long id) {

        logger.info("Cargando detalle DTO del alumno id={}", id);

        Alumno a = alumnoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado con id: " + id));

        AlumnoDetailDTO dto = new AlumnoDetailDTO();
        dto.setId(a.getId());
        dto.setNombre(a.getNombre());
        dto.setApellidos(a.getApellidos());
        dto.setEmail(a.getEmail());
        dto.setTelefono(a.getTelefono());
        dto.setFechaAlta(a.getFechaAlta().toString());
        dto.setActivo(a.isActivo());

        return dto;
    }
}