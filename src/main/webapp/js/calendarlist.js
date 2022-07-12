/**
 * view-controller for calendarlist.html
 * @author Ikenna Ogbueri
 */

const userRole = getCookie("userRole");

document.addEventListener("DOMContentLoaded", () => {
    readCalendars();
    showNav(userRole);

});

/**
 * reads all calendars
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
            showCalendarlist(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows the calendarlist as a table
 * @param data  the calendar
 */
function showCalendarlist(data) {
    let tBody = document.getElementById("calendarlist");
    data.forEach(calendar => {
        let row = tBody.insertRow(-1);
        row.insertCell(-1).innerHTML = calendar.calendarName;

        let button = document.createElement("button");
        button.innerHTML = "edit ...";
        button.type = "button";
        button.name = "editCalendar";
        button.setAttribute("data-calendarid", calendar.calendarID);
        button.addEventListener("click", editCalendar);
        row.insertCell(-1).appendChild(button);

        button = document.createElement("button");
        button.innerHTML = "Löschen ...";
        button.type = "button";
        button.name = "deleteCalendar";
        button.setAttribute("data-calendarid", calendar.calendarID);
        button.addEventListener("click", deleteCalendar);
        row.insertCell(-1).appendChild(button);
    });
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editCalendar(event) {
    const button = event.target;
    const calendarID = button.getAttribute("data-calendarid");
    window.location.href = "./calendaredit.html?id=" + calendarID;
}

/**
 * deletes a calendar
 * @param event  the click-event
 */
function deleteCalendar(event) {
    const button = event.target;
    const calendarID = button.getAttribute("data-calendarid");

    fetch("./resource/calendar/delete?id=" + calendarID,
        {
            method: "DELETE"
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./calendarlist.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}