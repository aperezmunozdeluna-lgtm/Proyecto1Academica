package com.Dataicode.proyecto1.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Dataicode.proyecto1.entity.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    
    boolean existsByAlumnoIdAndCursoIdAndEstado(Long alumnoId, Long cursoId, String estado);

  
    long countByCursoIdAndEstado(Long cursoId, String estado);


    List<Inscripcion> findByAlumnoIdAndEstado(Long alumnoId, String estado);
    List<Inscripcion> findByCursoIdAndEstado(Long cursoId, String estado);
    List<Inscripcion> findByEstado(String estado);
}