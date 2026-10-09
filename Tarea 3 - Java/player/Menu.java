package player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import objetos.Item;
import objetos.ItemTipo;
import objetos.RobotExcavador;

public class Menu {
    private final Scanner scanner;
    public List<String> mensajes = new ArrayList<>(); // Inicializa una conveniente cola de mensajes para mostrar en el hud

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    private void menuInterior(Jugador jugador) throws IOException {
        /*
         * Despliega el menú de la nave y maneja las opciones seleccionadas por el jugador.
         * @param Parámetro(s): Jugador jugador
         * @return void
         */

        System.out.println("----- Menú -----");
        System.out.println("1. Ascender/Descender");
        System.out.println("2. Inventario/bodega");
        System.out.println("3. Ir a la mesa de trabajo");
        System.out.println("4. Cambiar de zona");
        System.out.println("5. Salir de la nave");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); 
        
        switch (opcion) { 
            case 1 -> {
                System.out.println("¿Cuántos metros quieres subir/bajar (usa valores negativos para sumergirte más)...");
                int difProfundidad = scanner.nextInt();
                jugador.getNave().setProfundidadActual(difProfundidad);
            }
            case 2 -> {
                System.out.print("\033[H\033[2J");
                jugador.verInventario();
                System.out.println();
                jugador.getNave().verBodega();
                if (jugador.getRobot() != null) {
                    System.out.println();
                    jugador.getRobot().verInventario();
                }
                System.out.println("Presiona 1 para transferir tu inventario a la bodega de la nave (esta acción no se puede deshacer)...");
                System.out.println("Presiona 2 para volver al menú.");
                try {
                    int transferir = scanner.nextInt();
                    scanner.nextLine(); 
                    if (transferir == 1){
                        jugador.getNave().transferirObjetos(jugador);
                        if (jugador.getRobot() != null) jugador.getRobot().transferirObjetos(jugador);
                        this.mensajes.add("Has transferido tu inventario a la bodega de la nave.");
                    }
                } catch (InputMismatchException e) {
                    break; 
                }
            }
            case 3 -> {
                this.mesaDeTrabajo(jugador);
            }
            case 4 -> {
                this.menuZonas(jugador);
            }
            case 5 -> {
                jugador.inOutNave();
            }
            case 253 -> {
                this.cheatMenu(jugador);
            }
            default -> {
                this.mensajes.add("Opción ingresada inválida.");
            }
        }
    }

    private void cheatMenu(Jugador jugador) throws IOException {
        /*
         * Despliega un menú de trucos para añadir items al inventario del jugador.
         * Así se pueden probar funciones y cosas sin necesidad de jugar todo el juego.
         * Yupi.
         * @param Parámetro(s): Jugador jugador
         * @return void
         */
        System.out.println("----- Menú de trucos -----");
        System.out.println("1. Añadir 3 piezas de tanque al inventario (OJO: no elimina las piezas del arrecife si las hubiera.)");
        System.out.println("2. Añadir 10 de plata y 15 de cuarzo al inventario (Mejora de oxigeno)");
        System.out.println("3. Añadir 10 de silicio, 3 de oro y 5 de cuarzo al inventario (Traje termico)");
        System.out.println("4. Añadir 1 módulo de profundidad al inventario (OJO: No elimina el módulo de NaveEstrellada si lo hubiera)");
        System.out.println("5. Añadir 15 de cobre, 10 de magnetita, 5 de diamante y 20 de acero al inventario (Robot excavador)");
        System.out.println("6. Añadir planos, 50 de titanio, 30 de acero, 15 de uranio y 20 de sulfuro al inventario (Reparar nave estrellada)");
        int opcion = scanner.nextInt();
        scanner.nextLine(); 

        switch (opcion){
            case 1 -> {
                jugador.addRemoveToInventario(ItemTipo.PIEZA_TANQUE, 3);
                this.mensajes.add("CHEAT: Has añadido 3 piezas de tanque a tu inventario.");
            }
            case 2 -> {
                jugador.addRemoveToInventario(ItemTipo.PLATA, 10);
                jugador.addRemoveToInventario(ItemTipo.CUARZO, 15);
                this.mensajes.add("CHEAT: Has añadido 10 de plata y 15 de cuarzo a tu inventario.");
            }
            case 3 -> {
                jugador.addRemoveToInventario(ItemTipo.SILICIO, 10);
                jugador.addRemoveToInventario(ItemTipo.ORO, 3);
                jugador.addRemoveToInventario(ItemTipo.CUARZO, 5);
                this.mensajes.add("CHEAT: Has añadido 10 de silicio, 3 de oro y 5 de cuarzo a tu inventario.");
            }
            case 4 -> {
                jugador.addRemoveToInventario(ItemTipo.MODULO_PROFUNDIDAD, 1);
                this.mensajes.add("CHEAT: Has añadido 1 módulo de profundidad a tu inventario.");
            }
            case 5 -> {
                jugador.addRemoveToInventario(ItemTipo.COBRE, 15);
                jugador.addRemoveToInventario(ItemTipo.MAGNETITA, 10);
                jugador.addRemoveToInventario(ItemTipo.DIAMANTE, 5);
                jugador.addRemoveToInventario(ItemTipo.ACERO, 20);
                this.mensajes.add("CHEAT: Has añadido 15 de cobre, 10 de magnetita, 5 de diamante y 20 de acero a tu inventario.");
            }
            case 6 -> {
                jugador.addRemoveToInventario(ItemTipo.PLANO_NAVE, 1);
                jugador.addRemoveToInventario(ItemTipo.TITANIO, 50);
                jugador.addRemoveToInventario(ItemTipo.ACERO, 30);
                jugador.addRemoveToInventario(ItemTipo.URANIO, 15);
                jugador.addRemoveToInventario(ItemTipo.SULFURO, 20);
                this.mensajes.add("CHEAT: Has añadido los planos, 50 de titanio, 30 de acero, 15 de uranio y 20 de sulfuro a tu inventario.");
            }
            default -> {
                this.mensajes.add("Pillín jijuja");
            }
        }
    }

    private void menuExterior(Jugador jugador)  throws IOException {

        /*
         * Despliega el menú exterior y maneja las opciones seleccionadas por el jugador.
         * @param Parámetro(s): Jugador jugador
         * @return void
         */

        System.out.println("----- Menú -----");
        System.out.println("1. Ascender/Descender");
        System.out.println("2. Ver inventario");
        System.out.println("3. Recolectar recursos");
        System.out.println("4. Explorar la zona");
        System.out.println("5. Cambiar de zona");
        System.out.println("6. Entrar a la nave");
        if (jugador.getRobot() != null) System.out.println("7. Usar robot excavador");

        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> {
                System.out.println("¿Cuántos metros quieres subir/bajar (usa valores negativos para sumergirte más)...");
                int difProfundidad = scanner.nextInt();
                jugador.setProfundidadActual(difProfundidad); 
            }
            case 2 -> {
                System.out.print("\033[H\033[2J");
                jugador.verInventario();
                if (jugador.getRobot() != null) {
                    System.out.println();
                    jugador.getRobot().verInventario();
                }
                System.out.println("Presiona Enter para volver al menú...");
                System.in.read();
            }
            case 3 -> {
                jugador.getZonaActual().recolectar(jugador);
            }
            case 4 -> {
                jugador.getZonaActual().explorar(jugador);
            }
            case 5 -> {
                this.menuZonas(jugador);
            }
            case 6 -> {
                if (jugador.getProfundidadActual() == jugador.getNave().getProfundidadActual()){
                    jugador.inOutNave();
                }
                else {
                    this.mensajes.add("No puedes entrar a la nave si no estás a la misma profundidad que ella.");
                    this.mensajes.add("La nave está anclada en " + jugador.getNave().getProfundidadActual() + " metros.");
                }
            }
            case 7 -> {
                if (jugador.getRobot() != null) {
                    if (jugador.getRobot().isActivo()) {
                        jugador.getZonaActual().recolectar(jugador, jugador.getRobot());
                    } else {
                        this.mensajes.add("Tu robot excavador está averiado. Debes repararlo en la mesa de trabajo.");
                    }
                } else {
                    this.mensajes.add("No tienes un robot excavador. Debes crearlo en la mesa de trabajo.");
                }
            }
            default -> {
                this.mensajes.add("Opción ingresada inválida.");
            }
        }
    }

    public void mostrarMenu(Jugador jugador){
        jugador.verEstadoJugador();
        this.mensajesRecientes();
        if (jugador.estaEnNave()){
            try {this.menuInterior(jugador);}
            catch (IOException e) {e.printStackTrace();}
            catch (InputMismatchException e) {scanner.nextLine(); this.mensajes.add("Opción ingresada inválida.");}
        }
        else {
            try {this.menuExterior(jugador);}
            catch (IOException e) {e.printStackTrace();}
            catch (InputMismatchException e) {scanner.nextLine(); this.mensajes.add("Opción ingresada inválida.");}
        }
    }
    

    private void mensajesRecientes() {
        /*
        Maneja la lógica de la cola de mensajes acumulados enviados por
        las demás funciones. La verdad estoy orgulloso por lo conveniente que resultó
        esto. Los mensajes se acumulan durante la ejecución interna del turno y se imprimen al final
        durante la impresión del HUD.
        */
        System.out.println();
        if (mensajes.isEmpty()) {
            System.out.println();
        } else {
            for (String mensaje : mensajes) {
                System.out.println("- " + mensaje);
            }
            mensajes.clear(); // Limpiar los mensajes después de mostrarlos
        }
        System.out.println();
    }

    private void menuZonas(Jugador jugador) {
        /*
         * Despliega y maneja el menú de cambio de zonas.
         * Hace comprobaciones individuales para evitar que el jugador atraviese más de una zona a la vez.
         * (No hay tanto tiempo para programar bien eso :'[ )
         * @param Parámetro(s): Jugador jugador
         * @return void
         * 
         */

        System.out.println("----- Zonas Disponibles -----");
        System.out.println("1. Zona Arrecife");
        System.out.println("2. Zona Profunda");
        System.out.println("3. Zona Volcánica");
        System.out.println("4. Nave Estrellada");
        System.out.print("Seleccione una zona para entrar: ");
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> { //arrecife
                    if (jugador.getZonaActual().getNombre().equals("Volcán")){
                        this.mensajes.add("No puedes atravesar más de una zona a la vez. Debes viajar por las zonas una a la vez.");
                        break;
                    }
                    jugador.getZonas().get(0).entrar(jugador);
                }
                case 2 -> { //profunda
                    if (jugador.getZonaActual().getNombre().equals("Nave Estrellada")){
                        this.mensajes.add("No puedes atravesar más de una zona a la vez. Debes viajar por las zonas una a la vez.");
                        break;
                    }
                    jugador.getZonas().get(1).entrar(jugador);
                }
                case 3 -> {//volcanica
                    if (!jugador.getZonaActual().getNombre().equals("Profundidades")){
                        this.mensajes.add("No puedes atravesar más de una zona a la vez. Debes viajar por las zonas una a la vez.");
                        break;
                    }
                    jugador.getZonas().get(2).entrar(jugador);
                }
                case 4 -> { //nave estrellada
                    if (!jugador.getZonaActual().getNombre().equals("Arrecife")){
                        this.mensajes.add("No puedes atravesar más de una zona a la vez. Debes viajar por las zonas una a la vez.");
                        break;
                    }
                    jugador.getZonas().get(3).entrar(jugador);
                }
                default -> this.mensajes.add("Opción de zona ingresada inválida.");
            }
        } catch (InputMismatchException e) {
            scanner.nextLine();
            this.mensajes.add("Opción de zona ingresada inválida.");
        }

    }

    private void mesaDeTrabajo(Jugador jugador) {
        /*
         * Despliega y maneja el menú de la mesa de trabajo.
         * @param Parámetro(s): Jugador jugador
         * @return void
         * 
        */

        System.out.println("----- Mesa de Trabajo -----");
        System.out.println("1. Mejorar tanque de oxígeno");
        System.out.println("2. Mejorar profundidad máxima de la nave");
        System.out.println("3. Fabricar tanque de oxigeno extra");
        System.out.println("4. Fabricar traje térmico");
        System.out.println("5. Robot excavador");
        System.out.println("6. Reparar nave estrellada");
        
        System.out.print("Seleccione una opción: ");
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1 -> {
                    if (jugador.mejoraTanque()){
                        this.mensajes.add("Ya has mejorado tu tanque de oxígeno.");
                        break;
                    }
                    if (this.mejoraTanque(jugador)){
                        this.mensajes.add("Has mejorado tu tanque de oxígeno. Los efectos de la presión se anulan");
                    }
                    else {
                        this.mensajes.add("Necesitas 3 piezas de tanque para mejorar tu tanque de oxígeno.\nLas piezas no pudieron caer tan lejos. Prueba buscando en el arrecife.");
                    }
                }
                case 2 -> {
                    if (!jugador.getNave().isModuloEncontrado()){
                        if (this.instalarModulo(jugador)){
                            this.mensajes.add("Has instalado el módulo de profundidad. La profundidad máxima de tu nave ha aumentado a 1500 metros");
                        }
                        else {
                            this.mensajes.add("Necesitas el módulo de profundidad para mejorar la profundidad máxima de tu nave.\nPuedes encontrarlo explorando la nave estrellada.");
                        }
                    }
                    else {
                        this.mensajes.add("Ya has instalado el módulo de profundidad en tu nave.");
                    }
                }
                case 3 -> {
                    if (jugador.mejoraTanque()){
                        if (this.añadirTanque(jugador)){
                            this.mensajes.add("Has fabricado un tanque de oxígeno extra. Tu capacidad máxima de oxígeno ha aumentado en 30 unidades.");
                        }
                        else {
                            this.mensajes.add("Necesitas 10 de plata y 15 de cuarzo para fabricar un tanque de oxígeno extra.");
                        }
                    }
                    else {
                        this.mensajes.add("Necesitas mejorar tu tanque de oxígeno antes de poder fabricar tanques extra.");
                    }
                }
                case 4 -> {
                    if (!jugador.trajeTermico()){
                        if (this.fabricarTrajeTermico(jugador)){
                            this.mensajes.add("Has fabricado un traje térmico. Ahora puedes explorar sin preocuparte por la temperatura.");
                        }
                        else {
                            this.mensajes.add("Necesitas 10 de silicio, 3 de oro y 5 de cuarzo para fabricar un traje térmico.");
                        }
                    }
                    else {
                        this.mensajes.add("Ya tienes un traje térmico, alcornoque!");

                    }
                }
                case 5 -> {
                    menuRobotExcavador(jugador);
                }
                case 6 -> {
                    if (jugador.tienePlano()){
                        if (this.repararNave(jugador)){
                            this.mensajes.add("Has reparado la nave estrellada!");
                        }
                        else {
                            this.mensajes.add("Necesitas 50 de titanio, 30 de acero, 15 de uranio y 20 de sulfuro para reparar la nave estrellada.");
                        }
                    }
                    else {
                        this.mensajes.add("Necesitas conseguir los planos de la nave para poder repararla.");
                    }
                }
                default -> this.mensajes.add("Opción ingresada inválida.");
            }
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Limpiar el token inválido
            this.mensajes.add("Opción ingresada inválida.");
        }
    }

    private void menuRobotExcavador(Jugador jugador) {
        /*
         * Despliega y maneja el menú del robot excavador.
         * Permite crear, mejorar y reparar el robot excavador.
         * Obs: A diferencia de las demás funciones, decidí poner toda la lógica de crafteos
         * en cada switch case para evitar crear demasiadas funciones pequeñas.
         * @param Parámetro(s): Jugador jugador
         * @return void
         * 
         */
        System.out.println("----- Robot Excavador -----");
        if (jugador.getRobot() == null) {
            System.out.println("1. Crear robot excavador.");
            System.out.println("2. Volver al menú.");
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1 -> {
                        Item cobreJugador = null, magnetitaJugador = null, diamanteJugador = null, aceroJugador = null;
                        Item cobreNave = null, magnetitaNave = null, diamanteNave = null, aceroNave = null;

                        for (Item item : jugador.getInventario()) {
                            if (null != item.getTipo()) switch (item.getTipo()) {
                                case COBRE -> cobreJugador = item;
                                case MAGNETITA -> magnetitaJugador = item;
                                case DIAMANTE -> diamanteJugador = item;
                                case ACERO -> aceroJugador = item;
                                default -> {
                                }
                            }
                        }
                        for (Item item : jugador.getNave().getBodega()) {
                            if (null != item.getTipo()) switch (item.getTipo()) {
                                case COBRE -> cobreNave = item;
                                case MAGNETITA -> magnetitaNave = item;
                                case DIAMANTE -> diamanteNave = item;
                                case ACERO -> aceroNave = item;
                                default -> {
                                }
                            }
                        }

                        if (cobreJugador == null || magnetitaJugador == null || diamanteJugador == null || aceroJugador == null ||cobreNave == null || magnetitaNave == null || diamanteNave == null || aceroNave == null) {
                            this.mensajes.add("Error al craftear el robot excavador. Referencia a item nulo.");
                            return;
                        }
                    
                        if (cobreJugador.getCantidad() + cobreNave.getCantidad() >= 15 && magnetitaJugador.getCantidad() + magnetitaNave.getCantidad() >= 10 && diamanteJugador.getCantidad() + diamanteNave.getCantidad() >= 5 && aceroJugador.getCantidad() + aceroNave.getCantidad() >= 20) {
                            
                            if (cobreJugador.getCantidad() >= 15) {
                                jugador.addRemoveToInventario(ItemTipo.COBRE, -15);
                            } else {
                                jugador.getNave().añadirObjeto(ItemTipo.COBRE, -(15 - cobreJugador.getCantidad()));
                                jugador.addRemoveToInventario(ItemTipo.COBRE, cobreJugador.getCantidad());
                            }
                            if (magnetitaJugador.getCantidad() >= 10) {
                                jugador.addRemoveToInventario(ItemTipo.MAGNETITA, -10);
                            } else {
                                jugador.getNave().añadirObjeto(ItemTipo.MAGNETITA, -(10 - magnetitaJugador.getCantidad()));
                                jugador.addRemoveToInventario(ItemTipo.MAGNETITA, magnetitaJugador.getCantidad());
                            }
                            if (diamanteJugador.getCantidad() >= 5) {
                                jugador.addRemoveToInventario(ItemTipo.DIAMANTE, -5);
                            } else {
                                jugador.getNave().añadirObjeto(ItemTipo.DIAMANTE, -(5 - diamanteJugador.getCantidad()));
                                jugador.addRemoveToInventario(ItemTipo.DIAMANTE, diamanteJugador.getCantidad());
                            }
                            if (aceroJugador.getCantidad() >= 20) {
                                jugador.addRemoveToInventario(ItemTipo.ACERO, -20);
                            } else {
                                jugador.getNave().añadirObjeto(ItemTipo.ACERO, -(20 - aceroJugador.getCantidad()));
                                jugador.addRemoveToInventario(ItemTipo.ACERO, aceroJugador.getCantidad());
                            }
                            
                            jugador.setRobot(new RobotExcavador());
                            this.mensajes.add("Has creado un robot excavador. Puedes usarlo para extraer recursos más eficientemente. Viva la industrialización!");
                        }
                        else {
                            this.mensajes.add("Necesitas 15 de cobre, 10 de magnetita, 5 de diamante y 20 de acero para crear el robot excavador.");
                        }
                    }
                }

            } catch (InputMismatchException e) {
                scanner.nextLine(); // Limpiar el token inválido
                this.mensajes.add("Opción ingresada inválida.");
            }
            
        } else {
            System.out.println("1. Mejorar robot excavador");
            System.out.println("2. Reparar robot excavador");
            System.out.println("3. Volver al menú");
            System.out.print("Seleccione una opción: ");
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                switch (opcion) {
                    case 1 -> {
                        Item titanioJugador = null, cuarzoJugador = null;
                        Item titanioNave = null, cuarzoNave = null;

                        for (Item item : jugador.getInventario()) {
                            if (item.getTipo() == ItemTipo.TITANIO) {
                                titanioJugador = item;
                            } else if (item.getTipo() == ItemTipo.CUARZO) {
                                cuarzoJugador = item;
                            }
                        }
                        for (Item item : jugador.getNave().getBodega()) {
                            if (item.getTipo() == ItemTipo.TITANIO) {
                                titanioNave = item;
                            } else if (item.getTipo() == ItemTipo.CUARZO) {
                                cuarzoNave = item;
                            }
                        }

                        if (titanioJugador == null || cuarzoJugador == null || titanioNave == null || cuarzoNave == null) {
                            this.mensajes.add("Error al mejorar el robot excavador. Referencia a item nulo.");
                            return;
                        }

                        if (titanioJugador.getCantidad() + titanioNave.getCantidad() >= 10 && cuarzoJugador.getCantidad() + cuarzoNave.getCantidad() >= 20) {
                            
                            if (titanioJugador.getCantidad() >= 10) {
                                jugador.addRemoveToInventario(ItemTipo.TITANIO, -10);
                            } else {
                                jugador.getNave().añadirObjeto(ItemTipo.TITANIO, -(10 - titanioJugador.getCantidad()));
                                jugador.addRemoveToInventario(ItemTipo.TITANIO, titanioJugador.getCantidad());
                            }
                            if (cuarzoJugador.getCantidad() >= 20) {
                                jugador.addRemoveToInventario(ItemTipo.CUARZO, -20);
                            } else {
                                jugador.getNave().añadirObjeto(ItemTipo.CUARZO, -(20 - cuarzoJugador.getCantidad()));
                                jugador.addRemoveToInventario(ItemTipo.CUARZO, cuarzoJugador.getCantidad());
                            }
                            jugador.getRobot().mejorarCapacidad();
                            this.mensajes.add("Has mejorado tu robot excavador. Su capacidad de carga se incrementó un 25%");
                        } else {
                            this.mensajes.add("Necesitas 10 de titanio y 20 de cuarzo para mejorar el robot excavador.");
                        }
                    }
                    case 2 -> {
                        int cables, piezasDeMetal, magnetita;
                        cables = piezasDeMetal = magnetita = 0;
                        for (Item item : jugador.getInventario()) {
                            if (null != item.getTipo()) switch (item.getTipo()) {
                                case CABLES -> cables += item.getCantidad();
                                case PIEZAS_DE_METAL -> piezasDeMetal += item.getCantidad();
                                case MAGNETITA -> magnetita += item.getCantidad();
                                default -> {
                                }
                            }
                        }
                        for (Item item : jugador.getNave().getBodega()) {
                            if (null != item.getTipo()) switch (item.getTipo()) {
                                case CABLES -> cables += item.getCantidad();
                                case PIEZAS_DE_METAL -> piezasDeMetal += item.getCantidad();
                                case MAGNETITA -> magnetita += item.getCantidad();
                                default -> {
                                }
                            }
                        }
                        if (cables >= 4 && piezasDeMetal >= 3 && magnetita >= 5) {
                            jugador.addRemoveToInventario(ItemTipo.CABLES, -4);
                            jugador.addRemoveToInventario(ItemTipo.PIEZAS_DE_METAL, -3);
                            jugador.addRemoveToInventario(ItemTipo.MAGNETITA, -5);
                            jugador.getRobot().setActivo(true);
                            this.mensajes.add("Has reparado tu robot excavador. Como nuevo!");
                        } else {
                            this.mensajes.add("Necesitas 4 de cables, 3 de piezas de metal y 5 de magnetita para reparar el robot excavador.");
                        }
                    }
                    case 3 -> {break;}
                    default -> this.mensajes.add("Opción ingresada inválida.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Limpiar el token inválido
                this.mensajes.add("Opción ingresada inválida.");
            }
        }
    }

    private boolean mejoraTanque(Jugador jugador){
        /*
         * Mejora el tanque de oxígeno del jugador si tiene suficientes piezas.
         * Recorre los inventarios del jugador y la bodega de la nave para contar las piezas.
         * Si hay suficientes, mejora el tanque y elimina las piezas usadas priorizando las que estén en el inventario del jugador.
         * @param Parámetro(s): Jugador jugador
         * @return boolean (true si se mejoró el tanque, false si no se pudo mejorar)
         */
        Item piezasTanqueJugador = null, piezasTanqueNave = null;
        for (Item item : jugador.getInventario()){
            if (item.getTipo() == ItemTipo.PIEZA_TANQUE){
                piezasTanqueJugador = item;
            }

        }
        for (Item item : jugador.getNave().getBodega()){
            if (item.getTipo() == ItemTipo.PIEZA_TANQUE){
                piezasTanqueNave = item;
            }
        }

        if (piezasTanqueJugador == null || piezasTanqueNave == null) {
            this.mensajes.add("Error al mejorar el tanque de oxígeno. Referencia a item nulo.");
            return false;
        }

        if (piezasTanqueJugador.getCantidad() + piezasTanqueNave.getCantidad() >= 3){
            jugador.mejorarTanque();
            if (piezasTanqueJugador.getCantidad() >= 3){
                piezasTanqueJugador.addRemoveCantidad(-3);
            }
            else {
                jugador.getNave().añadirObjeto(ItemTipo.PIEZA_TANQUE, -(3 - piezasTanqueJugador.getCantidad()));
                jugador.addRemoveToInventario(ItemTipo.PIEZA_TANQUE, -piezasTanqueJugador.getCantidad());
            }
            return true;
        }
        return false;
    }

    private boolean añadirTanque(Jugador jugador){
        /*
         * Añade un tanque de oxígeno extra al jugador si tiene suficientes recursos.
         * Recorre los inventarios del jugador y la bodega de la nave para contar los recursos.
         * Si hay suficientes, añade el tanque y elimina los recursos usados priorizando los que estén en el inventario del jugador.
         * @param Parámetro(s): Jugador jugador
         * @return boolean (true si se añadió el tanque, false si no se pudo añadir)
         */
        Item plataJugador = null, cuarzoJugador = null, plataNave = null, cuarzoNave = null;
        for (Item item : jugador.getInventario()){
            if (item.getTipo() == ItemTipo.PLATA){
                plataJugador = item;
            }
            else if (item.getTipo() == ItemTipo.CUARZO){
                cuarzoJugador = item;
            }

        }
        for (Item item : jugador.getNave().getBodega()){
            if (item.getTipo() == ItemTipo.PLATA){
                plataNave = item;
            }
            else if (item.getTipo() == ItemTipo.CUARZO){
                cuarzoNave = item;
            }
        }

        if (plataJugador == null || cuarzoJugador == null || plataNave == null || cuarzoNave == null) {
            this.mensajes.add("Error al fabricar tanque de oxígeno extra. Referencia a item nulo.");
            return false;
        }

        if (plataJugador.getCantidad() + plataNave.getCantidad() >= 10 && cuarzoJugador.getCantidad() + cuarzoNave.getCantidad() >= 15){
            jugador.getTanqueOxigeno().mejorarOxigeno();
            if (plataJugador.getCantidad() >= 10){
                jugador.addRemoveToInventario(ItemTipo.PLATA, -10);
            }
            else {
                jugador.getNave().añadirObjeto(ItemTipo.PLATA, -(10 - plataJugador.getCantidad()));
                jugador.addRemoveToInventario(ItemTipo.PLATA, -plataJugador.getCantidad());
            }
            if (cuarzoJugador.getCantidad() >= 15){
                jugador.addRemoveToInventario(ItemTipo.CUARZO, -15);
            }
            else {
                jugador.getNave().añadirObjeto(ItemTipo.CUARZO, -(15 - cuarzoJugador.getCantidad()));
                jugador.addRemoveToInventario(ItemTipo.CUARZO, -cuarzoJugador.getCantidad());
            }
            return true;
        }
        return false;
    }

    private boolean instalarModulo(Jugador jugador){
        /*
         * Instala el módulo de profundidad en la nave si el jugador tiene uno en su inventario
         * Recorre los inventarios del jugador y la bodega de la nave para buscar el módulo.
         * Si lo encuentra, instala el módulo y elimina el módulo del inventario del jugador o de la nave.
         * @param Parámetro(s): Jugador jugador
         * @return boolean (true si se instaló el módulo, false si no se pudo instalar)
         */
        Item moduloJugador = null, moduloNave = null;
        for (Item item : jugador.getInventario()){
            if (item.getTipo() == ItemTipo.MODULO_PROFUNDIDAD){
                moduloJugador = item;
            }
        }
        for (Item item : jugador.getNave().getBodega()){
            if (item.getTipo() == ItemTipo.MODULO_PROFUNDIDAD){
                moduloNave = item;
            }
        }

        if (moduloJugador == null || moduloNave == null) {
            this.mensajes.add("Error al instalar el módulo de profundidad. Referencia a item nulo.");
            return false;
        }

        if (moduloJugador.getCantidad() + moduloNave.getCantidad() >= 1){
            jugador.getNave().setModuloEncontrado();
            if (moduloJugador.getCantidad() >= 1){
                jugador.addRemoveToInventario(ItemTipo.MODULO_PROFUNDIDAD, -1);
            }
            else {
                jugador.getNave().añadirObjeto(ItemTipo.MODULO_PROFUNDIDAD, -(1 - moduloJugador.getCantidad()));
                jugador.addRemoveToInventario(ItemTipo.MODULO_PROFUNDIDAD, -moduloJugador.getCantidad());

            }
            return true;
        }
        return false;
    }

    private boolean fabricarTrajeTermico(Jugador jugador){
        /*
         * Fabrica un traje térmico si el jugador tiene suficientes recursos.
         * Recorre los inventarios del jugador y la bodega de la nave para contar los recursos.
         * Si hay suficientes, fabrica el traje y elimina los recursos usados priorizando los que estén en el inventario del jugador.
         * @param Parámetro(s): Jugador jugador
         * @return boolean (true si se fabricó el traje, false si no se pudo fabricar)
         */
        Item silicioJugador = null, oroJugador = null, cuarzoJugador = null;
        Item silicioNave = null, oroNave = null, cuarzoNave = null;
        for (Item item : jugador.getInventario()){
            if (    null != item.getTipo())switch (item.getTipo()) {
                        case SILICIO -> silicioJugador = item;
                        case ORO -> oroJugador = item;
                        case CUARZO -> cuarzoJugador = item;
                        default -> {
                }
                    }
        }
        for (Item item : jugador.getNave().getBodega()){
            if (null != item.getTipo())switch (item.getTipo()) {
                case SILICIO -> silicioNave = item;
                case ORO -> oroNave = item;
                case CUARZO -> cuarzoNave = item;
                default -> {
                }
            }
        }

        if (silicioJugador == null || oroJugador == null || cuarzoJugador == null || silicioNave == null || oroNave == null || cuarzoNave == null) {
            this.mensajes.add("Error al fabricar el traje térmico. Referencia a item nulo.");
            return false;
        }

        if (silicioJugador.getCantidad() + silicioNave.getCantidad() >= 10 && oroJugador.getCantidad() + oroNave.getCantidad() >= 3 && cuarzoJugador.getCantidad() + cuarzoNave.getCantidad() >= 5){
            jugador.setTrajeTermico(true);

            if (silicioJugador.getCantidad() >= 10){
                jugador.addRemoveToInventario(ItemTipo.SILICIO, -10);
            }
            else {
                jugador.getNave().añadirObjeto(ItemTipo.SILICIO, -(10 - silicioJugador.getCantidad()));
                jugador.addRemoveToInventario(ItemTipo.SILICIO, -silicioJugador.getCantidad());
            }

            if (oroJugador.getCantidad() >= 3){
                jugador.addRemoveToInventario(ItemTipo.ORO, -3);
            }
            else {
                jugador.getNave().añadirObjeto(ItemTipo.ORO, -(3 - oroJugador.getCantidad()));
                jugador.addRemoveToInventario(ItemTipo.ORO, -oroJugador.getCantidad());
            }

            if (cuarzoJugador.getCantidad() >= 5){
                jugador.addRemoveToInventario(ItemTipo.CUARZO, -5);
            }
            else {
                jugador.getNave().añadirObjeto(ItemTipo.CUARZO, -(5 - cuarzoJugador.getCantidad()));
                jugador.addRemoveToInventario(ItemTipo.CUARZO, -cuarzoJugador.getCantidad());
            }
            return true;
        }
        return false;
    }

    private boolean repararNave(Jugador jugador){
        /*
         * Repara la nave estrellada si el jugador tiene suficientes recursos.
         * Recorre los inventarios del jugador y la bodega de la nave para contar los recursos.
         * Si hay suficientes, repara la nave y elimina los recursos usados priorizando los que estén en el inventario del jugador.
         * Al reparar la nave, se cambia a true el atributo/flag naveReparada del jugador.
         * Se debe seleccionar desde el menú "Mesa de trabajo".
         * La secuencia de fin del juego se maneja en Main mediante la flag.
         * @param Parámetro(s): Jugador jugador
         * @return boolean (true si se reparó la nave, false si no se pudo reparar).
         */
        Item titanioJugador = null, aceroJugador = null, uranioJugador = null, sulfuroJugador = null;
        Item titanioNave = null, aceroNave = null, uranioNave = null, sulfuroNave = null;

        for (Item item : jugador.getInventario()){
            if (null != item.getTipo()) switch (item.getTipo()) {
                    case TITANIO -> titanioJugador = item;
                    case ACERO -> aceroJugador = item;
                    case URANIO -> uranioJugador = item;
                    case SULFURO -> sulfuroJugador = item;
                    default -> {
                }
                }
        }
        for (Item item : jugador.getNave().getBodega()){
            if (null != item.getTipo()) switch (item.getTipo()) {
                case TITANIO -> titanioNave = item;
                case ACERO -> aceroNave = item;
                case URANIO -> uranioNave = item;
                case SULFURO -> sulfuroNave = item;
                default -> {
                }
            }
        }

        if (titanioJugador == null || aceroJugador == null || uranioJugador == null || sulfuroJugador == null || titanioNave == null || aceroNave == null || uranioNave == null || sulfuroNave == null){
            System.out.println("Error al contar los objetos necesarios para reparar la nave. Referencia a algún objeto == null");
            this.mensajes.add("Error al craftear. Referencia a algún objeto == null");
            return false;
        }

        if (titanioJugador.getCantidad() + titanioNave.getCantidad() >= 50 && aceroJugador.getCantidad() + aceroNave.getCantidad() >= 30 && uranioJugador.getCantidad() + uranioNave.getCantidad() >= 15 && sulfuroJugador.getCantidad() + sulfuroNave.getCantidad() >= 20){
            jugador.setNaveReparada(true);

            if (titanioJugador.getCantidad() >= 50){
                titanioJugador.addRemoveCantidad(-50);
            }
            else {
                jugador.getNave().añadirObjeto(ItemTipo.TITANIO, -(50 - titanioJugador.getCantidad()));
                jugador.addRemoveToInventario(ItemTipo.TITANIO, titanioJugador.getCantidad());
            }

            if (aceroJugador.getCantidad() >= 30){
                aceroJugador.addRemoveCantidad(-30);
            }
            else {
                jugador.getNave().añadirObjeto(ItemTipo.ACERO, -(30 - aceroJugador.getCantidad()));
                jugador.addRemoveToInventario(ItemTipo.ACERO, aceroJugador.getCantidad());
            }

            if (uranioJugador.getCantidad() >= 15){
                uranioJugador.addRemoveCantidad(-15);
            }
            else {
                jugador.getNave().añadirObjeto(ItemTipo.URANIO, -(15 - uranioJugador.getCantidad()));
                jugador.addRemoveToInventario(ItemTipo.URANIO, uranioJugador.getCantidad());
            }

            if (sulfuroJugador.getCantidad() >= 20){
                sulfuroJugador.addRemoveCantidad(-20);
            }
            else {
                jugador.getNave().añadirObjeto(ItemTipo.SULFURO, -(20 - sulfuroJugador.getCantidad()));
                jugador.addRemoveToInventario(ItemTipo.SULFURO, sulfuroJugador.getCantidad());
            }
            return true;
        }
        return false;
    }

}
