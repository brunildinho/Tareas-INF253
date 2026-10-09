#lang scheme

#|
Parametro 1: f (función de calibración)
Parametro 2: lista de valores a calibrar
construye recursivamente una nueva lista, aplica f al first de 'lista' y lo concatena con el resultado de llamar recursivamente a calibra-simple con el rest de 'lista'.
|#
(define (calibra-simple f lista)
  (if (null? lista)
      '() 
      (let ((x (first lista)) (y (rest lista)))
        (cons (f x) (calibra-simple f y))                     
      )
  )
 )

#|
Parametro 1: f (función de calibración)
Parametro 2: lista de valores a calibrar
Construye recursivamente una nueva lista, pero esta vez usando recursión de cola
A pesar del nombre de la función, esta no es capaz de calibrar colas (?
|#

(define (calibra-cola f lista)
  (if (null? lista)
    lista
    (let recursion ((x lista) (a '()))
    #|
    Parámetro 1: x (lista de valores a calibrar)
    Parámetro 2: a (lista acumuladora, inicialmente vacía)
    Se llama a si misma recursivamente, aplicandole un rest a x y añadiendo su first calibrado a a.
    |#
      (if (null? x)
        a
        (recursion (rest x) (append a (list (f (first x))) ) )
      )
    )
  )
)

#|
Parametro 1: función lambda (calibración) predefinida exactamente igual al del enunciado de la tarea.
Recibe un valor x y devuelve el valor calibrado según la fórmula.
|#
(define corr-sat (lambda (x) (let ((y (+ x 5))) (max 0 (min 100 y)))))

(calibra-simple corr-sat '(95 -3 50))
(calibra-cola corr-sat '(95 -3 50))

(calibra-simple (lambda (x) (if (< x 10) 0 x)) '(3 12 8 20))
(calibra-cola  (lambda (x) (if (< x 10) 0 x)) '(3 12 8 20))

(calibra-simple (lambda (x) (if (negative? x) 0 (sqrt x))) '(0 1 4 9 -5 16))
(calibra-cola (lambda (x) (if (negative? x) 0 (sqrt x))) '(0 1 4 9 -5 16))