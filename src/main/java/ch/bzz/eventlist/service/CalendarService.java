package ch.bzz.eventlist.service;

import ch.bzz.eventlist.data.DataHandler;
import ch.bzz.eventlist.model.Calendar;
import ch.bzz.eventlist.model.Event;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * services for reading calendar
 */
@Path("calendar")
public class CalendarService {
    /**
     * reads a list of all calendars
     * @return calendars as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCalendar() {
        List<Calendar> calendarList = DataHandler.readAllCalendar();
        return Response
                .status(200)
                .entity(calendarList)
                .build();
    }

    /**
     * reads a calendars identified by the uuid
     * @param calendarID  the key
     * @return calendar
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readCalendar(
            @QueryParam("id") String calendarID
    ) {
        Calendar calendar = DataHandler.readCalendarByID(calendarID);
        return Response
                .status(200)
                .entity(calendar)
                .build();
    }

    /**
     * creates a calendar with it params
     *
     * @return Response
     */
    @PUT
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertCalendar(
            @Valid @BeanParam Calendar calendar
    ) {
        calendar.setCalendarID(calendar.generateCalendarID(calendar.getCalendarName()));

        DataHandler.insertCalendar(calendar);

        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a calendar by its ID
     *
     * @param calendarID
     * @return Response
     */
    @POST
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateCalendar(
            @Valid @BeanParam Calendar calendar,
            @FormParam("calendarID") String calendarID
    ) {
        Calendar oldCalendar = DataHandler.readCalendarByID(calendarID);
        int httpStatus = 200;
        if (oldCalendar != null) {
            oldCalendar.setCalendarName(calendar.getCalendarName());
            oldCalendar.setCalendarID(oldCalendar.generateCalendarID(calendar.getCalendarName()));

            DataHandler.updateCalendar();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a calendar by its ID
     *
     * @param calendarID
     * @return
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCalendar(
            @QueryParam("id") String calendarID
    ) {
        int httpStatus = 200;
        if (DataHandler.deleteCalendar(calendarID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}
