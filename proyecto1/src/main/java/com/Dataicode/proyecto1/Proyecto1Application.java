package com.Dataicode.proyecto1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.Dataicode.proyecto1.entity.Alumno;
import com.Dataicode.proyecto1.repository.AlumnoRepository;

@SpringBootApplication
public class Proyecto1Application {

	public static void main(String[] args) {
		SpringApplication.run(Proyecto1Application.class, args);
	}
}
