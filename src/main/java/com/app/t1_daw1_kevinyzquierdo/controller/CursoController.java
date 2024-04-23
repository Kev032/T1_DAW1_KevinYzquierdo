package com.app.t1_daw1_kevinyzquierdo.controller;

import com.app.t1_daw1_kevinyzquierdo.models.CursoEntity;
import com.app.t1_daw1_kevinyzquierdo.models.DocenteEntity;
import com.app.t1_daw1_kevinyzquierdo.repository.CursoRepository;
import com.app.t1_daw1_kevinyzquierdo.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DocenteRepository docenteRepository;

//    localhost:9091/curso/
    @GetMapping("/")
    public String retornaSaludo(Model model) {
        List<CursoEntity> cursoLst = cursoRepository.findAll();
        model.addAttribute("cursos", cursoLst);
        model.addAttribute("curso", new CursoEntity());
        model.addAttribute("message", "Formulario para CRUD Curso");
        return "cursos";
    }


    //    localhost:9091/curso/guardar
    @PostMapping("/guardar")
    public String guardarCurso(@ModelAttribute("curso") CursoEntity curso, Model model) {
        cursoRepository.save(curso);


        List<CursoEntity> cursoLst = cursoRepository.findAll();
        model.addAttribute("cursos", cursoLst);
        model.addAttribute("curso", new CursoEntity());
        model.addAttribute("message", "Formulario para CRUD Curso");

        return "redirect:/curso/?success=Curso+agregado+correctamente&action=save";

    }


    //    localhost:9091/curso/delete
    @PostMapping("/delete")
    public String deleteCurso(@ModelAttribute("cursoId") int cursoId, Model model) {

        cursoRepository.deleteById(cursoId);
        List<CursoEntity> cursoLst = cursoRepository.findAll();
        model.addAttribute("curso", new CursoEntity());
        model.addAttribute("cursos", cursoLst);
        model.addAttribute("message", "Formulario para CRUD Curso");

        return "redirect:/curso/?success=Curso+eliminado+correctamente&action=delete";
    }

    @GetMapping("/mostrarcursos")
    public String mostrarCursos(Model model) {
        List<CursoEntity> cursosSinDocente = cursoRepository.findByDocenteIsNull();
        model.addAttribute("cursos", cursosSinDocente);
        model.addAttribute("message", "Asignar Curso a Docente");
        return "asignar-docente-curso";
    }

    @PostMapping("/asignarDocente")
    public String asignarDocenteACurso(@RequestParam("cursoId") int cursoId, Model model) {
        CursoEntity curso = cursoRepository.findById(cursoId).orElse(null);
        if (curso != null) {
            List<DocenteEntity> docentes = docenteRepository.findAll();
            model.addAttribute("curso", curso);
            model.addAttribute("docentes", docentes);
            return "seleccionar-docente";
        } else {
            return "redirect:/curso/?error=Curso+no+encontrado";
        }
    }

    @PostMapping("/guardarAsignacion")
    public String guardarAsignacion(@RequestParam("cursoId") String cursoIdStr, @RequestParam("docenteId") String docenteIdStr, Model model) {
        try {
            int cursoId = Integer.parseInt(cursoIdStr);
            int docenteId = Integer.parseInt(docenteIdStr);

            CursoEntity curso = cursoRepository.findById(cursoId).orElse(null);
            DocenteEntity docente = docenteRepository.findById(docenteId).orElse(null);

            if (curso != null && docente != null) {
                curso.setDocente(docente);
                cursoRepository.save(curso);
                return "redirect:/docente/inicio?success=Docente+asignado+al+curso+correctamente&action=assign";
            } else {
                return "redirect:/curso/asignarDocente?error=Curso+o+Docente+no+encontrado";
            }
        } catch (NumberFormatException | NoSuchElementException e) {
            return "redirect:/curso/asignarDocente?error=IDs+de+curso+o+docente+inválidos";
        }
    }
}
