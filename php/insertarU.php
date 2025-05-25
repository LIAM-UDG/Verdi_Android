<?php

/*include 'conexion.php';

$correo = $_POST['correo'];
$nombre = $_POST['nombre'];
$edad = $_POST['edad'];
$contrasena = $_POST['contrasena'];

$consulta = "INSERT INTO usuario (correo, nombre, edad, contrasena) VALUES ('$correo', '$nombre', '$edad', '$contrasena')";
mysqli_query($conexion, $consulta) or die (mysqli_error($conexion));

mysqli_close($conexion);*/

include 'conexion.php';
 
file_put_contents("log.txt", "[" . date("Y-m-d H:i:s") . "] POST: " . print_r($_POST, true) . "\n", FILE_APPEND);


if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $correo = $_POST['correo'] ?? '';
    $nombre = $_POST['nombre'] ?? '';
    $edad = $_POST['edad'] ?? '';
    $contrasena = $_POST['contrasena'] ?? '';

    // Mostrar los datos recibidos para depurar
    echo "Datos recibidos: correo=$correo, nombre=$nombre, edad=$edad, contrasena=$contrasena\n";

    if ($correo && $nombre && $edad && $contrasena) {
        $consulta = "INSERT INTO usuario (Correo, Nombre, Edad, Contraseña) VALUES ('$correo', '$nombre', '$edad', '$contrasena')";
        if (mysqli_query($conexion, $consulta)) {
            echo "INSERT_OK";
        } else {
            echo "ERROR_SQL: " . mysqli_error($conexion);
        }
    } else {
        echo "ERROR_DATOS_INCOMPLETOS";
    }

    mysqli_close($conexion);
} else {
    echo "NO_POST";
}

?>
