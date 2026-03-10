package com.Dataicode.proyecto1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.Dataicode.proyecto1.dto.CursoCreateDTO;
import com.Dataicode.proyecto1.dto.CursoUpdateDTO;
import com.Dataicode.proyecto1.entity.Curso;
import com.Dataicode.proyecto1.service.CursoService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping("/cursos/list")
    public String listar(Model model) {
        model.addAttribute("cursos", cursoService.listarTodosDTO());
        return "cursos-list";
    }

    @GetMapping("/cursos/new")
    public String mostrarFormulario(Model model) {
        model.addAttribute("curso", new Curso());
        return "cursos-form";
    }

    @PostMapping("/cursos/save")
    public String guardar(CursoCreateDTO dto) {
        cursoService.crearDesdeDTO(dto);
        return "redirect:/cursos/list";
    }

    @GetMapping("/cursos/edit/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Curso curso = cursoService.buscarPorId(id);
        model.addAttribute("curso", curso);
        return "cursos-form";
    }

    @PostMapping("/cursos/update/{id}")
    public String actualizar(@PathVariable Long id, CursoUpdateDTO dto) {
        cursoService.actualizarDesdeDTO(id, dto);
        return "redirect:/cursos/list";
    }

    @GetMapping("/cursos/desactivar/{id}")
    public String desactivar(@PathVariable Long id) {
        cursoService.desactivar(id);
        return "redirect:/cursos/list";
    }
    @GetMapping("/cursos/detail/{id}")
    public String detalle(@PathVariable Long id, Model model) {

        model.addAttribute("curso", cursoService.detalleDTO(id));

        return "cursos-detail";
    }
    @GetMapping("/cursos/buscar")
    public String buscar(@RequestParam String categoria, Model model) {
        model.addAttribute("cursos", cursoService.buscarPorCategoria(categoria));
        return "cursos-list";
    }
}