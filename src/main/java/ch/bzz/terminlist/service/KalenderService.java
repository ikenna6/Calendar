package ch.bzz.terminlist.service;

import ch.bzz.terminlist.data.DataHandler;
import ch.bzz.terminlist.model.Kalender;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

}
