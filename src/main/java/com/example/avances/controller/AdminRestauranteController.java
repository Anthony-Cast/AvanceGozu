package com.example.avances.controller;

import com.example.avances.entity.Plato;
import com.example.avances.entity.Restaurante;
import com.example.avances.entity.Usuario;
import com.example.avances.repository.PlatoRepository;
import com.example.avances.repository.RestauranteRepository;
import com.example.avances.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AdminRestauranteController {

    @Autowired
    PlatoRepository platoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RestauranteRepository restauranteRepository;

    @GetMapping("/login")
    public String loginAdmin(){
        return "AdminRestaurantes/login";
    }

    @GetMapping("/register")
    public String registerAdmin(@ModelAttribute("usuario") Usuario usuario){
        return "AdminRestaurantes/register";
    }
    @PostMapping("/espera")
    public String esperaConfirmacion(){
        return "AdminRestaurantes/espera";
    }

    @PostMapping("/estado")
    public String estadoAdmin(@RequestParam("correo") String correo) {
        //Se valida con el correo si en la bd aparece como usuario aceptado o en espera y tendría dos posibles salidas
        if(correo!=""){
            return "AdminRestaurantes/restaurante";
        }
        return "redirect:/login";
    }

    @PostMapping("/guardarCupon")
    public String guardarCupon(){

        return "AdminRestaurantes/cupones";
    }

    @PostMapping("/guardarPlato")
    public String guardarPlato(@ModelAttribute("plato") Plato plato, RedirectAttributes attr, Model model){
        if (plato.getIdplato() == 0) {
            attr.addFlashAttribute("msg", "Plato creado exitosamente");
            platoRepository.save(plato);
            return "redirect:/menu";
        } else {
            platoRepository.save(plato);
            attr.addFlashAttribute("msg", "Plato actualizado exitosamente");
            return "redirect:/menu";
        }
    }
    @GetMapping("/crearCupon")
    public String crearCupon(){

        return "AdminRestaurantes/generarCupon";
    }

    @GetMapping("/crearPlato")
    public String crearPlato(@ModelAttribute("plato") Plato plato, Model model){
        model.addAttribute("plato",plato);
        return "AdminRestaurantes/newPlato";
    }

    @GetMapping("/registerRestaurante")
    public String registerRestaurante(@ModelAttribute("restaurante")Restaurante restaurante){
        return "AdminRestaurantes/registerRestaurante";
    }
    @GetMapping("/sinrestaurante")
    public String sinRestaurante(){
        return "AdminRestaurantes/restaurante";
    }

    @PostMapping("/validarpersona")
    public String validarPersona(){
        return "AdminRestaurantes/restaurante";
    }

    @GetMapping("/correoconfirmar")
    public String correoConfirmar(){

        return "AdminRestaurantes/correo";
    }
    @PostMapping("/guardaradmin")
    public String guardarAdmin(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "AdminRestaurantes/register";
        }
        else {
            usuarioRepository.save(usuario);
        }
        return"AdminRestaurantes/correo";
    }

    @GetMapping("/perfil")
    public String perfilRestaurante(){
        return "AdminRestaurantes/perfilrestaurante";
    }

    @GetMapping("/menu")
    public String verMenu(Model model){
        System.out.println("TRACE 1");
        model.addAttribute("listaPlatos", platoRepository.findAll());
        System.out.println("TRACE 2");
        return "AdminRestaurantes/menu";
    }

    @GetMapping("/cupones")
    public String verCupones(){

        return "AdminRestaurantes/cupones";
    }

    @GetMapping("/pedidos")
    public String verPedidos(){

        return "AdminRestaurantes/pedidos";
    }

    @GetMapping("/reporte")
    public String verReporte(){
        return "AdminRestaurantes/reporte";
    }
    /*@GetMapping("/prueba")
    public String verEjemplo(){
        return "html/recipe";
    }*/
}
