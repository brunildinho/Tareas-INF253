#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include "armas.h"
#include "main.h"
#include "tablero.h"
#include "entidades.h"

Armas* armas_crear() {
    /*
    * Nombre: armas_crear
    * Parámetros: ninguno
    * Retorno: puntero a Armas
    * Descripción: Inicializa el struct de armas
    */

    Armas* armas = (Armas*)malloc(sizeof(Armas));
    armas->ammo_perforador = 7; 
    armas->ammo_especial = 1; 
    armas->fn[0] = arma_normal;
    armas->fn[1] = arma_perforador;
    armas->fn[2] = arma_especial;
    return armas;
}

bool disparar_armas(struct Juego *juego, int arma_id) {
    /*
    * Nombre: disparar_armas
    * Parámetros: puntero a Juego, id de arma [0-2]
    * Retorno: booleano
    * Descripción: comprueba si el id de arma es válido e intenta dispararla llamando a una función dentro del array FuncArmas.
    */

    if (arma_id >= 0 && arma_id <= 2) {
        return juego->armas->fn[arma_id](juego);
    }
    else return false;
}

bool arma_normal (struct Juego *juego) {
    /*
    * Nombre: arma_normal
    * Parámetros: Puntero a Juego.
    * Retorno: booleano (true siempre)
    * Descripción: busca el primer alien en la columna del jugador y le aplica daño.
    */

    int i = juego->jugador_x;
    for (int j = juego->t->H - 1; j >= 0; j--){
        if (((Celda *)juego->t->celdas[i][j])->alien != NULL) {
            ((Celda *)juego->t->celdas[i][j])->dano_pend = -1;
            return true; //piu piu
        }
    }
    return true; 
}

bool arma_perforador (struct Juego *juego) {
    /*
    * Nombre: arma_perforador
    * Parámetros: Puntero a Juego.
    * Retorno: booleano
    * Descripción: Comprueba si el arma tiene munición. Si es así,
    * aplica daño a todos los aliens en la columna del jugador, descuenta munición y retorna true.
    */

    if (juego->armas->ammo_perforador > 0) {
        juego->armas->ammo_perforador--;
        int i = juego->jugador_x;
        for (int j = juego->t->H - 1; j >= 0; j--){
            if (((Celda *)juego->t->celdas[i][j])->alien != NULL) {
                ((Celda *)juego->t->celdas[i][j])->dano_pend = -1;
            }
        }
        return true;
    }
    else return false;
}

bool arma_especial (struct Juego *juego) {
    /*
    * Nombre: arma_epecial
    * Parámetros: Puntero a Juego.
    * Retorno: booleano
    * Descripción: Comprueba si el arma tiene munición. Si es así,
    * aplica daño al alien más cercano a la base, descuenta munición y retorna true.
    */

    if (juego->armas->ammo_especial > 0) {
        juego->armas->ammo_especial--;
        for (int j = juego->t->H - 1; j >= 0; j--){
            for (int i = 0; i < juego->t->W; i++){
                if (((Celda *)juego->t->celdas[i][j])->alien != NULL) {
                    ((Celda *)juego->t->celdas[i][j])->dano_pend = -1;
                    return true;
                }
            }
        }
        return true; 
    }
    else return false;
}

void ammo_recargar(struct Juego *juego) {
    /*
    * Nombre: ammo_recargar
    * Parámetros: Puntero a Juego.
    * Retorno: nada xd
    * Descripción: Dada ciertas probabilidades intenta recargar alguna de las armas.
    */
    float prob1 = 0.25;
    float prob2 = 0.10;
    float seed = (float)rand()/(float)RAND_MAX;
    if (seed < prob1) {
        juego->armas->ammo_perforador++;
        printf("Municion perforante +1! ");
    }
    else if (seed < prob1 + prob2) {
        juego->armas->ammo_especial++;
        printf("Municion rastreadora +1! ");
    }
    else return;
}