package entorno;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import objetos.ItemTipo;
import objetos.RobotExcavador;
import player.Jugador;

public class ZonaProfunda extends Zona {
    private final int presion; 
    private final int multiplicadorPresion;
    /*
     * Nota: aunque el compilador diga que estas variables no se usan ni leen,
     * sí se usan en Jugador a través de getters para calcular el oxígeno necesario
     */

    public ZonaProfunda(){
        super("Profundidades",200,999,EnumSet.of(ItemTipo.PLATA, ItemTipo.ORO, ItemTipo.ACERO, ItemTipo.DIAMANTE, ItemTipo.MAGNETITA),2 ,6, 10, 6);
        this.presion = 10;
        this.multiplicadorPresion = 6;
    }


    @Override
    public void explorar(Jugador jugador){
        ItemTipo elegido = this.elegirRecurso();
        int cantidad = Math.max(1, Math.round((this.getnMin() * jugador.getProfundidadNormazada())));
        jugador.addRemoveToInventario(elegido, cantidad);
        jugador.getMenu().mensajes.add("Has encontrado " + cantidad + " de " + elegido + ".");
        jugador.getTanqueOxigeno().consumirO2(jugador.calcularO2('e'));
        jugador.movimientos += 1;
    }

    @Override
    public void recolectar (Jugador jugador){
        ItemTipo elegido = elegirRecurso();
        int cantidad = Math.max(1, (int) Math.floor((this.getnMin() + (this.getnMax() - this.getnMin()) * jugador.getProfundidadNormazada())));
        jugador.addRemoveToInventario(elegido, cantidad);
        jugador.getMenu().mensajes.add("Has recolectado " + cantidad + " de " + elegido + ".");
        jugador.getTanqueOxigeno().consumirO2(jugador.calcularO2('r'));
        jugador.movimientos += 1;
    }

    @Override
    public void recolectar(Jugador jugador, RobotExcavador robotExcavador){
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

    @Override
    public void entrar(Jugador jugador){
        super.entrar(jugador);
        if (!jugador.estaEnNave() && jugador.getProfundidadActual() == this.getProfundidadMax()){
            if (Math.random() < 0.2){
                jugador.muerte();
                jugador.getMenu().mensajes.add("El cambio de presión y calor ha sido demasiado para tí y pierdes el conocimiento.\nEl espíritu de Don Fede intercede por tí y te lleva de vuelta a la nave, pero has perdido tu inventario.");
            }
        }
    }


    private ItemTipo elegirRecurso(){
        /*
        * Elige un recurso aleatorio de los disponibles en la zona.
        * @param Parámetro(s): Nada
        * @return ItemTipo elegido
        */
        ItemTipo elegido = null;
        int count = 0;
        for (ItemTipo t : this.getRecursos()) {
            if (ThreadLocalRandom.current().nextInt(++count) == 0) {
            elegido = t;
            }
        }
        return elegido;
    }


}