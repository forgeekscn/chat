<?php

// $filename = date("YmdHis");
$filename = $_POST["username"];
$file = fopen("pic/".$filename . ".png", "w");
$data = base64_decode($_POST["img"]);
fwrite($file, $data);

fclose($file);

?>