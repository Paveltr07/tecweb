<?php
namespace Backend;
require_once 'DataBase.php';

class Products extends DataBase {
    protected $response;

    public function __construct($dbName = 'marketzone', $user = 'root', $password = 'pavel') {
        $this->response = null;
        parent::__construct($user, $password, $dbName);
    }

    protected function query($sql) {
        $result = mysqli_query($this->conexion, $sql);
        if ($result) {
            $this->response = $result;
        } else {
            $this->response = 'Error en la consulta: ' . mysqli_error($this->conexion);
        }
        return $this->response;
    }

    public function single($id) {
        $data = array();
        $sql = "SELECT * FROM productos WHERE id = {$id}";
        $result = $this->query($sql);

        if (is_object($result) && $result->num_rows > 0) {
            $row = $result->fetch_assoc();
            if (!is_null($row)) {
                foreach ($row as $key => $value) {
                    $data[$key] = ($value);
                }
            }
            $result->free();
        } else {
            die('Error en la consulta: ' . mysqli_error($this->conexion));
        }

        // Almacena los datos obtenidos en la propiedad response
        $this->response = $data;
    }

    public function singleByName($name) {
        $data = array();
        $sql = "SELECT * FROM productos WHERE name = {$name}";
        $result = $this->query($sql);

        if (is_object($result) && $result->num_rows > 0) {
            $row = $result->fetch_assoc();
            if (!is_null($row)) {
                foreach ($row as $key => $value) {
                    $data[$key] = utf8_encode($value);
                }
            }
            $result->free();
        } else {
            die('Error en la consulta: ' . mysqli_error($this->conexion));
        }

        // Almacena los datos obtenidos en la propiedad response
        $this->response = $data;
    }

    public function add($product) {
        $data = array(
            'status'  => 'error',
            'message' => 'Ya existe un producto con ese nombre'
        );

        $productData = json_decode(json_encode($product), false);

        if (isset($productData->nombre)) {
            $sql = "SELECT * FROM productos WHERE nombre = '{$productData->nombre}' AND eliminado = 0";
            $result = $this->query($sql);

            if (is_object($result) && $result->num_rows == 0) {
                $this->conexion->set_charset("utf8");

                $sql = "INSERT INTO productos VALUES (null, '{$productData->nombre}', '{$productData->marca}', '{$productData->modelo}', {$productData->precio}, '{$productData->detalles}', {$productData->unidades}, '{$productData->imagen}', 0)";
                
                if ($this->query($sql)) {
                    $data['status'] = "success";
                    $data['message'] = "Producto agregado";
                    
                } else {
                    $data['message'] = "ERROR: No se ejecutó $sql. " . mysqli_error($this->conexion);
                }
            }

            if (is_object($result)) {
                $result->free();
            }
        }

        // Almacena el resultado de la operación en response
        $this->response = $data;
    }

    public function delete($id) {
        // Inicializa el arreglo de respuesta
        $data = array(
            'status'  => 'error',
            'message' => 'La consulta falló'
        );
    
        // Realiza la consulta de eliminación lógica
        $sql = "UPDATE productos SET eliminado=1 WHERE id = {$id}";
    
        if ($this->query($sql)) {
            $data['status'] = "success";
            $data['message'] = "Producto eliminado";
        } else {
            $data['message'] = "ERROR: No se ejecutó $sql. " . mysqli_error($this->conexion);
        }
    
        // Almacena el resultado en response para luego poder usar getData()
        $this->response = $data;
    }
    
    public function edit($product) {
        // Inicializa el arreglo de respuesta
        $data = array(
            'status'  => 'error',
            'message' => 'La consulta falló'
        );
    
        // Convierte el objeto a JSON y luego a un arreglo asociativo
        $productData = json_decode(json_encode($product), false);
    
        // Verifica que se haya proporcionado el ID
        if (isset($productData->id)) {
            // Construye la consulta de actualización
            $sql = "UPDATE productos SET 
                        nombre = '{$productData->nombre}', 
                        marca = '{$productData->marca}', 
                        modelo = '{$productData->modelo}', 
                        precio = {$productData->precio}, 
                        detalles = '{$productData->detalles}', 
                        unidades = {$productData->unidades}, 
                        imagen = '{$productData->imagen}' 
                    WHERE id = {$productData->id} AND eliminado = 0";
    
            // Ejecuta la consulta y actualiza el estado según el resultado
            if ($this->query($sql)) {
                $data['status'] = "success";
                $data['message'] = "Producto actualizado";
            } else {
                $data['message'] = "ERROR: No se ejecutó $sql. " . mysqli_error($this->conexion);
            }
        } else {
            $data['message'] = "ID del producto no proporcionado";
        }
    
        // Almacena el resultado en response para luego poder usar getData()
        $this->response = $data;
    }

    public function list() {
        // Inicializa el arreglo de respuesta
        $data = array();
    
        // Realiza la consulta para obtener todos los productos no eliminados
        $sql = "SELECT * FROM productos WHERE eliminado = 0";
        $result = $this->query($sql);
    
        // Verifica si hubo resultados y procesa los datos
        if (is_object($result) && $result->num_rows > 0) {
            // Obtiene todos los resultados como un arreglo asociativo
            $rows = $result->fetch_all(MYSQLI_ASSOC);
    
            if (!is_null($rows)) {
                // Codifica a UTF-8 y mapea los datos al arreglo de respuesta
                foreach ($rows as $num => $row) {
                    foreach ($row as $key => $value) {
                        $data[$num][$key] = ($value);
                    }
                }
            }
            $result->free();
        } else {
            die('Error en la consulta: ' . mysqli_error($this->conexion));
        }
    
        // Almacena el resultado en response para luego poder usar getData()
        $this->response = $data;
    }

    public function search($search) {
        // Inicializa el arreglo de respuesta
        $data = array();
    
        // Construye la consulta para buscar en múltiples campos
        $sql = "SELECT * FROM productos WHERE (id = '{$search}' OR nombre LIKE '%{$search}%' OR marca LIKE '%{$search}%' OR detalles LIKE '%{$search}%') AND eliminado = 0";
        $result = $this->query($sql);
    
        // Verifica si hubo resultados y procesa los datos
        if (is_object($result) && $result->num_rows > 0) {
            // Obtiene todos los resultados como un arreglo asociativo
            $rows = $result->fetch_all(MYSQLI_ASSOC);
    
            if (!is_null($rows)) {
                // Codifica a UTF-8 y mapea los datos al arreglo de respuesta
                foreach ($rows as $num => $row) {
                    foreach ($row as $key => $value) {
                        $data[$num][$key] = ($value);
                    }
                }
            }
            $result->free();
        } else {
            die('Error en la consulta: ' . mysqli_error($this->conexion));
        }
    
        // Almacena el resultado en response para luego poder usar getData()
        $this->response = $data;
    }
    
    public function getData() {
        // Convierte el array de response a un string JSON y lo retorna
        return json_encode($this->response, JSON_PRETTY_PRINT);
    }

}
?>