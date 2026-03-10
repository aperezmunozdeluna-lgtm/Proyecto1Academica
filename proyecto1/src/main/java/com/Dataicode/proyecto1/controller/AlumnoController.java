package com.Dataicode.proyecto1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Dataicode.proyecto1.dto.AlumnoCreateDTO;
import com.Dataicode.proyecto1.dto.AlumnoUpdateDTO;
import com.Dataicode.proyecto1.entity.Alumno;
import com.Dataicode.proyecto1.service.AlumnoService;
import org.springframework.web.bind.annotation.PostMapping;
import com.Dataicode.proyecto1.entity.Alumno;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.Dataicode.proyecto1.entity.Alumno;
import org.springframework.web.bind.annotation.RequestParam;
import com.Dataicode.proyecto1.repository.AlumnoRepository;

@Controller
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping("/alumnos/list")
    public String listar(Model model) {
        model.addAttribute("alumnos", alumnoService.listarTodosDTO());
        return "alumnos-list";
    }
    @GetMapping("/alumnos/new")
    public String mostrarFormulario(Model model) {
        model.addAttribute("alumno", new Alumno());
        return "alumnos-form";
    }
    @PostMapping("/alumnos/save")
    public String guardar(AlumnoCreateDTO dto) {
        alumnoService.crearDesdeDTO(dto);
        return "redirect:/alumnos/list";
    }
    @GetMapping("/alumnos/desactivar/{id}")
    public String desactivar(@PathVariable Long id) {
        alumnoService.desactivar(id);
        return "redirect:/alumnos/list";
    }
    @GetMapping("/alumnos/edit/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Alumno alumno = alumnoService.buscarPorId(id);
        model.addAttribute("alumno", alumno);
        return "alumnos-form";
    }
    @PostMapping("/alumnos/update/{id}")
    public String actualizar(@PathVariable Long id, AlumnoUpdateDTO dto) {
        alumnoService.actualizarDesdeDTO(id, dto);
        return "redirect:/alumnos/list";
    }
    @GetMapping("/alumnos/buscar")
    public String buscar(@RequestParam String nombre, Model model) {
        model.addAttribute("alumnos", alumnoService.buscarPorNombre(nombre));
        return "alumnos-list";
    }
    @GetMapping("/alumnos/detail/{id}")
    public String detalle(@PathVariable Long id, Model model) {

        model.addAttribute("alumno", alumnoService.detalleDTO(id));
        model.addAttribute("inscripciones", alumnoService.inscripcionesActivas(id));

        return "alumnos-detail";
    }
}