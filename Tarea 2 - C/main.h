#ifndef MAIN_H
#define MAIN_H

struct PoolAliens;
struct Tablero;
struct Armas;
typedef struct Alien Alien;

typedef struct Juego {
    struct Tablero *t;
    struct Armas *armas;
    struct PoolAliens *pool;
    int dificultad;
    int turno,vivos, jugador_x;
    Alien **aliens_vivos;
} Juego;
#endif