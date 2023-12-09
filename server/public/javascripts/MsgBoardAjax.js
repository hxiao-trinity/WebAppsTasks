
const csrfToken = $("#csrfToken").val();
const loginRoute = $("#loginRoute").val();
const validateRoute = $("#validateRoute").val();
const createRoute = $("#createRoute").val();
const deleteRoute = $("#deleteRoute").val();
const putRoute = $("#putRoute").val();


$("#contents").load(loginRoute)

function login() {
    const username = $("#loginName").val();
    const password = $("#loginPass").val();
    $.post(validateRoute, {username, password, csrfToken}, data => {
        $("#contents").html(data)
    }); 
}

function createUser() {
    const username = $("#createName").val();
    const password = $("#createPass").val();
    $.post(createRoute, {username, password, csrfToken}, data => {
        $("#contents").html(data)
    });
}

function putMessage(){
    const from_user = $("#sessionUsername").val();
    const content = $("#message-content").val();
    const to_user = $("#message-to").val();
    console.log(putRoute); //putMessage2
    $.post(putRoute, {from_user, content, to_user, csrfToken}, data => {
        $("#contents").html(data)
    });
}