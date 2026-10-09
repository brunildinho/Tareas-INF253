CC = gcc
CFLAGS = -Wall -Wextra -std=c99 -g -no-pie

juego : main.o tablero.o entidades.o armas.o spawn.o
	$(CC) -o juego main.o tablero.o entidades.o armas.o spawn.o

main.o : main.c main.h tablero.h entidades.h armas.h spawn.h
	$(CC) -c main.c
tablero.o : tablero.c tablero.h
	$(CC) -c tablero.c
entidades.o : entidades.c entidades.h
	$(CC) -c entidades.c
armas.o : armas.c armas.h
	$(CC) -c armas.c
spawn.o : spawn.c spawn.h
	$(CC) -c spawn.c
clean :
	rm juego main.o tablero.o entidades.o armas.o spawn.o