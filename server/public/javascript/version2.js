/**
 * 
 */

function fetchLoad(id, route) {
	fetch(route).then(res => res.text()).then(body => {
		document.getElementById(id).innerHTML = body;
	})
}

function fetchPost(route, data, success) {
	fetch(route, { 
		method: 'POST',
		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		body: Object.keys(data).map(key => encodeURIComponent(key)+'='+encodeURIComponent(data[key])).join('&')
	}).then(res => res.text()).then(body => success(body));
}

const csrfToken = $("#csrfToken").val();
//const loginRoute = $("#loginRoute").val();
const loginRoute = document.getElementById("loginRoute").value;
const validateRoute = $("#validateRoute").val();
const createRoute = $("#createRoute").val();
const deleteRoute = $("#deleteRoute").val();
const addRoute = $("#addRoute").val();

// $("#contents").load(loginRoute);
fetchLoad("contents", loginRoute);

function login() {
	const username = $("#loginName").val();
	const password = $("#loginPass").val();
	// $.post(validateRoute,
	// 	{ username, password, csrfToken },
	// 	data => {
	// 		$("#contents").html(data);
	// 	});
	fetchPost(validateRoute,
		{ username, password, csrfToken },
		data => {
			document.getElementById("contents").innerHTML = data;
			//$("#contents").html(data);
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