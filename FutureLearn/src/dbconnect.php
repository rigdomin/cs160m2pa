<?php
/* File   : dbconnect.php
   Subject: CS174 demo
   Authors: Chris Tseng
   Version: 1.0
   Date   : Oct 22, 2014
   Description: create database connection to the selected database. Start by creating a database named
   cs174 and then import guestbook.mysql into it.
*/
//Create a database named cs174 and load guestbook.mysql into it before using this example
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "moocs160";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
?>