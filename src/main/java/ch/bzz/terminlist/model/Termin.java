package ch.bzz.terminlist.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Termin {
    private String terminUUID;
    private String titel;
    private Boolean ganzTaegig;
    private String beschreibung;
    private LocalDateTime startDatumZeit;
    private LocalDateTime endDatumZeit;


    /**
     * gets terminUUID
     *
     * @return value of terminUUID
     */
    public String getTerminUUID() {
        return terminUUID;
    }

    /**
     * gets terminUUID
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
     * gets titel
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
     * gets ganzTaegig
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
     * gets beschreibung
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
     * gets startDatumZeit
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
     * gets endDatumZeit
     *
     * @param endDatumZeit the value to set
     */
    public void setEndDatumZeit(LocalDateTime endDatumZeit) {
        this.endDatumZeit = endDatumZeit;
    }
}
