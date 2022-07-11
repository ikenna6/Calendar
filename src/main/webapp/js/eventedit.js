/**
 * view-controller for eventedit.html
 * @author Ikenna Ogbueri
 */
document.addEventListener("DOMContentLoaded", () => {
    readCalendars();
    readEvent();

    document.getElementById("eventeditForm").addEventListener("submit", saveEvent);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of an event
 */
function saveEvent(event) {
    event.preventDefault();

    const eventForm = document.getElementById("eventeditForm");
    const formData = new FormData(eventForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/event/";
    const eventUUID = getQueryParam("uuid");
    if (eventUUID == null) {
        method = "POST";
        url += "create";
    } else {
        method = "PUT";
        url += "update";
    }
    data.set("allDay", document.getElementById('allDay').checked);

    fetch(url,
        {
            method: method,
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: data
        })
        .then(function (response) {
            if (!response.ok) {
                console.log(response);
            } else return response;
        })
        .then()
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads an event
 */
function readEvent() {
    const eventUUID = getQueryParam("uuid");
    fetch("./resource/event/read?uuid=" + eventUUID)
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showEvent(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of an event
 * @param data  the event-data
 */
function showEvent(data) {
    document.getElementById("eventUUID").value = data.eventUUID;
    document.getElementById("title").value = data.title;
    document.getElementById("description").value = data.description;
    document.getElementById("calendar").value = data.calendarUUID;
    document.getElementById("allDay").value = data.allDay;
    document.getElementById("startDateTime").value = data.startDateTime;
    document.getElementById("endDateTime").value = data.endDateTime;
}

/**
 * reads all calendars as an array
 */
function readCalendars() {

    fetch("./resource/calendar/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showCalendars(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all calendars as a dropdown
 * @param data
 */
function showCalendars(data) {
    let dropdown = document.getElementById("calendar");
    data.forEach(calendar => {
        let option = document.createElement("option");
        option.text = calendar.calendarName;
        option.value = calendar.calendarID;
        dropdown.add(option);
    })
}

/**
 * redirects to the calendar
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./calendar.html";
}
