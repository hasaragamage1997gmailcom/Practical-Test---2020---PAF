<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.Hospital"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hospital Management</title>
<link href="resources/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="resources/css/sb-admin-2.min.css" rel="stylesheet">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/hospital.js"></script>
</head>
<body>
	<div class="container">
	<h1>Hospital Management</h1>
		<div class="row">
			<div class="col-6">
				
				<form id="formHospital" name="formHospital">
					Hospital code: <input id="HospitalCode" name="HospitalCode" type="text"
						class="form-control form-control-sm"> <br> Hospital name:
					<input id="HospitalName" name="HospitalName" type="text"
						class="form-control form-control-sm"> <br>Number of Rooms n the Hospital
					: <input id="HospitalRoom" name="HospitalRoom" type="text"
						class="form-control form-control-sm"> <br> Hospital
					description: <input id="HospitalDesc" name="HospitalDesc" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidHospitalIDSave" name="hidHospitalIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divHospitalsGrid">
					<%
					Hospital HospitalObj = new Hospital();
						out.print(HospitalObj.readHospital());
					%>
				</div>

			</div>
		</div>
	</div>

</body>
</html>
