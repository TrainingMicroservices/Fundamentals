package com.example.demo.jpa;

import java.util.logging.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 *
 */


@Path("/jpa")
//@RequestScoped 
public class JpaController {
    private static final Logger LOGGER = Logger.getLogger(JpaController.class.getName());

 
    @GET
    @Path("/hola")

    public Response diHola() { 
        return Response.status(200).entity(new String("Hello World")).build();
    }



}

