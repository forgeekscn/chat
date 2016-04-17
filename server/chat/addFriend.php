<?php


require_once ('./conn.php');
$username = $_GET['username'];
$target = $_GET['target'];

if ($username != "" && $target != "") {
    
    $sql = "select * from user  where username='$username' or username='$target'";
    $query = mysqli_query($connect, $sql) or die(mysqli_error($connect));
    
    if (! $query) {
        die(mysqli_error($connect));
    }
    
    $rows = mysqli_num_rows($query);
    
    if ($rows == 2) {
        $list1 = mysqli_fetch_array($query);
        $list2 = mysqli_fetch_array($query);
        $userid1 = $list1['ID'];
        $userid2 = $list2['ID'];
        
//         echo $userid1 . " " . $userid2;

        if ($userid1 > $userid2) {
            $temp = $userid1;
            $userid1 = $userid2;
            $userid2 = $temp;
        }
        
        $sql2 = "select * from friend where userid1='$userid1'  and userid2='$userid2' ";
        $query = mysqli_query($connect, $sql2);
        $rows = mysqli_num_rows($query);
        
        if ($rows == 0) {
            $sql3 = "insert into friend (userid1,userid2) values ( '$userid1','$userid2') ";
            $query = mysqli_query($connect, $sql3);
            echo "success";
        } else
            echo "error";
    } else
        echo "error";
} else
    echo "error";



?>

