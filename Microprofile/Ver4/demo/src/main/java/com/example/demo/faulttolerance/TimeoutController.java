package com.example.demo.faulttolerance;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 */
// @RequestScoped and @Singleton

@Path("/faulttolerance")
@RequestScoped
public class TimeoutController {
    private static final Logger LOGGER = Logger.getLogger(TimeoutController.class.getName());

    @Inject
    @ConfigProperty(name = "servidor")
    private String someUrl;

    @GET
    @Path("/timeout")
    @Produces({ MediaType.APPLICATION_JSON })
    @Timeout(100)

    public Response faultToleranceTimeoutSingle() {

        int codSalida = 200;
        String mensaje;

        longProcessingTask(120);
        mensaje = "Tarea finalizada...";

        return Response.status(codSalida).entity(mensaje).build();
    }

    @GET
    @Path("/circuitbreaker")
    @Produces({ MediaType.APPLICATION_JSON })
    @Timeout(100)
    @Retry(maxRetries = 10, delay = 1000)
    @CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 10, failureRatio=0.20,delay = 1000)
    @Fallback(fallbackMethod = "faultToleranceFallBack")


    public Response faultToleranceCircuitBreaker() {
        longProcessingTask(120);
        return Response.status(200).entity(new String("Salida")).build();
    }

    @GET
    @Path("/retry")
    @Produces({ MediaType.APPLICATION_JSON })
    @Timeout(100)
    @Retry(maxRetries = 3, delay = 100)
    @Fallback(fallbackMethod = "faultToleranceFallBack")

    public Response faultToleranceRetry() {
        longProcessingTask(120);
        return Response.status(200).entity(new String("Salida")).build();
    }

    @GET
    @Path("/fallback")
    @Produces({ MediaType.APPLICATION_JSON })
    @Timeout(100)
    @Fallback(fallbackMethod = "faultToleranceFallBack")

    public Response faultToleranceTimeout() {
        longProcessingTask(120);
        return Response.status(200).entity(new String("Salida")).build();
    }

    public Response faultToleranceFallBack() {
        LOGGER.log(Level.WARNING, "MetEireann backup service has been requested due to AccuWeather timeout");
        return Response.status(200)
                .entity(new String("MetEireann backup service has been requested due to AccuWeather timeout")).build();
    }

    private String longProcessingTask(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "AccuWeather task has been interrupted.");
        }
        return null;
    }

}
