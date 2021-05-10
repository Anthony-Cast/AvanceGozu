package com.example.avances.repository;

import com.example.avances.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante,Integer> {
    @Query(value="select*from restaurante\n" +
            "where idadminrest=?1",nativeQuery = true)
    Restaurante obtenerperfilRest(int id);
}
