package objetos;

import entorno.Zona;

public interface AccesoProfundidad {
    public boolean puedeAcceder(int requerido);
        /*
         * Comprueba si el jugador/nave puede acceder a la nueva profundidad.
         * @param Parámetro(s): int requerido (profundidad a la que se quiere acceder)
         * @return boolean (true si puede, false si no)
         */
    public void setProfundidadActual(int cambio);
        /*
        * Calcula la nueva profundidad dado un cambio (positivo o negativo) y llama a la función que actualiza la profundidad actual.
        * (que es la misma función pero sobrecargada).
        * Está pensado para cambios de profundidad relativos a la actual.
        * @param Parámetro(s): int cambio (diferencia de profundiadad, puede ser negativa)
        * @return void
        */
    public void setProfundidadActual(int nuevaProfundidad, Zona zonaActual);
        /*
        * Comprueba si el jugador puede acceder a la nueva profundidad y si está dentro de los límites de la zona actual.
        * Si es así, actualiza la profundidad actual del jugador y consume oxígeno si no está en la nave.
        * @param Parámetro(s): int nuevaProfundidad, Zona zonaActual (solo usado para sobrecargar el método)
        * @return void
        */
}