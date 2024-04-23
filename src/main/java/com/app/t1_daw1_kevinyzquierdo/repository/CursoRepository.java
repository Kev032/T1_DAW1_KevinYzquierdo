package com.app.t1_daw1_kevinyzquierdo.repository;

import com.app.t1_daw1_kevinyzquierdo.models.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<CursoEntity, Integer> {

    List<CursoEntity> findByDocenteIsNull();
}
