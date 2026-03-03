package Tienda;

public class StockInsuficiente extends Exception {

    public StockInsuficiente(String mensaje) {
        super(mensaje);
    }
}