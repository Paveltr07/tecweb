<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
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

    if (isset($_GET['id'])) {
        $id = intval($_GET['id']);

        // Verificar si el formulario ha sido enviado
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            $nombre = $link->real_escape_string($_POST['nombre']);
            $marca = $link->real_escape_string($_POST['marca']);
            $modelo = $link->real_escape_string($_POST['modelo']);
            $precio = floatval($_POST['precio']);
            $unidades = intval($_POST['unidades']);
            $detalles = $link->real_escape_string($_POST['detalles']);
            $imagen = $link->real_escape_string($_POST['imagen']);

            // Actualizar el producto en la base de datos
            $update_query = "UPDATE productos SET 
                             nombre='$nombre', 
                             marca='$marca', 
                             modelo='$modelo', 
                             precio='$precio', 
                             unidades='$unidades', 
                             detalles='$detalles', 
                             imagen='$imagen' 
                             WHERE id=$id";

            if ($link->query($update_query)) {
                echo '<p class="alert alert-success">Producto actualizado correctamente.</p>';
            } else {
                echo '<p class="alert alert-danger">Error al actualizar el producto: ' . $link->error . '</p>';
            }
        }

        // Recuperar los datos actuales del producto
        $query = "SELECT * FROM productos WHERE id = $id";
        if ($result = $link->query($query)) {
            if ($result->num_rows > 0) {
                $producto = $result->fetch_assoc();
                ?>
                
                <form action="formulario_productos_v2.php?id=<?php echo $id; ?>" method="post" class="form-group">
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
                        <input type="number" step="0.01" class="form-control" id="precio" name="precio" value="<?php echo htmlspecialchars($producto['precio']); ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="unidades">Unidades</label>
                        <input type="number" class="form-control" id="unidades" name="unidades" value="<?php echo htmlspecialchars($producto['unidades']); ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="detalles">Detalles</label>
                        <textarea class="form-control" id="detalles" name="detalles" rows="3"><?php echo htmlspecialchars($producto['detalles']); ?></textarea>
                    </div>
                    <div class="form-group">
                        <label for="imagen">URL de la Imagen</label>
                        <input type="text" class="form-control" id="imagen" name="imagen" value="<?php echo htmlspecialchars($producto['imagen']); ?>">
                    </div>
                    <button type="submit" class="btn btn-primary">Actualizar Producto</button>
                </form>
                <?php
            } else {
                echo '<p class="alert alert-warning">No se encontró el producto.</p>';
            }
            $result->free();
        } else {
            echo '<p>Error al ejecutar la consulta: ' . $link->error . '</p>';
        }
    } else {
        echo '<p>ID de producto no proporcionado.</p>';
    }

    $link->close();
    ?>
</body>
</html>