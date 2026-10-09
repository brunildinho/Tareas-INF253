#lang scheme

(define (traducir-codigos tabla codigos)
        #|
        Parámetro 1: tabla (alist de traducción código.recurso)
        Parámetro 2: codigos (lista de códigos a descifrar)
        Recorre la lista de códigos, los traduce todos (si no hay traducción los ignora) y retorna 
        una lista con valores traducidos.
        Explicación extensa disponible al final del archivo.
        |#

        (if (or (null? codigos) (null? tabla))
                '()
                (map (lambda (y) (cdr y)) (filter pair? (map (lambda (x) (assoc x tabla)) codigos)))
        )
)

(define tabla '((A . titanio) (B . cobre) (C . litio)))

(traducir-codigos tabla '(B X A C X))
(traducir-codigos '((Z . panel-solar) (Y . escaner)) '(Y Y X Z))
(traducir-codigos '() '(A B C))

#|
Primero: La función usa map para aplicar una función lambda sobre toda la lista de códigos.
Ésta función usa assoc para buscar si algún par código.recurso coincide con el código actual.
Si es el caso, assoc retorna dicho par, y el map se encarga de reemplazar el código con el par
código.recurso. Como es de esperarse, assoc retorna #f si no encuentra coincidencias.
Para resolver esto, a esta nueva lista de pares y booleanos se le aplica un filter pair?
que elimina todos los elementos que no sean pares (o sea, que no se les encontró una traducción).
Finalmente, a esta lista resultante se le aplica map con otra función lambda, lo que reemplaza cada par
código.recurso con el elemento recurso de dicho par solamente.
|#




#|VERSION SIN USAR MAP xd|#
; (define buscar (lambda (char tabla)
;     (assoc char tabla)))
;DEVUELVE EL PAR, NO EL VALOR

; (define (traducir-codigos tabla codigos)
;         (if (or (null? codigos) (null? tabla))
;         '()
;         (let recorrer-codigos ((x tabla) (y codigos) (decifrados '()))
;                 (if (null? y)
;                     (let ([resultado (filter (lambda (e) (not (boolean? e))) decifrados)]) resultado) ;revisar
;                     (recorrer-codigos x (cdr y) (append decifrados (list (if (pair? (buscar (first y) x)) (cdr (buscar (first y) x)) #f ))))))))