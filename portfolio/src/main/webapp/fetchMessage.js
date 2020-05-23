// async await function to fetch message 
async function fetchMessage() {
  const response = await fetch('/fetchMessage');
  const message = await response.text();
  document.getElementById('msg').innerText = message;
}