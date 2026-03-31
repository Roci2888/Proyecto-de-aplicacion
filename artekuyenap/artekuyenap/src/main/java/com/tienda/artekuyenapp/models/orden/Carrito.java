package com.tienda.artekuyenapp.models.orden;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "carritos")
public class Carrito {

    @Id
    private String id;

    private String usuarioId;

    private List<ItemCarrito> items = new ArrayList<>();

    public Carrito() {}

    public Carrito(String usuarioId) {
        this.usuarioId = usuarioId;
        this.items = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public List<ItemCarrito> getItems() { return items; }
    public void setItems(List<ItemCarrito> items) { this.items = items; }

    @Override
    public String toString() {
        return "Carrito{" +
                "id='" + id + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                ", items=" + items +
                '}';
    }
}
