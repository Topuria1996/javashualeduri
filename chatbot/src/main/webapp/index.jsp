<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ChatBot</title>
</head>
<body>

<textarea id="messageTextArea" rows="10" cols="50"> </textarea>
<br>

<form>
    <input id="textMessage" type="text">
    <input onclick="sendMessage();" value="Send Message" type="button">
</form>


<script>
    var webSocket = new WebSocket("ws://localhost:8081/chatbot_war_exploded/chatbot");
    var messageTextArea = document.getElementById("messageTextArea");
    webSocket.onopen = function (message) {
        processOpen(message);
    };
    webSocket.onmessage = function (message) {
        processMessage(message);
    };
    webSocket.onclose = function (message) {
        processClose(message);
    };
    webSocket.onerror = function (message) {
        processError(message);
    };
    function processOpen(message) {
        messageTextArea.value += "The connection is established .. "+"\n";
    }
    function processMessage(message) {
        messageTextArea.value += "Server : " + message.data +"\n";
    }
    function sendMessage() {
        webSocket.send(textMessage.value);
        messageTextArea.value += "Client :  " + textMessage.value+"\n";
    }
    function processClose(message) {
        webSocket.send("Client disconnected ... ")
        messageTextArea.value += "Server Disconnect ... ";
    }
    function processError(message) {
        messageTextArea.value += "error ... ";
    }
</script>


</body>
</html>