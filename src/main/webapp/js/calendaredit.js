/**
 * view-controller for calendaredit.html
 * @author Ikenna Ogbueri
 */

const userRole = getCookie("userRole");

document.addEventListener("DOMContentLoaded", () => {
    readCalendars();
    readCalendar();
    showNav(userRole);

    document.getElementById("calendareditForm").addEventListener("submit", saveCalendar);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of an calendar
 */
function saveCalendar(calendar) {
    calendar.preventDefault();

    const calendarForm = document.getElementById("calendareditForm");
    const formData = new FormData(calendarForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/calendar/";
    const calendarID = getQueryParam("id");
    if (calendarID == null) {
        method = "POST";
        url += "create";
    } else {
        method = "PUT";
        url += "update";
    }

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
            } else {
                window.location.href = "./calendarlist.html";
                return response;
            }
        })
        .then()
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads a calendar
 */
function readCalendar() {
    const calendarID = getQueryParam("id");
    fetch("./resource/calendar/read?id=" + calendarID)
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showCalendar(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of an calendar
 * @param data  the calendar-data
 */
function showCalendar(data) {
    document.getElementById("calendarID").value = data.calendarID;
    document.getElementById("calendarName").value = data.calendarName;
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
        });
}

/**
 * redirects to the calendarlist
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./calendarlist.html";
}
