<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Ejercicios PHP</title>
</head>
<body>

<h1>Practica 5 - Manejo de Variables en PHP</h1>
<h2>1. Determina cuál de las siguientes variables son válidas y explica por qué:</h2>

<?php
//Ejercicio 1 
$_myvar = "Válida";
$_7var = "Válida";
$myvar = "Válida";
//myvar = "No válida"; 
$var7 = "Válida";
$_element1 = "Válida";
//$house*5 = "No válida"; 

// Imprimir variables válidas
echo "<p>\$_myvar: $_myvar</p>";
echo "<p>\$_7var: $_7var</p>";
echo "<p>\$myvar: $myvar</p>";
echo 'myvar es INVALIDA no comienza con $';
echo "<p>\$var7: $var7</p>";
echo "<p>\$_element1: $_element1</p>";
echo '$house*5 es INVALIDA por el * (caracter especial)';

?>
<h2>2. Proporcionar los valores de $a, $b, $c como sigue:</h2>

<h2>  a. Ahora muestra el contenido de cada variable</h2>

<?php
$a = "ManejadorSQL";
$b = 'MySQL';
$c = &$a;

echo "<p>\$a: $a</p>";
echo "<p>\$b: $b</p>";
echo "<p>\$c: $c</p>";
?>

<h2>  b. Agrega al código actual las siguientes asignaciones:</h2>
<h2>$a = “PHP server”;</h2>
<h2>$b = &$a;</h2>

<h2>  c. Vuelve a mostrar el contenido de cada uno</h2>
<h2>  d. Describe en y muestra en la página obtenida qué ocurrió en el segundo bloque de
asignaciones</h2>

<?php
$a = "PHP server";
$b = &$a;

echo "<p>\$a: $a</p>";
echo "<p>\$c: $c</p>";

?>


<h2>3. Muestra el contenido de cada variable inmediatamente después de cada asignación,
verificar la evolución del tipo de estas variables (imprime todos los componentes de los
arreglo):</h2>

<?php

$a = "PHP5";
echo "\$a = $a (" . gettype($a) . ")<br>";

$z[] = &$a;
echo "\$z[0] = $z[0] (" . gettype($z) . ")<br>";

$b = "5a version de PHP";
echo "\$b = $b (" . gettype($b) . ")<br>";

$c = $b * 10;
echo "\$c = $c (" . gettype($c) . ")<br>";

//$a = $a . $b;
$a .= $b;
echo "\$a = $a (" . gettype($a) . ")<br>";

$b *= $c;
echo "\$b = $b (" . gettype($b) . ")<br>";

$z[0] = "MySQL";
echo "\$z[0] = $z[0] (" . gettype($z) . ")<br>";

echo "<br>Contenido final:<br>";
echo "\$a = $a (" . gettype($a) . ")<br>";
echo "\$b = $b (" . gettype($b) . ")<br>";
echo "\$c = $c (" . gettype($c) . ")<br>";
echo "\$z[0] = $z[0] (" . gettype($z) . ")<br>";

?>
<h2>4. Lee y muestra los valores de las variables del ejercicio anterior, pero ahora con la ayuda de
la matriz $GLOBALS o del modificador global de PHP.</h2>

<?php
$a = "PHP5";
echo "\$a = " . $GLOBALS['a'] . " (" . gettype($GLOBALS['a']) . ")<br>";

$z[] = &$a;
echo "\$z[0] = " . $GLOBALS['z'][0] . " (" . gettype($GLOBALS['z']) . ")<br>";

$b = "5a version de PHP";
echo "\$b = " . $GLOBALS['b'] . " (" . gettype($GLOBALS['b']) . ")<br>";

$c = $b * 10; 
echo "\$c = " . $GLOBALS['c'] . " (" . gettype($GLOBALS['c']) . ")<br>";

$a .= $b;  
echo "\$a = " . $GLOBALS['a'] . " (" . gettype($GLOBALS['a']) . ")<br>";

$b *= $c;
echo "\$b = " . $GLOBALS['b'] . " (" . gettype($GLOBALS['b']) . ")<br>";

$z[0] = "MySQL";  
echo "\$z[0] = " . $GLOBALS['z'][0] . " (" . gettype($GLOBALS['z']) . ")<br>";

echo "<br>Contenido final:<br>";
echo "\$a = " . $GLOBALS['a'] . " (" . gettype($GLOBALS['a']) . ")<br>";
echo "\$b = " . $GLOBALS['b'] . " (" . gettype($GLOBALS['b']) . ")<br>";
echo "\$c = " . $GLOBALS['c'] . " (" . gettype($GLOBALS['c']) . ")<br>";
echo "\$z[0] = " . $GLOBALS['z'][0] . " (" . gettype($GLOBALS['z']) . ")<br>";

?>

<h2>5. Dar el valor de las variables $a, $b, $c al final del siguiente script:</h2>
<?php

$a = "7 personas";
echo "\$a = $a<br>";

$b = (integer) $a;
echo "\$b = $b<br>";

$a = "9E3";
echo "\$a = $a<br>";

$c = (double) $a;
echo "\$c = $c<br>";

?>
<h2>6. Dar y comprobar el valor booleano de las variables $a, $b, $c, $d, $e y $f y muéstralas
usando la función var_dump(<datos>).</h2>

<h2>Después investiga una función de PHP que permita transformar el valor booleano de $c y $e
en uno que se pueda mostrar con un echo:</h2>
<?php
// valores
$a = "0";
$b = "TRUE";
$c = FALSE;
$d = ($a OR $b);
$e = ($a AND $c);
$f = ($a XOR $b);

// Mostrar los valores y tipos con var_dump()
echo "Valor de \$a: ";
var_dump($a);  // string(1) "0"
echo "<br>";

echo "Valor de \$b: ";
var_dump($b);  // string(4) "TRUE"
echo "<br>";

echo "Valor de \$c: ";
var_dump($c);  // bool(false)
echo "<br>";

echo "Valor de \$d: ";
var_dump($d);  // bool(true)
echo "<br>";

echo "Valor de \$e: ";
var_dump($e);  // bool(false)
echo "<br>";

echo "Valor de \$f: ";
var_dump($f);  // bool(true)
echo "<br>";

// Función para convertir booleano a cadena
function boolToString($bool) {
    return $bool ? 'true' : 'false';
}

// Mostrar valores booleanos transformados a cadenas

echo "Valor de \$c como cadena: " . boolToString($c) . "<br>";
echo "Valor de \$e como cadena: " . boolToString($e) . "<br>";

?>


<h2>7. Usando la variable predefinida $_SERVER, determina lo siguiente:</h2>
<h2>  a. La versión de Apache y PHP</h2>
<h2>  b. El nombre del sistema operativo (servidor),</h2>
<h2>  c. El idioma del navegador (cliente).</h2>

<?php
// a. Mostrar la versión de Apache y PHP
echo "Versión de Apache: " . (isset($_SERVER['SERVER_SOFTWARE']) ? $_SERVER['SERVER_SOFTWARE'] : 'No disponible') . "<br>";
echo "Versión de PHP: " . phpversion() . "<br>";

// b. Mostrar el nombre del sistema operativo del servidor
echo "Nombre del sistema operativo (servidor): ";
if (isset($_SERVER['SERVER_SOFTWARE'])) {

    echo $_SERVER['SERVER_SOFTWARE'];
} else {
    echo "No disponible";
}
echo "<br>";
echo "Idioma del navegador (cliente): " . (isset($_SERVER['HTTP_ACCEPT_LANGUAGE']) ? $_SERVER['HTTP_ACCEPT_LANGUAGE'] : 'No disponible') . "<br>";
?>

</body>
</html>