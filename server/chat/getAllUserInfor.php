<?php
header("Content-Type: text/html;charset=utf-8");

require_once ('./conn.php');

$username = $_GET['username'];
if ($username != "" && isset($username)) {
    
    $sql = "select * from user where username='$username' ";
    $query = mysqli_query($connect, $sql);
    
    $list = mysqli_fetch_array($query);
    
    $userid = $list['ID'];
    $totalTime = $list['totalTime'];
    $totalDistance = $list['totalDistance'];
    $totalEnergy = $list['totalEnergy'];
    $totalRunning = $list['totalRunning'];
    $farestSpeed = $list['farestSpeed'];
    $profilePic = $list['profilePic'];
    $runRecordID = $list['runRecordID'];
    $sexual = $list['sexual'];
    $age = $list['age'];
    $name = $list['name'];
    $weight = $list['weight'];
    $myWords = $list['mywords'];
    $class = $list['class'];
    
    $jarr = array(
        'userid' => $userid,
        'username' => $username,
        'totaltime' => $totalTime,
        'totaldistance' => $totalDistance,
        'totalenergy' => $totalEnergy,
        'totalrunning' => $totalRunning,
        'farestspeed' => $farestSpeed,
        'profilepic' => $profilePic,
        'runrecordid' => $runRecordID,
        'sexual' => $sexual,
        'age' => $age,
        'name' => $name,
        'weight' => $weight,
        'mywords' => $myWords,
        'class' => $class
    );
//     $jarr = encodeOperations($jarr);
//     echo json_encode($jarr);
//     echo json_decode($jarr,true);
    echo preg_replace("#\\\u([0-9a-f]{4})#ie", "iconv('UCS-2BE', 'UTF-8', pack('H4', '\\1'))", json_encode($jarr));
//     echo base64_encode(serialize($jarr));
    // echo $username . " -> " . $userid . " " . $totalTime . " ";
    
    // $sql2 = "select * from friend where userid1='$userid' or userid2='$userid' ";
    // $query = mysqli_query($connect, $sql2);
    
    // $friendList = array();
    // while ($list = mysqli_fetch_array($query)) {
    
    // // echo $list['userid1'] . "-" . $list['userid2'] . " ";
    // $target=( $list['userid1'] ==$userid ? $list['userid2'] : $list['userid1'] );
    // $sql2="select username from user where ID='$target' ";
    // $query2=mysqli_query( $connect,$sql2 );
    // $list2=mysqli_fetch_array($query2);
    // $friendList[]=$list2['username'];
    // // echo $list2['username']." ";
    
    // }
    
    // echo json_encode($friendList);
}

function encodeOperations($array)
{
    foreach ((array) $array as $key => $value) {
        if (is_array($value)) {
            encodeOperations($array[$key]);
        } else {
            $array[$key] = urlencode(mb_convert_encoding($value, 'UTF-8', 'UTF-8'));
        }
    }
    return $array;
}

?>
