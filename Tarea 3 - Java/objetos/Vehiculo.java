package objetos;
import java.util.ArrayList;
import java.util.List;
import player.Jugador; 

public abstract class Vehiculo{
    private final List<Item> bodega;

    public Vehiculo() {
        this.bodega = new ArrayList<>();
        for (ItemTipo tipo : ItemTipo.values()) {
            bodega.add(new Item(tipo));
        }
    }

    public List<Item> getBodega() {
        return bodega;
    }

    public void añadirObjeto(ItemTipo tipo, int cantidad) {
        /*
         * Añade una cantidad específica de un objeto al inventario del vehículo.
         * @param Parámetro(s): ItemTipo tipo, int cantidad
         * @return void
         * 
        */
        for (Item item : bodega) {
            if (item.getTipo() == tipo) {
                item.addRemoveCantidad(cantidad);
                break;
            }
        }
    }

    public void transferirObjetos(Jugador jugador) {
        /*
         * Transfiere todos los objetos del inventario del jugador a la bodega del vehículo.
         * @param Parámetro(s): Jugador jugador
         * @return void
         * 
        */
        for (Item item : jugador.getInventario()) { 
            añadirObjeto(item.getTipo(), item.getCantidad());
            item.addRemoveCantidad(-item.getCantidad());
        }
    }
}