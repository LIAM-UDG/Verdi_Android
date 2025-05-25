<?php

/*include 'conexion.php';

$Correo = $_POST['Correo'];
$Contraseña = $_POST['Contraseña'];

$sentencia = $conexion -> prepare("SELECT * FROM usuario WHERE Correo = ? AND  Contraseña = ?");
$sentencia -> bind_param('ss', $Correo, $Contraseña);
$sentencia->execute();

$resultado = $sentencia->get_result();
if ($fila = $resultado->fetch_assoc()) {
    echo json_encode($fila.JSON_UNESCAPED_UNICODE);
} else {
    echo "0";
}

$sentencia->close();
$conexion->close();*/

include 'conexion.php';

$Correo = isset($_POST['Correo']) ? $_POST['Correo'] : '';
$Contraseña = isset($_POST['Contraseña']) ? $_POST['Contraseña'] : '';

if (empty($Correo) || empty($Contraseña)) {
    echo json_encode(["estado" => "Vacio"]);
    exit;
}

// Evaluacion de si el correo existe
$consultaCorreo = $conexion->prepare("SELECT * FROM usuario WHERE Correo = ?");
$consultaCorreo->bind_param('s', $Correo);
$consultaCorreo->execute();
$resultadoCorreo = $consultaCorreo->get_result();

if ($resultadoCorreo->num_rows === 0) {
    echo json_encode(["estado" => "Correo_Incorrecto"]);
} else {
    $usuario = $resultadoCorreo->fetch_assoc();
    if ($usuario['Contraseña'] !== $Contraseña) {
        echo json_encode(["estado" => "Contrasena_Incorrecta"]);
    } else {
        echo json_encode([
            "estado" => "Ok",
            "nombre" => $usuario['Nombre'], // puedes devolver más datos aquí si quieres
        ], JSON_UNESCAPED_UNICODE);
    }
}

$consultaCorreo->close();
$conexion->close();

?>
