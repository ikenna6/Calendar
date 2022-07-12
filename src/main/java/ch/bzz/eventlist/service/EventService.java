package ch.bzz.eventlist.service;

import ch.bzz.eventlist.data.DataHandler;
import ch.bzz.eventlist.model.Event;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
     * reads a list of all eventsoldEvent
     *
     * @return events as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listEvents(
            @CookieParam("userRole") String userRole
    ) {
        List<Event> eventList = DataHandler.readAllEvents();
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
            eventList = null;
        } else {
            httpStatus = 200;
        }
        return Response
                .status(httpStatus)
                .entity(eventList)
                .build();
    }

    /**
     * reads an event identified by the uuid
     *
     * @param eventUUID the key
     * @return event
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readEvent(
            @QueryParam("uuid") String eventUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        Event event = DataHandler.readEventByUUID(eventUUID);
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
            event = null;
        } else if (event == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(event)
                .build();
    }

    /**
     * inserts a new event
     *
     * @param startDateTime
     * @param endDateTime
     * @param calendarID
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertEvent(
            @Valid @BeanParam Event event,
            @NotEmpty
            @Size(min = 16, max = 23)
            @FormParam("startDateTime") String startDateTime,
            @NotEmpty
            @Size(min = 16, max = 23)
            @FormParam("endDateTime") String endDateTime,
            @FormParam("calendarID") String calendarID,
            @CookieParam("userRole") String userRole
    ) {


        int httpStatus = 200;
        if (userRole == null || !userRole.equals("admin")) {
            httpStatus = 403;
        } else {
            event.setEventUUID(UUID.randomUUID().toString());
            event.setStartDateTime(LocalDateTime.parse(startDateTime));
            event.setEndDateTime(LocalDateTime.parse(endDateTime));
            event.setCalendarID(calendarID);
            DataHandler.insertEvent(event);
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates an event
     *
     * @param startDateTime
     * @param event
     * @param endDateTime
     * @param calendarID
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateEvent(
            @Valid @BeanParam Event event,
            @NotEmpty
            @Size(min = 16, max = 23)
            @FormParam("startDateTime") String startDateTime,
            @NotEmpty
            @Size(min = 16, max = 23)
            @FormParam("endDateTime") String endDateTime,
            @FormParam("calendarID") String calendarID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        Event oldEvent = DataHandler.readEventByUUID(event.getEventUUID());
        if (userRole == null || !userRole.equals("admin")) {
            httpStatus = 403;
        } else if (oldEvent != null) {
            oldEvent.setTitle(event.getTitle());
            oldEvent.setDescription(event.getDescription());
            oldEvent.setAllDay(event.getAllDay());
            oldEvent.setStartDateTime(LocalDateTime.parse(startDateTime));
            oldEvent.setEndDateTime(LocalDateTime.parse(endDateTime));
            oldEvent.setCalendarID(calendarID);
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
     *
     * @param eventUUID
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteEvent(
            @QueryParam("uuid") String eventUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || !userRole.equals("admin")) {
            httpStatus = 403;
        } else {
            if (!DataHandler.deleteEvent(eventUUID)) {
                httpStatus = 410;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}
