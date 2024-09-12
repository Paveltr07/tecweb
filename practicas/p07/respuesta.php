<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Respuesta</title>
</head>
<body>
    <h2>Resultado de la Validación</h2>
    <?php
    // Verificar si el formulario ha sido enviado
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        // Obtener los valores del formulario
        $edad = $_POST['edad'];
        $sexo = $_POST['sexo'];
        
        // Validar la edad y el sexo
        if ($sexo === 'femenino' && $edad >= 18 && $edad <= 35) {
            echo "<p>Bienvenida, usted está en el rango de edad permitido.</p>";
        } else {
            echo "<p>Error: Usted no cumple con el rango de edad o sexo requerido.</p>";
        }
    } else {
        echo "<p>No se ha enviado ningún dato.</p>";
    }
    ?>
</body>
</html>