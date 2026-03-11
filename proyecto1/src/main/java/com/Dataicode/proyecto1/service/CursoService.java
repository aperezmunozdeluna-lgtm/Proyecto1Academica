package com.Dataicode.proyecto1.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.Dataicode.proyecto1.repository.InscripcionRepository;
import com.Dataicode.proyecto1.dto.CursoCreateDTO;
import com.Dataicode.proyecto1.dto.CursoDetailDTO;
import com.Dataicode.proyecto1.dto.CursoListDTO;
import com.Dataicode.proyecto1.dto.CursoUpdateDTO;
import com.Dataicode.proyecto1.entity.Curso;
import com.Dataicode.proyecto1.exception.NotFoundException;
import com.Dataicode.proyecto1.repository.CursoRepository;

@Service
public class CursoService {

    private static final Logger logger = LoggerFactory.getLogger(CursoService.class);

    private final CursoRepository cursoRepository;
    private final InscripcionRepository inscripcionRepository;
    
    public CursoService(CursoRepository cursoRepository, InscripcionRepository inscripcionRepository) {
        this.cursoRepository = cursoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }
    public List<Curso> listarTodos() {
        logger.info("Listando todos los cursos");
        return cursoRepository.findAll();
    }

    public Curso buscarPorId(Long id) {
        logger.info("Buscando curso por id={}", id);

        return cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado con id: " + id));
    }
 
    public Curso crear(Curso curso) {
        logger.info("Creando curso: {}", curso.getNombre());
        curso.setActivo(true);
        return cursoRepository.save(curso);
    }

    public void actualizar(Long id, Curso datosFormulario) {
        logger.info("Actualizando curso id: {}", id);
        logger.warn("Intento de actualizar curso no existente id={}", id);

        Curso cursoBD = cursoRepository.findById(id).orElse(null);

        if (cursoBD != null) {
            cursoBD.setNombre(datosFormulario.getNombre());
            cursoBD.setCategoria(datosFormulario.getCategoria());
            cursoBD.setNivel(datosFormulario.getNivel());
            cursoBD.setFechaInicio(datosFormulario.getFechaInicio());
            cursoBD.setFechaFin(datosFormulario.getFechaFin());
            cursoBD.setPlazas(datosFormulario.getPlazas());

            cursoRepository.save(cursoBD);
            logger.info("Curso actualizado correctamente id: {}", id);
        } else {
            logger.warn("Intento de actualizar curso que no existe. id: {}", id);
        }
    }

    public void desactivar(Long id) {
        logger.info("Desactivando curso id={}", id);

        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado con id: " + id));

        curso.setActivo(false);
        cursoRepository.save(curso);

        logger.info("Curso desactivado correctamente id={}", id);
    }
    public long plazasOcupadas(Long cursoId) {
    	logger.info("Calculando plazas ocupadas del curso id={}", cursoId);
        return inscripcionRepository.countByCursoIdAndEstado(cursoId, "ACTIVA");
    }
    public List<Curso> buscarPorCategoria(String categoria) {
        logger.info("Buscando cursos por categoria que contenga: {}", categoria);
        return cursoRepository.findByCategoriaContainingIgnoreCase(categoria);
    }
    public void actualizarDesdeDTO(Long id, CursoUpdateDTO dto) {

        logger.info("Actualizando curso id={}", id);

        Curso c = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado con id: " + id));

        c.setNombre(dto.getNombre());
        c.setCategoria(dto.getCategoria());
        c.setNivel(dto.getNivel());
        c.setFechaInicio(dto.getFechaInicio());
        c.setFechaFin(dto.getFechaFin());
        c.setPlazas(dto.getPlazas());
        c.setActivo(dto.isActivo());

        cursoRepository.save(c);

        logger.info("Curso actualizado correctamente id={}", id);
    }
    public void crearDesdeDTO(CursoCreateDTO dto) {

        logger.info("Creando curso desde DTO: {}", dto.getNombre());

        Curso c = new Curso();
        c.setNombre(dto.getNombre());
        c.setCategoria(dto.getCategoria());
        c.setNivel(dto.getNivel());
        c.setFechaInicio(dto.getFechaInicio());
        c.setFechaFin(dto.getFechaFin());
        c.setPlazas(dto.getPlazas());
        c.setActivo(dto.isActivo());

        cursoRepository.save(c);
    }
    public List<CursoListDTO> listarTodosDTO() {

        logger.info("Listando todos los cursos en DTO");

        return cursoRepository.findAll().stream().map(c -> {
            CursoListDTO dto = new CursoListDTO();
            dto.setId(c.getId());
            dto.setNombre(c.getNombre());
            dto.setCategoria(c.getCategoria());
            dto.setNivel(c.getNivel());
            dto.setFechaInicio(c.getFechaInicio().toString());
            dto.setFechaFin(c.getFechaFin().toString());
            dto.setPlazas(c.getPlazas());
            dto.setActivo(c.isActivo());
            return dto;
        }).collect(Collectors.toList());
    }
    public CursoDetailDTO detalleDTO(Long id) {

        logger.info("Cargando detalle del curso id={}", id);

        Curso c = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado con id: " + id));

        long plazasOcupadas = inscripcionRepository.countByCursoIdAndEstado(id, "ACTIVA");
        long plazasRestantes = c.getPlazas() - plazasOcupadas;

        List<String> alumnosInscritos = inscripcionRepository.findByCursoIdAndEstado(id, "ACTIVA")
                .stream()
                .map(i -> i.getAlumno().getNombre() + " " + i.getAlumno().getApellidos())
                .collect(Collectors.toList());

        CursoDetailDTO dto = new CursoDetailDTO();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setCategoria(c.getCategoria());
        dto.setNivel(c.getNivel());
        dto.setFechaInicio(c.getFechaInicio().toString());
        dto.setFechaFin(c.getFechaFin().toString());
        dto.setPlazas(c.getPlazas());
        dto.setActivo(c.isActivo());

        dto.setPlazasOcupadas((int) plazasOcupadas);
        dto.setPlazasRestantes((int) plazasRestantes);
        dto.setAlumnosInscritos(alumnosInscritos);

        return dto;
    }
}