package com.example.avances.repository;

import com.example.avances.dto.PedidosAdminRestDto;
import com.example.avances.entity.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos,Integer> {
    @Query(value = "select p.idpedidos,p.montototal,p.calificacionrestaurante,concat(u.nombre,' ',u.apellidos)cliente,cast(p.fechahorapedido as DATE)fechahorapedido from pedidos p inner join usuarios u on p.idcliente = u.idusuarios\n" +
            "inner join restaurante r on p.restaurante_idrestaurante = r.idrestaurante\n" +
            "where r.idrestaurante=3 and p.estadorestaurante='pendiente'",nativeQuery = true)
    List<PedidosAdminRestDto> listaPedidos();


}
