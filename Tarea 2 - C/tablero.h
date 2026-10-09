#ifndef TABLERO_H
#define TABLERO_H
#include "main.h"

typedef struct Tablero{
    int W, H;
    void ***celdas; 
    char *jugador;
} Tablero;

Tablero*tablero_crear(int ancho, int alto);
void tablero_imprimir(struct Juego *juego);
void tablero_cerrar(struct Tablero *tablero);
#endif 