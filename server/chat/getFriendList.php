<?php
require_once './conn.php';

$username = $_GET['username'];
if ($username != "" && isset($username)) {
    
    $sql = "select ID from user where username='$username' ";
    $query = mysqli_query($connect, $sql);
    
    $list = mysqli_fetch_array($query);
    
    $userid = $list['ID'];
//     echo $username . " -> " . $userid . "  :  ";
    
    $sql2 = "select * from friend where userid1='$userid' or userid2='$userid' ";
    $query = mysqli_query($connect, $sql2);
    
    $friendList = array();
    while ($list = mysqli_fetch_array($query)) {
        
//         echo $list['userid1'] . "-" . $list['userid2'] . "  ";
        $target=( $list['userid1'] ==$userid ? $list['userid2'] : $list['userid1'] );
        $sql2="select username from user where ID='$target' ";
        $query2=mysqli_query( $connect,$sql2 );
        $list2=mysqli_fetch_array($query2);
        $friendList[]=$list2['username'];
//         echo $list2['username']." ";
        
        
    }
    
    
    echo json_encode($friendList);
    
    
}

?>