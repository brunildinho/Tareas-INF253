#ifndef ENTIDADES_H
#define ENTIDADES_H
#include <stdbool.h>
typedef struct Alien{
    int tipo;
    int hp; 
    int x, y; 
    int dx; 
} Alien;

typedef struct{
    Alien *alien;
    int dano_pend;
} Celda;

struct Juego; 

void mover_aliens(struct Juego *juego);
void mover_drones(struct Juego *juego);
void mover_skater(struct Juego *juego);
void mover_especial(struct Juego *juego);
bool resolver_colision(struct Juego *juego, Alien *alien);
void resolver_danos(struct Juego *juego);
bool game_over(struct Juego *juego);

#endif