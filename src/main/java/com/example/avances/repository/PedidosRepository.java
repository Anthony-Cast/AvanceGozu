package com.example.avances.repository;

import com.example.avances.dto.*;
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

    @Query(value = "select \n" +
            "p.idpedidos as numeropedido,\n" +
            "p.fechahorapedido as fechahorapedido, \n" +
            "u.nombre as nombre, \n" +
            "u.apellidos as apellidos,\n" +
            "p.montototal as montototal, \n" +
            "pt.nombre as nombreplato,\n" +
            "m.metodo as metodo, \n" +
            "d.distrito as distrito\n" +
            "from pedidos p \n" +
            "inner join usuarios u on u.idusuarios = p.idcliente \n" +
            "inner join direcciones d on d.iddirecciones = p.direccionentrega \n" +
            "inner join metodospago m on m.idmetodospago = p.idmetodopago\n" +
            "inner join pedidos_has_plato pl on pl.pedidos_idpedidos = p.idpedidos\n" +
            "inner join plato pt on pt.idplato = pl.plato_idplato\n" +
            "where p.estadorestaurante = 'entregado'and p.restaurante_idrestaurante = ?1\n" +
            "order by p.fechahorapedido asc", nativeQuery = true)
    List<PedidosReporteDto> listaPedidosReporteporFechamasantigua(Integer id);

    @Query(value = "select\n" +
            "    p.idpedidos as numeropedido,\n" +
            "    p.fechahorapedido as fechahorapedido,\n" +
            "    u.nombre as nombre,\n" +
            "    u.apellidos as apellidos,\n" +
            "    p.montototal as montototal,\n" +
            "    pt.nombre as nombreplato,\n" +
            "    m.metodo as metodo,\n" +
            "    d.distrito as distrito\n" +
            "    from pedidos p\n" +
            "    inner join usuarios u on u.idusuarios = p.idcliente\n" +
            "    inner join direcciones d on d.iddirecciones = p.direccionentrega\n" +
            "    inner join metodospago m on m.idmetodospago = p.idmetodopago\n" +
            "    inner join pedidos_has_plato pl on pl.pedidos_idpedidos = p.idpedidos\n" +
            "    inner join plato pt on pt.idplato = pl.plato_idplato\n" +
            "    where p.estadorestaurante = 'entregado' and p.restaurante_idrestaurante = ?2\n" +
            "    and (p.idpedidos like %?1% or p.fechahorapedido like %?1%\n" +
            "            or u.nombre like %?1% or u.apellidos like %?1%\n" +
            "            or p.montototal like %?1% or pt.nombre like %?1%\n" +
            "            or m.metodo like %?1% or d.distrito like %?1%)\n" +
            "    order by p.fechahorapedido asc", nativeQuery = true)
    List<PedidosReporteDto> buscarPorReporte(String name, Integer id);

    @Query(value = "select \n" +
            "MONTHNAME(p.fechahorapedido) as mes,\n" +
            "YEAR(p.fechahorapedido) as anio,\n" +
            "sum(p.montototal) as ganancia from pedidos p where p.restaurante_idrestaurante = ?1\n" +
            "group by MONTHNAME(p.fechahorapedido)", nativeQuery = true)
    List<PedidosGananciaMesDto> gananciaPorMes(Integer id);

    @Query(value="select pt.nombre as nombreplato,\n" +
            "count(pl.plato_idplato) as cantidad,\n" +
            "sum(p.montototal) as ganancia\n" +
            "from plato pt\n" +
            "inner join pedidos_has_plato pl on pl.plato_idplato = pt.idplato\n" +
            "inner join pedidos p on p.idpedidos = pl.pedidos_idpedidos\n" +
            "where p.restaurante_idrestaurante = ?1\n" +
            "group by pt.nombre order by cantidad desc limit 5",nativeQuery = true)
    List<PedidosTop5Dto> platosMasVendidos(Integer id);

    @Query(value="select pt.nombre as nombreplato,\n" +
            "count(pl.plato_idplato) as cantidad,\n" +
            "sum(p.montototal) as ganancia\n" +
            "from plato pt\n" +
            "inner join pedidos_has_plato pl on pl.plato_idplato = pt.idplato\n" +
            "inner join pedidos p on p.idpedidos = pl.pedidos_idpedidos\n" +
            "where p.restaurante_idrestaurante = ?1\n" +
            "group by pt.nombre order by cantidad asc limit 5;",nativeQuery = true)
    List<PedidosTop5Dto> platosMenosVendidos(Integer id);

    @Query(value = "select \n" +
            "p.calificacionrestaurante as calificacion,\n" +
            "p.comentario as comentario\n" +
            "from pedidos p where p.restaurante_idrestaurante = ?1\n" +
            "order by p.calificacionrestaurante desc", nativeQuery = true)
    List<ComentariosDto>comentariosUsuarios(Integer id);

    @Query(value = "select \n" +
            "p.calificacionrestaurante as calificacion,\n" +
            "p.comentario as comentario\n" +
            "from pedidos p where p.restaurante_idrestaurante = ?2\n" +
            "and p.calificacionrestaurante like %?1%\n" +
            "order by p.calificacionrestaurante desc", nativeQuery = true)
    List<ComentariosDto>buscarComentariosUsuarios(String name, Integer id);

    @Query(value="select \n" +
            "avg(p.calificacionrestaurante) as calificacionpromedio\n" +
            "from pedidos p where p.restaurante_idrestaurante = 1", nativeQuery = true)
    List<AvgCalifDto>calificacionPromedio(Integer id);
}
