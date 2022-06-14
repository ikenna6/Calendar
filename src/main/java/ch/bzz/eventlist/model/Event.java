package ch.bzz.eventlist.model;

import ch.bzz.eventlist.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.time.LocalDateTime;

public class Event {
    @JsonIgnore
    private Calendar calendar;

    @FormParam("eventUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String eventUUID;

    @FormParam("title")
    @NotEmpty
    @Size(min=3, max=40)
    private String title;

    @FormParam("description")
    @NotEmpty
    @Size(min=3, max=200)
    private String description;

    @FormParam("allDay")
    private Boolean allDay;

    @Future
    private LocalDateTime startDateTime;

    @Future
    private LocalDateTime endDateTime;

    /**
     * gets the calendarID from the Calendar-object
     *
     * @return the calendarID
     */
    public String getCalendarID() {
        if (getCalendar() == null) return null;
        return getCalendar().getCalendarID();
    }

    /**
     * creates a Calendar-object without the eventlist
     *
     * @param calendarID the key
     */
    public void setCalendarID(String calendarID) {
        setCalendar(new Calendar());
        Calendar calendar = DataHandler.readCalendarByID(calendarID);
        getCalendar().setCalendarID(calendarID);
        getCalendar().setCalendarName(calendar.getCalendarName());
    }

    /**
     * gets calendar
     *
     * @return value of calendar
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * sets calendar
     *
     * @param calendar the value to set
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * gets eventUUID
     *
     * @return value of eventUUID
     */
    public String getEventUUID() {
        return eventUUID;
    }

    /**
     * sets eventUUID
     *
     * @param eventUUID the value to set
     */
    public void setEventUUID(String eventUUID) {
        this.eventUUID = eventUUID;
    }

    /**
     * gets title
     *
     * @return value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets title
     *
     * @param title the value to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * gets allDay
     *
     * @return value of allDay
     */
    public Boolean getAllDay() {
        return allDay;
    }

    /**
     * sets allDay
     *
     * @param allDay the value to set
     */
    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    /**
     * gets description
     *
     * @return value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets description
     *
     * @param description the value to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * gets startDateTime
     *
     * @return value of startDateTime
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * sets startDateTime
     *
     * @param startDateTime the value to set
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * gets endDateTime
     *
     * @return value of endDateTime
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * sets endDateTime
     *
     * @param endDateTime the value to set
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
