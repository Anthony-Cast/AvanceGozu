package com.example.avances.repository;

import com.example.avances.entity.Cupones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuponesRepository extends JpaRepository<Cupones,Integer> {

    @Query(value = "select * from cupones where restaurante_idrestaurante = ?1",
            nativeQuery = true)
    List<Cupones> buscarCuponesPorIdRestaurante(int idrestaurante);
}
