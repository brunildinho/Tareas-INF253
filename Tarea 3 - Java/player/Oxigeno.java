package player;

public class Oxigeno{
    private int oxigenoRestante, capacidadMaxima;

    public Oxigeno() {
        this.capacidadMaxima = 60;
        this.oxigenoRestante = capacidadMaxima; // Inicialmente lleno
    }

    public void consumirO2(int unidades) {
        oxigenoRestante -= unidades;
    }

    public void recargarCompleto(){
        this.oxigenoRestante = this.capacidadMaxima;
    }

    public int getOxigenoRestante() {
        return oxigenoRestante;
    }

    public void mejorarOxigeno() { 
        this.capacidadMaxima += 30;
    }


    /*
     * A mitad de la tarea me di cuenta que quizá seria buena idea que el método de calcular O2 estuviera aquí en la clase Oxigeno
     * en vez de en Jugador, así que copié el método para adaptarlo y cambiarlo más adelante...
     * Se me olvidó cambiarlo xd, pero me da penita borrarlo, así que lo dejé comentado por si acaso.
     */
    // public int calcularO2(int difProfundidad, Jugador jugador){ //por reemplazar
    //     if (jugador.estaEnNave()){
    //         System.out.println("Calculando O2 para cambio de profundidad de " + difProfundidad + " metros.");
    //         System.out.println("Profundidad normalizada: " + jugador.getProfundidadNormazada());
    //         System.out.println("O2 calculado: " +(3 + 3*jugador.getProfundidadNormazada()) * (Math.abs(difProfundidad) / 50) + " aprox " + Math.round((3 + 3*jugador.getProfundidadNormazada()) * (Math.abs(difProfundidad) / 50)));
    //         return Math.round((3 + 3*jugador.getProfundidadNormazada()) * (Math.abs(difProfundidad) / 50));
    //     }
    //     else return 0;
    // }
}