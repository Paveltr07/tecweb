<?php
// Definición del arreglo asociativo con los datos de los auto
$vehiculos = array(
    'ABC1234' => array(
        'Auto' => array(
            'marca' => 'Toyota',
            'modelo' => '2022',
            'tipo' => 'sedan'
        ),
        'Propietario' => array(
            'nombre' => 'Juan Pérez',
            'ciudad' => 'Ciudad de México',
            'direccion' => 'Avenida Reforma 123'
        )
    ),

    'XYZ5678' => array(
        'Auto' => array(
            'marca' => 'Ford',
            'modelo' => '2021',
            'tipo' => 'camioneta'
        ),
        'Propietario' => array(
            'nombre' => 'Ana López',
            'ciudad' => 'Guadalajara',
            'direccion' => 'Paseo de la Reforma 456'
        )
    ),

    'DEF5678' => array(
        'Auto' => array(
            'marca' => 'Honda',
            'modelo' => '2021',
            'tipo' => 'hatchback'
        ),
        'Propietario' => array(
            'nombre' => 'Ana López',
            'ciudad' => 'Guadalajara',
            'direccion' => 'Calle 5 de Febrero 456'
        )
    ),

    'GHI9012' => array(
        'Auto' => array(
            'marca' => 'Ford',
            'modelo' => '2020',
            'tipo' => 'camioneta'
        ),
        'Propietario' => array(
            'nombre' => 'Carlos Ruiz',
            'ciudad' => 'Monterrey',
            'direccion' => 'Av. Constitución 789'
        )
    ),
    'JKL3456' => array(
        'Auto' => array(
            'marca' => 'Chevrolet',
            'modelo' => '2023',
            'tipo' => 'sedan'
        ),
        'Propietario' => array(
            'nombre' => 'Marta Gómez',
            'ciudad' => 'Puebla',
            'direccion' => 'Calle 11 Sur 101'
        )
    ),
    'MNO7890' => array(
        'Auto' => array(
            'marca' => 'Mazda',
            'modelo' => '2019',
            'tipo' => 'hatchback'
        ),
        'Propietario' => array(
            'nombre' => 'Luis Martínez',
            'ciudad' => 'Querétaro',
            'direccion' => 'Avenida de la Luz 202'
        )
    ),
    'PQR2345' => array(
        'Auto' => array(
            'marca' => 'Nissan',
            'modelo' => '2022',
            'tipo' => 'camioneta'
        ),
        'Propietario' => array(
            'nombre' => 'Laura Hernández',
            'ciudad' => 'Cancún',
            'direccion' => 'Calle Isla 303'
        )
    ),
    'STU6789' => array(
        'Auto' => array(
            'marca' => 'Kia',
            'modelo' => '2021',
            'tipo' => 'sedan'
        ),
        'Propietario' => array(
            'nombre' => 'Miguel García',
            'ciudad' => 'Tijuana',
            'direccion' => 'Boulevard Agua Caliente 404'
        )
    ),
    'VWX0123' => array(
        'Auto' => array(
            'marca' => 'Hyundai',
            'modelo' => '2020',
            'tipo' => 'hatchback'
        ),
        'Propietario' => array(
            'nombre' => 'Sofía Fernández',
            'ciudad' => 'Saltillo',
            'direccion' => 'Avenida Hidalgo 505'
        )
    ),

    'YZA4567' => array(
        'Auto' => array(
            'marca' => 'Volkswagen',
            'modelo' => '2019',
            'tipo' => 'camioneta'
        ),
        'Propietario' => array(
            'nombre' => 'Andrés Jiménez',
            'ciudad' => 'San Luis Potosí',
            'direccion' => 'Calle Juárez 606'
        )
    ),
    'BCD8901' => array(
        'Auto' => array(
            'marca' => 'Subaru',
            'modelo' => '2022',
            'tipo' => 'sedan'
        ),
        'Propietario' => array(
            'nombre' => 'Verónica Álvarez',
            'ciudad' => 'Hermosillo',
            'direccion' => 'Calle 7 707'
        )
    ),
    'EFG2345' => array(
        'Auto' => array(
            'marca' => 'Jeep',
            'modelo' => '2021',
            'tipo' => 'camioneta'
        ),
        'Propietario' => array(
            'nombre' => 'Jorge Castillo',
            'ciudad' => 'La Paz',
            'direccion' => 'Avenida de las Flores 808'
        )
    ),
    'HIJ6789' => array(
        'Auto' => array(
            'marca' => 'Chrysler',
            'modelo' => '2020',
            'tipo' => 'sedan'
        ),
        'Propietario' => array(
            'nombre' => 'Patricia Moreno',
            'ciudad' => 'Chihuahua',
            'direccion' => 'Calle 5 Norte 909'
        )
    ),
    'KLM9012' => array(
        'Auto' => array(
            'marca' => 'Buick',
            'modelo' => '2019',
            'tipo' => 'hatchback'
        ),
        'Propietario' => array(
            'nombre' => 'Ricardo Soto',
            'ciudad' => 'Puebla',
            'direccion' => 'Calle del Valle 1010'
        )
    ),

    'NOP3456' => array(
        'Auto' => array(
            'marca' => 'Cadillac',
            'modelo' => '2022',
            'tipo' => 'camioneta'
        ),
        'Propietario' => array(
            'nombre' => 'Elena Gómez',
            'ciudad' => 'Ciudad Juárez',
            'direccion' => 'Avenida 16 de Septiembre 1111'
        )
    ),

);

// Función para buscar un vehículo por matrícula
function buscarPorMatricula($matricula, $vehiculos) {
    if (isset($vehiculos[$matricula])) {
        return $vehiculos[$matricula];
    } else {
        return "Matrícula no encontrada.";
    }
}

// Consultar la información del formulario
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (isset($_POST['matricula']) && !empty($_POST['matricula'])) {
        $matricula = $_POST['matricula'];
        $resultado = buscarPorMatricula($matricula, $vehiculos);
        echo "<h3>Resultado de la búsqueda:</h3>";
        echo "<pre>";
        print_r($resultado);
        echo "</pre>";
    } elseif (isset($_POST['mostrar_todos'])) {
        echo "<h3>Todos los autos registrados:</h3>";
        echo "<pre>";
        print_r($vehiculos);
        echo "</pre>";
    }
}
?>