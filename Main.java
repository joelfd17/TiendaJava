package Tienda;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Tienda2026 tienda = new Tienda2026();   // crear tienda
        tienda.cargaDatos();            // cargar datos

        // Crear un cliente
        Cliente cliente = new Cliente(
                "C01",
                "Ana López",
                "600123456",
                "ana@email.com"
        );

        // Crear artículos
        Articulo portatil = new Articulo(
                "A01",
                "Portátil Lenovo",
                10,
                899.99
        );

        Articulo raton = new Articulo(
                "A02",
                "Ratón inalámbrico",
                50,
                19.95
        );

        // Crear un pedido
        Pedido pedido = new Pedido(
                "P01",
                cliente,
                LocalDate.now()
        );

        // Crear líneas de pedido y añadirlas al pedido
        LineaPedido lp1 = new LineaPedido(portatil, 1);
        LineaPedido lp2 = new LineaPedido(raton, 2);

        pedido.addLineaPedido(lp1);
        pedido.addLineaPedido(lp2);

        // Mostrar resultados
        System.out.println(cliente);
        System.out.println(portatil);
        System.out.println(raton);
        System.out.println(pedido);
    }
}
