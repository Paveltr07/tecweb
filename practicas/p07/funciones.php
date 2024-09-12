<?php
// Función para comprobar si un número es múltiplo de 5 y 7
function Multiplo($num) {

    if ($num % 5 == 0 && $num % 7 == 0) {
        return '<h3> El número ' . $num . ' SÍ es múltiplo de 5 y 7.</h3>';
    } 
    
    else {
        return '<h3> El número ' . $num . ' NO es múltiplo de 5 y 7.</h3>';
    }
}

//Numero aleatorio
function generarNumeroAleatorio() {
    return rand(100, 999);
}

// Función para verificar si un número es par
function esPar($numero) {
    return $numero % 2 == 0;
}

// Función para verificar si una secuencia cumple con el patrón impar, par, impar
function cumplePatron($numeros) {
    return esPar($numeros[1]) && !esPar($numeros[0]) && !esPar($numeros[2]);
}

?>