
// async await function to fetch message 
async function fetchMessage() {
  const response = await fetch('/fetchMessage');
  const message = await response.json();
  var container=document.getElementById('msg');
  container.innerHTML = '';
    container.appendChild(
        createListElement('Message 1: ' + message.commentList[0]));
    container.appendChild(
        createListElement('Message 2: ' + message.commentList[1]));
    container.appendChild(
        createListElement('Message 3: ' + message.commentList[2]));
}
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}