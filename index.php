<html lang="en-US">
	<head>
		<meta charset="utf-8">
			<title>CS160 Group 2 M2PA</title>
		</meta>
		<style>
			table
			{
				border: 5px;
				border-style: solid;

				border-color: navy;
				background-color: yellow;

				padding: 0px;
			}
			tr,th,td
			{
				border: 1px;
				border-style: solid;
				border-color: navy;

				padding: 0px;
				margin: 0px;
			}
			img
			{
				width: 80px;
				height: 80px;
			}
		</style>
	</head>
	<body>
		<h2>DATA FROM MOOCS160 DATABASE</h2>
		<div>
			<?php
				try
				{
					$con = new PDO("mysql:host=localhost;dbname=moocs160", "root", "xxxxxx");
					$con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
			
					$query = "SELECT * FROM course_data as C, coursedetails as D WHERE C.id = D.course_id";
					$result = $con->query($query);
					$row = $result->fetch(PDO::FETCH_ASSOC);

					print "<table>\n";
						print "<tr>\n";
							foreach ($row as $field => $value)
							{
								print "<th>$field</th>\n";
							}
						print "</tr>\n";

						$data = $con->query($query);
						$data->setFetchMode(PDO::FETCH_ASSOC);

						foreach($data as $row)
						{
							print "<tr>\n";
							foreach ($row as $element => $value)
							{
								if($element == "course_image" || $element == "profimage")
									print "<td><img src=$value /><td>";
								else
									print "<td>$value</td>\n";
							}
							print "</tr>\n";
						}
					print "</table>\n";
				}
				catch(PDOException $ex)
				{
					echo 'ERROR: '.$ex->getMessage();
				}
			?>
		</div>
	</body>
</html>
