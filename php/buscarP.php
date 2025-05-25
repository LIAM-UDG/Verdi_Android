<?php

include 'conexion.php';
$conexion->set_charset("utf8");

$id_planta = isset($_GET['planta']) ? (int) $_GET['planta'] : 0;

$consulta = $conexion->prepare("SELECT * FROM planta WHERE ID_Planta = ?");
$consulta->bind_param("i", $id_planta);
$consulta->execute();

$resultado = $consulta->get_result();
$planta = [];

while ($fila = $resultado->fetch_assoc()) {
    if (isset($fila['Imagen'])) {
        $fila['Imagen'] = base64_encode($fila['Imagen']);
    }
    $planta[] = $fila;
}

echo json_encode($planta, JSON_UNESCAPED_UNICODE); // <- importante

$consulta->close();
$conexion->close();

/*include 'conexion.php';
$pkplanta=$_GET['planta'];

$consulta = "SELECT * from planta where ID_Planta = '$pkplanta'";
$resultado = $conexion -> query($consulta);

//utf8 permite el uso de caracteres especiales
while($fila=$resultado -> fetch_array()){
    $planta[] = array_map('utf8_encode', $fila);

}

echo json_encode($planta);
$resultado -> close();

header('Content-Type: application/json; charset=utf-8');

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
