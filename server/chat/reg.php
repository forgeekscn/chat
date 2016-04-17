<?php

require_once('./conn.php');
require_once('./api.php');

	$sql=" select * from user where username='$_GET[username]'";
	$query=mysqli_query($connect,$sql);
	$row=mysqli_num_rows($query);

	
	$password= md5($_GET['password']);

	$p= new ServerAPI("n19jmcy59zgc9","me2J51LwHotGBh");
	$r=$p->getToken($_GET['username'],"","");


	$obj=json_decode($r);
	if($obj->code != "200")
	{
		$result=array("status"=>"error");
		echo "error";
	}

	$token=$obj->token;
	$sql2="insert into `user` (username,password,token) values('$_GET[username]','$password','$token')";
	$query=mysqli_query($connect,$sql2);
	$result=array("status"=>"success");
	echo $r;



/*
	if($row==0){
		$query=mysqli_query($connect,"INSERT INTO user (username, password, token) VALUES ('$_GET[username]', '$_GET[password]','tokennnnnn')");
		
		echo "regist success";
	}

*/



/*
	while($row = mysqli_fetch_array($query))
	{
		echo "<br />";
		echo $row['username'] . " " . $row['password'];
		
	}

*/
	mysqli_close($connect);




/*
if( $_GET['username']!="" && $_GET['password']!="")
{

	$password= md5($_GET['password']);
    $sql=" select * from user where username='$_GET[username]'";
	//$sql=" select * from user";
	//echo $sql;
	$query=mysqli_query($connect,$sql);
	$row=mysqli_num_rows($query);
	//echo "row";
	echo $row;


	//mysqli_query($connect,"INSERT INTO user (username, password, token) VALUES ('Pet', 'Griffin', '35asdasd')");
	//echo "success insert";
	if($row==0)
	{
		$p= new ServerAPI("n19jmcy59zgc9","me2J51LwHotGBh");
		$r=$p->getToken("111",$_GET['username'],"");
		$obj=json_decode($r);
		if($obj->code != "200")
		{
			$result=array("status"=>"error");
			echo $result;
			//echo "error";
		}
		else 
		{
			$token=$obj->token;
			$sql2="insert into `user` (username,password,token) values('$_GET[username]','$password','$token')";
			$query=mysqli_query($connect,$sql2);
			$result=array("status"=>"success");
			echo $result;
			//echo "success";
	
		}






	}else
	{
		
		
		while($row = mysql_fetch_array($result))
		  {
		  echo $row['FirstName'] . " " . $row['LastName'];
		  echo "<br />";
		  }

		$result=array("status"=>"exists");
		echo json_decode($result->status);
	}



}
*/

?>