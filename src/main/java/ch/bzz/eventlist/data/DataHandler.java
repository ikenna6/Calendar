package ch.bzz.eventlist.data;

import ch.bzz.eventlist.model.Calendar;
import ch.bzz.eventlist.model.Event;
import ch.bzz.eventlist.model.User;
import ch.bzz.eventlist.service.Config;
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
    private static List<Event> eventList;
    private static List<Calendar> calendarList;
    private static List<User> userList;


    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
    }

    /**
     * reads all events
     *
     * @return list of events
     */
    public static List<Event> readAllEvents() {
        return getEventList();
    }

    /**
     * reads a event by its uuid
     *
     * @param eventUUID
     * @return the Event (null=not found)
     */
    public static Event readEventByUUID(String eventUUID) {
        Event event = null;
        for (Event entry : getEventList()) {
            if (entry.getEventUUID().equals(eventUUID)) {
                event = entry;
            }
        }
        return event;
    }

    /**
     * inserts a new event into the eventList
     *
     * @param event the event to be saved
     */
    public static void insertEvent(Event event) {
        getEventList().add(event);
        writeEventJSON();
    }

    /**
     * updates the eventList
     */
    public static void updateEvent() {
        writeEventJSON();
    }

    /**
     * reads a user by the username/password provided
     *
     * @param username
     * @param password
     * @return user-object
     */
    public static User readUser(String username, String password) {
        User user = new User();
        for (User entry : getUserList()) {
            if (entry.getUsername().equals(username) &&
                    entry.getPassword().equals(password)) {
                user = entry;
            }
        }
        return user;
    }

    /**
     * gets userList
     *
     * @return value of userList
     */

    public static List<User> getUserList() {
        if (DataHandler.userList == null) {
            DataHandler.setUserList(new ArrayList<>());
            readUserJSON();
        }
        return userList;
    }

    /**
     * deletes a event identified by the eventUUID
     *
     * @param eventUUID the key
     * @return success=true/false
     */
    public static boolean deleteEvent(String eventUUID) {
        Event event = readEventByUUID(eventUUID);
        if (event != null) {
            getEventList().remove(event);
            writeEventJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all calendar
     *
     * @return list of events
     */
    public static List<Calendar> readAllCalendar() {
        return getCalendarList();
    }

    /**
     * reads a calendar by its uuid
     *
     * @param calendarID
     * @return the Calendar (null=not found)
     */
    public static Calendar readCalendarByID(String calendarID) {
        Calendar calendar = null;
        for (Calendar entry : getCalendarList()) {
            if (entry.getCalendarID().equals(calendarID)) {
                calendar = entry;
            }
        }
        return calendar;
    }

    /**
     * inserts a new calendar into the eventList
     *
     * @param calendar the calendar to be saved
     */
    public static void insertCalendar(Calendar calendar) {
        getCalendarList().add(calendar);
        writeCalendarJSON();
    }

    /**
     * updates the calendarList
     */
    public static void updateCalendar() {
        writeCalendarJSON();
    }

    /**
     * deletes a calendar identified by the calendarID
     *
     * @param calendarID the key
     * @return success=true/false
     */
    public static boolean deleteCalendar(String calendarID) {
        Calendar calendar = readCalendarByID(calendarID);
        if (calendar != null) {
            getCalendarList().remove(calendar);
            writeCalendarJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads the event from the JSON-file
     */
    private static void readEventJSON() {
        try {
            String path = Config.getProperty("eventJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            Event[] events = objectMapper.readValue(jsonData, Event[].class);
            for (Event event : events) {
                getEventList().add(event);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the eventList to the JSON-file
     */
    private static void writeEventJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String eventPath = Config.getProperty("eventJSON");
        try {
            fileOutputStream = new FileOutputStream(eventPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getEventList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the calendar from the JSON-file
     */
    private static void readCalendarJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("calendarJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            Calendar[] calendar = objectMapper.readValue(jsonData, Calendar[].class);
            for (Calendar oneCalendar : calendar) {
                getCalendarList().add(oneCalendar);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the calendarList to the JSON-file
     */
    private static void writeCalendarJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String calendarPath = Config.getProperty("calendarJSON");
        try {
            fileOutputStream = new FileOutputStream(calendarPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getCalendarList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the users from the JSON-file
     */
    private static void readUserJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("userJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            User[] users = objectMapper.readValue(jsonData, User[].class);
            for (User user : users) {
                getUserList().add(user);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * gets eventList
     *
     * @return value of eventList
     */

    private static List<Event> getEventList() {

        if (eventList == null) {
            setEventList(new ArrayList<>());
            readEventJSON();
        }
        return eventList;
    }

    /**
     * sets eventList
     *
     * @param eventList the value to set
     */

    private static void setEventList(List<Event> eventList) {
        DataHandler.eventList = eventList;
    }

    /**
     * gets calendarList
     *
     * @return value of calendarList
     */

    private static List<Calendar> getCalendarList() {
        if (calendarList == null) {
            setCalendarList(new ArrayList<>());
            readCalendarJSON();
        }

        return calendarList;
    }

    /**
     * sets calendarList
     *
     * @param calendarList the value to set
     */

    private static void setCalendarList(List<Calendar> calendarList) {
        DataHandler.calendarList = calendarList;
    }

    /**
     * sets userList
     *
     * @param userList the value to set
     */

    public static void setUserList(List<User> userList) {
        DataHandler.userList = userList;
    }

}
