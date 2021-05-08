package com.example.avances.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name="restaurante")
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idrestaurante;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private String distrito;
    @Column(nullable = false)
    private String ruc;
    @Column(nullable = false)
    private String nombre;
    private Float calificacionpromedio;
    @OneToOne
    @JoinColumn(name="idadminrest")
    private Usuario usuario;
    private Byte foto;

    public Integer getIdrestaurante() {
        return idrestaurante;
    }

    public void setIdrestaurante(Integer idrestaurante) {
        this.idrestaurante = idrestaurante;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getCalificacionpromedio() {
        return calificacionpromedio;
    }

    public void setCalificacionpromedio(Float calificacionpromedio) {
        this.calificacionpromedio = calificacionpromedio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Byte getFoto() {
        return foto;
    }

    public void setFoto(Byte foto) {
        this.foto = foto;
    }
}
