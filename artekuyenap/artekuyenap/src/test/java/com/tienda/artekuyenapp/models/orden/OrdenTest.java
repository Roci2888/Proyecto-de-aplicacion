package com.tienda.artekuyenapp.models.orden;

import com.tienda.artekuyenapp.models.usuario.Direccion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class OrdenTest {

    private Orden orden;
    private List<ItemCarrito> itemsDePrueba;
    private Direccion direccionDePrueba;
    private LocalDateTime fechaCreacionInicial;

    @BeforeEach
    void setUp() {
        System.out.println("=== Configurando datos de prueba para Orden ===");

        // Configurar fecha de referencia
        fechaCreacionInicial = LocalDateTime.now().minusMinutes(1);

        // Crear items de prueba (basados en datos reales)
        itemsDePrueba = new ArrayList<>();


        // Crear orden con constructor completo
        orden = new Orden(
                "usuario123",
                itemsDePrueba,
                125000.0,  // Total: 50000 + 15000 + 60000
                "PENDIENTE",
                direccionDePrueba
        );

        // Establecer ID manualmente (simulando MongoDB)
        orden.setId("ord_abc123def456");
    }

    @AfterEach
    void tearDown() {
        System.out.println("=== Limpiando recursos de prueba ===");
        orden = null;
        itemsDePrueba = null;
        direccionDePrueba = null;
        fechaCreacionInicial = null;
    }

    @Nested
    @DisplayName("Pruebas de Construcción e Inicialización")
    class PruebasConstruccion {

        @Test
        @DisplayName("Constructor sin parámetros crea orden válida")
        void constructorVacio_CreaOrdenValida() {
            // Arrange & Act
            Orden ordenVacia = new Orden();

            // Assert
            assertNotNull(ordenVacia, "La orden no debería ser null");
            assertNotNull(ordenVacia.getItems(), "Items no debería ser null");
            assertTrue(ordenVacia.getItems().isEmpty(), "Items debería estar vacío");
            assertNotNull(ordenVacia.getFechaCreacion(), "Fecha creación no debería ser null");
            assertThat(ordenVacia.getFechaCreacion())
                    .isCloseTo(LocalDateTime.now(), within(5, ChronoUnit.SECONDS));
        }

        @Test
        @DisplayName("Constructor completo inicializa todos los campos")
        void constructorCompleto_InicializaCorrectamente() {
            // Arrange
            LocalDateTime antes = LocalDateTime.now().minusSeconds(1);

            // Act
            Orden nuevaOrden = new Orden(
                    "nuevoUsuario",
                    itemsDePrueba,
                    99999.99,
                    "PAGADA",
                    direccionDePrueba
            );

            LocalDateTime despues = LocalDateTime.now().plusSeconds(1);

            // Assert
            assertEquals("nuevoUsuario", nuevaOrden.getUsuarioId());
            assertEquals(itemsDePrueba, nuevaOrden.getItems());
            assertEquals(99999.99, nuevaOrden.getTotal(), 0.01);
            assertEquals("PAGADA", nuevaOrden.getEstado());
            assertEquals(direccionDePrueba, nuevaOrden.getDireccion());

            // Verificar que fechaCreacion está entre antes y después
            assertTrue(nuevaOrden.getFechaCreacion().isAfter(antes) ||
                    nuevaOrden.getFechaCreacion().isEqual(antes));
            assertTrue(nuevaOrden.getFechaCreacion().isBefore(despues) ||
                    nuevaOrden.getFechaCreacion().isEqual(despues));
        }
    }

    @Nested
    @DisplayName("Pruebas de Getters y Setters")
    class PruebasGettersSetters {

        @Test
        @DisplayName("Getter y Setter de ID funcionan correctamente")
        void setIdYGetId_ActualizanCorrectamente() {
            // Arrange
            String nuevoId = "nuevo_id_789";

            // Act
            orden.setId(nuevoId);

            // Assert
            assertEquals(nuevoId, orden.getId());
        }

        @Test
        @DisplayName("Getter y Setter de usuarioId funcionan correctamente")
        void setUsuarioIdYGetUsuarioId_ActualizanCorrectamente() {
            // Arrange
            String nuevoUsuarioId = "usuario_nuevo_456";

            // Act
            orden.setUsuarioId(nuevoUsuarioId);

            // Assert
            assertEquals(nuevoUsuarioId, orden.getUsuarioId());
        }


        @Test
        @DisplayName("Getter y Setter de total funcionan correctamente")
        void setTotalYGetTotal_ActualizanCorrectamente() {
            Double nuevoTotal = 999999.99;

            orden.setTotal(nuevoTotal);

            assertEquals(nuevoTotal, orden.getTotal(), 0.01);
        }

        @Test
        @DisplayName("Total null es manejado correctamente")
        void setTotalNull_EsPermitido() {
            orden.setTotal(null);

            assertNull(orden.getTotal());
        }

        @Test
        @DisplayName("Getter y Setter de estado funcionan correctamente")
        void setEstadoYGetEstado_ActualizanCorrectamente() {
            String[] estadosValidos = {"PENDIENTE", "PAGADA", "ENVIADA", "ENTREGADA", "CANCELADA"};

            for (String estado : estadosValidos) {
                orden.setEstado(estado);

                assertEquals(estado, orden.getEstado());
            }
        }

        @Test
        @DisplayName("Getter y Setter de fechaCreacion funcionan correctamente")
        void setFechaCreacionYGetFechaCreacion_ActualizanCorrectamente() {
            LocalDateTime nuevaFecha = LocalDateTime.of(2024, 1, 15, 10, 30, 0);

            orden.setFechaCreacion(nuevaFecha);

            assertEquals(nuevaFecha, orden.getFechaCreacion());
        }


        @Test
        @DisplayName("Setter de direccion null es permitido")
        void setDireccionNull_EsPermitido() {
            orden.setDireccion(null);

            assertNull(orden.getDireccion());
        }
    }

    @Nested
    @DisplayName("Pruebas de Estados de Orden")
    class PruebasEstadosOrden {

        @ParameterizedTest
        @ValueSource(strings = {"PENDIENTE", "PAGADA", "ENVIADA", "ENTREGADA", "CANCELADA"})
        @DisplayName("Orden puede tener diferentes estados válidos")
        void orden_PuedeTenerDiferentesEstados(String estado) {
            orden.setEstado(estado);

            assertEquals(estado, orden.getEstado());
        }

        @Test
        @DisplayName("Orden recién creada tiene estado PENDIENTE por defecto")
        void ordenRecienCreada_EstadoPendiente() {
            assertEquals("PENDIENTE", orden.getEstado());
        }

        @Test
        @DisplayName("Transición de estados es posible")
        void transicionDeEstados_FuncionaCorrectamente() {
            orden.setEstado("PENDIENTE");
            assertEquals("PENDIENTE", orden.getEstado());

            orden.setEstado("PAGADA");
            assertEquals("PAGADA", orden.getEstado());

            orden.setEstado("ENVIADA");
            assertEquals("ENVIADA", orden.getEstado());

            orden.setEstado("ENTREGADA");
            assertEquals("ENTREGADA", orden.getEstado());
        }
    }

    @Nested
    @DisplayName("Pruebas de Integridad de Datos")
    class PruebasIntegridadDatos {

        @Test
        @DisplayName("Fecha de creación se mantiene consistente")
        void fechaCreacion_SeMantieneConsistente() {
            // Arrange
            LocalDateTime fechaOriginal = orden.getFechaCreacion();

            orden.setEstado("PAGADA");
            orden.setTotal(50000.0);

            assertEquals(fechaOriginal, orden.getFechaCreacion());
        }

        @Nested
        @DisplayName("Pruebas de Métodos de Negocio (si existieran)")
        class PruebasMetodosNegocio {

            @Nested
            @DisplayName("Pruebas de Edge Cases y Validaciones")
            class PruebasEdgeCases {

                @Test
                @DisplayName("Orden con lista de items vacía es válida")
                void ordenConItemsVacios_EsValida() {
                    Orden ordenVacia = new Orden();
                    ordenVacia.setItems(new ArrayList<>());
                    ordenVacia.setTotal(0.0);
                    ordenVacia.setEstado("PENDIENTE");

                    assertNotNull(ordenVacia.getItems());
                    assertTrue(ordenVacia.getItems().isEmpty());
                    assertEquals(0.0, ordenVacia.getTotal(), 0.01);
                }

                @Test
                @DisplayName("Orden con total cero es válida")
                void ordenConTotalCero_EsValida() {
                    orden.setTotal(0.0);

                    assertEquals(0.0, orden.getTotal(), 0.01);
                }

                @Test
                @DisplayName("Orden con total negativo es permitida (para descuentos)")
                void ordenConTotalNegativo_EsPermitida() {
                    orden.setTotal(-5000.0);

                    assertEquals(-5000.0, orden.getTotal(), 0.01);
                }

                @Test
                @DisplayName("Orden sin dirección es válida")
                void ordenSinDireccion_EsValida() {
                    orden.setDireccion(null);

                    assertNull(orden.getDireccion());
                    assertNotNull(orden.getItems());
                    assertNotNull(orden.getEstado());
                }

                @Test
                @DisplayName("Fecha de creación en el futuro es permitida")
                void fechaCreacionFutura_EsPermitida() {
                    LocalDateTime fechaFutura = LocalDateTime.now().plusDays(7);

                    orden.setFechaCreacion(fechaFutura);

                    assertTrue(orden.getFechaCreacion().isAfter(LocalDateTime.now()));
                }
            }

            @ParameterizedTest
            @CsvSource({
                    "usuario1, 50000, PENDIENTE",
                    "usuario2, 75000, PAGADA",
                    "usuario3, 120000, ENVIADA",
                    "usuario4, 0, CANCELADA"
            })
            @DisplayName("Pruebas parametrizadas con diferentes configuraciones de orden")
            void pruebasParametrizadasOrdenes(String usuarioId, double total, String estado) {
                Orden ordenParam = new Orden();
                ordenParam.setUsuarioId(usuarioId);
                ordenParam.setTotal(total);
                ordenParam.setEstado(estado);

                assertEquals(usuarioId, ordenParam.getUsuarioId());
                assertEquals(total, ordenParam.getTotal(), 0.01);
                assertEquals(estado, ordenParam.getEstado());
                assertNotNull(ordenParam.getFechaCreacion());
            }
        }
    }
}
