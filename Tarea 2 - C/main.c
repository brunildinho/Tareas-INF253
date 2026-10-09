#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>
#include "main.h"
#include "tablero.h"
#include "spawn.h"
#include "entidades.h"
#include "armas.h"

int main() {
    srand((unsigned)time(NULL));
    Juego* juego = malloc(sizeof(Juego));
    printf("Bienvenido a Space Invaders: USM Edition!\n\n");
    printf("Los aliens se han enterado del gran prestigio de nuestra universidad, así que han venido aquí a destruirla!\n");
    printf("Tu misión es defender la santa maria de los invasores (los demás estudiantes están ocupados intentando salvar LP D:)\n\n");
    printf("Controles: a = izquierda | d = derecha | 1 = Mun. normal | 2 = Mun. perforante | 3 = Mun. rastreadora | q = salir\n\n");
    printf("Seleccione dificultad: f = facil | d = dificil\n");
    char dif = getchar();
    if (dif == 'f' || dif == 'F') {
        juego->dificultad = 0; // facil
    } else if (dif == 'd' || dif == 'D') {
        juego->dificultad = 1; // dificil
    } else {
        printf("Por elegir mal, ahora te tocará jugar en dificil >:)\n");
        juego->dificultad = 1;
    }
    int ancho, alto;
    if (juego->dificultad == 0) {
        ancho = 5, alto = 15;
    } else {
        ancho = 7, alto = 15;
    }

    juego->jugador_x = ancho / 2;
    juego->t = tablero_crear(ancho, alto);
    juego->vivos = 0;
    juego->turno = 1;
    juego->armas = armas_crear();
    spawn_inicio(juego);

    system("clear");
    /* Ciclo de juego */
    while (1){
        tablero_imprimir(juego);
        juego->turno++;
        char c;
        scanf(" %c", &c); //input 

        if (c == 'q' || c == 'Q') break;

        else if (c == 'a' || c == 'A') {
            if (juego->jugador_x > 0) {
                juego->t->jugador[juego->jugador_x] = ' ';
                juego->jugador_x--;
                juego->t->jugador[juego->jugador_x] = '^';
                system("clear");
            }
            else {
                system("clear");
                printf("Error: Movimiento fuera de rango");
                juego->turno--;
                continue;
            }
        }

        else if (c == 'd' || c == 'D') {
            if (juego->jugador_x < juego->t->W - 1) {
                juego->t->jugador[juego->jugador_x] = ' ';
                juego->jugador_x++;
                juego->t->jugador[juego->jugador_x] = '^';
                system("clear");
            }
            else {
                system("clear");
                printf("Error: Movimiento fuera de rango");
                juego->turno--;
                continue;
            }
        }

        else if (c == '1' || c == '2' || c == '3') {
            if (disparar_armas(juego, (c - '0') - 1)){
                resolver_danos(juego);
            }
            else {
                system("clear");
                printf("Sin munición!");
                juego->turno--;
                continue;
            }
        }

        else {
            system("clear");
            printf("Error: Comando no reconocido, intente de nuevo.");
            juego->turno--;
            continue;
        }


        if (juego->vivos == 0) {
            system("clear");
            tablero_imprimir(juego);
        printf("¡Has ganado! Master, idolo, crack, maquina, super saiayin\nAhora tendrás más tiempo para estudiar matemáticas\n");
            break;
        }

        if (juego->turno % 2 != 0 && juego->turno != 1) mover_aliens(juego);
        
        spawn_turno(juego);
        if (rand() % 100 < 30) spawn_turno(juego); 

        if (game_over(juego)) {
            tablero_imprimir(juego);
            printf("Has perdido, los aliens han invadido la santa maria! :(\n");
            break;
        }
        

    }
    
    free(juego->armas);
    tablero_cerrar(juego->t);
    liberar_aliens(juego);
    free(juego);
    return 0;
}