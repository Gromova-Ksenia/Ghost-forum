//Получаем посты
async function getPosts(){
    try{
        const responce = await('http://127.0.0.1:8081/posts');
        if (!responce.ok){
            throw new Error('Ошибка при загрузке данных с сервера');
        }
        const posts = await responce.json();
        diplayPosts(posts);
        console.log("Скрипт отработал")
    } catch (error){
        console.error('Ошибка ', error);
    }
}

//Получаем посты по юзеру
async function getPostsByUser(){
    try{
        const responce = await(`http://127.0.0.1:8081/user${id}`);
        if (!responce.ok){
            throw new Error('Ошибка при загрузке данных с сервера');
        }
        const posts = await responce.json();
        diplayPosts(posts);
    } catch (error){
        console.error('Ошибка ', error);
    }
}

//Окно редактирования поста
function showEditPostModal(post){
    const modalBody = document.querySelector('#editPostModal .modal');
    const form = document.createElement('form');
    form.id = 'editPostForm';

    Object.entries(post).forEach(([key, value]) =>{
        const inputGroup = document.createElement('div');
        inputGroup.classList.add('form-group');
        inputGroup.innerHTML =
            `<label for="${key}">${key}</label>
      <input type="text" class="form-control" id="${key}" value="${value}" ${key === 'id' ? 'disabled' : ''}>`;
        form.appendChild(inputGroup);
    });

    modalBody.innerHTML = '';
    modalBody.appendChild(form);

    $('#editPostModal').modal('show');
}

//Отправляем запрос на сохранение изменений
async function updatePost(updatedPost){
    try{
        const response = await fetch('http://127.0.0.1:8081/content/posts/edit', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedPost)
        });
        if (!response.ok){
            throw new Error('Ошибка при сохранении изменений');
        }
        console.log('Изменения успешносохранены');
        $('#editPostModal').modal('hide');
    } catch (error){
        console.error('Ошибка ', error);
    }
}

//Функция сборки формы
async function handleFormSubmit(event){
    event.preventDefault();

    const formData = new  FormData(event.target);

    const updatedPost = {};
    formData.forEach((value, key) => {
        updatedPost[key] = value;
    });

    await updatePost(updatedPost);
}

//Обработчик итить его за ногу
document.getElementById('editPostForm').addEventListener('submit', handleFormSubmit);

//
function createEditIcon(id){
    const icon = document.createElement('i');
    icon.classList.add('fas', 'fa-pencil-alt');
    icon.setAttribute('title', 'Редактировать');
    icon.addEventListener('click', function() {
        handleEditClick(id);
    });
    return icon;
}

function displayPosts (posts){
    const tableBody = document.getElementById('postListBody');
    tableBody.innerHTML = '';
    posts.forEach(post => {
        const row = document.createElement('tr');
        row.innerHTML = `
        <td class="author-name"><a href='http://127.0.0.1:8081/user${post.authorId}'>${post.authorUsername}</a></td>
        <td class="post-name"><a href='http://127.0.0.1:8081/post${post.id}'>${post.title}</a></td>
        <td class="creaton-time">${post.creationTime}</td>`;
    })
}

