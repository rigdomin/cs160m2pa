<?php

$courseID = $_GET['id'];

try {
	$con = new PDO("mysql:host=localhost;dbname=youthcyb_cs160s4g2;charset=utf8mb4", "youthcyb_160s4g2", "group2gumface");
			$con->exec("set names utf8");
	$con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	$sql = "INSERT INTO roadmap_data (course_id)
	VALUES (".$courseID.")";
    // use exec() because no results are returned
	$con->exec($sql);

	$message1 = "New course added successfully to your roadmap";
	echo "<script type='text/javascript'>alert('$message1');</script>";
}
catch(PDOException $e)
{
	$message2 = "already in your roadmap";
	echo "<script type='text/javascript'>alert('$message2');</script>";
}

?>