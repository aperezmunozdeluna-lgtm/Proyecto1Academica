package com.Dataicode.proyecto1.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Curso curso;

    @Column(nullable = false)
    private LocalDate fechaInscripcion;
}