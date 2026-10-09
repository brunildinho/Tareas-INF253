package entorno;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import objetos.ItemTipo;
import objetos.RobotExcavador;
import player.Jugador;
public class ZonaVolcanica extends Zona {
    private boolean planoEncontrado;

    public ZonaVolcanica(){ //PLANO NAVE Tiene 15% de prob de encontrarse al explorar
        super("Volcán",1000,1500,EnumSet.of(ItemTipo.PLANO_NAVE, ItemTipo.TITANIO, ItemTipo.SULFURO, ItemTipo.URANIO),3 , 8, Integer.MAX_VALUE, 0);
        this.planoEncontrado = false;
    }

    public boolean isPlanoEncontrado() {
        return planoEncontrado;
    }
    
    public void setPlanoEncontrado(boolean planoEncontrado) {
        this.planoEncontrado = planoEncontrado;
    }

    @Override
    public void explorar(Jugador jugador){
        jugador.getTanqueOxigeno().consumirO2(jugador.calcularO2('e'));
        jugador.movimientos += 1;
        if (!this.planoEncontrado){
            if (Math.random() < 0.15){
                jugador.addRemoveToInventario(ItemTipo.PLANO_NAVE, 1);
                this.planoEncontrado = true;
                jugador.setTienePlano(planoEncontrado);
                jugador.getMenu().mensajes.add("Has encontrado los planos de la nave");
                return;
            }
        }
        ItemTipo elegido = elegirRecurso();

        int cantidad = Math.max(1, Math.round((this.getnMin() * jugador.getProfundidadNormazada())));
        jugador.addRemoveToInventario(elegido, cantidad);
        jugador.getMenu().mensajes.add("Has encontrado " + cantidad + " de " + elegido + ".");
        
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

    private ItemTipo elegirRecurso(){
        /*
        * Elige un recurso aleatorio de los disponibles en la zona, excepto PLANO_NAVE.
        * @param Parámetro(s): Nada
        * @return ItemTipo elegido
        */
        ItemTipo elegido = null;
        boolean flag = false;
        while (!flag) {
        int count = 0;
            for (ItemTipo t : this.getRecursos()) {
                if (ThreadLocalRandom.current().nextInt(++count) == 0  && t != ItemTipo.PLANO_NAVE) {
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