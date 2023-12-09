
const loginRoute = $("#loginRoute").val();
const validateRoute = $("#validateRoute").val();

$("#contents").load(loginRoute)

function login() {
    const username = $("#loginName").val();
    const password = $("#loginPass").val();
    $.post(validateRoute, {username, password}, data => {
        $("#contents").html(data)
    }); 
}

function createUser() {
    const username = $("#createName").val();
    const password = $("#createPass").val();
    $("#contents").load("/create2?username=" + username + "&password=" + password);
}

function putMessage(){
    const fromuser = $("#sessionUsername").val();
    const ctt = $("#message-content").val();
    const touser = $("#message-to").val();
    $("#contents").load("/putMessage2?from_username=" + fromuser + "&content=" + encodeURIComponent(ctt) + "&to_username=" + touser)
}