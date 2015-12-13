<?php

$courseID = (int)$_GET['delete'];

try {
	$con = new PDO("mysql:host=localhost;dbname=youthcyb_cs160s4g2;charset=utf8mb4", "youthcyb_160s4g2", "group2gumface");
			$con->exec("set names utf8");
	$con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	$sql = "DELETE FROM roadmap_data
	WHERE course_id = ".$courseID;
    // use exec() because no results are returned
	$con->exec($sql);

	$message1 = "course successfully deleted from your roadmap";
	echo "<script type='text/javascript'>alert('$message1');</script>";
}
catch(PDOException $e)
{
	print 'ERROR: '.$ex->getMessage();
}

?>