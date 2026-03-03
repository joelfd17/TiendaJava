package Tienda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Comparator;

public class FernandezDiazJoel {

    private Scanner sc =new Scanner(System.in);
    private Map<String, Articulo> articulos = new HashMap<>();
    private Map<String, Cliente> clientes = new HashMap<>();
    private ArrayList<Pedido> pedidos = new ArrayList<>();

    public FernandezDiazJoel(ArrayList<Pedido> pedidos, HashMap<String, Cliente> clientes, HashMap<String, Articulo> articulos) {
        this.pedidos = pedidos;
        this.clientes = clientes;
        this.articulos = articulos;
    }

    private double gastoCliente(Cliente c) {
        return pedidos.stream()
                .filter(p -> p.getClientePedido().equals(c))
                .flatMap(p -> p.getCestaCompra().stream())
                .mapToDouble(l -> l.getUnidades() * l.getArticulo().getPvp())
                .sum();
    }

    private void uno() {

        clientes.values().stream()
                .sorted(Comparator.comparingDouble(this::gastoCliente).reversed())
                .forEach(c -> System.out.println(c + " Total GASTADO: " + gastoCliente(c)));
    }

    private void dos() {

        System.out.println("SECCION A LISTAR:");
        String sec = sc.nextLine();

        articulos.values().stream()
                .filter(a -> a.getIdArticulo().startsWith(sec))
                .filter(a -> a.getExistencias() > 0)
                .sorted(Comparator.comparingDouble(Articulo::getPvp).reversed())
                .forEach(a -> System.out.println(a.getIdArticulo() + " - " + a.getDescripcion() + " - " + a.getExistencias() + " (" + a.getPvp() + ")"));
    }

    private void tres() {

        ArrayList<Articulo> articulosNoVendidos = new ArrayList<>();

        for (Articulo a : articulos.values()) {
            boolean vendido = false;

            for (Pedido p : pedidos) {
                for (LineaPedido lp : p.getCestaCompra()) {
                    if (lp.getArticulo().equals(a)) {
                        vendido = true;
                        break;
                    }
                }
                if (vendido) break;
            }
            if (!vendido) {
                articulosNoVendidos.add(a);
            }
        }
        articulosNoVendidos.forEach(a -> System.out.println(a.getIdArticulo() + " - " + a.getDescripcion() + " - " + a.getExistencias() + " (" + a.getPvp() + ")"));
    }

/*    private void cuatro() {

        LocalDate hoy = LocalDate.now();
        LocalDate hace5dias = hoy.minusDays(5);

        double total = pedidos.stream()
                .filter(p -> p.getFechaPedido().isBefore(hace5dias))
                .flatMap(p -> p.getCestaCompra().stream())
                .mapToDouble()
    } */

    private void cinco(){

        double importeMedio = pedidos.stream()
                .mapToDouble(p -> p.getCestaCompra().stream().mapToDouble(lp -> lp.getUnidades() * lp.getArticulo().getPvp()).sum())
                .average()
                .orElse(0);

        System.out.println("Importe Medio Pedidos TIENDA: " + importeMedio);
    }
}