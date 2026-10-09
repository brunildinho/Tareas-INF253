import re

digitos = r"[0-9]"
vocal_no_acentuada = r"[AEIOUaeiou]"
vocal_acentuada = r"[ÁÉÍÓÚáéíóú]"
vocal = re.compile(rf"({vocal_no_acentuada}|{vocal_acentuada})")
letras = r"[A-Za-zÑñ]"
caracteres_permitidos = r"[¿?¡!,.;'’ \-():\"]"
cadena = rf"({vocal}|{vocal_acentuada}|{letras})"
palabra = re.compile(rf"({cadena}|{caracteres_permitidos}|{digitos})+")

def clean_string(string):
    #***
    #Parámetro: string; Cadena de texto a limpiar
    #***
    #Tipo de retorno: string
    #***
    #La función quita los carácteres especiales y dígitos del string, dejando solo letras
    #***
    return re.sub(f"{caracteres_permitidos}|{digitos}", "", string).lower()

def lector(filename):
    #***
    #Parámetro 1: filename (string); Nombre del archivo que contiene la letra de la rutina
    #***
    #Tipo de retorno: Lista de listas.
    #*** 
    #La función lee el archivo y guarda las líricas en una lista de listas, donde cada estrofa se guarda como una lista de versos (strings).
    # lyrics = [[verso1, verso2, verso3, verso4], [verso1, verso2, verso3, verso4], ...]
    #También guarda las palabras clave en una lista (keywords)
    #***
    
    file = open(filename, 'r', encoding='utf-8')
    
    lyrics = []
    estrofa_temp = []

    keywords = file.readline().strip().split(", ")
    
    next(file)
    
    for linea in file:
        if not (linea == "" or linea == "\n" or linea == " "):
            estrofa_temp.append(linea.strip())
        else:
            lyrics.append(estrofa_temp)
            estrofa_temp = []

    if estrofa_temp: 
        lyrics.append(estrofa_temp)

    file.close()

    return lyrics, keywords

def validador(estrofa):
    #***
    #Parámetro: estrofa (lista); Lista de versos que se quiere validar
    #***
    #Tipo de retorno: tupla (bool, str)
    #***
    #La función valida si una estrofa es válida. Si no lo es, además de False retorna la razón.
    #***
    
    if len(estrofa) != 4:
        razon = rf"({len(estrofa)} versos)"
        return False, razon
    
    for verso in estrofa:
        if not palabra.fullmatch(verso):
            razon = "(Carácter inválido)"
            return False, razon
    
    return True, ""
    
def rima_consonante(word1, word2):
    #***
    #Parámetro 1: word1 (string)
    #Parámetro 2: word2 (string); Palabras a comparar
    #***
    #Tipo de retorno: tupla (int, bool)
    #***
    #La función juzga si las dos palabras hacen una rima consonante o si tienen la misma terminación (últimos 2 carácteres coinciden).
    #Retorna el puntaje en la 1ra posición de la tupla.
    #Si además alguna de las palabras está dentro de las palabras clave, retorna True en la segunda posición.
    #***
    
    word1 = clean_string(word1)
    word2 = clean_string(word2)
    
    if (word1 == "") or (word2 == ""):
        return 0, False
    
    if (word1 in keywords) or (word2 in keywords):
        bonus = True
    else:
        bonus = False
        
    i = 0
    while (i < 3):
        patron = rf""
        
        if i == 0:
            if len(word1) < 5 or len(word2) < 5:
                i += 1; continue
            patron += re.escape(word1[-5:]) + "$"
            if re.search(patron, word2):
                return 8, bonus
        
        if i == 1:
            if len(word1) < 3 or len(word2) < 3:
                i += 1; continue
            patron += re.escape(word1[-3:]) + "$"
            if re.search(patron, word2):
                return 5, bonus
        
        if i == 2:
            if len(word1) < 2 or len(word2) < 2:
                i += 1; continue
            patron += re.escape(word1[-2:]) + "$"
            if re.search(patron, word2):
                return 2, bonus
            
        i += 1
    return 0, False

def rima_asonante(word1, word2):
    #***
    #Parámetro 1: word1 (string)
    #Parámetro 2: word2 (string); Palabras a comparar
    #***
    #Tipo de retorno: tupla (int, bool)
    #***
    #La función juzga si las dos palabras hacen una rima asonante comparando solo las vocales.
    #Retorna el puntaje en la 1ra posición de la tupla.
    #Si además alguna de las palabras está dentro de las palabras clave, retorna True en la segunda posición.
    #***
    
    if (word1 in keywords) or (word2 in keywords):
        bonus = True
    else:
        bonus = False
    
    word1 = "".join(vocal.findall(clean_string(word1)))
    word2 = "".join(vocal.findall(clean_string(word2)))
    if (word1 == "") or (word2 == ""):
        return 0, False
    
    i = 0
    while (i < 3):
        patron = rf""
        if i == 0:
            if len(word1) < 3 or len(word2) < 3: 
            #Comprueba que haya suficientes vocales para validar una rima de 3 vocales
                i+=1;continue
            patron += re.escape(word1[-3:]) + "$"
            if re.search(patron, word2):
                return 8, bonus
        if i == 1:
            if len(word1) < 2 or len(word2) < 2:
                i+=1;continue
            patron += re.escape(word1[-2:]) + "$"
            if re.search(patron, word2):
                return 4, bonus
        if i == 2:
            patron += re.escape(word1[-1:]) + "$"
            if re.search(patron, word2):
                return 3, bonus
        i += 1
    return 0, False

def evaluar_rimas(lyrics, keywords):
    #***
    #Parámetro 1: lyrics (lista); Lista de listas que contiene las estrofas a evaluar
    #Parámetro 2: keywords (lista); Lista de palabras clave
    #***
    #Tipo de retorno: None
    #*** 
    #La función evalúa todas las estrofas y escribe los resultados en un archivo de salida.
    #***

    outfile = open("decision.txt", "w", encoding="utf-8")

    k = 1
    for estrofa in lyrics:
        bonus_estrofa = False
        puntaje = 0
        tipo_rimas = [[False, "consonante"],[False, "asonante"],[False, "misma terminación"],[False, "gemela"]] 
        bool_rimas = [False] * 4  #True/False -> [verso 1, verso 2, verso 3, verso 4]
        
        if not validador(estrofa)[0]:
                outfile.write(f"Estrofa: {k}: Inválida {validador(estrofa)[1]}\n\n")
        else:
            palabras = [] 
            for verso in estrofa:
                palabras.append(clean_string(verso.strip().split()[-1]))

            for i, palabra1 in enumerate(palabras):
                
                for j, palabra2 in enumerate(palabras):
                    
                    if i < j:
                        
                        if (clean_string(palabra1) == clean_string(palabra2)) and not (clean_string(palabra1) == "" or clean_string(palabra2) == ""):
                            puntaje += 1
                            tipo_rimas[3][0] = True
                            bool_rimas[i] = True
                            bool_rimas[j] = True
                            continue
                        
                        resultado_con, bonus_con = rima_consonante(palabra1, palabra2)
                        resultado_as, bonus_as = rima_asonante(palabra1, palabra2)
                        
                        resultado = max(resultado_con, resultado_as)
                        
                        
                        if resultado == 0:
                            continue
                        elif resultado_con == 2:
                            tipo_rimas[2][0] = True
                            bool_rimas[i] = True
                            bool_rimas[j] = True
                        elif resultado_con >= resultado_as:
                            tipo_rimas[0][0] = True
                            bool_rimas[i] = True
                            bool_rimas[j] = True
                        elif resultado_as > resultado_con:
                            tipo_rimas[1][0] = True
                            bool_rimas[i] = True
                            bool_rimas[j] = True
                        
                        #bonus = False
                        resultado = max(resultado_con, resultado_as)
                        if (bonus_con or bonus_as):
                            bonus_estrofa = True
                        
                        #Aquí hay que poner la funcion de palabras asonantes y compararla con la consonante
                        #USAR EL re.sub para quitar las consonantes al evaluar la rima asonante?
                        
                        
                        puntaje += resultado
                        #if bonus:
                            #bonus_estrofa = True
                            
            if bonus_estrofa:
                puntaje += 2

            if not all(bool_rimas):
                puntaje -= 2

            outfile.write(f"Estrofa: {k}: {round(puntaje/5, 1)}/10")
            if bonus_estrofa:
                outfile.write(" (BONUS)\n")
            else:
                outfile.write("\n")

            if all(bool_rimas):
                tipos_rimas_temp = []
                for i in range(len(tipo_rimas)):
                    if tipo_rimas[i][0]:
                        tipos_rimas_temp.append(tipo_rimas[i][1])
                rimas_estrofa = ", ".join(tipos_rimas_temp)
                outfile.write(f"Rimas: {rimas_estrofa}\n\n")
            else:
                outfile.write("Rimas: ninguna\n\n")
            
        k += 1
        
    outfile.close()

lyrics, keywords = lector("estrofas.txt")
evaluar_rimas(lyrics, keywords)

#Bruno Andrade
#v1.2