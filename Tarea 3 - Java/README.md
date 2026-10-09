Bruno Andrade



Objetivo de la tarea:

Construir un prototipo en Java de un juego de exploración submarina por turnos donde el jugador gestiona oxígeno (O2), presión y temperatura para recolectar recursos y planos, y finalmente reparar la Nave Estrellada para escapar del planeta. El mundo se compone de cuatro zonas únicas y persistentes: ZonaArrecife, ZonaProfunda, ZonaVolcanica y NaveEstrellada.



Consideraciones:

\-Para iniciar, ejecute por terminal make clean -> make run

\-Por motivos prácticos, se estableció la "profundidad" de la zona NaveEstrellada como -1.

\-Por motivos de gameplay, se dejó que los movimientos en NaveEstrellada consumieran oxígeno; Para entrar a una zona en llamas por tiempos prolongados se necesitan tanques de oxígeno, como hacen los bomberos.

\-El uso del robot excavador gasta de forma fija 1 de oxígeno por cada uso. Aunque esto es fácilmente modificable cambiando el valor correspondiente en la linea jugador.getTanqueOxigeno().consumirO2(1) en el método recolectar(Jugador jugador, RobotExcavador robotExcavador) de cada zona.

\-Además, el robot excavador recolecta la misma cantidad de items que el jugador normal, ya que consideré que hacer que recogiera más estaría muy roto. De todas formas, es fácilmente modificable ya que el robot excavador usa su propia función de recolección definida en cada zona.

\-Se puede acceder a un menú de cheats secreto, ingresando al interior de la nave el código 253

\-El cambio de zona debe ser manual. No se puede acceder automáticamente a una profundidad fuera de los límites de la zona actual.

\-No se permite el cambio de zona a una que no sea contigua a la actual; no se pueden "atravesar" 2 zonas en un mismo movimiento. Si se quiere, por ejemplo, ir a la zona volcánica dede el arrecife, se debe primero cambiar manualmente a zona profunda, y una vez ahí, cambiar a zona volcánica.

\-No se permite entrar a la nave si el jugador no está en la misma profundidad de anclaje. Si el jugador intenta entrar a una profundidad distinta, se imprime una advertencia y la profundidad actual de la nave.

\-Todas las acciones de crafteo, mejoras y reparaciones se efectuan desde el submenú "Mesa de trabajo" al interior de la nave.

\-Al programar en VSCode, me aparecieron 5 warning relacionados al "no uso ni lectura" de algunas variables. Estas variables sí son usadas en el código a base de getters. O son usadas solamente para sobrecargar métodos.

