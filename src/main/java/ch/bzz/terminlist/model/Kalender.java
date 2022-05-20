package ch.bzz.terminlist.model;

import java.util.List;

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

    /**
     * gets termine
     *
     * @return value of termine
     */
    public List<Termin> getTermine() {
        return termine;
    }

    /**
     * gets termine
     *
     * @param termine the value to set
     */
    public void setTermine(List<Termin> termine) {
        this.termine = termine;
    }

    private List<Termin> termine;


}
