package com.example.avances.controller;

import com.example.avances.entity.Cupones;
import com.example.avances.entity.Plato;
import com.example.avances.entity.Restaurante;
import com.example.avances.entity.Usuario;
import com.example.avances.repository.CuponesRepository;
import com.example.avances.repository.PedidosRepository;
import com.example.avances.repository.PlatoRepository;
import com.example.avances.repository.RestauranteRepository;
import com.example.avances.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminRestauranteController {

    @Autowired
    PlatoRepository platoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    CuponesRepository cuponesRepository;
    @Autowired
    PedidosRepository pedidosRepository;

    @GetMapping("/loginadmin")
    public String loginAdmin(){
        return "AdminRestaurantes/login";
    }

    @GetMapping("/register")
    public String registerAdmin(@ModelAttribute("usuario") Usuario usuario){
        return "AdminRestaurantes/register";
    }

    @PostMapping("/categorias")
    public String esperaConfirmacion(@ModelAttribute("restaurante") Restaurante restaurante,@RequestParam("categorias")String categorias){
        System.out.println(categorias);
        restauranteRepository.save(restaurante);
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

    @GetMapping("/registerRestaurante")
    public String registerRestaurante(@ModelAttribute("restaurante")Restaurante restaurante, Model model){
        Usuario usuario = new Usuario();
        usuario.setIdusuarios(14);
        restaurante.setUsuario(usuario);
        model.addAttribute("restaurante",restaurante);
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

    /************************PERFIL************************/

    @GetMapping("/perfil")
    public String perfilRestaurante(){
        return "AdminRestaurantes/perfilrestaurante";
    }

    /************************PLATOS************************/

    @GetMapping("/menu")
    public String verMenu(Model model){
        Integer idrestaurante = 1;
        model.addAttribute("listaPlatos", platoRepository.buscarPlatosPorIdRestaurante(idrestaurante));
        return "AdminRestaurantes/menu";
    }

    @GetMapping("/crearPlato")
    public String crearPlato(@ModelAttribute("plato") Plato plato, Model model){
        model.addAttribute("plato",plato);
        return "AdminRestaurantes/newPlato";
    }

    @GetMapping("/editarPlato")
    public String editarPlato(Model model, @RequestParam("idplato") int id, @ModelAttribute("plato") Plato plato){

        Optional<Plato> optionalPlato = platoRepository.findById(id);

        if(optionalPlato.isPresent()){
            plato = optionalPlato.get();
            model.addAttribute("plato",plato);
            return "AdminRestaurantes/newPlato";
        }else{
            return "redirect:/menu";
        }
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

    @GetMapping("/borrarPlato")
    public String borrarPlato(@RequestParam("idplato") int id, RedirectAttributes attr){

        Optional<Plato> optionalPlato = platoRepository.findById(id);

        if(optionalPlato.isPresent()){
            platoRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Producto borrado exitosamente");
        }
        return "redirect:/menu";
    }

    /************************CUPONES************************/

    @GetMapping("/cupones")
    public String verCupones(Model model, @RequestParam(value = "idrestaurante", required = false) Integer idrestaurante){
        idrestaurante = 1;
        List<Cupones> listaCupones = cuponesRepository.buscarCuponesPorIdRestaurante(idrestaurante);
        List<String> listaDisponibilidad = new ArrayList<String>();

        for (Cupones i: listaCupones){
            Date inicio = i.getFechainicio();
            Date fin = i.getFechafin();
            Date ahora = Date.valueOf(LocalDate.now());

            if(inicio.compareTo(ahora) > 0){
                listaDisponibilidad.add("No");
            }else if(fin.compareTo(ahora) < 0){
                listaDisponibilidad.add("No");
            }else if((inicio.compareTo(ahora) < 0) && (fin.compareTo(ahora) > 0)){
                listaDisponibilidad.add("Si");
            }else{
                listaDisponibilidad.add("No");
            }

        }

        model.addAttribute("listaCupones", listaCupones);
        model.addAttribute("listaDisponibilidad", listaDisponibilidad);
        return "AdminRestaurantes/cupones";

    }

    @GetMapping("/crearCupon")
    public String crearCupon(@ModelAttribute("cupon") Cupones cupon, Model model){
        int idrestaurante = 1;
        Restaurante restaurante = new Restaurante();
        restaurante.setIdrestaurante(idrestaurante);
        cupon.setRestaurante(restaurante);
        model.addAttribute("cupon",cupon);
        List<Plato> listaPlatos = platoRepository.buscarPlatosPorIdRestaurante(idrestaurante);
        model.addAttribute("listaPlatos",listaPlatos);
        return "AdminRestaurantes/generarCupon";
    }

    @GetMapping("/editarCupon")
    public String editarCupon(@ModelAttribute("cupon") Cupones cupon, @RequestParam("idcupon") int id, Model model){

        Optional<Cupones> optCupon = cuponesRepository.findById(id);

        if(optCupon.isPresent()){
            cupon = optCupon.get();
            Restaurante restaurante = cupon.getRestaurante();
            int idrestaurante = restaurante.getIdrestaurante();
            model.addAttribute("cupon",cupon);
            List<Plato> listaPlatos = platoRepository.buscarPlatosPorIdRestaurante(idrestaurante);
            model.addAttribute("listaPlatos",listaPlatos);
            return "AdminRestaurantes/generarCupon";
        }else{
            return "redirect:/cupones";
        }
    }

    @GetMapping("/borrarCupon")
    public String borrarCupon(@RequestParam("idcupon") int id, RedirectAttributes attr){

        Optional<Cupones> optCupon = cuponesRepository.findById(id);

        if(optCupon.isPresent()){
            cuponesRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Cupon borrado exitosamente");
            return "redirect:/cupones";
        }else{
            return "redirect:/cupones";
        }
    }

    @PostMapping("/guardarCupon")
    public String guardarCupon(@ModelAttribute("cupon") Cupones cupon, RedirectAttributes attr,
                               Model model){

        if (cupon.getIdcupones() == 0) {
            cuponesRepository.save(cupon);
            attr.addFlashAttribute("msg", "Cupon creado exitosamente");
            return "redirect:/cupones";

        } else {
            cuponesRepository.save(cupon);
            attr.addFlashAttribute("msg", "Cupon actualizado exitosamente");
            return "redirect:/cupones";
        }
    }

    /************************PEDIDOS************************/

    @GetMapping("/pedidos")
    public String verPedidos(Model model){
        model.addAttribute("listaPedidos",pedidosRepository.listaPedidos());
        return "AdminRestaurantes/pedidos";
    }

    /************************REPORTE************************/

    @GetMapping("/reporte")
    public String verReporte(Model model){
        Integer id = 1;
        model.addAttribute("listaPedidosPorFecha",pedidosRepository.listaPedidosReporteporFechamasantigua(id));
        model.addAttribute("listaGanancias",pedidosRepository.gananciaPorMes(id));
        return "AdminRestaurantes/reporte";
    }

    @PostMapping("/buscarReporte")
    public String searchReporte(@RequestParam("name") String name, Model model) {
        Integer id = 1;
        model.addAttribute("listaPedidosPorFecha",pedidosRepository.buscarPorReporte(name,id));
        model.addAttribute("listaGanancias",pedidosRepository.gananciaPorMes(id));
        return "AdminRestaurantes/reporte";
    }
}
