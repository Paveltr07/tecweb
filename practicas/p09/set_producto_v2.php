<?php
// Conexión a la base de datos
@$link = new mysqli('localhost', 'root', 'pavel', 'marketzone');

// Comprobar la conexión
if ($link->connect_errno) {
    die('Falló la conexión: ' . $link->connect_error);
}

// Obtener datos del formulario
$nombre = trim($_POST['nombre']);
$marca = trim($_POST['marca']);
$modelo = trim($_POST['modelo']);
$precio = floatval($_POST['precio']);
$detalles = trim($_POST['detalles']);
$unidades = intval($_POST['unidades']);

// Procesar la imagen (opcional)
$imagen = '';
if (isset($_FILES['imagen']) && $_FILES['imagen']['error'] === UPLOAD_ERR_OK) {
    $imagen = 'img/' . basename($_FILES['imagen']['name']);
    move_uploaded_file($_FILES['imagen']['tmp_name'], $imagen);
} else {
    $imagen = 'img/default.png'; // Imagen por defecto si no se sube ninguna
}

// Validar que los campos requeridos no estén vacíos
if (empty($nombre) || empty($marca) || empty($modelo)) {
    die('<p>Error: Todos los campos obligatorios deben ser llenados.</p>');
}

// Comprobar si el producto ya existe
if ($stmt = $link->prepare("SELECT 1 FROM productos WHERE nombre = ? AND marca = ? AND modelo = ?")) {
    $stmt->bind_param("sss", $nombre, $marca, $modelo);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        echo '<p>Error: El producto con el mismo nombre, marca y modelo ya existe.</p>';
    } else {
        // Nueva query de inserción usando nombres de columnas
        if ($insert_stmt = $link->prepare("INSERT INTO productos (nombre, marca, modelo, precio, detalles, unidades, imagen) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            // Vinculando los parámetros con los tipos correctos
            $insert_stmt->bind_param("sssdsis", $nombre, $marca, $modelo, $precio, $detalles, $unidades, $imagen);

            // Ejecutar la nueva query
            if ($insert_stmt->execute()) {
                echo '<h1>Producto registrado exitosamente</h1>';
                echo '<p><strong>Nombre:</strong> ' . htmlspecialchars($nombre) . '</p>';
                echo '<p><strong>Marca:</strong> ' . htmlspecialchars($marca) . '</p>';
                echo '<p><strong>Modelo:</strong> ' . htmlspecialchars($modelo) . '</p>';
                echo '<p><strong>Precio:</strong> ' . htmlspecialchars($precio) . '</p>';
                echo '<p><strong>Detalles:</strong> ' . htmlspecialchars($detalles) . '</p>';
                echo '<p><strong>Unidades:</strong> ' . htmlspecialchars($unidades) . '</p>';
                echo '<p><strong>Imagen:</strong> <img src="' . htmlspecialchars($imagen) . '" alt="Imagen del producto" width="100"></p>';
            } else {
                echo '<p>Error: No se pudo registrar el producto. Por favor, intenta nuevamente.</p>';
            }
            // Cerrar la declaración de inserción
            $insert_stmt->close();
        } else {
            echo '<p>Error: No se pudo preparar la consulta de inserción.</p>';
        }
    }
    // Cerrar la declaración de selección
    $stmt->close();
}

// Cerrar la conexión a la base de datos
$link->close();
?>