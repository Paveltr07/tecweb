<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Productos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
</head>
<body>
    <h3>PRODUCTOS VIGENTES</h3>
    <br/>

    <?php
    $link = new mysqli('localhost', 'root', 'pavel', 'marketzone');
    if ($link->connect_error) {
        die("Connection failed: " . $link->connect_error);
    }

    $link->set_charset("utf8mb4");
    $tope = isset($_GET['tope']) ? intval($_GET['tope']) : 0;

    $query = "SELECT * FROM productos WHERE eliminado = 0";
    if ($tope > 0) {
        $query .= " AND unidades <= $tope";
    }

    if ($result = $link->query($query)) {
        if ($result->num_rows > 0) {
            echo '<table class="table">';
            echo '<thead class="thead-dark">
                    <tr>
                    <th scope="col">#</th>
                    <th scope="col">Nombre</th>
                    <th scope="col">Marca</th>
                    <th scope="col">Modelo</th>
                    <th scope="col">Precio</th>
                    <th scope="col">Unidades</th>
                    <th scope="col">Detalles</th>
                    <th scope="col">Imagen</th>
                    <th scope="col">Acciones</th>
                    </tr>
                  </thead>';
            echo '<tbody>';
            while ($row = $result->fetch_array(MYSQLI_ASSOC)) {
                echo '<tr>';
                echo '<th scope="row">' . htmlspecialchars($row['id']) . '</th>';
                echo '<td>' . htmlspecialchars($row['nombre']) . '</td>';
                echo '<td>' . htmlspecialchars($row['marca']) . '</td>';
                echo '<td>' . htmlspecialchars($row['modelo']) . '</td>';
                echo '<td>' . htmlspecialchars($row['precio']) . '</td>';
                echo '<td>' . htmlspecialchars($row['unidades']) . '</td>';
                echo '<td>' . htmlspecialchars(utf8_encode($row['detalles'])) . '</td>';
                echo '<td><img src="' . htmlspecialchars($row['imagen']) . '" alt="Imagen del producto" style="max-width: 100px;"></td>';
                echo '<td><a href="formulario_productos_v3.php?id=' . $row['id'] . '" class="btn btn-primary">Editar</a></td>';
                echo '</tr>';
            }
            echo '</tbody>';
            echo '</table>';
        } else {
            echo '<p>No se encontraron productos vigentes.</p>';
        }
        $result->free();
    } else {
        echo '<p>Error al ejecutar la consulta: ' . $link->error . '</p>';
    }

    $link->close();
    ?>
</body>
</html>