package objetos;
public class Item{
    private final ItemTipo tipo;
    private int cantidad;

    public Item(ItemTipo tipo) {
        this.tipo = tipo;
        this.cantidad = 0;
    }

    public ItemTipo getTipo() {
        return tipo;
    }
    public int getCantidad() {
        return cantidad;
    }

    public void addRemoveCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}