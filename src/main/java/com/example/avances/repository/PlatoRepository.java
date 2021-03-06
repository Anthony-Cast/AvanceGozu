package com.example.avances.repository;

import com.example.avances.entity.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato,Integer> {

    @Query(value = "select * from plato where restaurante_idrestaurante = ?1",
            nativeQuery = true)
    List<Plato> buscarPlatosPorIdRestaurante(int idrestaurante);
}
