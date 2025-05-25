<?php

/*$hostname = 'localhost';
$database = 'sistema';
$username = 'root';
$password = '1234';

$conexion = new mysqli($hostname, $username, $password, $database);
if($conexion->connect_errno){
    echo"Problemas de conexion al sitio web";
}
*/

$hostname = '127.0.0.1';   // Cambié localhost por la IP local
$database = 'sistema';      // Base de datos 'sistema'
$username = 'root';         // Usuario 'root' de XAMPP
$password = '';             // Contraseña vacía (por defecto en XAMPP)
$port = 3307;               // Puerto 3307 de XAMPP

$conexion = new mysqli($hostname, $username, $password, $database, $port);

if($conexion->connect_errno){
    echo "Problemas de conexion al sitio web: " . $conexion->connect_error;
} /*else {
    echo "Conexión exitosa a la base de datos 'sistema'.";
}*/

?>
