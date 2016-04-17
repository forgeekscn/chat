<?php
header("Content-Type: text/html;charset=utf-8");

require_once ('./conn.php');

$username = $_POST['username'];
$sexual = $_POST['sexual'];
$name = $_POST['name'];
$mywords = $_POST['mywords'];
$classname = $_POST['classname'];
$height = $_POST['height'];
$weight = $_POST['weight'];
if ($username != "" && isset($username)) {
    
    $sql = "UPDATE user SET sexual = '$sexual' ,mywords='$mywords',class='$classname' ,name='$name',weight='$weight',height='$height'  WHERE username='$username' ";
    $query = mysqli_query($connect, $sql);
    echo "success";
}

?>