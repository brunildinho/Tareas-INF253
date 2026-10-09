#ifndef ARMAS_H
#define ARMAS_H
#include <stdbool.h>

struct Juego;
typedef bool(*FuncArmas)(struct Juego* j);
typedef struct Armas{
    int ammo_perforador;
    int ammo_especial;
    FuncArmas fn[3]; 
} Armas;
Armas* armas_crear();
bool disparar_armas(struct Juego *juego, int arma_id);
bool arma_normal (struct Juego *juego); 
bool arma_perforador (struct Juego *juego); 
bool arma_especial (struct Juego *juego); 
void ammo_recargar(struct Juego *juego);

#endif