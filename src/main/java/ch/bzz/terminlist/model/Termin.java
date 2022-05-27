package ch.bzz.terminlist.model;

import ch.bzz.terminlist.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class Termin {
    @JsonIgnore
    private Kalender kalender;

    private String terminUUID;
    private String titel;
    private Boolean ganzTaegig;
    private String beschreibung;
    private LocalDateTime startDatumZeit;
    private LocalDateTime endDatumZeit;

    /**
     * gets the kalenderID from the Kalender-object
     *
     * @return the kalenderID
     */
    public String getKalenderID() {
        if (getKalender() == null) return null;
        return getKalender().getKalenderID();
    }

    /**
     * creates a Kalender-object without the terminlist
     *
     * @param kalenderID the key
     */
    public void setKalenderID(String kalenderID) {
        setKalender(new Kalender());
        Kalender kalender = DataHandler.readKalenderByID(kalenderID);
        getKalender().setKalenderID(kalenderID);
        getKalender().setKalenderName(kalender.getKalenderName());
    }

    /**
     * gets terminUUID
     *
     * @return value of terminUUID
     */
    public String getTerminUUID() {
        return terminUUID;
    }

    /**
     * sets terminUUID
     *
     * @param terminUUID the value to set
     */
    public void setTerminUUID(String terminUUID) {
        this.terminUUID = terminUUID;
    }

    /**
     * gets titel
     *
     * @return value of titel
     */
    public String getTitel() {
        return titel;
    }

    /**
     * sets titel
     *
     * @param titel the value to set
     */
    public void setTitel(String titel) {
        this.titel = titel;
    }

    /**
     * gets ganzTaegig
     *
     * @return value of ganzTaegig
     */
    public Boolean getGanzTaegig() {
        return ganzTaegig;
    }

    /**
     * sets ganzTaegig
     *
     * @param ganzTaegig the value to set
     */
    public void setGanzTaegig(Boolean ganzTaegig) {
        this.ganzTaegig = ganzTaegig;
    }

    /**
     * gets beschreibung
     *
     * @return value of beschreibung
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * sets beschreibung
     *
     * @param beschreibung the value to set
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * gets startDatumZeit
     *
     * @return value of startDatumZeit
     */
    public LocalDateTime getStartDatumZeit() {
        return startDatumZeit;
    }

    /**
     * sets startDatumZeit
     *
     * @param startDatumZeit the value to set
     */
    public void setStartDatumZeit(LocalDateTime startDatumZeit) {
        this.startDatumZeit = startDatumZeit;
    }

    /**
     * gets endDatumZeit
     *
     * @return value of endDatumZeit
     */
    public LocalDateTime getEndDatumZeit() {
        return endDatumZeit;
    }

    /**
     * sets endDatumZeit
     *
     * @param endDatumZeit the value to set
     */
    public void setEndDatumZeit(LocalDateTime endDatumZeit) {
        this.endDatumZeit = endDatumZeit;
    }

    /**
     * gets kalender
     *
     * @return value of kalender
     */
    public Kalender getKalender() {
        return kalender;
    }

    /**
     * sets kalender
     *
     * @param kalender the value to set
     */
    public void setKalender(Kalender kalender) {
        this.kalender = kalender;
    }
}
