
// async await function to fetch message 
async function fetchMessage() {
  const response = await fetch('/addComment');
  const message = await response.json();
    console.log(message);
  var container=document.getElementById('msg');
  for(var i=0;i<message.commentList.length;i++){
    container.appendChild(
        createListElement( message.commentList[i]));}
}
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}