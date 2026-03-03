package Tienda;

public class LineaPedido {

    private Articulo articulo;
    private int unidades;

    public LineaPedido(Articulo articulo, int unidades) {
        this.articulo = articulo;
        this.unidades = unidades;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    @Override
    public String toString() {
        return articulo.getDescripcion() + " - " + unidades + " uds";
    }
}

