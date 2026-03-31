package com.tienda.artekuyenapp.models.orden;

import com.tienda.artekuyenapp.models.usuario.Direccion;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "ordenes")
public class Orden {

    @Id
    private String id;

    private String usuarioId;
    private List<ItemCarrito> items = new ArrayList<>();
    private Double total;
    private String estado;  // PENDIENTE, PAGADA, ENVIADA, etc.
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private Direccion direccion;

    public Orden() {}

    public Orden(String usuarioId, List<ItemCarrito> items, Double total,
                 String estado, Direccion direccion) {
        this.usuarioId = usuarioId;
        this.items = items;
        this.total = total;
        this.estado = estado;
        this.direccion = direccion;
        this.fechaCreacion = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public List<ItemCarrito> getItems() { return items; }
    public void setItems(List<ItemCarrito> items) { this.items = items; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
}
