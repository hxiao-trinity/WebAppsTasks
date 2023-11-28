$("#contents").load("/login2")


function login() {
    const username = $("#loginName").val()
    const password = $("#loginPass").val()
    $("contents").load("/validate2?username=" + username + "&password=" + password);
    console.log("Trying to login with " + username + ", " + password)
}

function createUser() {
    const username = $("#createName").val()
    const password = $("#createPass").val()
    console.log("Trying to create user with " + username + ", " + password)
}