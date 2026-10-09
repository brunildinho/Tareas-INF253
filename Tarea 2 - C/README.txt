Bruno Andrade

Objetivo de la tarea:
Implementar un minijuego de defensa espacial por turnos en terminal, modelando un tablero bidimensional con triple puntero, un inventario de disparos implementado mediante punteros a función, y un sistema de olas controlado. El estudiante deberá diseñar estructuras de datos dinámicas, respetar reglas deterministas de movimiento/colisión y producir una interfaz ASCII clara con HUD persistente.

Instrucciones:
Ejecutar el archivo make para compilar, ingresando por terminal:
>make
Se generará un objeto juego. Para iniciar el juego, ingresar por terminal:
./juego
Los controles se mencionan antes de iniciar el juego.

Arma especial: Munición rastreadora
Busca al alien más cercano a la base del tablero y le infringe un daño de 1 [hp].
En caso de conflicto, se dispara al de más a la izquierda.
Se entrega 1 munición al inicio de la partida y cada alien muerto tiene un 10% de probabilidad de soltar una.

Alien especial: Errante
Cada dos turnos baja una casilla, y horizontalmente se mueve al azar a la izquierda, derecha, o en linea recta. 1 [hp]

Observaciones:
-El tablero está ordenado de tal forma que celdas[i][0] corresponde a la fila superior, y celdas[i][H-1] a la fila inferior.

-El jugador es capaz de ingresar una entrada multiple de una sola vez (ejemplos: 'aaaa', 'ddd', 'aa1', 'ddd2'), en cuyo caso imprimirá el tablero con la conseccuencia de la útlima acción ingresada.
Debido a esto, no se recomienda ingresar entradas del tipo 'aaa1dd', pues el hud no mostrará si al disparar hubo alguna baja.
Se puede usar esta mecánica para moverse entre varios bloques de una vez, y luego disparar si así se desea.

-Se implementó un sistema anticolisiones para los aliens que funciona de la siguiente manera:
.Se mueven los aliens empezando por los de más abajo y en el siguiente orden: Drones, Skaters, Especiales.
.Si un Skater o Especial intenta moverse a una celda ocupada, intentará rebotar. Si al intentar rebotar se topa con otra celda ocupada, intentará bajar en linea recta. Si también está ocupada, se autodestruye y avisa por pantalla (Colisión entre aliens)

-Si a la hora de elegir dificutad se lee una entrada invalida, por defecto el juego iniciará en dificil.

V1.1