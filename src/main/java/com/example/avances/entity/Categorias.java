package com.example.avances.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="categorias")
public class Categorias {
    @Id
    private Integer idcategorias;
    private String nombrecategoria;

}
