<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
			<title>CS160 Group 2 M2PA</title>
		</meta>
		<!-- TEMPORARY STYLE -->
		<link rel="stylesheet" href="css/cs160main.css">
	</head>
	<script type="text/javascript" src="js/jquery-1.11.3.js"></script>
	<body>
		<div id="pagewrapper">
			<header>
				<div id="logo">
					<a href="index.php"><img src="img/mooc_logo.png" alt="CS160-Group2-Logo" /></a>
				</div>
				<h1>CS160 Learn It All</h1>
				<a href=""><p>Login</p></a>
				<div>
					<nav>
						<a href="index.php">
							<div id="nav_tab">
								<p>Home</p>
							</div>
						</a>
						<a href="browse.php">
							<div id="nav_tab">
								<p>Browse</p>
							</div>
						</a>
						<a href="roadmaps.php">
							<div id="nav_tab">
								<p>Roadmaps</p>
							</div>
						</a>
						<a href="forums.php">
							<div id="nav_tab">
								<p>Forums</p>
							</div>
						</a>
						<a href="aboutus.php">
							<div id="nav_tab">
								<p>About Us</p>
							</div>
						</a>
					</nav>
					<div id="search_container">
						<form action="aggregate.php" method="post">
							<div id="search_bar">
								<input type="text" placeholder="Search..." />
							</div>
							<input type="submit" id="search_button" name="submit" value="SEARCH"/>
						</form>
					</div>
				</div>
			</header>
