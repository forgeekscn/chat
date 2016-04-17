<?php
require_once ('./conn.php');

$username = $_GET['username'];
$x = $_GET['x'];
$y = $_GET['y'];

$sql1 = "Select * from onlinelist where username= '$username' ";
$query = mysqli_query($connect, $sql1);

$row = mysqli_fetch_array($query);

if (! mysqli_num_rows($query)) {
    
    $sql = "select name from user where username='$username'";
    $query = mysqli_query($connect, $sql);
    $list=mysqli_fetch_array($query);
    $name=$list['name'];
    $name= preg_replace("#\\\u([0-9a-f]{4})#ie", "iconv('UCS-2BE', 'UTF-8', pack('H4', '\\1'))", $name) ;
    $sql = "INSERT INTO onlinelist (username,x,y,name) VALUES ('$username', '$x','$y','$name')";
    $query = mysqli_query($connect, $sql);
    echo "insert success";
} else {
    $sql = "UPDATE onlinelist   SET x='$x',y='$y' where username='$username'  ";
    $query = mysqli_query($connect, $sql);
    echo "update success";
}



?>