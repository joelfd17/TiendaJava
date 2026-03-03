package Tienda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Anterior {

    private Scanner sc =new Scanner(System.in);
    private Map<String, Articulo> articulos = new HashMap<>();
    private Map<String, Cliente> clientes = new HashMap<>();
    private ArrayList<Pedido> pedidos = new ArrayList<>();

        private String nombreSeccion(char seccion){
            return switch (seccion){
                case '1' -> "PERIFERICOS";
                case '2' -> "ALMACENAMIENTO";
                case '3' -> "IMPRESORAS";
                case '4' -> "MONITORES";
                default -> "PRESIONA OTRA TECLA";
            };
        }

        private void uno(){

            System.out.println("SECCION A LISTAR:");
            String s = sc.nextLine();
            char seccion = s.charAt(0);

            System.out.println("ARTICULOS DE LA SECCION " + nombreSeccion(seccion) + ":");

            for(Articulo a : articulos.values()) {
                if(a.getIdArticulo().charAt(0) == seccion){
                    System.out.println(a.getIdArticulo() + " - " + a.getDescripcion() + " - " + a.getExistencias() + " (" + a.getPvp() + ")");
                }
            }
        }

        private void dos(){

                for(char seccion='1'; seccion<='4'; seccion++){
                    System.out.println(nombreSeccion(seccion));

                    for(Articulo a : articulos.values()){
                        if(a.getIdArticulo().charAt(0) == seccion){
                            System.out.println(a.getIdArticulo() + " - " + a.getDescripcion() + " - " + a.getExistencias() + " (" + a.getPvp() + ")");
                        }
                    }
                }
        }

        /* private void tres(){

            System.out.println("DNI CLIENTE:");
            String idCliente = sc.nextLine().toUpperCase();

            double totalGastado = 0;

        }

        private int unidadesVendidasArticulo(String idArticulo){

            int total = 0;

            for(Pedido p : pedidos){
                for(LineaPedido l : p.getCestaCompra()){
                    if(l.getIdArticulo().equals(idArticulo)){
                        total += l.getUnidades();
                    }
                }
            }
            return total;
        }

        private void cuatro(){

            System.out.println("LISTADO ARTICULOS - UNIDADES VENDIDAS:");

        }
*/
        private void cinco(){

            ArrayList<Cliente> sinPedidos = new ArrayList<>();
            for(Cliente c : clientes.values()){

                boolean tienePedidos = false;

                for(Pedido p : pedidos){
                    if(p.getClientePedido().getIdCliente().equals(c.getIdCliente())){
                        tienePedidos = true;
                        break;
                    }
                }

                if(tienePedidos){
                    sinPedidos.add(c);
                }
            }
            System.out.println("LISTADO CLIENTES SIN PEDIDOS.");

            for (Cliente c : sinPedidos){
                System.out.println("(" + c.getIdCliente() + ")" + c.getNombre() + " - " + c.getTelefono() + " - " + c.getEmail());
            }
        }
}