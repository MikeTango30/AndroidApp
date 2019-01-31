	<?php 

	$servername = "localhost";
	$username = "id7924278_pokemon";
	$password = "123456";
	$dbname = "id7924278_pokemon";

	//Create connection
	$conn = new mysqli ($servername, $username, $password, $dbname);

	//Check connection
	if ($conn->connect_error){
		die("Connection failed: " . $conn->connect_error);
	}

	if (isset($_POST['insert'])) {
		$name = $_POST['name'];
		$weight = $_POST['weight'];
		$cp = $_POST['cp'];
		$abilities = $_POST['abilities'];
		$type = $_POST['type'];


		$sql = "INSERT INTO pokemon(name, weight, cp, abilities, type) VALUES('$name', '$weight', '$cp' , '$abilities' , '$type')";

		if($conn->query($sql) === TRUE){
			echo "New record created successfuly";
		}else{
			echo "Error: " . $sql . "<br>" . $conn ->error;
		}
	}

	$conn->close();
?>