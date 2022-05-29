package ch.bzz.terminlist.data;

import ch.bzz.terminlist.model.Kalender;
import ch.bzz.terminlist.model.Termin;
import ch.bzz.terminlist.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */

public final class DataHandler {
    private static DataHandler instance;
    private static List<Termin> terminList;
    private static List<Kalender> kalenderList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
    }

    /**
     * reads all termine
     *
     * @return list of termine
     */
    public static List<Termin> readAllTermine() {
        return getTerminList();
    }

    /**
     * reads a termin by its uuid
     *
     * @param terminUUID
     * @return the Termin (null=not found)
     */
    public static Termin readTerminByUUID(String terminUUID) {
        Termin termin = null;
        for (Termin entry : getTerminList()) {
            if (entry.getTerminUUID().equals(terminUUID)) {
                termin = entry;
            }
        }
        return termin;
    }

    /**
     * inserts a new termin into the terminList
     *
     * @param termin the termin to be saved
     */
    public static void insertTermin(Termin termin) {
        getTerminList().add(termin);
        writeTerminJSON();
    }

    /**
     * updates the terminList
     */
    public static void updateTermin() {
        writeTerminJSON();
    }

    /**
     * deletes a termin identified by the terminUUID
     *
     * @param terminUUID the key
     * @return success=true/false
     */
    public static boolean deleteTermin(String terminUUID) {
        Termin termin = readTerminByUUID(terminUUID);
        if (termin != null) {
            getTerminList().remove(termin);
            writeTerminJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all kalender
     *
     * @return list of termine
     */
    public static List<Kalender> readAllKalender() {
        return getKalenderList();
    }

    /**
     * reads a kalender by its uuid
     *
     * @param kalenderID
     * @return the Kalender (null=not found)
     */
    public static Kalender readKalenderByID(String kalenderID) {
        Kalender kalender = null;
        for (Kalender entry : getKalenderList()) {
            if (entry.getKalenderID().equals(kalenderID)) {
                kalender = entry;
            }
        }
        return kalender;
    }

    /**
     * inserts a new kalender into the terminList
     *
     * @param kalender the kalender to be saved
     */
    public static void insertKalender(Kalender kalender) {
        getKalenderList().add(kalender);
        writeKalenderJSON();
    }

    /**
     * updates the kalenderList
     */
    public static void updateKalender() {
        writeKalenderJSON();
    }

    /**
     * deletes a kalender identified by the kalenderID
     *
     * @param kalenderID the key
     * @return success=true/false
     */
    public static boolean deleteKalender(String kalenderID) {
        Kalender kalender = readKalenderByID(kalenderID);
        if (kalender != null) {
            getKalenderList().remove(kalender);
            writeKalenderJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads the termine from the JSON-file
     */
    private static void readTerminJSON() {
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
     * writes the terminList to the JSON-file
     */
    private static void writeTerminJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String terminPath = Config.getProperty("terminJSON");
        try {
            fileOutputStream = new FileOutputStream(terminPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getTerminList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the kalender from the JSON-file
     */
    private static void readKalenderJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("kalenderJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            Kalender[] kalender = objectMapper.readValue(jsonData, Kalender[].class);
            for (Kalender oneKalender : kalender) {
                getKalenderList().add(oneKalender);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the kalenderList to the JSON-file
     */
    private static void writeKalenderJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String kalenderPath = Config.getProperty("kalenderJSON");
        try {
            fileOutputStream = new FileOutputStream(kalenderPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getKalenderList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * gets terminList
     *
     * @return value of terminList
     */

    private static List<Termin> getTerminList() {

        if (terminList == null) {
            setTerminList(new ArrayList<>());
            readTerminJSON();
        }
        return terminList;
    }

    /**
     * sets terminList
     *
     * @param terminList the value to set
     */

    private static void setTerminList(List<Termin> terminList) {
        DataHandler.terminList = terminList;
    }

    /**
     * gets kalenderList
     *
     * @return value of kalenderList
     */

    private static List<Kalender> getKalenderList() {
        if (kalenderList == null) {
            setKalenderList(new ArrayList<>());
            readKalenderJSON();
        }

        return kalenderList;
    }

    /**
     * sets kalenderList
     *
     * @param kalenderList the value to set
     */

    private static void setKalenderList(List<Kalender> kalenderList) {
        DataHandler.kalenderList = kalenderList;
    }


}
