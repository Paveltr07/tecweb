<?php
// Obtención de datos del formulario 
$name = isset($_POST['name']) ? htmlspecialchars($_POST['name']) : 'No proporcionado';
$email = isset($_POST['email']) ? htmlspecialchars($_POST['email']) : 'No proporcionado';
$phone = isset($_POST['phone']) ? htmlspecialchars($_POST['phone']) : 'No proporcionado'; 
$story = isset($_POST['story']) ? htmlspecialchars($_POST['story']) : 'No proporcionado';
$color = isset($_POST['color']) ? htmlspecialchars($_POST['color']) : 'No especificado';
$size = isset($_POST['size']) ? htmlspecialchars($_POST['size']) : 'No especificado';

// Mostrar la respuesta
echo '<html>';  
echo '<head>';
echo '<meta charset="utf-8">';
echo '<title>Registro Exitoso</title>';
echo '<style>';
echo 'body { background-color: #c3dd9b; color: #155724; font-family: Arial, sans-serif; padding: 40px; }';
echo 'h1 { color: #065925; }'; //Verde
echo 'h2 { color: #800080; }'; //Morado norma
echo 'p { color: #0d1b07; }';//Negro 
echo 'li { color: #0d1b07; }'; //Negro
echo '</style>';
echo '</head>';
echo '<body>';

//Linea
echo '<title>Línea Verde en PHP</title>';
    echo '<style>';
    echo '.underline-green {';
    echo '    position: relative;';
    echo '    display: inline-block;';
    echo '}';
    echo '.underline-green::after {';
    echo '    content: "";';
    echo '    position: absolute;';
    echo '    left: 0;';
    echo '    bottom: -5px;';  // Ajusta la posición vertical de la línea
    echo '    width: 500%;'; //Ajusta el horizontal 100% perfecto, 500% completo
    echo '    height: 2px;';  // Ajusta el grosor de la línea
    echo '    background-color: green;';  // Color de la línea
    echo '}';
    echo '</style>';
    echo '</head>';
    echo '<body>';

echo '<h1 class="underline-green">MUCHAS GRACIAS</h1>';

echo '<p>Gracias por entrar al concurso de Tenis Mike® "Chidos mis Tenis". Hemos recibido la siguiente información de tu registro:</p>';
echo '<h2>Acerca de ti:</h2>';
echo '<ul>';
echo '<li><strong>Nombre:</strong> ' . $name . '</li>';
echo '<li><strong>E-mail:</strong> ' . $email . '</li>';
echo '<li><strong>Télefono:</strong> ' . $phone . '</li>';
echo '</ul>';
echo '<p><strong>Tu triste historia:</strong></h2>';
echo '<p>' . $story . '</p>';

echo '<h2>Tu diseño de Tenis (si ganas)</h2>';
echo '<ul>';
echo '<li><strong>Color:</strong> ' . $color . '</li>';
echo '<li><strong>Tamaño:</strong> ' . $size . '</li>';
echo '</ul>'; 
echo '</body>'; 
echo '</html>';
?>