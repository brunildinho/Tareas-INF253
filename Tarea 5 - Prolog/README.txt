Bruno Andrade

Objetivo de la tarea:
Recrear la malla curricular de la carrera Ingeniería Civil Informática de la UTFSM (código 7313) como un grafo dirigido en el lenguaje de programación Prolog y crear predicados que permitan realizar las consultas necesarias.

consideraciones: 
Se usaron operadores externos como findall, etc. (Autorizado por ayudante coordinador via foro)
Esto podría llevar a comportamietos ligeramente distintos a los de la pauta (cambio de orden en la salida).

El predicado siguiente_semestre fue implementado parcialmente: Solo entrega una lista de tuplas con los créditos,
código, orden y semestres de cada ramo disponible (habilitado) para dar y perteneciente al semestre actual.
La intención era iterar sobre la lista de ramos habilitados, usando el predicado catch_semestre
para obtener los ramos habilitados de cierto semestre. Luego, recorrer estos ramos en orden descendiente de créditos SCT
e ir añadiendolos a la lista de salida si es que la suma de los créditos del ramo a añadir y los créditos acumulados
no supera los créditos máximos solicitados. Y repetir este proceso por cada semestre hasta que se alcance
el máximo de créditos solicitado.

El predicado titulacion_optima no fue implementado.