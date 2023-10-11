const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});


var sessionId = "";
var userId = "";

stompClient.onConnect = (frame) => {


    setConnected(true);

    stompClient.subscribe('/user/topic/register', (response) => {
        console.log(response)
});


    stompClient.subscribe('/user/topic/errors', (response) => {
        console.log(response.body)
    });
    stompClient.subscribe('/user/topic/chat', (greeting) => {
        showGreeting(JSON.parse(greeting.body));
    });

    registerUserToRoom();

    console.log('Connected: ' + frame);


};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {

    userId = $("#userid").val();
    const actualBrokerURL = 'ws://localhost:8080/ws?userId=' + userId;
    stompClient.brokerURL = actualBrokerURL;
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}
function registerUserToRoom() {
    var json = {};
    json.room = $("#room").val()
    stompClient.publish({
        destination: '/app/register',
        body: JSON.stringify(json),
    })
}
function sendName() {
    var json = {};
    json.content = $("#content").val()

    stompClient.publish({
        destination: "/app/chat",
        body: JSON.stringify(json),
    });
}

function showGreeting(message) {
    var formattedMessage = `
    <tr>
        <td>
            <div class="message-container">
                <span class="message-username">${message.senderId == userId ? 'You' : 'Other User'}</span><br>
                <span class="message-label">Content:</span> <span class="message-content">${message.content}</span><br>
                <span class="message-label">Sender:</span> <span class="message-content">${message.roomId}</span><br>
                <span class="message-label">Room:</span> <span class="message-content">${message.senderId}</span>
            </div>
        </td>
    </tr>
`;
    $("#greetings").append(formattedMessage);
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName());
});