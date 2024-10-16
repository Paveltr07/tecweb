<?php
    include_once __DIR__.'/database.php';

    // SE OBTIENE LA INFORMACIÓN DEL PRODUCTO ENVIADA POR EL CLIENTE
    $producto = file_get_contents('php://input');
    
    if (!empty($producto)) {
        // SE TRANSFORMA EL STRING DEL JSON A OBJETO
        $jsonOBJ = json_decode($producto);

        // VALIDACIÓN: Verificar si ya existe un producto con el mismo nombre y con eliminado en 0
        $sql = "SELECT * FROM productos WHERE nombre = ? AND eliminado = 0";
        $stmt = $conexion->prepare($sql);
        $stmt->bind_param("s", $jsonOBJ->nombre);
        $stmt->execute();
        $result = $stmt->get_result();

        // SI EL PRODUCTO YA EXISTE
        if ($result->num_rows > 0) {
            echo json_encode(array(
                "status" => "error",
                "message" => "El producto ya existe. Por favor, introduce un producto diferente."
            ));
        } else {
            // EL PRODUCTO NO EXISTE, PROCEDER CON LA INSERCIÓN
            $sql = "INSERT INTO productos (id, nombre, marca, modelo, precio, detalles, unidades, imagen, eliminado) 
                    VALUES (null, ?, ?, ?, ?, ?, ?, ?, 0)";
            $stmt = $conexion->prepare($sql);
            $stmt->bind_param("sssdsis", 
                $jsonOBJ->nombre, 
                $jsonOBJ->marca, 
                $jsonOBJ->modelo, 
                $jsonOBJ->precio, 
                $jsonOBJ->detalles, 
                $jsonOBJ->unidades, 
                $jsonOBJ->imagen
            );

            if ($stmt->execute()) {
                echo json_encode(array(
                    "status" => "success",
                    "message" => "Producto insertado con éxito",
                    "product_id" => $conexion->insert_id
                ));
            } else {
                echo json_encode(array(
                    "status" => "error",
                    "message" => "El Producto no pudo ser insertado."
                ));
            }
        }
        
        // CERRAR CONEXIÓN Y RECURSOS
        $stmt->close();
        $conexion->close();
    } else {
        echo json_encode(array(
            "status" => "error",
            "message" => "No se recibieron datos del producto."
        ));
    }
?>