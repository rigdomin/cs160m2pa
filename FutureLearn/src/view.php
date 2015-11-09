<?php
/* File   : view.php
   Subject: CS174 demo
   Authors: Chris Tseng
   Version: 1.0
   Date   : Oct. 23, 2014
   Description: Retrieve all data from guestbook and display on browser
*/
   include("dbconnect.php"); ?>

   <h2>View FutureLearn Database!</h2>

   <?php
   $sql = "select * ".
   "from course_data, coursedetails ".
   "where coursedetails.course_id = course_data.id";
   $result = mysqli_query($conn, $sql);
   if ($result)
   {

/*
while (list($name_entered, $location, $email, $url, $comments) = mysqli_fetch_array($result))
        {
        print "<b>Name:</b>";
        print $name_entered;
        print "<br>\n";
        print "<b>Location:</b>";
        print $location;
        print "<br>\n";
        print "<b>Email:</b>";
        print $email;
        print "<br>\n";
        print "<b>URL:</b>";
        print $url;
        print "<br>\n";
        print "<b>Comments:</b>";
        print $comments;
        print "<br>\n";
        print "<br>\n";
        print "<br>\n";
        }
        */


//another way to retrieve data
        
        while ($row = mysqli_fetch_array($result))
        {
            print "<b>CourseID: </b>";
            print $row["course_id"];
            print "<br>\n";
            print "<b>Course Title: </b>";
            print $row["title"];
            print "<br>\n";
            print "<b>ShortDesc: </b>";
            print $row["short_desc"];
            print "<br>\n";
            print "<b>LongDesc: </b>";
            print $row["long_desc"];
            print "<br>\n";
            print "<b>Course Link: </b>";
            print $row["course_link"];
            print "<br>\n";
            print "<b>Video Link: </b>";
            print $row["video_link"];
            print "<br>\n";
            print "<b>Start date: </b>";
            print $row["start_date"];
            print "<br>\n";
            print "<b>Course Length: </b>";
            print $row["course_length"];
            print "<br>\n";
            print "<b>Course Image: </b>";
            print $row["course_image"];
            print "<br>\n";
            print "<b>Category: </b>";
            print $row["category"];
            print "<br>\n";
            print "<b>Site: </b>";
            print $row["site"];
            print "<br>\n";
            print "<b>Course Fee: </b>";
            print $row["course_fee"];
            print "<br>\n";
            print "<b>Language: </b>";
            print $row["language"];
            print "<br>\n";
            print "<b>Certificate: </b>";
            print $row["certificate"];
            print "<br>\n";
            print "<b>University: </b>";
            print $row["university"];
            print "<br>\n";
            print "<b>Time Scraped: </b>";
            print $row["time_scraped"];
            print "<br>\n";
            print "<b>Professor's Name: </b>";
            print $row["profname"];
            print "<br>\n";
            print "<b>Professor's Image: </b>";
            print $row["profimage"];
            print "<br>\n";
            print "<br>\n";
            print "<br>\n";
        }

        mysqli_free_result($result);
    }
    ?>