package com.example.demo;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;



@Path("/api")
@RequestScoped 
public class HelloController {
    private static final Logger LOGGER = Logger.getLogger(HelloController.class.getName());
    
  @GET
    @Path("/hello")

    public Response sayHello() {

        LOGGER.log(Level.WARNING, "MetEireann backup service has been requested due to AccuWeather timeout");

        return Response.status(Status.OK).entity(new String("Hello World")).build();
    }

}

