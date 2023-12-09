@()

$("#contents").load("/login2").load("@routes.MsgBoardAjax.login")

console.log("MsgBoardAjax.js loaded");

function login() {
    const username = $("#loginName").val();
    const password = $("#loginPass").val();
    @* $("#contents").load("@routes.MsgBoardAjax.validate"); *@
    $("#contents").load("/validate2?username=" + username + "&password=" + password); 
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