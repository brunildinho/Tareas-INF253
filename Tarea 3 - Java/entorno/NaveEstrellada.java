package entorno;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import objetos.ItemTipo;
import objetos.RobotExcavador;
import player.Jugador;
public class NaveEstrellada extends Zona {
    private boolean moduloEncontrado;


    public NaveEstrellada(){
        super("Nave Estrellada",-1,-1,EnumSet.of(ItemTipo.MODULO_PROFUNDIDAD, ItemTipo.CABLES, ItemTipo.PIEZAS_DE_METAL), 1, 4,0,1);
        this.moduloEncontrado = false;
    }

    public boolean isModuloEncontrado() {
        return moduloEncontrado;
    }

    public void setModuloEncontrado(boolean moduloEncontrado) {
        this.moduloEncontrado = moduloEncontrado;
    }

    @Override
    public void explorar(Jugador jugador){
        jugador.getTanqueOxigeno().consumirO2(jugador.calcularO2('e'));
        jugador.movimientos += 1;
        if (!this.moduloEncontrado){
            if (Math.random() < 0.25){
                jugador.addRemoveToInventario(ItemTipo.MODULO_PROFUNDIDAD, 1);
                this.moduloEncontrado = true;
                jugador.getMenu().mensajes.add("Has encontrado el módulo de profundidad. Puedes mejorar la presión máxima de tu nave exploradora.");
                return;
            }
        }

        ItemTipo elegido = elegirRecurso();
        int cantidad = Math.max(1, Math.round((this.getnMin() * jugador.getProfundidadNormazada())));
        jugador.addRemoveToInventario(elegido, cantidad);
        jugador.getMenu().mensajes.add("Has encontrado " + cantidad + " de " + elegido + ".");
        if (maxMovimientosAlcanzado(jugador)){
            jugador.getMenu().mensajes.add("Sucumbes al calor abrasador de las llamas. El espíritu de Don Fede intercede por tí y te lleva de vuelta a la nave, pero has perdido tu inventario. Quizá sea buena idea tomar un traje térmico la próxima vez (o también una coquita helada).");
            jugador.muerte();
        }
    }

    @Override
    public void recolectar (Jugador jugador){
        ItemTipo elegido = elegirRecurso();
        int cantidad = Math.max(1, (int) Math.floor((this.getnMin() + (this.getnMax() - this.getnMin()) * jugador.getProfundidadNormazada())));
        jugador.addRemoveToInventario(elegido, cantidad);
        jugador.getMenu().mensajes.add("Has recolectado " + cantidad + " de " + elegido + ".");
        jugador.getTanqueOxigeno().consumirO2(jugador.calcularO2('r'));
        jugador.movimientos += 1;
        if (maxMovimientosAlcanzado(jugador)){
            jugador.getMenu().mensajes.add("Sucumbes al calor abrasador de las llamas. El espíritu de Don Fede intercede por tí y te lleva de vuelta a la nave, pero has perdido tu inventario. Quizá sea buena idea tomar un traje térmico la próxima vez (o también una coquita helada).");
            jugador.muerte();
        }
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
        if (maxMovimientosAlcanzado(jugador)){
            jugador.getMenu().mensajes.add("Sucumbes al calor abrasador de las llamas. El espíritu de Don Fede intercede por tí y te lleva de vuelta a la nave, pero has perdido tu inventario. Quizá sea buena idea tomar un traje térmico la próxima vez (o también una coquita helada).");
            jugador.muerte();
        }
    }

    private ItemTipo elegirRecurso(){
        /*
        * Elige un recurso aleatorio de los disponibles en la zona, excepto el modulo de profunidad.
        * @param Parámetro(s): Nada
        * @return ItemTipo elegido
        */
        ItemTipo elegido = null;
        boolean flag = false;
        while (!flag) {
        int count = 0;
            for (ItemTipo t : this.getRecursos()) {
                if (ThreadLocalRandom.current().nextInt(++count) == 0  && t != ItemTipo.MODULO_PROFUNDIDAD) {
                elegido = t;
                }
            }
            if (elegido != null) {
                flag = true;
            }
        }
        return elegido;
    }

    private boolean maxMovimientosAlcanzado(Jugador jugador){
        /*
        * Calcula si el jugador ha alcanzado el máximo de movimientos permitidos en la zona (1) y no tiene el traje térmico.
        * @param Parámetro(s): Jugador jugador
        * @return boolean jugador.movimientos > 1 && !jugador.trajeTermico()
        */
        return (jugador.movimientos > 1 && !jugador.trajeTermico());
    }

}