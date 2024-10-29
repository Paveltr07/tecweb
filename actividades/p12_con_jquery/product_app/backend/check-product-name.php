<?php
// check-product-name.php
include_once __DIR__.'/database.php';

// Conexión a la base de datos

// Obtener el nombre del producto
$nombre = $_POST['nombre'];

// Preparar la consulta
$stmt = $conn->prepare("SELECT COUNT(*) as count FROM productos WHERE nombre = ?");
$stmt->bind_param("s", $nombre);
$stmt->execute();
$result = $stmt->get_result();
$row = $result->fetch_assoc();

// Devolver el resultado como JSON
echo json_encode(['exists' => $row['count'] > 0]);

$stmt->close();
$conn->close();
?>