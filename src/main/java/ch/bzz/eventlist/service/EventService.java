package ch.bzz.eventlist.service;

import ch.bzz.eventlist.data.DataHandler;
import ch.bzz.eventlist.model.Event;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * services for reading events
 */
@Path("event")
public class EventService {
    /**
     * reads a list of all events
     * @return  events as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listEvents() {
        List<Event> eventList = DataHandler.readAllEvents();
        return Response
                .status(200)
                .entity(eventList)
                .build();
    }

    /**
     * reads an event identified by the uuid
     * @param eventUUID the key
     * @return event
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readEvent(
            @QueryParam("uuid") String eventUUID
    ) {
        Event event = DataHandler.readEventByUUID(eventUUID);
        return Response
                .status(200)
                .entity(event)
                .build();
    }

    /**
     * inserts a new event
     * @param title
     * @param description
     * @param allDay
     * @param startDateTime
     * @param endDateTime
     * @param calendarID
     * @return Response
     */
    @PUT
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertEvent(
            @FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("allDay") Boolean allDay,
            @FormParam("startDateTime") String startDateTime,
            @FormParam("endDateTime") String endDateTime,
            @FormParam("calendarID") String calendarID
    ) {
        Event event = new Event();
        event.setEventUUID(UUID.randomUUID().toString());
        event.setTitle(title);
        event.setDescription(description);
        event.setAllDay(allDay);
        event.setStartDateTime(LocalDateTime.parse(startDateTime));
        event.setEndDateTime(LocalDateTime.parse(endDateTime));
        event.setCalendarID(calendarID);

        DataHandler.insertEvent(event);

        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a event
     * @param eventUUID
     * @param title
     * @param description
     * @param allDay
     * @param startDateTime
     * @param endDateTime
     * @param calendarID
     * @return Response
     */
    @POST
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateEvent(
            @FormParam("eventUUID") String eventUUID,
            @FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("allDay") Boolean allDay,
            @FormParam("startDateTime") String startDateTime,
            @FormParam("endDateTime") String endDateTime,
            @FormParam("calendarID") String calendarID
    ) {
        Event event = DataHandler.readEventByUUID(eventUUID);
        int httpStatus = 200;
        if (event != null) {
            event.setTitle(title);
            event.setDescription(description);
            event.setAllDay(allDay);
            event.setStartDateTime(LocalDateTime.parse(startDateTime));
            event.setEndDateTime(LocalDateTime.parse(endDateTime));
            event.setCalendarID(calendarID);

            DataHandler.updateEvent();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a event identified by its UUID
     * @param eventUUID
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteEvent(
            @QueryParam("uuid") String eventUUID
    ) {
        int httpStatus = 200;
        if (DataHandler.deleteEvent(eventUUID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}
