<?php
$link = mysqli_connect("localhost", "root", "pavel", "marketzone");

if ($link === false) {
    die("ERROR: No pudo conectarse con la DB. " . mysqli_connect_error());
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $id = isset($_POST['id']) ? intval($_POST['id']) : 0;
    $nombre = isset($_POST['nombre']) ? mysqli_real_escape_string($link, $_POST['nombre']) : '';
    $marca = isset($_POST['marca']) ? mysqli_real_escape_string($link, $_POST['marca']) : '';
    $modelo = isset($_POST['modelo']) ? mysqli_real_escape_string($link, $_POST['modelo']) : '';
    $precio = isset($_POST['precio']) ? floatval($_POST['precio']) : 0;
    $unidades = isset($_POST['unidades']) ? intval($_POST['unidades']) : 0;
    $detalles = isset($_POST['detalles']) ? mysqli_real_escape_string($link, $_POST['detalles']) : '';
    $imagen = isset($_POST['imagen']) ? mysqli_real_escape_string($link, $_POST['imagen']) : '';

    $sql = "UPDATE productos SET 
                nombre='$nombre', 
                marca='$marca', 
                modelo='$modelo', 
                precio=$precio, 
                unidades=$unidades, 
                detalles='$detalles', 
                imagen='$imagen' 
            WHERE id=$id";

    if (mysqli_query($link, $sql)) {
        echo "Registro actualizado.";
    } else {
        echo "ERROR: No se ejecutó $sql. " . mysqli_error($link);
    }
} else {
    echo "ERROR: No se recibieron datos para actualizar.";
}

mysqli_close($link);
?>