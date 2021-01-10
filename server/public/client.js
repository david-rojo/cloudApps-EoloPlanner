let socket = new WebSocket("ws://" + window.location.host + "/notifications");
const restServerUrl = "http://localhost:3000/api/eoloplants";

socket.onopen = function (e) {
    console.log("WebSocket connection established");
};

socket.onmessage = function (event) {
    console.log(`[message] Data received from server: ${event.data}`);
    const data = JSON.parse(event.data);
    updatePlantProgress(data);
};

socket.onclose = function (event) {
    if (event.wasClean) {
        console.log(`[close] Connection closed cleanly, code=${event.code} reason=${event.reason}`);
    } else {
        console.log('[close] Connection died');
    }
};

socket.onerror = function (error) {
    console.log(`[error] ${error.message}`);
};

function create() { 
    let city = document.getElementById("city").value;
    if (city == "") {
        alert("Write a city please");
    }
    else {
        alert("city is " + city);
        let request = {"city": city};
        fetch(restServerUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(request)
        })
        .then(function(response) {
            console.log('response =', response);
            return response.json();
        })
        .then(function(data) {
            console.log('data = ', data);
            document.getElementById("createButton").enabled(false);
            updatePlantProgress(data);
        })
        .catch(function(err) {
            console.error(err);
        });
    }

}

function updatePlantProgress(data) {
    
    let progressLabel = document.getElementById("progress");

    if (data.progress === 100) {
        progressLabel.innerHTML = "";
        document.getElementById("createButton").enabled(true);
        //include plant in the created list
    } else {
        progressLabel.innerText = `Eolo Plant for ${plant.city} city --> Progress:  ${plant.progress}%`;
    }
}