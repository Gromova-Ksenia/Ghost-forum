const emailField = $("#email");
const passwordField = $("#password");
const usernameField = $("#username");

const registrationForm = {
    email: "",
    password: "",
    username: "",
};

$("#registration-button").click(onRegistration);

function onRegistration(){
    registrationForm.username = usernameField.val();
    registrationForm.email = emailField.val();
    registrationForm.password = passwordField.val();

    sendRegistrationForm();
}

function sendRegistrationForm(){
    let url = "/api/registration";
    let data = JSON.stringify(registrationForm);

    $.ajax({
        url: url,
        type: "POST",
        data: data,
        contentType: "application/json; charset=utf-8",
        dataType: "text"
    })
        .done(function () {
            console.log('Успешная регистрация!');
        })
        .fail(function(err) {
            console.log('Ошибка при регистрации.');
            console.log(err)
        })
}