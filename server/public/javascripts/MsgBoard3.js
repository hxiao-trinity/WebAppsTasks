"use strict"

const csrfToken = document.getElementById("csrfToken").value;
const validateRoute = document.getElementById("validateRoute").value;
const messagesRoute = document.getElementById("messagesRoute").value;
const createRoute = document.getElementById("createRoute").value;
const putRoute = document.getElementById("putRoute").value;

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
            loadMessages();
        }
        else{

        }
    });
}

function loadMessages() {
    
    const ul = document.getElementById("message-list")
    fetch(messagesRoute).then(res => res.json()).then(messages => {
        for (const message of messages){
            const li = document.createElement("li");
            const fromUserText = document.createTextNode(message.from);
            const contentText = document.createTextNode(message.content);
            const toUserText = document.createTextNode(message.to);
            const sentAtText = document.createTextNode(message.sentAt);

            li.appendChild(sentAtText);
            li.appendChild(fromUserText);
            li.appendChild(document.createTextNode(" TO "));
            li.appendChild(toUserText);
            li.appendChild(document.createTextNode(": "))
            li.appendChild(contentText);
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
        }
        else{

        }
    });
}