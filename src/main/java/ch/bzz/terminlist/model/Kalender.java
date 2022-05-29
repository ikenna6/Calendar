package ch.bzz.terminlist.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Kalender {
    private String kalenderID;
    private String kalenderName;

    /**
     * gets kalenderID
     *
     * @return value of kalenderID
     */
    public String getKalenderID() {
        return kalenderID;
    }

    /**
     * gets kalenderID
     *
     * @param kalenderID the value to set
     */
    public void setKalenderID(String kalenderID) {
        this.kalenderID = kalenderID;
    }

    /**
     * gets kalenderName
     *
     * @return value of kalenderName
     */
    public String getKalenderName() {
        return kalenderName;
    }

    /**
     * gets kalenderName
     *
     * @param kalenderName the value to set
     */
    public void setKalenderName(String kalenderName) {
        this.kalenderName = kalenderName;
    }


    public static String generatekalenderID(String kalenderName) {
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        return kalenderName + "-" + timestamp.getTime();
    }
}
