<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">/Users/psvel/Desktop/tecweb/practicas/p10/get_productos_xhtml_v2.php
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Actualizar Producto</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
</head>
<body>
    <h3>Actualizar Producto</h3>
    <br/>

    <?php
    // Conexión a la base de datos
    $link = new mysqli('localhost', 'root', 'pavel', 'marketzone');

    if ($link->connect_error) {
        die("Connection failed: " . $link->connect_error);
    }

    // Obtener el ID del producto
    if (isset($_GET['id'])) {
        $id = intval($_GET['id']);

        // Consulta para obtener los datos del producto
        $query = "SELECT * FROM productos WHERE id = $id AND eliminado = 0";
        if ($result = $link->query($query)) {
            if ($result->num_rows > 0) {
                // Obtener los datos del producto
                $producto = $result->fetch_assoc();
            } else {
                echo '<p>Producto no encontrado.</p>';
                exit;
            }
        } else {
            echo '<p>Error al ejecutar la consulta: ' . $link->error . '</p>';
            exit;
        }
    } else {
        echo '<p>ID de producto no especificado.</p>';
        exit;
    }

    $link->close();
    ?>

    <form action="update_producto.php" method="post" class="form-group">
        <input type="hidden" name="id" value="<?php echo htmlspecialchars($producto['id']); ?>">
        
        <div class="form-group">
            <label for="nombre">Nombre del Producto</label>
            <input type="text" class="form-control" id="nombre" name="nombre" value="<?php echo htmlspecialchars($producto['nombre']); ?>" required>
        </div>

        <div class="form-group">
            <label for="marca">Marca</label>
            <input type="text" class="form-control" id="marca" name="marca" value="<?php echo htmlspecialchars($producto['marca']); ?>" required>
        </div>

        <div class="form-group">
            <label for="modelo">Modelo</label>
            <input type="text" class="form-control" id="modelo" name="modelo" value="<?php echo htmlspecialchars($producto['modelo']); ?>" required>
        </div>

        <div class="form-group">
            <label for="precio">Precio</label>
            <input type="number" class="form-control" id="precio" name="precio" value="<?php echo htmlspecialchars($producto['precio']/Users/psvel/Desktop/tecweb/practicas/p10/get_/Users/psvel/Desktop/tecweb/practicas/p10/update_producto.phpproductos_xhtml_v2.php); ?>" required>
        </div>

        <div class="form-group">
            <label for="unidades">Unidades</label>
            <input type="number" class="form-control" id="unidades" name="unidades" value="<?php echo htmlspecialchars($producto['unidades']); ?>" required>
        </div>

        <div class="form-group">
            <label for="detalles">Detalles</label>
            <textarea class="form-control" id="detalles" name="detalles" required><?php echo htmlspecialchars($producto['detalles']); ?></textarea>
        </div>

        <div class="form-group">
            <label for="imagen">Imagen</label>
            <textarea class="form-control" id="imagen" name="imagen" required><?php echo htmlspecialchars($producto['imagen']); ?></textarea>
        </div>

        <button type="submit" class="btn btn-primary">Actualizar Producto</button>
    </form>
</body>
</html>
