<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Práctica 7</title>
</head>
<body>
    <h2>Ejercicio 1</h2>
    <p>Escribir programa para comprobar si un número es un múltiplo de 5 y 7</p>

    <h2>Ejemplo de GET</h2>
    <form action="index.php" method="GET">
        Numero: <input type="text" name="numero"><br>
        <input type="submit" value="Comprobar">
    </form>
    <br>

    <?php
    // Incluir el archivo donde está la función
    include 'funciones.php';
    // Verificar si el número está presente en la y usar la función
    
    if(isset($_GET['numero'])){  //Veriifica que el parametro este 
        $num = $_GET['numero']; //Lo ingresado se pasa num 

        if(is_numeric($num)){  //ve si es numero o nel
            echo Multiplo(($num)); //Imprime funcion 
        }

        else{
            echo "<h3>Ingrese un número válido.</h3>"; //Inprime que no es
        }
    }
    ?>

    <h2>Ejercicio 2</h2>
    <p>Crea un programa para la generación repetitiva de 3 números aleatorios hasta obtener una
    secuencia compuesta por:</p>

    <?php
    include_once 'funciones.php';

    $filas = 0;
    $totalNumerosGenerados = 0;
    $matriz = [];
    $mostrarResultados = false;

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        do {
            $filas++;
            $numeros = [];
            
            for ($i = 0; $i < 3; $i++) {
                $numero = generarNumeroAleatorio();
                $numeros[] = $numero;
                $totalNumerosGenerados++;
            }
            
            $matriz[] = $numeros;
            
            $patronCumplido = cumplePatron($numeros);

        } while (!$patronCumplido);

        $mostrarResultados = true;
    }

    if ($mostrarResultados) {
        echo "Número de iteraciones: $filas<br>";
        echo "Cantidad de números generados: $totalNumerosGenerados<br>";

        echo "<h2>Matriz generada:</h2>";
        echo "<table border='1'>";
        echo "<tr><th>Número 1</th><th>Número 2</th><th>Número 3</th></tr>";
        foreach ($matriz as $fila) {
            echo "<tr>";
            foreach ($fila as $numero) {
                echo "<td>$numero</td>";
            }
            echo "</tr>";
        }
        echo "</table>";
    } else {
        echo '<form method="POST" action="index.php">
                <button type="submit">Generar Números</button>
            </form>';
    }

    

?>
    <h2>Ejercicio 3</h2>
    <p>Utiliza un ciclo while para encontrar el primer número entero obtenido aleatoriamente,
    pero que además sea múltiplo de un número dado.</p>
    
    <?php
    // Incluyendo funciones si no está incluido
    include_once 'funciones.php';

    // Verificar si el número dado está presente en la URL
    if (isset($_GET['numero'])) {
        $numeroDado = intval($_GET['numero']);
        
        // Usar ciclo while
        $resultadoWhile = null;
        $iteracionesWhile = 0;

        while ($resultadoWhile === null) {
            $iteracionesWhile++;
            $numeroAleatorio = generarNumeroAleatorio(); // Obtener número aleatorio del mismo método que Ejercicio 2
            if ($numeroAleatorio % $numeroDado == 0) {
                $resultadoWhile = $numeroAleatorio;
            }
        }

        // Usar ciclo do-while
        $resultadoDoWhile = null;
        $iteracionesDoWhile = 0;

        do {
            $iteracionesDoWhile++;
            $numeroAleatorio = generarNumeroAleatorio(); // Obtener número aleatorio del mismo método que Ejercicio 2
            if ($numeroAleatorio % $numeroDado == 0) {
                $resultadoDoWhile = $numeroAleatorio;
            }
        } while ($resultadoDoWhile === null);

        echo "<h2>Resultados del Ejercicio 3:</h2>";
        echo "Número dado: $numeroDado<br>";
        echo "Resultado con while: $resultadoWhile<br>";
        echo "Iteraciones con while: $iteracionesWhile<br>";
        echo "Resultado con do-while: $resultadoDoWhile<br>";
        echo "Iteraciones con do-while: $iteracionesDoWhile<br>";
    } else {
        echo "<p>Ingrese un número en la URL para usarlo aquí.</p>";
    }
    ?>

    <h2>Ejercicio 4</h2>
        <p>Crear un arreglo cuyos índices van de 97 a 122 y cuyos valores son las letras de la ‘a’
    a la ‘z’. Usa la función chr(n) que devuelve el caracter cuyo código ASCII es n para poner
    el valor en cada índice. Es decir:</p>

    <?php
    // Crear el arreglo con índices del 97 al 122 y valores de 'a' a 'z'
    $arreglo = [];

    for ($i = 97; $i <= 122; $i++) {
        $arreglo[$i] = chr($i);
    }

    // Mostrar el arreglo en una tabla XHTML
    echo "<table border='1'>";
    echo "<tr><th>Índice</th><th>Letra</th></tr>";
    
    foreach ($arreglo as $key => $value) {
        echo "<tr><td>$key</td><td>$value</td></tr>";
    }

    echo "</table>";
    ?>







</body>
</html>