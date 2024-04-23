package com.app.t1_daw1_kevinyzquierdo.controller;

import com.app.t1_daw1_kevinyzquierdo.models.DocenteEntity;
import com.app.t1_daw1_kevinyzquierdo.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestDocenteController {

    @Autowired
    private DocenteRepository docenteRepository;

    @GetMapping("/saludo")
    public String saludoCibertec() {
        return "Hola Cibertec";
    }


    @GetMapping("/home/docentes")
    public List<DocenteEntity> getDocentes() {
        return docenteRepository.findAll();
    }


}
