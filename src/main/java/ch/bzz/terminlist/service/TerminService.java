package ch.bzz.terminlist.service;

import ch.bzz.terminlist.data.DataHandler;
import ch.bzz.terminlist.model.Termin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * services for reading termine
 */
@Path("termin")
public class TerminService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTermine() {
        List<Termin> terminList = DataHandler.readAllTermine();
        return Response
                .status(200)
                .entity(terminList)
                .build();
    }

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readTermin(
            @QueryParam("uuid") String terminUUID
    ) {
        Termin termin = DataHandler.readTerminByUUID(terminUUID);
        return Response
                .status(200)
                .entity(termin)
                .build();
    }

}
