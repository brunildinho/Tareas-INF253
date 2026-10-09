#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include "main.h"
#include "tablero.h"
#include "entidades.h"
#include "armas.h"

void mover_aliens(struct Juego *juego){
    /*
    * Nombre: mover_aliens
    * Parámetros: Puntero a Juego.
    * Retorno: error 404, return not found (vacio)
    * Descripción: ejecuta en secuencia las funciones individuales para mover cada tipo de alien.
    * Esto debido a que cada alien tiene requerimientos especiales para moverse.
    */
    mover_drones(juego);
    mover_skater(juego);
    mover_especial(juego);
}

void mover_drones(struct Juego *juego){
    /*
    * Nombre: mover_drones
    * Parámetros: Puntero a Juego.
    * Retorno: ...qué es un retorno? (vacio)
    * Descripción: Mueve los aliens tipo drone (tipo 2) hacia abajo en línea recta.
    */
    for(int j = juego->t->H - 1; j >= 0; j--){
        for(int i = 0; i < juego->t->W; i++){
            Alien *alien = ((Celda*)juego->t->celdas[i][j])->alien;
            
            if (!alien || alien->tipo != 2) continue;

            int x_new = alien->x + alien->dx;
            int y_new = alien->y + 1;

            Celda *celda_actual = (Celda *)juego->t->celdas[alien->x][alien->y];
            Celda *nueva_celda = (Celda *)juego->t->celdas[x_new][y_new];
            if (nueva_celda->alien == NULL) {
                nueva_celda->alien = alien; 
                nueva_celda->alien->x = x_new;
                nueva_celda->alien->y = y_new;
                celda_actual->alien = NULL;
            }
        }
    }
}

void mover_skater(struct Juego *juego){
    /*
    * Nombre: mover_skater
    * Parámetros: Puntero a Juego.
    * Retorno: en terminos de retorno, no hay retorno.
    * Descripción: Mueve los aliens tipo skater (tipo 1) en diagonal, cambiando de dirección
    * al chocar con los bordes. Si colisiona con otro alien, intenta redirigirse.
    * Si no puede redirigirse, el alien es obliterado.
    */
    for(int j = juego->t->H - 1; j >= 0; j--){
        for(int i = 0; i < juego->t->W; i++){

            Alien *alien = ((Celda*)juego->t->celdas[i][j])->alien;

            if (!alien || alien->tipo != 1) continue;

            int x_new = alien->x + alien->dx;
            int y_new = alien->y + 1;

            if (x_new < 0 || x_new >= juego->t->W) {
                alien->dx = -alien->dx; 
                x_new = alien->x + alien->dx; 
            }

            Celda *celda_actual = (Celda *)juego->t->celdas[alien->x][alien->y];
            Celda *nueva_celda = (Celda *)juego->t->celdas[x_new][y_new];
            if (nueva_celda->alien == NULL) {
                nueva_celda->alien = alien; 
                nueva_celda->alien->x = x_new;
                nueva_celda->alien->y = y_new;
                celda_actual->alien = NULL; 
            }
            else {
                if (!resolver_colision(juego, alien)){
                    for (int k = juego->vivos - 1; k >= 0; k--) {
                        if (juego->aliens_vivos[k] == alien) {
                        free(alien);
                        celda_actual->alien = NULL;
                        juego->aliens_vivos[k] = juego->aliens_vivos[juego->vivos - 1];
                        juego->vivos--;
                        break;
                        }
                    }
                }
            }
        }
    }
}

void mover_especial(struct Juego *juego){
    /*
    * Nombre: mover_especial
    * Parámetros: Puntero a Juego.
    * Retorno: vacio
    * Descripción: Mueve los aliens tipo especial (tipo 0) en una dirección diagonal aleatoria.
    * Si colisiona con otro alien, intenta redirigirse.
    */
    for(int j = juego->t->H - 1; j >= 0; j--){
        for(int i = 0; i < juego->t->W; i++){
            Alien *alien = ((Celda*)juego->t->celdas[i][j])->alien;
            
            if (!alien || alien->tipo != 0) continue;
            
            int x_new = alien->x + ((rand() % 3) - 1);
            int y_new = alien->y + 1;

            
            if (x_new < 0 || x_new >= juego->t->W) {
                alien->dx = -alien->dx; 
                x_new = alien->x + alien->dx; 
            }

            Celda *celda_actual = (Celda *)juego->t->celdas[alien->x][alien->y];
            Celda *nueva_celda = (Celda *)juego->t->celdas[x_new][y_new];
            if (nueva_celda->alien == NULL) {
                nueva_celda->alien = alien; 
                nueva_celda->alien->x = x_new;
                nueva_celda->alien->y = y_new;
                celda_actual->alien = NULL; 
            }
            else {
                if (!resolver_colision(juego, alien)) {
                    for (int k = juego->vivos - 1; k >= 0; k--) {
                        if (juego->aliens_vivos[k] == alien) {
                        free(alien);
                        celda_actual->alien = NULL;
                        juego->aliens_vivos[k] = juego->aliens_vivos[juego->vivos - 1];
                        juego->vivos--;
                        break;
                        }
                    }
                }   
            }    
        }
    }
}

bool resolver_colision(struct Juego *juego, Alien *alien){
    /*
    * Nombre: resolver_colision
    * Parámetros: Puntero a Juego.
    * Retorno: booleano
    * Descripción: Intenta resolver las colisiones (explicado en readme.txt).
    * Si falla, retorna false. Si lo logra, retorna true.
    */
    if ((alien->x - alien->dx) >= 0  && (alien->x - alien->dx) < juego->t->W && ((Celda *)juego->t->celdas[alien->x - alien->dx][alien->y + 1])->alien == NULL){
        
        Celda *celda_actual = (Celda *)juego->t->celdas[alien->x][alien->y];
        Celda *nueva_celda = (Celda *)juego->t->celdas[alien->x - alien->dx][alien->y + 1];
        
        nueva_celda->alien = alien; // Mover alien
        nueva_celda->alien->x = alien->x - alien->dx;
        nueva_celda->alien->y = alien->y + 1;
        celda_actual->alien = NULL;
        return true;
    }
    else if ((alien->x - alien->dx) >= 0  && (alien->x - alien->dx) < juego->t->W && ((Celda *)juego->t->celdas[alien->x][alien->y + 1])->alien == NULL){
        Celda *celda_actual = (Celda *)juego->t->celdas[alien->x][alien->y];
        Celda *nueva_celda = (Celda *)juego->t->celdas[alien->x][alien->y + 1];
        
        nueva_celda->alien = alien; // Mover alien
        nueva_celda->alien->x = alien->x;
        nueva_celda->alien->y = alien->y + 1;
        celda_actual->alien = NULL;
        return true;
    }
    else{
        printf("Colision entre aliens (-1 alien) ");
        return false;
    }
}

bool game_over(struct Juego *juego) {
    /*
    * Nombre: game_over
    * Parámetros: Puntero a Juego.
    * Retorno: booleano
    * Descripción: Comprueba si algún alien ha llegado a la última fila del tablero.
    */
    for (int i = 0; i < juego->t->W; i++) {
        Celda *celda = (Celda *)juego->t->celdas[i][juego->t->H - 1];
        if (celda->alien != NULL) {
            return true;
        }
    }
    return false;
}

void resolver_danos(struct Juego *juego){
    /*
    * Nombre: arma_epecial
    * Parámetros: Puntero a Juego.
    * Retorno: vacio
    * Descripción: Recorre la lista de aliens vivos aplicando el daño pendiente en cada celda.
    */
   
    for(int i = juego->vivos - 1; i >= 0; i--){
        Alien *alien = juego->aliens_vivos[i];
        Celda *celda_alien = (Celda *)juego->t->celdas[alien->x][alien->y];
        system("clear");
        if (celda_alien->dano_pend != 0){
            printf("Impacto confirmado! ");
            alien->hp += celda_alien->dano_pend;
            celda_alien->dano_pend = 0;
            if (alien->hp <= 0){
                printf("Alien destruido. ");
                free(alien);
                juego->aliens_vivos[i] = juego->aliens_vivos[juego->vivos - 1];
                celda_alien->alien = NULL;
                juego->vivos--;
                ammo_recargar(juego);
            }
        }
    }
}