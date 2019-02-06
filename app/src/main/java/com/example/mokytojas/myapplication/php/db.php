	<?php 

	$servername = "localhost";
	$username = "id7924_pokemon";
	$password = "slaptas";
	$dbname = "id7924_pokemon";

	//Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);

	//Check connection
	if ($conn->connect_error){
		die("Connection failed: " . $conn->connect_error);
	}

	if ($_POST['action'] == 'insert') {
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
	
	if (isset($_POST['searchQuery'])) {
		
		$search = $_POST['searchQuery'];	
		
		$sql = "SELECT * FROM pokemon WHERE name LIKE '%$search%'";

		$res = $conn->query($sql);
		
		$result = array();
			while($row = $res->fetch_assoc()) {
				$result[] = $row;
			}
			echo json_encode($result);
			
			
		/*if($res === TRUE){
			//41-45 eilutes
		}else{
			echo "no row ".$sql;
		}*/
	}

	$conn->close();
?>