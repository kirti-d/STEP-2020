
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

function createListElement(comment) {
  const liElement = document.createElement('li');
  liElement.innerText = comment.text;
  return liElement;
}