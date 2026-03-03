package Tienda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class Tienda2026Test {

    private Tienda2026 tienda;

    @BeforeEach
    void setUp() {
        tienda = new Tienda2026();
        tienda.cargaDatos();
    }

    @Test
    void testCargaDatos() {

        assertAll("Pruebas cargaDatos()",
                () -> assertEquals(10, tienda.getArticulos().size()),
                () -> assertEquals(4, tienda.getClientes().size()),
                () -> assertEquals(5, tienda.getPedidos().size())
        );
    }

    @Test
    void testGeneraIdPedido() {

        int year = LocalDate.now().getYear();

        assertAll("Pruebas generaIdPedido()",
                () -> assertEquals("80580845T-003/" + year,
                        tienda.generaIdPedido("80580845T")),
                () -> assertEquals("36347775R-003/" + year,
                        tienda.generaIdPedido("36347775R")),
                () -> assertEquals("63921307Y-002/" + year,
                        tienda.generaIdPedido("63921307Y")),
                () -> assertEquals("02337565Y-001/" + year,
                        tienda.generaIdPedido("02337565Y"))
        );
    }

    /* @Test
    void testTotalPedido() {

        assertAll("Pruebas totalPedido()",
                () -> assertEquals(585.0, tienda.getPedidos().get(0).totalPedido()),
                () -> assertEquals(3220.0, tienda.getPedidos().get(1).totalPedido()),
                () -> assertEquals(390.0, tienda.getPedidos().get(2).totalPedido()),
                () -> assertEquals(1980.0, tienda.getPedidos().get(3).totalPedido()),
                () -> assertEquals(2360.0, tienda.getPedidos().get(4).totalPedido())
        );
    }

    @Test
    void testTotalCliente() {

        assertAll("Pruebas totalCliente()",
                () -> assertEquals(3805.0, tienda.totalCliente("80580845T")),
                () -> assertEquals(2370.0, tienda.totalCliente("36347775R")),
                () -> assertEquals(2360.0, tienda.totalCliente("63921307Y")),
                () -> assertEquals(0.0, tienda.totalCliente("02337565Y"))
        );
    } */

    @Test
    void testStock() {
        assertThrows(StockCero.class, () -> tienda.stock(tienda.getArticulos().get("1-11"),5));
        assertThrows(StockCero.class, () -> tienda.stock(tienda.getArticulos().get("1-22"), 1));
        assertThrows(StockInsuficiente.class, () -> tienda.stock(tienda.getArticulos().get("2-11"), 10));
        assertThrows(StockInsuficiente.class, () -> tienda.stock(tienda.getArticulos().get("2-22"), 20));
    }
}