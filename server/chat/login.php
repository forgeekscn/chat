<?php

	require_once('./conn.php');
	
	if($_GET['username']!="" && $_GET['password']!=""  )
	{
		$username=$_GET['username'];
		$password=$_GET['password'];

		$sql="select * from user where username='$username'";
		$query=mysqli_query($connect,$sql);
		
		
		if($query === FALSE) { 
			yourErrorHandler(mysqli_error($mysqli));
			echo "error";
		}

		$row = mysqli_fetch_array($query);
//		echo $row['username']."  ".$row['password'];

		if(md5($password)==$row['password'])
		{
			$result=array("status"=>"success","token"=>$row['token']);
			echo $row['token'];
		}
		else 
		{
			echo "error";
		}


/*
		while($row = mysqli_fetch_array($query))
		{
			echo "<br />";
			echo $row['username'] . " " . $row['password'];
				
		}



		if($list['username']==$_GET['username']  &&md5($list[password])==$_GET['password'] )
		{
			$result=array('status'=>"success","token"=>$list['token']);
			echo json_decode($result);
		}
		else 
		{
			$result=array('status'=>"error");
			echo json_decode($result);


		
		}


		*/

	}
	else
	{
			$result=array('status'=>"error");
			echo json_decode($result);
	}
	





?>