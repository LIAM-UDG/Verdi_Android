<?php

include 'conexion.php';
$correo=$_GET['correo'];

$consulta = "select * from usuario where correo = '$correo'";
$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()){
    $usuario[] = array_map('utf8_encode', $fila);

}

echo json_encode($usuario);
$resultado -> close();

/*header('Content-Type: application/json; charset=utf-8');

include 'conexion.php';

$correo = $conexion->real_escape_string($_GET['correo']);
$usuario = [];

$consulta = "SELECT nombre, edad, correo, contraseña FROM usuario WHERE correo = '$correo'";
$resultado = $conexion->query($consulta);

if ($resultado) {
    while ($fila = $resultado->fetch_assoc()) {
        // Codifica los datos en UTF-8 si hace falta
        array_walk($fila, function (&$val) {
            $val = utf8_encode($val);
        });
        $usuario[] = $fila;
    }
    echo json_encode($usuario);
} else {
    http_response_code(500);
    echo json_encode(["error" => "Error en la consulta"]);
}

$resultado->close();
$conexion->close();*/

?>
