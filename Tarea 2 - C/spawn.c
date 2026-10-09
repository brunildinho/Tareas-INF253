#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "spawn.h"
#include "tablero.h"
#include "main.h"
#include "entidades.h"

void spawn_inicio(struct Juego *juego) {
    /*
    * Nombre: spawn_inicio
    * Parámetros: Puntero a Juego.
    * Retorno: void
    * Descripción: Inicializa el pool de aliens y genera los primeros aliens en el tablero.
    */
    juego->pool = malloc(sizeof(PoolAliens));
    int spawn;
    if (juego->dificultad == 0) {
        juego->pool->especial = 5;
        juego->pool->skater = 4;
        juego->pool->drone = 6;
        juego->pool->vivos_tope = 6;
        juego->aliens_vivos = malloc((juego->pool->especial + juego->pool->skater + juego->pool->drone) * sizeof(Alien*));
        spawn = 2;
    }
    else {
        juego->pool->especial = 6;
        juego->pool->skater = 6;
        juego->pool->drone = 8;
        juego->pool->vivos_tope = 8;
        juego->aliens_vivos = malloc((juego->pool->especial + juego->pool->skater + juego->pool->drone) * sizeof(Alien*));
        spawn = 3;
    }
    for (int i = 0; i < spawn; i++) {
        spawn_turno(juego);
    }
}

void spawn_turno(struct Juego *juego) {
    /*
    * Nombre: spawn_turno
    * Parámetros: Puntero a Juego.
    * Retorno: vacuo
    * Descripción: Intenta spawnear un alien en el tablero según probabilidades
    * si no se ha alcanzado el tope de aliens vivos.
    */
    
    while (1){
        if (juego->pool->vivos_tope > juego->vivos) {
            float prob0 = 1/3.0; //33.3% especial
            float prob1 = 4/15.0; // 26.7% skater
            float prob2 = 2/5.0; //40% drone
            float seed = (float)rand()/(float)RAND_MAX;
            
            if (seed < prob0 && juego->pool->especial > 0) {
                int casilla = rand() % juego->t->W;

                Celda *celda = (Celda *)juego->t->celdas[casilla][0];
                if (celda->alien == NULL) {
                    celda->alien = malloc(sizeof(Alien));
                    celda->alien->tipo = 0; // especial
                    celda->alien->hp = 2;
                    celda->alien->x = casilla;
                    celda->alien->y = 0;
                    celda->alien->dx = 0;
                    juego->aliens_vivos[juego->vivos] = celda->alien;
                    juego->pool->especial--;
                    juego->vivos++;
                    break;
                }
            } else if (seed < (prob0 + prob1) && juego->pool->skater > 0) {
                int casilla = rand() % juego->t->W;

                Celda *celda = (Celda *)juego->t->celdas[casilla][0]; 
                if (celda->alien == NULL) {
                    celda->alien = malloc(sizeof(Alien));
                    celda->alien->tipo = 1; // skater
                    celda->alien->hp = 1;
                    celda->alien->x = casilla;
                    celda->alien->y = 0;
                    celda->alien->dx = (rand() % 2) ? 1 : -1; // Direccion inicial aleatoria
                    juego->aliens_vivos[juego->vivos] = celda->alien;
                    juego->pool->skater--;
                    juego->vivos++;
                    break;
                }
            } else if (juego->pool->drone > 0) {
                int casilla = rand() % juego->t->W;

                Celda *celda = (Celda *)juego->t->celdas[casilla][0]; // Cast a Celda*
                if (celda->alien == NULL) {
                    celda->alien = malloc(sizeof(Alien));
                    celda->alien->tipo = 2; // drone
                    celda->alien->hp = 2;
                    celda->alien->x = casilla;
                    celda->alien->y = 0;
                    celda->alien->dx = 0;
                    juego->aliens_vivos[juego->vivos] = celda->alien;
                    juego->pool->drone--;
                    juego->vivos++;
                    break;
                }

            }
            else if (juego->pool->especial == 0 && juego->pool->skater == 0 && juego->pool->drone == 0) {
                break;
            }
        }
        else{
            break;
        }
    }
}

void liberar_aliens(struct Juego *juego) {
    /*
    * Nombre: liberar_aliens
    * Parámetros: Puntero a Juego.
    * Retorno: no
    * Descripción: Libera la memoria de los aliens (ahora son libres :D)
    */
    for (int i = 0; i < juego->vivos; i++) {
        free(juego->aliens_vivos[i]);
    }
    free(juego->aliens_vivos);
    free(juego->pool);
}