package com.Dataicode.proyecto1.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Dataicode.proyecto1.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByActivoTrue();
    List<Curso> findByCategoriaContainingIgnoreCase(String categoria);
}