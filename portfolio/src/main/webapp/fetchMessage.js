// async await function to check if user is authenticated or not
async
function checkAuth() {
    const response = await fetch('/authenticate');
    const message = await response.json();
    fetchMessage();
    const commentForm = document.getElementById('addComment');
    const authenticationForm = document.getElementById('authenticate');
    const logoutForm = document.getElementById('logout');
    if (message == false) {
        authenticationForm.classList.remove("hide");
        authenticationForm.className = 'show';
        commentForm.className = 'hide';
    } else {
        commentForm.classList.remove("hide");
        logout.classList.remove("hide");
        commentForm.className = 'show';
        logout.className = 'show';
        authenticationForm.className = 'hide';
    }
}


// async await function to add comments to the page DOM 
async
function fetchMessage() {
    const totalComments = document.getElementById('commentNo');
    const value = totalComments.value;
    const response = await fetch('/addComment?totalComments=' + value);
    const message = await response.json();
    const container = document.getElementById('msg');
    container.innerHTML = "";
    message.forEach((comment) => {
        container.appendChild(
            createListElement(comment));
    })
}

// function for creating li element for comment
function createListElement(comment) {

    const liElement = document.createElement('li');
    liElement.className = 'comment';

    const text = document.createElement('span');
    text.innerText = comment.text;

    const userName = document.createElement('span');
    userName.innerText = comment.user;

    const div = document.createElement('div');
    const image = document.createElement('img');
    image.src = comment.image;
    const deleteButton = document.createElement('button');
    deleteButton.innerText = "Delete";
    deleteButton.addEventListener('click', () => {
        // remove comment from datastore
        deleteComment(comment);
    });
    div.appendChild(image);
    liElement.appendChild(text);
    liElement.appendChild(div);
    liElement.appendChild(userName);
    liElement.appendChild(deleteButton);
    return liElement;
}

// function for deleting comment 
function deleteComment(comment) {
    const params = new URLSearchParams();
    params.append('id', comment.id);
    fetch('/deleteComment', {
        method: 'POST',
        body: params
    });
    window.location.href = "/comments.jsp";
}