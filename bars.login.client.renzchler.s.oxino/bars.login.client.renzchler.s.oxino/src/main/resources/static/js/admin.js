/**
 *
 */
$(document).ready(function() {
	// Initialize table
	var table = $('#userTable').DataTable();

	// Get all users
	$.ajax({
		method : "GET",
		datatype : "json",
		url : "http://localhost:3000/server/users",
		error:function(){
			$("#message").text("*" + data.entity).css("color", "red");
		}
	}).done(function(data) {

		const users = Object.values(data);

		for(var user of users){
			table.row.add([
			user.username,
			user.password,
			(user.roleId == 1)? "admin" : "user",
			"<div class='d-flex justify-content-center'>" +
			"<a class='fa fa-edit p-2' onclick='toggleEditUserModal("+user.id+")'></a>" +
			"<a class='fa fa-trash p-2' onclick='toggleDeleteUserModal("+ user.id +")'></a></div>",
			]).draw(false);
		}
	});

	// switch password only edit
	$("#passwordOnlySwitch").on("click", function(){
		if ($(this).is(":checked") == true) {
			$("#editUsername").attr("disabled", "disabled");
		}else{
			$("#editUsername").removeAttr("disabled", "disabled");
		}
	});

	// form validation
	$("input[name='username']").on("blur",function(){

		var identifier = this.id;
		switch(identifier){
			case "username":
				checkUsername(identifier);
				break;
			case "editUsername":
				checkUsername(identifier);
				break;
			default:
				console.log("error");

		}

	});

	//password confirmation
	$("input[name='password'").on("blur", function(){

		var identifier = this.id;
		switch(identifier){
			case "password":
				checkPassword(identifier);
				break;
			case "editPassword":
				checkPassword(identifier);
				break;
			default:
				console.log("error");

		}

	});

	//confirm
	$("input[name='passwordConfirm']").on("blur", function(){

		var identifier = this.id;
		console.log(identifier)
		switch(identifier){
			case "passwordConfirm":
				passwordConfirm(identifier, '');
				break;
			case "editPasswordConfirm":
				passwordConfirm(identifier, 'edit');

				break;
			default:
				console.log("error");

		}

	})

	$("#addUserModal, #editUserModal").on('hidden.bs.modal', function () {
		$("#addUserForm, #editUserForm").trigger("reset");
		$("input[name='username']").removeClass("is-invalid");
		$("input[name='username']").removeClass("is-valid");
		$("input[name='password']").removeClass("is-invalid");
		$("input[name='password']").removeClass("is-valid");
		$("input[name='passwordConfirm']").removeClass("is-invalid");
		$("input[name='passwordConfirm']").removeClass("is-valid");

	})

});

//check confirm password
function passwordConfirm(identifier, type){

	var element = "#"+identifier;
	var pass;
	if (type == "edit") {
		pass = $("#editPassword").val();
	}else{
		pass = $("#password").val();
	}
	var confirm = $(element).val();

	if (confirm != pass) {
		$(element).addClass("is-invalid");
		$(element).addClass("hit");
		$(element).removeClass("is-valid");
		$(".confirm-password-msg").addClass("invalid-feedback").text("Password does not match.");
		$(".submit").attr("disabled", "disabled");
	}else{
		if (!$("input[name='password'").hasClass("hit")) {
			$(element).removeClass("is-invalid");
			$(element).removeClass("hit");
			$(element).addClass("is-valid");
			$(".confirm-password-msg").removeClass("invalid-feedback").text("Matched.");
			$(".submit").removeAttr("disabled", "disabled");
		}
	}
}

//check password field
function checkPassword(identifier){
	var element = "#"+identifier;
	var pass = $(element).val();
	var len = pass.length;
	if (len < 6 || $.trim(pass) == '') {
		$(element).addClass("is-invalid");
		$(element).addClass("hit");
		$(element).removeClass("is-valid");
		$("input[name='passwordConfirm']").attr("disabled","disabled");
		$(".password-msg").addClass("invalid-feedback").text("Password cannot be empty and must be 6 characters minimum.");
		$(".submit").attr("disabled", "disabled");
	}else{
		$(element).removeClass("is-invalid");
		$(element).addClass("is-valid");
		$(element).removeClass("hit");
		$("input[name='passwordConfirm']").removeAttr("disabled","disabled");
		$(".password-msg").removeClass("invalid-feedback").text("Good.");
		$(".submit").removeAttr("disabled", "disabled");
	}
}

//check user name input field
function checkUsername(identifier){

	var element = "#"+identifier;
	var username = $(element).val();
	if (username != null && $.trim(username) != '' && username.length >= 6) {
		if (!$(element).hasClass("hit")) {
			$(element).addClass("is-valid");
			$(element).removeClass("is-invalid");
			$(".username-msg").addClass("valid-feedback").text("Good.");
			$(".submit").removeAttr("disabled", "disabled");
		}
	}else{
		$(element).addClass("is-invalid");
		$(element).removeClass("is-valid");
		$(".username-msg").addClass("invalid-feedback").text("Username cannot be empty and must be 6 characters minimum.");
		$(".submit").attr("disabled", "disabled");
	}

}

//validate username database
function validateUsername(type){
	var username;
	if (type == "add") {
		username = {"username":$("#username").val()};
	}else{
		username = {"username":$("#editUsername").val()};
	}
	$.ajax({
		url:"http://localhost:3000/server/validate-user",
		method:"POST",
		data:JSON.stringify(username),
		datatype:"json",
		contentType : "application/json",
		error:function(data){
			if (data.status == 404) {
				$("input[name='username']").addClass("is-invalid");
				$("input[name='username']").removeClass("is-valid");
				$("input[name='username']").addClass("hit");
				$(".username-msg").addClass("invalid-feedback").text(data.responseText);
				$(".submit").attr("disabled", "disabled");
			}else{
				$("input[name='username']").addClass("is-valid");
				$("input[name='username']").removeClass("is-invalid");
				$("input[name='username']").removeClass("hit");
				$(".username-msg").addClass("valid-feedback").text("Good.");
				$(".submit").removeAttr("disabled", "disabled");
			}
		}
		});
}


// add user
function addUser() {

	var table = $('#userTable').DataTable();

	$("#addUserForm").submit(function(e) {
		e.preventDefault();
	});
	$("#addUserModal").modal("hide");
	var username = $("#username").val();
	var password = $("#password").val();
	var role = $("#role").val();

	var user = {
		"username" : username,
		"password" : password,
		"roleId" : role
	};

	$.ajax({
		url : "http://localhost:3070/register",
		method : "POST",
		data : user,
		error:function(data){
			$("#message").text("*" + data.entity).css("color", "red");
		},
	}).done(function(data) {

		switch(data.status){
			case 200:
				$("#addUserForm").trigger("reset");
				$("#message").text(data.entity).css("color", "green");
				break;
			default:
				$("#message").text("*" + data.entity).css("color", "red");
		}

		$.ajax({
			method : "GET",
			datatype : "json",
			url : "http://localhost:3000/server/users"
		}).done(function(data) {

			const users = Object.values(data);
			table.clear();
			for(var user of users){
				table.row.add([
				user.username,
				user.password,
				(user.roleId == 1)? "admin" : "user",
				"<div class='d-flex justify-content-center'>" +
				"<a class='fa fa-edit p-2' onclick='toggleEditUserModal("+user.id+")'></a>" +
				"<a class='fa fa-trash p-2' onclick='toggleDeleteUserModal("+ user.id +")'></a></div>",
				]).draw(false);
			}

		});
	});
}

// toggle delete modal
function toggleDeleteUserModal(id){
	$("#deleteUserModal").modal("show");
	$("#deleteUserIdInput").val(id);
}

// delete user
function deleteUser(){
	var table = $('#userTable').DataTable();
	var id = {"id":$("#deleteUserIdInput").val()};

	$("#deleteUserModal").modal("hide");
	$.ajax({
		url : "http://localhost:3000/server/delete-user",
		datatype : "json",
		method : "DELETE",
		data : JSON.stringify(id),
		contentType : "application/json",
		error:function(data){
			$("#message").text("*" + data.entity).css("color", "red");
		}
	}).done(function() {

		$("#addUserForm").trigger("reset");
		$("#message").text("User has been deleted.").css("color","red");

		$.ajax({
			method : "GET",
			datatype : "json",
			url : "http://localhost:3000/server/users"
		}).done(function(data) {

			const users = Object.values(data);
			table.clear();
			for(var user of users){
				table.row.add([
				user.username,
				user.password,
				(user.roleId == 1)? "admin" : "user",
				"<div class='d-flex justify-content-center'>" +
				"<a class='fa fa-edit p-2' onclick='toggleEditUserModal("+user.id+")'></a>" +
				"<a class='fa fa-trash p-2' onclick='toggleDeleteUserModal("+ user.id +")'></a></div>",
				]).draw(false);
			}

		});
	});
}

// toggle edit modal
function toggleEditUserModal(id){
	$("#editUserModal").modal("show");

	$.ajax({
		method : "GET",
		datatype : "json",
		url : "http://localhost:3000/server/get-user/" + id
	}).done(function(data) {
		$("#editUserIdInput").val(data.id);
		$("#editUsername").val(data.username);
		$("#editRole").val(data.roleId);


	});
}

// edit user
function editUser(){

	var table = $('#userTable').DataTable();

	$("#editUserForm").submit(function(e) {
		e.preventDefault();
	});
	$("#editUserModal").modal("hide");

	var id = $("#editUserIdInput").val();
	var username = $("#editUsername").val();
	var password = $("#editPassword").val();
	var roleId = $("#editRole").val();
	var url;

	if ($("#editUsername").prop("disabled")) {
		url = "http://localhost:3070/update-password-only";
	}else{
		url = "http://localhost:3070/update";
	}

	var user = {
			"id" : id,
			"username" : username,
			"password" : password,
			"roleId" : roleId
		};

	$.ajax({
		url : url,
		method : "PUT",
		data : user,
		error:function(data){
			$("#message").text("*" + data.entity).css("color", "red");
		},
	}).done(function(data) {
		$("#editUserForm").trigger("reset");

		switch(data.status){
		case 200:
			$("#editUserForm").trigger("reset");
			$("#message").text(data.entity).css("color", "green");
			break;
		default:
			$("#message").text("*" + data.entity).css("color", "red");
		}

		$.ajax({
			method : "GET",
			datatype : "json",
			url : "http://localhost:3000/server/users"
		}).done(function(data) {
			$("#editUserForm").trigger("reset");
			const users = Object.values(data);
			table.clear();
			for(var user of users){
				table.row.add([
				user.username,
				user.password,
				(user.roleId == 1)? "admin" : "user",
				"<div class='d-flex justify-content-center'>" +
				"<a class='fa fa-edit p-2' onclick='toggleEditUserModal("+user.id+")'></a>" +
				"<a class='fa fa-trash p-2' onclick='toggleDeleteUserModal("+ user.id +")'></a></div>",
				]).draw(false);
			}

		});
	});

}