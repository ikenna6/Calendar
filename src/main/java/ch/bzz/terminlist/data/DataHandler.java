package ch.bzz.terminlist.data;

import ch.bzz.terminlist.model.Kalender;
import ch.bzz.terminlist.model.Termin;
import ch.bzz.terminlist.service.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Termin> terminList;
    private List<Kalender> kalenderList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setKalenderList(new ArrayList<>());
        readKalenderJSON();
        setTerminList(new ArrayList<>());
        readTerminJSON();
    }

    /**
     * gets the only instance of this class
     *
     * @return
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }

    /**
     * reads all termine
     *
     * @return list of termine
     */

    public List<Termin> readAllTermine() {
        return getTerminList();
    }

    /**
     * reads a termin by its uuid
     *
     * @param terminUUID
     * @return the Termin (null=not found)
     */
    public Termin readTerminByUUID(String terminUUID) {
        Termin termin = null;
        for (Termin entry : getTerminList()) {
            if (entry.getTerminUUID().equals(terminUUID)) {
                termin = entry;
            }
        }
        return termin;
    }

    /**
     * reads all Kalender
     *
     * @return list of kalender
     */
    public List<Kalender> readAllKalender() {

        return getKalenderList();
    }

    /**
     * reads a kalender by its uuid
     *
     * @param kalenderID
     * @return the kalender (null=not found)
     */
    public Kalender readKalendererByUUID(String kalenderID) {
        Kalender kalender = null;
        for (Kalender entry : getKalenderList()) {
            if (entry.getKalenderID().equals(kalenderID)) {
                kalender = entry;
            }
        }
        return kalender;
    }

    /**
     * reads the termine from the JSON-file
     */
    private void readTerminJSON() {
        try {
            String path = Config.getProperty("terminJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)

            );
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            Termin[] termine = objectMapper.readValue(jsonData, Termin[].class);
            for (Termin termin : termine) {
                getTerminList().add(termin);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the kalender from the JSON-file
     */
    private void readKalenderJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("kalenderJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Kalender[] kalenders = objectMapper.readValue(jsonData, Kalender[].class);
            for (Kalender kalender : kalenders) {
                getKalenderList().add(kalender);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * gets terminList
     *
     * @return value of terminList
     */
    private List<Termin> getTerminList() {
        return terminList;
    }

    /**
     * ssetets terminList
     *
     * @param terminList the value to set
     */
    public void setTerminList(List<Termin> terminList) {
        this.terminList = terminList;
    }

    /**
     * gets kalenderList
     *
     * @return value of kalenderList
     */
    public List<Kalender> getKalenderList() {
        return kalenderList;
    }

    /**
     * sets kalenderList
     *
     * @param kalenderList the value to set
     */
    public void setKalenderList(List<Kalender> kalenderList) {
        this.kalenderList = kalenderList;
    }
}
