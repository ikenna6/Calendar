package ch.bzz.terminlist.service;

import ch.bzz.terminlist.data.DataHandler;
import ch.bzz.terminlist.model.Termin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    /**
     *
     * @param titel
     * @param beschreibung
     * @param ganzTaegig
     * @param startDatumZeit
     * @param endDatumZeit
     * @param kalenderID
     * @return
     */
    @PUT
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertTermin(
            @FormParam("titel") String titel,
            @FormParam("beschreibung") String beschreibung,
            @FormParam("ganzTaegig") Boolean ganzTaegig,
            @FormParam("startDatumZeit") String startDatumZeit,
            @FormParam("endDatumZeit") String endDatumZeit,
            @FormParam("kalenderID") String kalenderID
    ) {
        Termin termin = new Termin();
        termin.setTerminUUID(UUID.randomUUID().toString());
        termin.setTitel(titel);
        termin.setBeschreibung(beschreibung);
        termin.setGanzTaegig(ganzTaegig);
        termin.setStartDatumZeit(LocalDateTime.parse(startDatumZeit));
        termin.setEndDatumZeit(LocalDateTime.parse(endDatumZeit));

        DataHandler.insertTermin(termin);

        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * @param terminUUID
     * @param titel
     * @param beschreibung
     * @param ganzTaegig
     * @param startDatumZeit
     * @param endDatumZeit
     * @param kalenderID
     * @return
     */
    @POST
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateTermin(
            @FormParam("terminUUID") String terminUUID,
            @FormParam("titel") String titel,
            @FormParam("beschreibung") String beschreibung,
            @FormParam("ganzTaegig") Boolean ganzTaegig,
            @FormParam("startDatumZeit") String startDatumZeit,
            @FormParam("endDatumZeit") String endDatumZeit,
            @FormParam("kalenderID") String kalenderID
    ) {
        Termin termin = DataHandler.readTerminByUUID(terminUUID);
        int httpStatus = 200;
        if (termin != null) {
            termin.setTitel(titel);
            termin.setBeschreibung(beschreibung);
            termin.setGanzTaegig(ganzTaegig);
            termin.setStartDatumZeit(LocalDateTime.parse(startDatumZeit));
            termin.setEndDatumZeit(LocalDateTime.parse(endDatumZeit));

            DataHandler.updateTermin();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a termin identified by its UUID
     *
     * @param terminUUID
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteTermin(
            @QueryParam("uuid") String terminUUID
    ) {
        int httpStatus = 200;
        if (DataHandler.deleteTermin(terminUUID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}
