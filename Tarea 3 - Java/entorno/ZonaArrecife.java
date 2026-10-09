package entorno;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import objetos.ItemTipo;
import objetos.RobotExcavador;
import player.Jugador;

public class ZonaArrecife extends Zona {
    private int piezasTanque;
    
    public ZonaArrecife(){
        super("Arrecife",0,199,EnumSet.of(ItemTipo.PIEZA_TANQUE, ItemTipo.CUARZO, ItemTipo.SILICIO, ItemTipo.COBRE), 1, 3, 0, 0);
        this.piezasTanque = 3;
    }

    public int getPiezasTanque() {
        return piezasTanque;
    }

    public void addRemovePiezaTanque(int cantidad){
        this.piezasTanque += cantidad;
    }

    @Override
    public void explorar(Jugador jugador){

        jugador.getTanqueOxigeno().consumirO2(jugador.calcularO2('e'));
        jugador.movimientos += 1;
        if (this.piezasTanque > 0){
            if (Math.random() < 0.3){
                jugador.addRemoveToInventario(ItemTipo.PIEZA_TANQUE, 1);
                this.addRemovePiezaTanque(-1);
                jugador.getMenu().mensajes.add("Has encontrado una pieza del tanque de oxígeno. Te quedan " + this.piezasTanque + " piezas en la zona.");
                return;
            }
        }

        ItemTipo elegido = elegirRecurso();

        int cantidad = Math.max(1, (int) Math.floor((this.getnMin() * jugador.getProfundidadNormazada())));
        jugador.addRemoveToInventario(elegido, cantidad);
        jugador.getMenu().mensajes.add("Has encontrado " + cantidad + " de " + elegido + ".");
    }

    @Override
    public void recolectar (Jugador jugador){

        ItemTipo elegido = elegirRecurso();
        int cantidad = Math.max(1, (int) (Math.floor((this.getnMin() + (this.getnMax() - this.getnMin()) * jugador.getProfundidadNormazada()))));
        jugador.addRemoveToInventario(elegido, cantidad);
        jugador.getMenu().mensajes.add("Has recolectado " + cantidad + " de " + elegido + ".");
        jugador.getTanqueOxigeno().consumirO2(jugador.calcularO2('r'));
        jugador.movimientos += 1;
    }

    @Override
    public void recolectar (Jugador jugador, RobotExcavador robotExcavador){

        if (!robotExcavador.isActivo()){
            jugador.getMenu().mensajes.add("Necesitas reparar el robot excavador antes de volver a utilizarlo.");
            return;
        }
        ItemTipo elegido = elegirRecurso();
        int cantidad = Math.max(1, (int) Math.floor((this.getnMin() + (this.getnMax() - this.getnMin()) * jugador.getProfundidadNormazada())));
        robotExcavador.añadirObjeto(elegido, cantidad);
        jugador.getMenu().mensajes.add("El robot excavador ha recolectado " + cantidad + " de " + elegido + ".");
        jugador.getTanqueOxigeno().consumirO2(1);
        jugador.movimientos += 1;
        if (robotExcavador.isBodegaLlena()){
            jugador.getMenu().mensajes.add("El robot excavador ha alcanzado su capacidad máxima de carga y ha dejado de funcionar.");
            robotExcavador.setActivo(false);
        }
    }

    private ItemTipo elegirRecurso(){
        /*
        * Elige un recurso aleatorio de los disponibles en la zona, excepto piezas de tanque.
        * @param Parámetro(s): Nada
        * @return ItemTipo elegido
        */
        ItemTipo elegido = null;
        boolean flag = false;
        while (!flag) {
        int count = 0;
            for (ItemTipo t : this.getRecursos()) {
                if (ThreadLocalRandom.current().nextInt(++count) == 0  && t != ItemTipo.PIEZA_TANQUE) {
                elegido = t;
                }
            }
            if (elegido != null) {
                flag = true;
            }
        }
        return elegido;
    }
}