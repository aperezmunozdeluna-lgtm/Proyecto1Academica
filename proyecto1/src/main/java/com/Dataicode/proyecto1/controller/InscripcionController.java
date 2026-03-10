package com.Dataicode.proyecto1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.Dataicode.proyecto1.service.InscripcionService;
import com.Dataicode.proyecto1.dto.InscripcionCreateDTO;

@Controller
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @GetMapping("/inscripciones/list")
    public String listar(Model model) {
        model.addAttribute("inscripciones", inscripcionService.listarTodasDTO());
        return "inscripciones-list";
    }
    
    @GetMapping("/inscripciones/new")
    public String formulario(Model model) {
        model.addAttribute("alumnos", inscripcionService.alumnosActivos());
        model.addAttribute("cursos", inscripcionService.cursosActivosConPlazas());
        return "inscripciones-form";
    }

    @GetMapping("/inscripciones/cancelar/{id}")
    public String cancelar(@PathVariable Long id) {
        inscripcionService.cancelar(id);
        return "redirect:/inscripciones/list";
    }
    @GetMapping("/inscripciones/estado")
    public String porEstado(@RequestParam String estado, Model model) {
        model.addAttribute("inscripciones", inscripcionService.listarPorEstado(estado));
        return "inscripciones-list";
    }
    @PostMapping("/inscripciones/save")
    public String guardar(InscripcionCreateDTO dto) {
        inscripcionService.inscribir(dto.getAlumnoId(), dto.getCursoId(), dto.getObservaciones());
        return "redirect:/inscripciones/list";
    }
}