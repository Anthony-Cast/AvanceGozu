package com.example.avances.repository;

import com.example.avances.entity.Cupones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuponesRepository extends JpaRepository<Cupones,Integer> {
}
