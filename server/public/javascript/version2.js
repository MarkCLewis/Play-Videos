/**
 * 
 */

const csrfToken = $("#csrfToken").val();
const loginRoute = $("#loginRoute").val();
const validateRoute = $("#validateRoute").val();
const createRoute = $("#createRoute").val();
const deleteRoute = $("#deleteRoute").val();
const addRoute = $("#addRoute").val();

$("#contents").load(loginRoute);

function login() {
	const username = $("#loginName").val();
	const password = $("#loginPass").val();
	$.post(validateRoute,
		{ username, password, csrfToken },
		data => {
			$("#contents").html(data);
		});
}

function createUser() {
	const username = $("#createName").val();
	const password = $("#createPass").val();
	$.post(createRoute,
		{ username, password, csrfToken },
		data => {
			$("#contents").html(data);
		});
}

function deleteTask(index) {
	$.post(deleteRoute,
		{ index, csrfToken },
		data => {
			$("#contents").html(data);
		});
}

function addTask() {
	const task = $("#newTask").val();
	$.post(addRoute,
		{ task, csrfToken },
		data => {
			$("#contents").html(data);
		});
}