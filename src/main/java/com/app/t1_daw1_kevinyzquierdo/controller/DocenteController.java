package com.app.t1_daw1_kevinyzquierdo.controller;

import com.app.t1_daw1_kevinyzquierdo.models.DocenteEntity;
import com.app.t1_daw1_kevinyzquierdo.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/docente")
public class DocenteController {

    @Autowired
    private DocenteRepository docenteRepository;


    //    localhost:9091/delete
    @PostMapping("/delete")
    public String deleteDocente(@ModelAttribute("docenteId") int docenteId, Model model) {

//        System.out.println(docenteId);
        docenteRepository.deleteById(docenteId);
        List<DocenteEntity> docenteLst = docenteRepository.findAll();
        model.addAttribute("docente", new DocenteEntity());
        model.addAttribute("docentes", docenteLst);
        model.addAttribute("message", "Formulario para CRUD Docente");

        return "redirect:/docente/inicio?success=Docente+eliminado+correctamente&action=delete";
    }


    //    localhost:9091/docentes
    @GetMapping({"/docentes", "/", "/inicio"})
    public String retornaSaludo(Model model) {

        List<DocenteEntity> docenteLst = docenteRepository.findAll();

        for (DocenteEntity docente : docenteLst) {
            System.out.println(docente.getName() + " " + docente.getLastName());
        }

        model.addAttribute("docentes", docenteLst);
        model.addAttribute("docente", new DocenteEntity());
        model.addAttribute("message", "Formulario para CRUD Docente");
        return "index";
    }


    @PostMapping("/guardar")
    public String guardarDocente(@ModelAttribute("docente") DocenteEntity docente, Model model) {

        docenteRepository.save(docente);
        List<DocenteEntity> docenteLst = docenteRepository.findAll();
        model.addAttribute("docentes", docenteLst);
        model.addAttribute("docente", new DocenteEntity());
        model.addAttribute("message", "Formulario para CRUD Docente");
        return "redirect:/docente/inicio?success=Docente+agregado+correctamente&action=save";

    }


    //    localhost:9091/delete
    @PostMapping("/edit")
    public String editDocente(@ModelAttribute("docenteId") int docenteId, Model model) {

        System.out.println(docenteId);
        Optional<DocenteEntity> docenteEntity = docenteRepository.findById(docenteId);
        List<DocenteEntity> docenteLst = docenteRepository.findAll();
        model.addAttribute("docente", docenteEntity.isPresent() ? docenteEntity : new DocenteEntity());
        model.addAttribute("docentes", docenteLst);
        model.addAttribute("message", "Formulario para CRUD Docente");

        return "index";
    }

}
