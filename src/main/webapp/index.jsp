<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8>
<title>Tomcat WebSocket Chat</title>
<script>
var ws = new WebSocket("ws://localhost:8080/chat/message");
ws.onopen = function(){
};
ws.onmessage = function(message){
document.getElementById("chatlog").textContent += message.data + "\n";
};
function postToServer(){
ws.send(document.getElementById("msg").value);
document.getElementById("msg").value = "";
}
function closeConnect(){
ws.close();
}
</script>
</head>
<body>
<textarea id="chatlog" readonly style="height:500px" ></textarea><br/>
<input id="msg" type="text" />
<button type="submit" id="sendButton" onClick="postToServer()">Send!</button>
<button type="submit" id="sendButton" onClick="closeConnect()">End</button>
</body>
</html>
