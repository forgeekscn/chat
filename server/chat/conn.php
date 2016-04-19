<?php
header("Content-Type: text/html;charset=utf-8");

$db_username = "root";
$db_password = "root";
$db_host = "localhost";
$db_port = 3306;
$db_name = "chat";

$connect = @mysqli_connect($db_host, $db_username, $db_password, $db_name, $db_port);

?>