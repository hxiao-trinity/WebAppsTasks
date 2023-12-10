"user strict"

const csrfToken = document.getElementById("#csrfToken").value;
const validateRoute = document.getElementById("#validateRoute").value;
const messagesRoute = document.getElementById("#messagesRoute").value;
const createRoute = document.getElementById("#createRoute").value;
const deleteRoute = document.getElementById("#deleteRoute").value;
const putRoute = document.getElementById("#putRoute").value;

function login() {
    const username = document.getElementById("#loginName").value;
    const password = document.getElementById("#loginPass").value;
    fetch(validateRoute, { 
		method: 'POST',
		headers: {'Content-Type': 'application/json', 'Csrf-Token':csrfToken},
		body: JSON.stringify({ username, password })
	}).then(res => res.json()).then(data => {
        console.log(data);
        if (data){
            document.getElementById("login-section").hidden = true;
            document.getElementById("message-section").hidden = false;
        }
        else{

        }
    });
}

function loadMessages() {
    fetch(messagesRoute).then(res => res.json()).then(msgs =>{
        
    });
}