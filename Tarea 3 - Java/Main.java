import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Iniciando juego");

        objetos.NaveExploradora nave = new objetos.NaveExploradora();
        entorno.ZonaArrecife zonaArrecife = new entorno.ZonaArrecife();
        entorno.ZonaProfunda zonaProfunda = new entorno.ZonaProfunda();
        entorno.ZonaVolcanica zonaVolcanica = new entorno.ZonaVolcanica();
        entorno.NaveEstrellada naveEstrellada = new entorno.NaveEstrellada();
        List<entorno.Zona> zonas = List.of(zonaArrecife, zonaProfunda, zonaVolcanica, naveEstrellada);
        //0: Arrecife, 1: Profunda, 2: Volcánica, 3: Nave Estrellada
        /* El manejo de zonas se hace a través de una lista de zonas que luego es pasada como
         * un estado del jugador (para hacer más facil su referenciación).
         * Para acceder a una zona en particular,
         * se accede a través de su índice indicado más arriba.
         */

        player.Jugador jugador = new player.Jugador(zonaArrecife, nave, zonas);
        player.Menu menu = new player.Menu();
        nave.setJugador(jugador); 
        jugador.setMenu(menu);

        System.out.println("Juego cargado correctamente :D");
        System.out.print("\033[H\033[2J");
        System.out.println("Subnáutica: USM Edition");
        System.out.println("-----------------------");
        System.out.println("Malas noticias: Has fallado en intentar frenar la invasión de aliens.\nTodos los estudiantes fueron abducidos por los aliens y obligados a programar un subnáutica en Java para una tarea de LP.\nAfortunadamente conseguiste robar una de sus naves y escapar, pero uno de sus lásers te alcanzó, dañando tu nave y haciendote chocar contra el INACAP. \nTu nave (y el INACAP) están en llamas, pero pudiste escapar hacia Caleta Portales junto a una nave exploradora. \nDeberás arreglártelas para reparar tu nave y rescatar a tus compañeros.");
        System.out.println("Presiona cualquier tecla para continuar...");
        System.in.read();

        System.out.print("\033[H\033[2J");
        while (true) {
             //limpia pantalla
            System.out.flush();
            menu.mostrarMenu(jugador);
            if (jugador.getTanqueOxigeno().getOxigenoRestante() <= 0) {
                /*
                 * Comprueba si el jugador se quedó sin oxígeno.
                 * Si es así, ejecuta la secuencia de muerte.
                 */
                jugador.muerte();
                jugador.getMenu().mensajes.add("Te has quedado sin oxígeno y perdiste el conocimiento. El espíritu de Don Fede intercede por tí y te lleva de vuelta a la nave, pero has perdido tu inventario.");
            }
            if (jugador.isNaveReparada()) {
                /* 
                 * Comprueba si la nave está reparada.
                 */
                System.out.print("\033[H\033[2J");
                System.out.println("¡Felicidades! Has reparado tu nave. Pero los aliens siguen ahí fuera.\nTu misión aún no ha terminado, tus compañeros te necesitan...\n(Continuará...?)");
                break;
            }
            System.out.print("\033[H\033[2J");
        }
    }




}