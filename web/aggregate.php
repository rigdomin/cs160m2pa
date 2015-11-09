<?php include('header.php'); ?>

		<div id="container">
			<?php
				try
				{
					$con = new PDO("mysql:host=localhost;dbname=moocs160", "root", "xxxxxxx");
					$con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
			
					$query = "SELECT * FROM course_data as C, coursedetails as D WHERE C.id = D.course_id";
					$result = $con->query($query);
					$row = $result->fetch(PDO::FETCH_ASSOC);

					print "<div id='course_container'>";
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
					print "</div>";
				}
				catch(PDOException $ex)
				{
					echo 'ERROR: '.$ex->getMessage();
				}
			?>
		</div>

<?php include('footer.php'); ?>
