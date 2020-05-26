
// async await function to add comments to the page DOM 
async function fetchMessage() {
    var totalComments=document.getElementById('commentNo');
    var value=totalComments.value;
  const response = await fetch('/addComment?totalComments='+value);
  const message = await response.json();
    totalComments.value=value;
    console.log(value);
  const container=document.getElementById('msg');
  container.innerHTML="";
  message.forEach((comment)=>{
    container.appendChild(
    createListElement(comment));
  })
}

// function for creating li element for comment
function createListElement(comment) {

    const liElement = document.createElement('li');

    const text=document.createElement('span');
    text.innerText = comment.text;

    const deleteButton=document.createElement('button');
    deleteButton.addEventListener('click', () => {
        // remove comment from datastore
        deleteComment(comment);
        // Remove the comment from the DOM.
        liElement.remove();
    });

    liElement.appendChild(text);
    liElement.appendChild(deleteButton);
    return liElement;
}

// function for deleting comment 
function deleteComment(comment) {
  const params = new URLSearchParams();
  params.append('id', comment.id);
  fetch('/deleteComment', {method: 'POST', body: params});
}