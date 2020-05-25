
// async await function to add comments to the page DOM 
async function fetchMessage() {
  const response = await fetch('/addComment');
  const message = await response.json();
  const container=document.getElementById('msg');
  console.log(message);
  message.forEach((comment)=>{
    container.appendChild(
    createListElement(comment));
  })
}
function createListElement(comment) {
  const liElement = document.createElement('li');
  liElement.innerText = comment.text;
  return liElement;
}