$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateHospitalForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidHospitalIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
			{
			 url : "HospitalAPI",
			 type : type,
			 data : $("#formHospital").serialize(),
			 dataType : "text",
			 complete : function(response, status)
			 {
			 onHospitalSaveComplete(response.responseText, status);
			 }
			});
});

function onHospitalSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divHospitalsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidHospitalIDSave").val("");
	$("#formHospital")[0].reset();
}

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "HospitalAPI",
		type : "DELETE",
		data : "hospitalid=" + $(this).data("hospitalid"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
});

function onHospitalDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divHospitalsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidHospitalIDSave").val(
					$(this).closest("tr").find('#hidHospitalIDUpdate').val());
			$("#HospitalCode").val($(this).closest("tr").find('td:eq(0)').text());
			$("#HospitalName").val($(this).closest("tr").find('td:eq(1)').text());
			$("#HospitalRoom").val($(this).closest("tr").find('td:eq(2)').text());
			$("#HospitalDesc").val($(this).closest("tr").find('td:eq(3)').text());
		});
// CLIENTMODEL=========================================================================
function validateHospitalForm() {
	// CODE
	if ($("#HospitalCode").val().trim() == "") {
		return "Insert Hospital Code.";
	}
	// NAME
	if ($("#HospitalName").val().trim() == "") {
		return "Insert Hospital Name.";
	}

	// Rooms----------------------------
	if ($("#HospitalRoom").val().trim() == "") {
		return "Insert Number of Rooms in the Hospital.";
	}
	// is numerical value
	var tmpRoom = $("#HospitalRoom").val().trim();
	if (!$.isNumeric(tmpRoom)) {
		return "Insert a numerical value for Number of Rooms in the Hospital.";
	}
	// convert to decimal rooms
	$("#HospitalRoom").val(parseFloat(tmpRoom).toFixed(2));
	// DESCRIPTION------------------------
	if ($("#HospitalDesc").val().trim() == "") {
		return "Insert Hospital Description.";
	}
	return true;
}