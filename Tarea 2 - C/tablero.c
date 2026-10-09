#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "tablero.h"
#include "main.h"
#include "entidades.h"
#include "spawn.h"
#include "armas.h"

struct Tablero*tablero_crear(int ancho, int alto){
    /*
    * Nombre: tablero_crear
    * Parámetros: 2 enteros (ancho y alto)
    * Retorno: Puntero a Tablero.
    * Descripción: Inicializa el tablero con un triple puntero,
    * además de un array para la posición inicial del jugador.
    */

    Tablero* tablero = malloc(sizeof(Tablero));
    tablero->H = alto;
    tablero->W = ancho;
    tablero->celdas = malloc(ancho * sizeof(*tablero->celdas));
    for(int i = 0; i < ancho; i++){
        tablero->celdas[i] = malloc(alto * sizeof(*tablero->celdas[i]));
        for(int j = 0; j < alto; j++){
            Celda *cel = malloc(sizeof *cel);
            cel->alien = NULL;
            cel->dano_pend = 0;
            tablero->celdas[i][j] = (void*)cel;
        }
    }

    tablero->jugador = malloc(ancho * sizeof(char));
    memset(tablero->jugador, ' ', ancho * sizeof(char));
    tablero->jugador[ancho/2] = '^';
    return tablero;
}

void tablero_imprimir(Juego *juego){
    /*
    * Nombre: tablero_imprimir
    * Parámetros: Puntero a Juego.
    * Retorno: ...nada
    * Descripción: Recorre el tablero para imprimirlo por pantalla.
    * Luego imprime el hud.
    */
    printf("\n=============================\n");
    for(int j = 0; j < juego->t->H; j++){
        for(int i = 0; i < juego->t->W; i++){
            Celda *c = (Celda*) juego->t->celdas[i][j];
            if (c == NULL || c->alien == NULL){
                printf("| |");
                //printf("|%d, %d|", i, j); // Representa una celda vacía
            } else {
                if (c->alien->tipo == 0){
                    printf("|E|");
                }
                else if (c->alien->tipo == 1){
                    printf("|S|");
                }
                else if (c->alien->tipo == 2){
                    printf("|D|");
                }
            }
        }
        printf("\n");
    }
    for (int i = 0; i < juego->t->W; i++){
        printf("|%c|", juego->t->jugador[i]);
    }
    /* HUD */
    printf("\n=============================\n");
    printf("Turno: %d | Vivos: %d | Restantes (inv): %d\n" , juego->turno, juego->vivos, juego->pool->especial + juego->pool->skater + juego->pool->drone);
    printf("Municion: Normal: Ilimitada | Perforador: (%d) | Especial: (%d)\n", juego->armas->ammo_perforador, juego->armas->ammo_especial);

}

void tablero_cerrar(Tablero *tablero){
    /*
    * Nombre: tablero_cerrar
    * Parámetros: Puntero a Tablero.
    * Retorno: vacio
    * Descripción: Libera el tablero
    */
    for(int i=0; i < tablero->W; i++){
        for(int j=0; j < tablero->H; j++){
            free((Celda*)tablero->celdas[i][j]);
            }
        free(tablero->celdas[i]);
        }
    free(tablero->jugador);
    free(tablero->celdas);
    free(tablero);
}