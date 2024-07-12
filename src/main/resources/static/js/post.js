const titleField = $("#title");
const bodyField = $("#post-body");

const newPostForm = {
    title: "",
    body: "",
};

$("#post-button").click(onCreate);

function onCreate(){
    newPostForm.title = titleField.val();
    newPostForm.body = bodyField.val();

    sendPostForm();
}

function sendPostForm(){
    let url = "/api/posts/new-post";
    let data = JSON.stringify(newPostForm);

    $.ajax({
        url: url,
        type: "POST",
        data: data,
        contentType: "application/json; charset=utf-8",
        dataType: "text"
    })
        .fail(function(err) {
            console.log('Ошибка при регистрации.');
            console.log(err)
        })
}