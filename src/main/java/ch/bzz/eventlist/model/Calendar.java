package ch.bzz.eventlist.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Calendar {
    @FormParam("calendarID")
    //@Pattern(regexp = "/[a-zA-Z0-9]-[0-9]{13}/g")
    private String calendarID;

    @FormParam("calendarName")
    @NotEmpty
    @Size(min = 3, max = 40)
    private String calendarName;

    /**
     * gets calendarID
     *
     * @return value of calendarID
     */
    public String getCalendarID() {
        return calendarID;
    }

    /**
     * gets calendarID
     *
     * @param calendarID the value to set
     */
    public void setCalendarID(String calendarID) {
        this.calendarID = calendarID;
    }

    /**
     * gets calendarName
     *
     * @return value of calendarName
     */
    public String getCalendarName() {
        return calendarName;
    }

    /**
     * gets calendarName
     *
     * @param calendarName the value to set
     */
    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    /**
     * creates a calendar ID consisting of @param calendarName and current timestamp
     * in between those two values there is a "-"
     * @param calendarName
     * @return calenderID
     */
    public static String generateCalendarID(String calendarName) {
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        return calendarName + "-" + timestamp.getTime();
    }
}
