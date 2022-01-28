console.log("Home");

let websocket;
createConnection();

async function loadName() {
    return await fetch("/get-name")
        .then(async function (response) {
            return await response.json();
        })
        .then(function (text) {
            return text["name"];
        });
}

async function addMessage(msg) {
    let ct;
    let name = await loadName();
    if(msg.content.includes("has joined the chat") || msg.content.includes("left the chat")) {
        ct = '<div class="box">' +
            '<strong><span>' + msg.name + " " + msg.content + '</span></strong>' +
            '<span class="time-right">' + msg.msg_date + '</span>' +
            '</div>';
    }
    else {
        ct = '<div class="box">' +
            '<strong><p style="float: right"><span>' + msg.name + '</strong></p>' +
            '<p>' + msg.content + '</p>' +
            '<span style="float: right">' + msg.msg_date + '</span>' +
            '</div>';
    }
    document.getElementById("chat").innerHTML += ct;
}

function getDate(date = new Date()) {
    let day = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();
    let hours = date.getHours();
    let minutes = date.getMinutes();

    if(day < 10) day = '0' + day;
    if(month < 10) month = '0' + month;
    if(hours < 10) hours = '0' + hours;
    if(minutes < 10) minutes = '0' + minutes;

    return  day + "-" + month + "-" + year + " " + hours + ":" + minutes;
}

async function createConnection() {
    if(typeof websocket == 'undefined') {
        await loadMessages();
        let name = await loadName();
        websocket = new WebSocket("ws://192.168.1.7:8080/chat/" + name);
        websocket.onopen = function (message) {
            processOpen(message);
        };
        websocket.onmessage = function (message) {
            processMessage(message);
        };
        websocket.onclose = function (message) {
            processClose(message);
        };
        websocket.onerror = function (message) {
            processError(message);
        };

        function processOpen(message) {
            console.log("Server connect... \n");
        }

        function processMessage(message) {
            let msg = {
                name: message.data.split(":")[0],
                msg_date: getDate(),
                content: message.data.split(":")[1]
            }
            addMessage(msg);
        }

        function processClose(message) {
            console.log(message);
        }

        function processError(message) {
            console.log(message);
        }
    }
}


async function loadMessages() {
    return await fetch("/get-messages")
        .then(async function (response) {
            return await response.json();
        })
        .then(function (msgs) {
            for(let i in msgs) {
                msgs[i].msg_date = getDate(new Date(msgs[i].msg_date));
                addMessage(msgs[i]);
            }
        });
}

function sendMessage(text) {
    if (typeof websocket != 'undefined' && websocket.readyState == WebSocket.OPEN) {
        websocket.send(text);
    }
}

function saveMessage(text, name) {
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/save-message", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify({
        name: name,
        content: text,
        msg_date: Date.now().toString()
    }));
}

$(function () {
    $("#sendBtn").bind('click', async function () {
        let text = document.getElementById("msg").value;
        // Send msg to server
        sendMessage(text);
    });
});

$(function () {
    $("#logout").bind('click', function () {
        if (websocket.readyState == WebSocket.OPEN) {
            websocket.close();
        }
    });
});
