"use strict"

const csrfToken = document.getElementById("csrfToken").value;
const validateRoute = document.getElementById("validateRoute").value;
const messagesRoute = document.getElementById("messagesRoute").value;
const createRoute = document.getElementById("createRoute").value;
const putRoute = document.getElementById("putRoute").value;
const logOutRoute = document.getElementById("logOutRoute").value;

function login() {

    const username = document.getElementById("loginName").value;
    const password = document.getElementById("loginPass").value;
    fetch(validateRoute, { 
		method: 'POST',
		headers: {'Content-Type': 'application/json', 'Csrf-Token':csrfToken},
		body: JSON.stringify({ username, password })
	}).then(res => res.json()).then(data => {
        console.log(data);
        if (data){
            document.getElementById("login-section").hidden = true;
            document.getElementById("message-section").hidden = false;
            document.getElementById("login-mess").innerHTML = "";
            document.getElementById("create-mess").innerHTML = "";
            loadMessages();
        }
        else{
            document.getElementById("login-mess").innerHTML = "Login Failed";
        }
    });
}

function loadMessages() {
    
    const ul = document.getElementById("message-list");
    ul.innerHTML = "";
    fetch(messagesRoute).then(res => res.json()).then(messages => {
        for (const message of messages){
            const li = document.createElement("li");
            //set the states in V2.2
            li.appendChild(document.createTextNode(message.sentAt));
            li.appendChild(document.createTextNode(" "))
            li.appendChild(document.createTextNode(message.from));
            li.appendChild(document.createTextNode(" TO "));
            li.appendChild(document.createTextNode(message.to));
            li.appendChild(document.createTextNode(": "));
            li.appendChild(document.createTextNode(message.content));
            li.onclick = e => {

            }
            ul.appendChild(li);
        }
    });
    
}

function putMessage() {
    let from = document.getElementById("message-to").value;
    let content = document.getElementById("message-content").value;
    let to = document.getElementById("message-to").value;
    fetch(putRoute, { 
		method: 'POST',
		headers: {'Content-Type': 'application/json', 'Csrf-Token':csrfToken},
		body: JSON.stringify({ from, content, to })
	}).then(res => res.json()).then(data => {
        console.log(data);
        if (data){
            loadMessages();
            document.getElementById("message-to").value = "";
            document.getElementById("message-content").value = "";
            document.getElementById("put-mess").innerHTML = "";
        }
        else{
            document.getElementById("put-mess").innerHTML = "Put Message Failed";
        }
    });
}

function createUser() {

    const username = document.getElementById("createName").value;
    const password = document.getElementById("createPass").value;
    fetch(createRoute, { 
		method: 'POST',
		headers: {'Content-Type': 'application/json', 'Csrf-Token':csrfToken},
		body: JSON.stringify({ username, password })
	}).then(res => res.json()).then(data => {
        console.log(data);
        if (data){
            document.getElementById("login-section").hidden = true;
            document.getElementById("message-section").hidden = false;
            document.getElementById("create-mess").innerHTML = "";
            document.getElementById("login-mess").innerHTML = "";
            loadMessages();
        }
        else{
            document.getElementById("create-mess").innerHTML = "Create User Failed";
        }
    });
}

function logOut() {
    fetch(logOutRoute).then(res => res.json()).then(messages =>{
        document.getElementById("login-section").hidden = false;
        document.getElementById("message-section").hidden = true;
    });
}