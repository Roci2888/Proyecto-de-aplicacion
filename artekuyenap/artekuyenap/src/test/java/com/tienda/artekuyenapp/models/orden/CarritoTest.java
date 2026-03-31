package com.tienda.artekuyenapp.models.orden;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CarritoTest {

    private Carrito carrito;
    private List<ItemCarrito> itemsDePrueba;

    @BeforeEach
    void setUp() {
        System.out.println("=== Inicializando prueba con datos reales de DB ===");

        itemsDePrueba = new ArrayList<>();

        carrito = new Carrito("INVITADO");
        carrito.setItems(itemsDePrueba);
        carrito.setId("693383ed572f947a4eb68d6d");
    }

    @AfterEach
    void tearDown() {
        System.out.println("=== Limpieza de datos de prueba ===");
        carrito = null;
        itemsDePrueba = null;
    }
        @Test
        @DisplayName("Carrito vacío para usuario 6935adf781886a753d8ee611")
        void carritoVacio_UsuarioSinItems() {
            Carrito carritoVacio = new Carrito("6935adf781886a753d8ee611");
            carritoVacio.setId("693653ea3e0e4d37399e6b45");
            carritoVacio.setItems(new ArrayList<>());

            assertEquals("6935adf781886a753d8ee611", carritoVacio.getUsuarioId());
            assertTrue(carritoVacio.getItems().isEmpty());
        }
}
