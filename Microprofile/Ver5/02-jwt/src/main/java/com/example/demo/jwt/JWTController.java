package com.example.demo.jwt;

import java.security.Principal;
import java.util.Base64;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 *
 */
// @RequestScoped and @Singleton

@Path("/jwt")
@RequestScoped
public class JWTController {
    private static final Logger LOGGER = Logger.getLogger(JWTController.class.getName());

    @Inject
    private JsonWebToken tokenInformation;
    @Inject
    private Principal principal;

    @Inject
    @Claim("groups")
    private Set<String> groups;

    @GET
    @Path("/permitall")
    @PermitAll

    public Response permitAll() {

        String[] chunks = tokenInformation.getRawToken().split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        LOGGER.log(Level.WARNING, "MetEireann backup service has been requested due to AccuWeather timeout");
        if (tokenInformation != null) {
            System.out.println("... header: " + header);
            System.out.println("... payload: " + payload);

            System.out.println("... Principal: " + principal.getName());
            // System.out.println("... Groups: " + groups.toString() );
            System.out.println("... [Author] MP JWT getIssuedAtTime " + tokenInformation.getIssuedAtTime());
            System.out.println("... [Author] getIssuer: " + tokenInformation.getIssuer());
            System.out.println("... [Author] getRawToken: " + tokenInformation.getRawToken());
            System.out.println("... [Author] getTokenID: " + tokenInformation.getTokenID());
            if (tokenInformation.getGroups() != null) {
                for (String name : tokenInformation.getGroups()) {
                    System.out.println("... [Author] getGroups : " + name);
                }

            } else
                System.out.println("... [Author] getGroups :  null");
        }

        return Response.status(200).entity(new String("Hello World")).build();
    }

    
    @GET
    @Path("/withRoleAdmin")
    @RolesAllowed("ROL_ADMIN")
    public Response withRoleAdmin() {

        String[] chunks = tokenInformation.getRawToken().split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        LOGGER.log(Level.WARNING, "MetEireann backup service has been requested due to AccuWeather timeout");
        if (tokenInformation != null) {
            System.out.println("... header: " + header);
            System.out.println("... payload: " + payload);

            System.out.println("... Principal: " + principal.getName());
            // System.out.println("... Groups: " + groups.toString() );
            System.out.println("... [Author] MP JWT getIssuedAtTime " + tokenInformation.getIssuedAtTime());
            System.out.println("... [Author] getIssuer: " + tokenInformation.getIssuer());
            System.out.println("... [Author] getRawToken: " + tokenInformation.getRawToken());
            System.out.println("... [Author] getTokenID: " + tokenInformation.getTokenID());
            if (tokenInformation.getGroups() != null) {
                for (String name : tokenInformation.getGroups()) {
                    System.out.println("... [Author] getGroups : " + name);
                }

            } else
                System.out.println("... [Author] getGroups :  null");
        }
        return Response.status(200).entity(new String("Hello World")).build();
    }

    @GET
    @Path("/withRoleHSBC")
    @RolesAllowed("ROLE_PROVIDER_HSBC")
    public Response withRoleHSBC() {

        String[] chunks = tokenInformation.getRawToken().split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        LOGGER.log(Level.WARNING, "MetEireann backup service has been requested due to AccuWeather timeout");
        if (tokenInformation != null) {
            System.out.println("... header: " + header);
            System.out.println("... payload: " + payload);

            System.out.println("... Principal: " + principal.getName());
            // System.out.println("... Groups: " + groups.toString() );
            System.out.println("... [Author] MP JWT getIssuedAtTime " + tokenInformation.getIssuedAtTime());
            System.out.println("... [Author] getIssuer: " + tokenInformation.getIssuer());
            System.out.println("... [Author] getRawToken: " + tokenInformation.getRawToken());
            System.out.println("... [Author] getTokenID: " + tokenInformation.getTokenID());
            if (tokenInformation.getGroups() != null) {
                for (String name : tokenInformation.getGroups()) {
                    System.out.println("... [Author] getGroups : " + name);
                }

            } else
                System.out.println("... [Author] getGroups :  null");
        }
        return Response.status(200).entity(new String("Hello World")).build();
    }
}
