package com.example.avances.dto;

import java.util.Date;

public interface PedidosAdminRestDto {
    int getidpedidos();
    int getmontototal();
    String getcliente();
    Date getfechahorapedido();
    String getdireccion();
    String getdistrito();

}
