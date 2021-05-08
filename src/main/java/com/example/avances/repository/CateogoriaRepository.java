package com.example.avances.repository;

import com.example.avances.entity.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CateogoriaRepository extends JpaRepository<Categorias,Integer> {
}
