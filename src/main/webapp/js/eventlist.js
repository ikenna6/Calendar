/**
 * view-controller for eventlist.html
 * @author Ikenna Ogbueri
 */
const userRole = getCookie("userRole");

document.addEventListener("DOMContentLoaded", () => {
    readEvents();
    showNav(userRole);
});

/**
 * reads all events
 */
function readEvents() {
    fetch("./resource/event/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showEventlist(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows the eventlist as a table
 * @param data  the event
 */
function showEventlist(data) {
    let tBody = document.getElementById("eventlist");
    data.forEach(event => {
        let row = tBody.insertRow(-1);
        row.insertCell(-1).innerHTML = event.calendar.calendarName;
        row.insertCell(-1).innerHTML = event.title;
        row.insertCell(-1).innerHTML = event.description;
        row.insertCell(-1).innerHTML = event.allDay;
        row.insertCell(-1).innerHTML = event.startDateTime;
        row.insertCell(-1).innerHTML = event.endDateTime;

        let button = document.createElement("button");
        button.innerHTML = "edit ...";
        button.type = "button";
        button.name = "editEvent";
        button.setAttribute("data-eventuuid", event.eventUUID);
        button.addEventListener("click", editEvent);
        row.insertCell(-1).appendChild(button);

        button = document.createElement("button");
        button.innerHTML = "Löschen ...";
        button.type = "button";
        button.name = "deleteEvent";
        button.setAttribute("data-eventuuid", event.eventUUID);
        button.addEventListener("click", deleteEvent);
        row.insertCell(-1).appendChild(button);
    });
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editEvent(event) {
    const button = event.target;
    const eventUUID = button.getAttribute("data-eventuuid");
    window.location.href = "./eventedit.html?uuid=" + eventUUID;
}

/**
 * deletes a event
 * @param event  the click-event
 */
function deleteEvent(event) {
    const button = event.target;
    const eventUUID = button.getAttribute("data-eventuuid");

    fetch("./resource/event/delete?uuid=" + eventUUID,
        {
            method: "DELETE"
        })
        .then(function (response) {
            if (response.ok ) {
                window.location.href = "./eventlist.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}