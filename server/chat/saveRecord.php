<?php
header("Content-Type: text/html;charset=utf-8");

require_once ('./conn.php');

$speed = $_POST['speed'];
$far = $_POST['far'];
$time = $_POST['time'];
$username = $_POST['username'];


if ($username != "" && isset($username)) {
    
    // $sql = "UPDATE user SET sexual = '$sexual' ,mywords='$mywords',class='$classname' ,name='$name',weight='$weight',height='$height' WHERE username='$username' ";
    $sql = "INSERT INTO runrecord (far, time,speed,username) VALUES ('$far', '$time','$speed','$username')";
    echo "INSERT INTO runrecord (far, time,speed,username) VALUES ('$far', '$time','$speed','$username') ";
    $query = mysqli_query($connect, $sql);
    
    $sql2 = "SELECT * FROM user WHERE username='$username' ";
    $query1 = mysqli_query($connect, $sql2);
    $list = mysqli_fetch_array($query1);
    
    $totalDistance = $list['totalDistance'] + $far;
    $totalTime = $list['totalTime'] + $time;
    $totalEnergy = $list['totalEnergy'] + $time*1000;
    $totalRunning = $list['totalRunning'] + 1;
    $fastestSpeed =0;
    if ($list['farestSpeed'] < $speed) {
        $fastestSpeed = $speed;
    }
    
    $sql = "UPDATE user SET totalDistance='$totalDistance' , totalTime='$totalTime' , totalEnergy='$totalEnergy',totalRunning='$totalRunning' , farestSpeed='$fastestSpeed' WHERE username='$username' ";
    echo "UPDATE user SET totalDistance='$totalDistance' , totalTime='$totalTime' , totalEnergy='$totalEnergy',totalRunning='$totalRunning' , fastestSpeed='$fastestSpeed' WHERE username='$username' ";
    
    $query = mysqli_query($connect, $sql);
    
    echo "success";
}

?>