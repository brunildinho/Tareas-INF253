package objetos;

import entorno.Zona;
import player.Jugador;
public class NaveExploradora extends Vehiculo implements AccesoProfundidad{
    private int profundidadSoportada;
    private int profundidadActual; //AÑADIDO POR PROGRAMADOR
    private Jugador jugador; //AÑADIDO POR PROGRAMADOR
    public Zona zonaActual; //AÑADIDO POR PROGRAMADOR
    private ModuloProfundidad moduloProfundidad; //AÑADIDO POR PROGRAMADOR

    public class ModuloProfundidad{
        private final int profundidadExtra;

        public ModuloProfundidad(){
            this.profundidadExtra = 1000;
        }

        public void aumentarProfundidad(){
            aumentarProfundidadSoportada(profundidadExtra);
        }
    }

    public NaveExploradora(){
        super();
        this.profundidadSoportada = 500; 
        this.profundidadActual = 0; 
        this.zonaActual = null; 
        this.moduloProfundidad = null; 
    }

    public void setJugador(Jugador jugador) { 
        /*
         * Settea el jugador para obtener su referencia más facilmente
         * zonaActual debería inicializarce como la zona inicial.
         * @param Parámetro(s): Jugador jugador
         * @return void
         */
        this.jugador = jugador;
        this.zonaActual = jugador.getZonaActual();
    }

    public void setModuloEncontrado(){
        this.moduloProfundidad = new ModuloProfundidad();
        moduloProfundidad.aumentarProfundidad();
    }

    public boolean isModuloEncontrado(){
        return this.moduloProfundidad != null;
    }

    public int getProfundidadSoportada() {
        return profundidadSoportada;
    }

    public int getProfundidadActual() {
        return profundidadActual;
    }

    public Zona getZonaActual() {
        return zonaActual;
    }

    public void setZonaActual(Zona zonaActual) {
        /*
         * Settea la zona actual de la nave. Usado más que nada para saber a qué zona transportar al jugador si muere.
         * @param Parámetro(s): Zona zonaActual
         * @return void
         * 
        */
        this.zonaActual = zonaActual;
    }

    public void aumentarProfundidadSoportada(int profundidadExtra) {
        this.profundidadSoportada += profundidadExtra;
    }

    @Override
    public boolean puedeAcceder(int requerido) {
        if (requerido < 0) { 
            return false;
        }
        System.out.println("Profundidad requerida: " + requerido + ", Profundidad soportada: " + this.profundidadSoportada);
        return requerido <= this.profundidadSoportada;
    }

    @Override
    public void setProfundidadActual(int cambio) {
        int nuevaProfundidad = this.profundidadActual - cambio;
        this.setProfundidadActual(nuevaProfundidad, this.zonaActual);
    }

    @Override
    public void setProfundidadActual(int nuevaProfundidad, Zona zonaActual) {
        if (this.puedeAcceder(nuevaProfundidad)){
            if (nuevaProfundidad <= zonaActual.getProfundidadMax() && nuevaProfundidad >= zonaActual.getProfundidadMin()){
                this.profundidadActual = nuevaProfundidad;
                this.jugador.setProfundidadActual(nuevaProfundidad, this.jugador.getZonaActual());
            }
            else {
                this.jugador.getMenu().mensajes.add("No puedes acceder a " + nuevaProfundidad + ", está fuera de los límites de la zona actual.");
            }
        }
        else {
            jugador.getMenu().mensajes.add("No puedes acceder a " + nuevaProfundidad + " con la nave exploradora.");
        }
    }

    public void verBodega() {
        /*
         * Recorre la bodega de la nave y muestra por consola los objetos y cantidades que contiene.
         * @param Parámetro(s): Ninguno
         * @return void
         * 
        */
        System.out.println("Bodega:");
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