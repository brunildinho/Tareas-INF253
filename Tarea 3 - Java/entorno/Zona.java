package entorno;
import java.util.EnumSet;
import objetos.ItemTipo;
import objetos.RobotExcavador;
import player.Jugador;

public abstract class Zona{
    public String nombre;
    private final int profundidadMin, profundidadMax, nMin, nMax, presion, multiplicadorPresion; //se pone final pq no se modificaran en la ejecucion
    private final EnumSet<ItemTipo> recursos;

    public Zona(String nombre, int profundidadMin, int profundidadMax, EnumSet<ItemTipo> recursos, int nMin, int nMax, int presion, int multiplicadorPresion) {
        this.nombre = nombre;
        this.profundidadMin = profundidadMin;
        this.profundidadMax = profundidadMax;
        this.recursos = recursos;
        this.nMin = nMin;
        this.nMax = nMax;
        this.presion = presion;
        this.multiplicadorPresion = multiplicadorPresion;
    }

    public String getNombre(){
        return this.nombre;
    }

    public int getProfundidadMin() {
        return profundidadMin;
    }

    public int getProfundidadMax() {
        return profundidadMax;
    }

    public EnumSet<ItemTipo> getRecursos() {
        return recursos;
    }

    public int getPresion(){
        return this.presion;
    }

    public int getMultiplicadorPresion() {
        return multiplicadorPresion;
    }

    public int getnMin() {
        return nMin;
    }

    public int getnMax() {//
        return nMax;
    }

    public void entrar(Jugador jugador) { 
    /*
    * Permite al jugador entrar en la zona si cumple los requisitos de profundidad
    * Ejecuta diferentes acciones dependiendo de si el jugador ya está en la zona o no,
    * o si está en la nave o no (y comprueba los requisitos individuales de cada caso).
    * Además, comprueba por qué lado de la zona entra el jugador y ajusta su profundidad de entrada
    * en consecuencia.
    * @param Parámetro(s): Jugador jugador
    * @return Vacio
    */

        if (jugador.getZonaActual().getNombre().equals(this.nombre)) {
            jugador.getMenu().mensajes.add("Ya estás en " + this.nombre + ".");
            return;
        }

        if ((jugador.estaEnNave() && jugador.getNave().puedeAcceder(this.profundidadMin)) || (!jugador.estaEnNave() && jugador.puedeAcceder(this.profundidadMax))) {
            
            if (jugador.getProfundidadActual() < this.profundidadMin) {
                if (jugador.estaEnNave()) {
                    jugador.getNave().setProfundidadActual(this.profundidadMin - 1, jugador.getZonaActual());
                    jugador.setZonaActual(this);
                    jugador.getNave().setProfundidadActual(this.profundidadMin, jugador.getZonaActual());
                }
                else {
                    jugador.setProfundidadActual(this.profundidadMin - 1, jugador.getZonaActual());
                    jugador.setZonaActual(this);
                    jugador.setProfundidadActual(this.profundidadMin, jugador.getZonaActual());
                }
            } else if (jugador.getProfundidadActual() > this.profundidadMax) {
                if (jugador.estaEnNave()) {
                    jugador.getNave().setProfundidadActual(this.profundidadMax + 1, jugador.getZonaActual());
                    jugador.setZonaActual(this);
                    jugador.getNave().setProfundidadActual(this.profundidadMax, jugador.getZonaActual());
                }
                else {
                    jugador.setProfundidadActual(this.profundidadMax + 1, jugador.getZonaActual());
                    jugador.setZonaActual(this);
                    jugador.setProfundidadActual(this.profundidadMax, jugador.getZonaActual());
                }
            }
            jugador.movimientos = 0;
            jugador.getMenu().mensajes.add("Has entrado en " + this.nombre + ".");
        }
        else {
            jugador.getMenu().mensajes.add("No puedes entrar en " + this.nombre + ".");
        }


    }

    public abstract void explorar(Jugador jugador);
        /*
        * Calcula O2, ejecuta la lógica de los items especiales. Si no obtiene uno, elije un recurso
        * aleatorio. Aumenta el contador de movimientos.
        * @param Parámetro(s): Jugador jugador
        * @return void
        */

    public abstract void recolectar(Jugador jugador);
        /*
        * Elije un recurso y calcula la cantidad a recolectar. Consume oxígeno y aumenta el contador de movimientos.
        * aleatorio. 
        * @param Parámetro(s): Jugador jugador
        * @return void
        */

    public abstract void recolectar(Jugador jugador, RobotExcavador robotExcavador);
        /*
        * Lo mismo que recolectar normal, pero el recurso se añade al inventario del robot.
        * Comprueba si el robot está activo y consume 1 unidad de oxígeno. Si la bodega del robot
        * se llena, lo desactiva.
        * @param Parámetro(s): Jugador jugador, RobotExcavador robotExcavador
        * @return void
        */
}




