package com.Dataicode.proyecto1.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Dataicode.proyecto1.entity.Alumno;
import java.util.List;




public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
	List<com.Dataicode.proyecto1.entity.Alumno> findByActivoTrue();
	List<Alumno> findByNombreContainingIgnoreCase(String nombre);
	List<Alumno> findByEmailContainingIgnoreCase(String email);
  
}