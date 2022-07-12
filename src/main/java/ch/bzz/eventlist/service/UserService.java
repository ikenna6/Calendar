package ch.bzz.eventlist.service;

import ch.bzz.eventlist.data.DataHandler;
import ch.bzz.eventlist.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("user")
public class UserService {
    /**
     * method to check if username and password is correct
     * @param username
     * @param password
     * @return
     */
    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginUser(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        int httpStatus;
        User user = DataHandler.readUser(username, password);
        if (user == null || user.getRole() == null || user.getRole().equals("guest")) {
            httpStatus = 404;
        } else {
            httpStatus = 200;
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                user.getRole(),
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        return Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
    }

    /**
     * logout current user
     * @return object with guest-Cookie
     */
    @DELETE
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout(){
        NewCookie cookie = new NewCookie(
                "userRole",
                "guest",
                "/",
                "",
                "Login-Cookie",
                1,
                false
        );
        return Response
                .status(200)
                .entity("")
                .cookie(cookie)
                .build();
    }

}
