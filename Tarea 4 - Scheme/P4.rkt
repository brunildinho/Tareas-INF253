#lang scheme


(define (indice-corte v1 v2 umbral)
#|
Parámetro 1: v1 (vector 1)
Parámetro 2: v2 (vector 2)
Parámetro 3: umbral (límite a alcanzar para detener la función)
Si la función se detiene prematuramente (acumulación de prod. puntos
excede el umbral), se retorna el índice en el cual se alcanzó el límite.
|#
    (if (or (= (vector-length v1) 0) (= (vector-length v2) 0))
        0
        (let recorrer ((x (vector->list v1)) (y (vector->list v2)) (u 0) (i 0))
        #|
        Parámetro 1: x (vector v1 convertido a lista (por comodidad))
        Parámetro 2: y (vector v2 convertido a lista)
        Parámetro 3: u (acumulador de productos puntos)
        Parámetro 4: i (contador de índices)
        Función recursiva que recorre ambas listas a la vez elemento por elemento.
        Explicación extensa disponible al final del archivo
        |#
            (if (or (null? x) (null? y))
                0
                (let ((prod-punto (delay (+ u (* (first x) (first y))))))
                    #|Usa delay para prometer que calculará el producto entre el first de x e y, 
                    y que lo acumulará con los anteriores sumando el resultado del producto con u|#
                (if (> u umbral)
                    i
                    (recorrer (rest x) (rest y) (force prod-punto) (+ i 1))
                )
                )
            )
        )
    )
)

#|
recorrer:
Por cada iteracion comprueba si se han acabado los elementos en alguna de las listas
(en ese caso, retorna 0).
Luego define el producto punto con delay, y comprueba si ya se ha alcanzado
el umbral (en la primera iteración esta condición siempre se cumple pues u se inicia como 0).
Si se ha alcanzado, retorna el índice actual.
Si no se ha alcanzado, llama de nuevo a la función, pasando el rest de x e y, y forzando
la acumulación del producto de los elementos actuales con los anteriores (evidentemente usando las listas
antes de hacer rest). Y añade un +1 al contador de índices :)
|#

(indice-corte '#(10 0 0 0) '#(3 9 9 9) 25)
(indice-corte '#(1 1 1 1 1 100 1 1) '#(1 1 1 1 1 1 1 1) 50)
(indice-corte '#(5 5 5) '#(1 1 1) 15)
(indice-corte '#(1 2 3) '#(1 1 1) 10)

#|Después de tantas mejoras, la nave exploradora se hace muy pesada,
por lo que termina hundiéndose en las profundidades
más allá de su límite técnico e implosionando D:
Fin, tan tan|#

