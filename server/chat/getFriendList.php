<?php

header("Content-Type: text/html;charset=utf-8");
require_once './conn.php';

$username = $_GET['username'];
if ($username != "" && isset($username)) {
    
//     $sql = "select ID from user where username='$username' ";
//     $query = mysqli_query($connect, $sql);
    
//     $list = mysqli_fetch_array($query);
    
//     $userid = $list['ID'];
//     echo $username . " -> " . $userid . "  :  ";
    
    $sql2 = "select * from friend1 where username1='$username' or username2='$username' ";
    $query = mysqli_query($connect, $sql2);
    
    $friendList = array();
    while ($list = mysqli_fetch_array($query)) {
        
//         echo $list['userid1'] . "-" . $list['userid2'] . "  ";
        $target=( $list['username1'] ==$username ? $list['username2'] : $list['username1'] );
        $sql2="select name from user where username='$target' ";
        $query2=mysqli_query( $connect,$sql2 );
        $list2=mysqli_fetch_array($query2);
        $n=preg_replace("#\\\u([0-9a-f]{4})#ie", "iconv('UCS-2BE', 'UTF-8', pack('H4', '\\1'))", $list2['name']);
        $friendList[]=$n;
//         echo $list2['username']." ";
        
        
    }
    
    
//     echo json_encode($friendList);
    echo preg_replace("#\\\u([0-9a-f]{4})#ie", "iconv('UCS-2BE', 'UTF-8', pack('H4', '\\1'))", json_encode($friendList));
    
}

?>