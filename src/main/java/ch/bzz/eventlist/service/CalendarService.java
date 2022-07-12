package ch.bzz.eventlist.service;

import ch.bzz.eventlist.data.DataHandler;
import ch.bzz.eventlist.model.Calendar;

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
     *
     * @return calendars as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCalendar(
            @CookieParam("userRole") String userRole
    ) {
        List<Calendar> calendarList = DataHandler.readAllCalendar();
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
            calendarList = null;
        } else {
            httpStatus = 200;
        }
        return Response
                .status(httpStatus)
                .entity(calendarList)
                .build();
    }

    /**
     * reads a calendars identified by the uuid
     *
     * @param calendarID the key
     * @return calendar
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readCalendar(
            @QueryParam("id") String calendarID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        Calendar calendar = DataHandler.readCalendarByID(calendarID);
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
            calendar = null;
        } else if (calendar == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(calendar)
                .build();
    }

    /**
     * creates a calendar with it params
     *
     * @param calendar
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertCalendar(
            @Valid @BeanParam Calendar calendar,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            calendar.setCalendarID(calendar.generateCalendarID(calendar.getCalendarName()));
            DataHandler.insertCalendar(calendar);
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates a calendar by its ID
     *
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateCalendar(
            @Valid @BeanParam Calendar calendar,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        Calendar oldCalendar = DataHandler.readCalendarByID(calendar.getCalendarID());
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else if (oldCalendar != null) {
            oldCalendar.setCalendarID(calendar.generateCalendarID(calendar.getCalendarName()));
            oldCalendar.setCalendarName(calendar.getCalendarName());
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
            @QueryParam("id") String calendarID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            if (!DataHandler.deleteCalendar(calendarID)) {
                httpStatus = 410;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}
