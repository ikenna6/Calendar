package ch.bzz.terminlist.service;

import ch.bzz.terminlist.data.DataHandler;
import ch.bzz.terminlist.model.Kalender;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * services for reading kalender
 */
@Path("kalender")
public class KalenderService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listkalender() {
        List<Kalender> kalenderList = DataHandler.readAllKalender();
        return Response
                .status(200)
                .entity(kalenderList)
                .build();
    }

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readKalender(
            @QueryParam("id") String kalenderID
    ) {
        Kalender kalender = DataHandler.readKalenderByID(kalenderID);
        return Response
                .status(200)
                .entity(kalender)
                .build();
    }

    /**
     * creates a kalender with it params
     *
     * @param kalenderName
     * @return Response
     */
    @PUT
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertKalender(
            @FormParam("kalenderName") String kalenderName
    ) {
        Kalender kalender = new Kalender();
        kalender.setKalenderName(kalenderName);
        kalender.setKalenderID(kalender.generatekalenderID(kalenderName));

        DataHandler.insertKalender(kalender);

        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a kalender by its ID
     *
     * @param kalenderID
     * @param kalenderName
     * @return Response
     */
    @POST
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateKalender(
            @FormParam("kalenderID") String kalenderID,
            @FormParam("kalenderName") String kalenderName
    ) {
        Kalender kalender = DataHandler.readKalenderByID(kalenderID);
        int httpStatus = 200;
        if (kalender != null) {
            kalender.setKalenderName(kalenderName);
            kalender.setKalenderID(kalender.generatekalenderID(kalenderName));

            DataHandler.updateKalender();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a kalender by its ID
     *
     * @param kalenderID
     * @return
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteKalender(
            @QueryParam("id") String kalenderID
    ) {
        int httpStatus = 200;
        if (DataHandler.deleteKalender(kalenderID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}
