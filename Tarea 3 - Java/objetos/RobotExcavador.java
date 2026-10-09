package objetos;
import player.Jugador;

public class RobotExcavador extends Vehiculo{
    private int capacidadCarga;
    private boolean activo; //AÑADIDO POR PROGRAMADOR

    public RobotExcavador() {
        super();
        this.capacidadCarga = 1000;
        this.activo = true;
    }

    public void mejorarCapacidad() {
        this.capacidadCarga += capacidadCarga*0.25; 
    }

    public boolean isBodegaLlena(){
        /*
         * Recorre la bodega y cuenta los items
         * Si estos superan la capacidad máxima, devuelve true
         * La función que llame este método debería también manejar el estado del robot (activo/inactivo)
         * @param Parámetro(s): Nada
         * @return boolean
         * 
        */
        int cargaTotal = 0;
        for (Item item : this.getBodega()) {
            cargaTotal += item.getCantidad();
        }
        return cargaTotal >= capacidadCarga;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public void transferirObjetos(Jugador jugador) {
        /*
         * Sobreescribe el método de Vehiculo para transferir los objetos desde el robot a la nave.
         * @param Parámetro(s): Jugador jugador
         * @return void
         */
        for (Item item : this.getBodega()) { 
            jugador.getNave().añadirObjeto(item.getTipo(), item.getCantidad());
            item.addRemoveCantidad(-item.getCantidad());
        }
    }
    
    public void verInventario(){
        /*
         * Recorre la bodega del robot y muestra por consola los objetos y cantidades que contiene.
         * @param Parámetro(s): Nada
         * @return void
         * 
        */
        System.out.println("Robot Excavador:");
        boolean flag = false;
        for (Item item : this.getBodega()) {
            if (item.getCantidad() > 0) {
                System.out.println("- " + item.getCantidad() + " " + item.getTipo());
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("- Vacío");
        }
    }

}