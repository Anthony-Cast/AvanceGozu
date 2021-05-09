package com.example.avances.dto;

import java.util.Date;

public interface PedidosBuscarReporteDto {
    int getnumeropedido();
    Date getfechahorapedido();
    String getnombre();
    String getapellidos();
    float getmontototal();
    String getnombreplato();
    String getmetodo();
    String getdistrito();
}
