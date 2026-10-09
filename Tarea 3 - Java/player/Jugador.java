package player;
import entorno.Zona;
import java.util.ArrayList;
import java.util.List;
import objetos.AccesoProfundidad;
import objetos.Item;
import objetos.ItemTipo;
import objetos.NaveExploradora;
import objetos.RobotExcavador;


public class Jugador implements AccesoProfundidad{
    private final Oxigeno tanqueOxigeno;
    private Zona zonaActual;
    private int profundidadActual;
    public int movimientos; //Para requisito de muerte en NaveEstrellada
    private boolean tienePlano, trajeTermico, mejoraTanque, naveReparada, enNave;
    private final NaveExploradora nave;
    private RobotExcavador robot; 
    private final List<Item> inventario;
    private Menu menu; //Referencia al menú para añadir mensajes
    private final List<entorno.Zona> zonas; //Para tener referencia facil a las zonas.

    public Jugador(Zona zonaInicial, NaveExploradora nave, List<entorno.Zona> zonas){
        this.zonaActual = zonaInicial;
        this.nave = nave;
        this.tanqueOxigeno = new Oxigeno();
        this.profundidadActual = 0;
        this.movimientos = 0;
        this.tienePlano = false;
        this.trajeTermico = false;
        this.mejoraTanque = false;
        this.naveReparada = false;
        this.enNave = true;
        this.inventario = new ArrayList<>();
        /*
         * El inventario del jugador y de todas las entidades 
         * se inicializa con todos los tipos de objetos posibles, pero con cantidad 0.
         * Su manejo es a base de forseaches y comprobaciones de cantidad > 0/algún valor.
         */
        for (ItemTipo tipo : ItemTipo.values()) {
            inventario.add(new Item(tipo));
        }
        this.zonas = zonas;
    }

    public RobotExcavador getRobot() {
        return robot;
    }

    public Menu getMenu() {
        return menu;
    }

    public float getProfundidadNormazada() { 
        /*
         * Devuelve la profundidad actual del jugador normalizada entre 0 y 1 según la zona en la que se encuentre.
         * @param Parámetro(s): Nada
         * @return float
         * 
        */
        if (this.profundidadActual < 0) {
            return 0;
        }
        return (float)((this.profundidadActual - zonaActual.getProfundidadMin())) / Math.max(1, zonaActual.getProfundidadMax() - zonaActual.getProfundidadMin());
    }

    public NaveExploradora getNave() {
        return nave;
    }

    public boolean estaEnNave() {
        return enNave;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public boolean tienePlano(){
        return this.tienePlano;
    }

    public boolean setTienePlano(boolean tienePlano){
        return this.tienePlano = tienePlano;
    }

    public boolean trajeTermico(){
        return this.trajeTermico;
    }

    public boolean setTrajeTermico(boolean trajeTermico){
        return this.trajeTermico = trajeTermico;
    }

    public boolean mejoraTanque(){
        return this.mejoraTanque;
    }

    public void mejorarTanque(){
        if (!this.mejoraTanque){
            this.mejoraTanque = true;
        }
    }

    public List<Item> getInventario() {
        return inventario;
    }

    public Oxigeno getTanqueOxigeno() {
        return this.tanqueOxigeno;
    }

    public int getProfundidadActual(){
        return this.profundidadActual;
    }

    public List<entorno.Zona> getZonas() {
        return zonas;
    }

    public Zona getZonaActual() {
        return this.zonaActual;
    }

    public void setZonaActual(Zona zonaActual) {
        /*
         * ADVERTENCIA: SOLO SETTER PARA EL ESTADO DEL OBJETO JUGADOR, No está pensado para su uso regular por otras funciones salvo Zona.entrar(jugador)
         * @param Parámetro(s): Zona zonaActual
         * @return void
         * 
        */
        this.zonaActual = zonaActual;
        if (this.estaEnNave()) this.nave.setZonaActual(zonaActual);
    }

    public void setRobot(RobotExcavador robot) {
        this.robot = robot;
    }

    public boolean isNaveReparada() {
        return naveReparada;
    }

    public void setNaveReparada(boolean naveReparada) {
        this.naveReparada = naveReparada;
    }

    @Override
    public boolean puedeAcceder(int requerido) {
        if (requerido < -1) { 
            return false;
        }
        if (this.trajeTermico && this.mejoraTanque){
            return requerido <= this.getZonas().get(2).getProfundidadMax();
        }
        else {
            if (this.enNave){
                return requerido <= this.getZonas().get(2).getProfundidadMax(); //1500 
            }
            else return requerido < this.getZonas().get(2).getProfundidadMin(); //1000 
        }
    }

    @Override
    public void setProfundidadActual(int cambio) {
        int nuevaProfundidad = this.profundidadActual - cambio;
        this.setProfundidadActual(nuevaProfundidad, this.zonaActual);
    }

    @Override
    public void setProfundidadActual(int nuevaProfundidad, Zona auxiliar) {

        if (this.puedeAcceder(nuevaProfundidad)){
            if (nuevaProfundidad <= this.zonaActual.getProfundidadMax() && nuevaProfundidad >= this.zonaActual.getProfundidadMin()){
                if (!this.enNave){
                    this.tanqueOxigeno.consumirO2(this.calcularO2(Math.abs(this.profundidadActual - nuevaProfundidad)));
                }
                this.profundidadActual = nuevaProfundidad;
            }
            else this.menu.mensajes.add("No puedes acceder a esa profundidad, está fuera de los límites de la zona actual.");
        }
        else {
            this.menu.mensajes.add("No puedes acceder a esa profundidad.");
        }
    }

    private void setProfundidadActual(int nuevaProfundidad, boolean absoluto){
        /*
         * Cambia la profundidad actual del jugador sin hacer comprobaciones previas.
         * ADVERTENCIA: No está pensado para su uso regular, solo para casos especiales (como la muerte del jugador)
         * @param Parámetro(s): int nuevaProfundidad, boolean absoluto (solo usado para sobrecargar el método)
         * @return void
         */
        this.profundidadActual = nuevaProfundidad;
    }

    public int calcularO2(char tipoAccion){
        /*
         * Para usar en la exploración y recolección de recursos.
         * Calcula el oxígeno a consumir según la acción (recolección o exploración) realizada y la profundidad actual del jugador.
         * Si el jugador está en la nave, no consume oxígeno.
         * Si el jugador tiene mejora de tanque, no se le aplica la presión.
         * @param Parámetro(s): char tipoAccion ('e' para explorar, 'r' para recolectar)
         * @return int
         * 
         */

        if (!this.enNave){
            float presion;
            if(this.mejoraTanque){
                presion = 0;
            }
            else {
                presion = this.zonaActual.getPresion() + (this.zonaActual.getMultiplicadorPresion() * this.getProfundidadNormazada());
            }

            switch (tipoAccion) {
                case 'e' -> {return (int) Math.ceil(12 + 10*this.getProfundidadNormazada() + presion);}
                case 'r' -> {return (int) Math.ceil(10 + 6*this.getProfundidadNormazada() + presion);}
                default -> {return 0;}
            }
        }
        else return 0;
    }

    public int calcularO2(int difProfundidad){
        /*
         * Para usar al cambiar de profundidad.
         * Calcula el oxígeno a consumir según la diferencia de profundidad a la que se quiere acceder.
         * Si el jugador está en la nave, no consume oxígeno.
         * @param Parámetro(s): int difProfundidad (diferencia de profundidad)
         * @return int
         */
        if (!this.enNave){
            return (int) Math.ceil((3 + 3*this.getProfundidadNormazada()) * (Math.abs(difProfundidad) / 50));
        }
        else return 0;
    }



    public void inOutNave(){
        /*
         * Cambia el estado del jugador entre dentro y fuera de la nave.
         * Si el jugador entra en la nave, recarga el tanque de oxígeno al completo y resetea los movimientos.
         * Si el jugador está fuera de la nave, comprueba si puede salir (si tiene traje térmico o mejora de tanque a ciertas profundidades).
         * @param Parámetro(s): Nada
         * @return void
         * 
        */
        if (this.enNave){
            if (this.profundidadActual >= this.getZonas().get(2).getProfundidadMin() && !this.trajeTermico && !this.mejoraTanque){ //1500 es la profundidad máxima de la ZonaVolcanica
                this.menu.mensajes.add("No puedes salir de la nave a esta profundidad sin un traje térmico ni mejorar tu tanque de oxígeno.");
                return;
            }
        }
        this.enNave = !this.enNave;
        this.menu.mensajes.add(this.estaEnNave() ? "Has entrado a la nave." : "Has salido de la nave.");
        if (this.enNave){
            this.tanqueOxigeno.recargarCompleto();
            this.movimientos = 0;
        }
    }

    public void verInventario(){
        /*
         * Recorre el inventario del jugador y muestra por consola los objetos y cantidades que contiene.
         * @param Parámetro(s): Nada
         * @return void
         */
        System.out.print("\033[H\033[2J"); 
        System.out.println("Inventario:");
        boolean flag = false;
        for (Item item : inventario) {
            if (item.getCantidad() > 0) {
                System.out.println("- " + item.getCantidad() + " " + item.getTipo());
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("- Vacío");
        }
        
    }

    public void verEstadoJugador(){
        /*
         * Muestra por consola el estado actual del jugador.
         * De forma básica muestra profundidad, oxígeno, zona actual y si está en la nave o no.
         * Se dejaron comentadas otros parámetros que podrían ser útiles para debuggear o revisión ;).
         * @param Parámetro(s): Nada
         * @return void
         * 
        */
        System.out.println("Profundidad actual: " + profundidadActual + " [m] || O2 Restante: " + tanqueOxigeno.getOxigenoRestante() + " [bar]");
        System.out.println("Ahora estás en " + zonaActual.getNombre() + (enNave ? " dentro de tu nave exploradora" : " a la interperie"));
        // System.out.println("Tiene plano: " + (tienePlano ? "Sí" : "No"));
        // System.out.println("Tiene traje térmico: " + (trajeTermico ? "Sí" : "No"));
        // System.out.println("Mejora de tanque de oxígeno: " + (mejoraTanque ? "Sí" : "No"));
        // System.out.println("Profundidad soportada por la nave: " + nave.getProfundidadSoportada() + " metros");
        // System.out.println("Profundidad de anclaje de la nave: " + nave.getProfundidadAnclaje() + " metros");
    }

    public void addRemoveToInventario(ItemTipo tipo, int cantidad) {
        /*
         * Recorre el inventario del jugador y añade o quita una cantidad específica de un objeto.
         * @param Parámetro(s): ItemTipo tipo, int cantidad
         * @return void
         * 
        */
        for (Item item : inventario) {
            if (item.getTipo() == tipo) {
                item.addRemoveCantidad(cantidad);
                return;
            }
        }

    }

    public void muerte() {
        /*
         * Maneja la muerte del jugador.
         * Resetea el inventario del jugador, vuelve a añadir los items especiales a sus respectivas zonas.
         * Lleva al jugador a la zona y profundidad de la nave, luego entra a ella.
         * @param Parámetro(s): Nada
         * @return void
         * 
        */

        for(Item item : this.inventario){
            if (item.getTipo() == ItemTipo.PIEZA_TANQUE && item.getCantidad() > 0){
                for (Zona zona : this.zonas){
                    if (zona instanceof entorno.ZonaArrecife zone){
                        zone.addRemovePiezaTanque(+item.getCantidad());
                    }
                }
            }
            else if (item.getTipo() == ItemTipo.MODULO_PROFUNDIDAD && item.getCantidad() > 0){
                for (Zona zona : this.zonas){
                    if (zona instanceof entorno.NaveEstrellada zone){
                        zone.setModuloEncontrado(false);
                    }
                }
            }
            else if (item.getTipo() == ItemTipo.PLANO_NAVE && item.getCantidad() > 0){
                for (Zona zona : this.zonas){
                    if (zona instanceof entorno.ZonaVolcanica zone){
                        zone.setPlanoEncontrado(false);
                    }
                }
            }
            item.addRemoveCantidad(-item.getCantidad());
        }
        this.setZonaActual(this.nave.getZonaActual());
        this.setProfundidadActual(this.nave.getProfundidadActual(), true);
        this.inOutNave();
        this.getMenu().mensajes.remove(this.getMenu().mensajes.size() - 1);
    }

}