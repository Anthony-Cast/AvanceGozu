package com.example.avances.entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;



@Entity
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuario;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String contraseniahash;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false)
    private Date fechanacimiento;
    @Column(nullable = false)
    private String sexo;
    @Column(nullable = false)
    private String dni;
    private Integer comisionventa;
    @Column(nullable = false)
    private String rol;
    private Integer cuentaactiva;
    private Date ultimafechaingreso;

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseniahash() {
        return contraseniahash;
    }

    public void setContraseniahash(String contraseniahash) {
        this.contraseniahash = contraseniahash;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getComisionventa() {
        return comisionventa;
    }

    public void setComisionventa(Integer comisionventa) {
        this.comisionventa = comisionventa;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getCuentaactiva() {
        return cuentaactiva;
    }

    public void setCuentaactiva(Integer cuentaactiva) {
        this.cuentaactiva = cuentaactiva;
    }

    public Date getUltimafechaingreso() {
        return ultimafechaingreso;
    }

    public void setUltimafechaingreso(Date ultimafechaingreso) {
        this.ultimafechaingreso = ultimafechaingreso;
    }
}
