#lang scheme

(define (radar-detecta recurso escaneo)
    #|
    Parámetro 1 : recurso (simbolo del recurso a buscar en las lecturas de escaneo)
    Parámetro 2 : escaneo (símbolo o lista/lista anidada de símbolos con varios recursos detectados)
    Busca recursivamente cuantas veces aparece recurso en escaneo recurso retorna un entero con el conteo.
    |#
    (if (null? escaneo)
        0
        (if (list? escaneo) 
            (let buscar ((x escaneo) (acc 0))
                #|
                Parámetro 1: x (símbolos restantes por evaluar)
                Parámetro 2: acc (acumulador/contador de elementos encontrados encontrados)
                Función recursiva que recorre la lista x elemento por elemento.
                Explicación extensa disponible al final del archivo
                |#
                (if (null? x)
                    acc
                    (if (list? (first x))
                        (buscar (rest x) (buscar (first x) acc))
                        (if (eq? (first x) recurso)
                            (buscar (rest x) (+ acc 1))
                            (buscar (rest x) acc)
                        )
                    )
                )
            )
            (if (eq? escaneo recurso)
                ;caso de que escaneo sea solo un símbolo recurso, no una lista.
                1
                0
            )
        )
    )
)

#|
buscar:
Función recursiva que recorre la lista x elemento por elemento.

Si el elemento es una lista, llama de forma anidada a buscar, pasando
el first de x (la lista) y el acumulador actual acc. El resultado de esta llamada anidada
se pasa como nuevo acumulador a la llamada principal, que hace el rest x y continúa buscando normalmente.

Si el elemento no es una lista, comprueba si es igual al recurso a buscar.
Si es igual, suma 1 al acumulador y continúa buscando con el rest de x.
Si no es igual, continúa con el rest de x sin sumar +1 al acumulador.
|#

(radar-detecta 'titanio 'titanio)
(radar-detecta 'titanio 'cobre)
(radar-detecta 'titanio '())
(radar-detecta 'titanio '(titanio cobre titanio))
(radar-detecta 'diamante '(sector-a (cueva-1 diamante)(cueva-2 (nodo diamante diamante)) coral))
(radar-detecta 'cobre '(scan ((cobre) ((cobre titanio) arena)) (baliza cobre)))