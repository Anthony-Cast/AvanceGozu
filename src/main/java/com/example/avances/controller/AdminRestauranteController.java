package com.example.avances.controller;

import com.example.avances.entity.Cupones;
import com.example.avances.entity.Pedidos;
import com.example.avances.entity.Plato;
import com.example.avances.entity.Restaurante;
import com.example.avances.entity.Usuario;
import com.example.avances.repository.CuponesRepository;
import com.example.avances.repository.PedidosRepository;
import com.example.avances.repository.PlatoRepository;
import com.example.avances.repository.RestauranteRepository;
import com.example.avances.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
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

    @GetMapping("/login")
    public String loginAdmin(){
        return "AdminRestaurantes/login";
    }

    @GetMapping("/register")
    public String registerAdmin(@ModelAttribute("usuario") Usuario usuario){
        return "AdminRestaurantes/register";
    }

    @PostMapping("/categorias")
    public String esperaConfirmacion(@ModelAttribute("restaurante") Restaurante restaurante,@RequestParam("imagen") MultipartFile file) throws IOException {
        try {
            restaurante.setFoto(file.getBytes());
            System.out.println(file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    @GetMapping("/borrarPlato")
    public String borrarPlato(@RequestParam("idplato") int id, RedirectAttributes attr){

        Optional<Plato> optionalPlato = platoRepository.findById(id);

        if(optionalPlato.isPresent()){
            platoRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Producto borrado exitosamente");
        }
        return "redirect:/menu";
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

    @GetMapping("/imagen")
    public ResponseEntity<byte[]> perfilRestaurante(Model model) {
        Optional<Restaurante> optional = restauranteRepository.findById(6);
        if (optional.isPresent()) {
            byte[] imagen = optional.get().getFoto();
            HttpHeaders httpHeaders=new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType("image/png"));
            return new ResponseEntity<>(imagen,httpHeaders, HttpStatus.OK);
        }
        return null;
    }
    @GetMapping("/perfil")
    public String perfilRestaurante(){
        return "AdminRestaurantes/perfilrestaurante";
    }

    @GetMapping("/menu")
    public String verMenu(Model model){
        model.addAttribute("listaPlatos", platoRepository.findAll());
        return "AdminRestaurantes/menu";
    }

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

    @GetMapping("/pedidos")
    public String verPedidos(Model model){

        model.addAttribute("listaPedidos",pedidosRepository.listaPedidos());
        return "AdminRestaurantes/pedidos";
    }

    @GetMapping("/reporte")
    public String verReporte(){
        return "AdminRestaurantes/reporte";
    }

}
