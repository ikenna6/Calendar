package ch.bzz.eventlist.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Calendar {
    private String calendarID;
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


    public static String generateCalendarID(String calendarName) {
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        return calendarName + "-" + timestamp.getTime();
    }
}
