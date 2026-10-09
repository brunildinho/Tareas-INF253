#ifndef SPAWN_H
#define SPAWN_H

struct Juego;
struct Alien;

typedef struct PoolAliens {
    int especial; int skater; int drone;
    int vivos_tope; 
} PoolAliens;

void spawn_turno(struct Juego *juego);
void spawn_inicio(struct Juego *juego);
void matar_alien(struct Juego *juego, struct Alien *alien);
void liberar_aliens(struct Juego *juego);

#endif