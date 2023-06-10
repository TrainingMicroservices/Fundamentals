package com.example.demo;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;
//import org.jetbrains.annotations.NotNull;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 */
//@RequestScoped and @Singleton

@Path("/ms")
@RequestScoped 
public class HelloController {
    private static final Logger LOGGER = Logger.getLogger(HelloController.class.getName());
    
    @Inject
    @ConfigProperty(name = "servidor")
    private String someUrl;

    @Inject private JsonWebToken tokenInformation;
    @Inject private Principal principal;

    @Inject
    @Claim("groups")
    private Set<String> groups;


    @GET
    @Path("/hello")
    @RolesAllowed("ROL_ADMIN")
    //@RolesAllowed, @PermitAll, @DenyAll
    //@PermitAll
    public String sayHello() {

        String[] chunks = tokenInformation.getRawToken().split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));


        LOGGER.log(Level.WARNING, "MetEireann backup service has been requested due to AccuWeather timeout");
        if (tokenInformation != null){
            System.out.println("... header: " + header);
            System.out.println("... payload: " + payload );

            System.out.println("... Principal: " + principal.getName() );
            //System.out.println("... Groups: " + groups.toString() );
            System.out.println("... [Author] MP JWT getIssuedAtTime " + tokenInformation.getIssuedAtTime() );
            System.out.println("... [Author] getIssuer: " + tokenInformation.getIssuer());
            System.out.println("... [Author] getRawToken: " + tokenInformation.getRawToken());
            System.out.println("... [Author] getTokenID: " + tokenInformation.getTokenID());
            if (tokenInformation.getGroups()!= null) {
               for (String name : tokenInformation.getGroups()) {
                   System.out.println("... [Author] getGroups : " +name);
               }
            
            }
            else
               System.out.println("... [Author] getGroups :  null");
}
        return "Hello World";
    }

    @GET
    @Path("/peter/")
    @Produces({ MediaType.APPLICATION_JSON })
    //@RolesAllowed("mysimplerole")

    public Response doThings(
            @DefaultValue("99999") @QueryParam("price") Integer price) {
        // String output = "Hello World.. price:"+price +"- - - "+LocalTime.now();

        JsonObject json = Json.createObjectBuilder()
                .add("name", "Falco")
                .add("age", BigDecimal.valueOf(3))
                .add("biteable", Boolean.FALSE).build();
        String result = json.toString();

        Dog dog = new Dog();
        dog.name = "Falco";
        dog.age = 4;
        dog.bitable = false;

        // Create Jsonb and serialize
        Jsonb jsonb = JsonbBuilder.create();
        result = jsonb.toJson(dog);
        //System.out.println(result);
        // Deserialize back
        //dog = jsonb.fromJson("{\"name\":\"Falco\",\"age\":4,\"bitable\":false}", Dog.class);

        Producto producto = new Producto(1, "Chocolates", 100);
        ArrayList<Producto> arrProduct = new ArrayList<>();
        arrProduct.add(producto);
        arrProduct.add(new Producto(2, "papitas", 100));
        arrProduct.add(new Producto(3, "chetos", 100));
        arrProduct.add(new Producto(4, "rufles " + price, 100));
        arrProduct.add(new Producto(5, someUrl, 100));
        arrProduct.add(new Producto(6, result, 100));

        return Response.status(200).entity(arrProduct).build();

    }

    @GET
    @Path("/retry")
    @Produces({ MediaType.APPLICATION_JSON })
    @Timeout(100)
    @Retry( maxRetries = 3, delay =100  )
    @Fallback(fallbackMethod = "faultToleranceFallBack")
    
    public Response faultToleranceRetry (){
        longProcessingTask(120);
        return Response.status(200).entity(new String("Salida")).build();
    }

    @GET
    @Path("/fallback")
    @Produces({ MediaType.APPLICATION_JSON })
    @Timeout(100)
    @Fallback(fallbackMethod = "faultToleranceFallBack")
    
    public Response faultToleranceTimeout (){
        longProcessingTask(120);
        return Response.status(200).entity(new String("Salida")).build();
    }

    public Response faultToleranceFallBack (){
        LOGGER.log(Level.WARNING, "MetEireann backup service has been requested due to AccuWeather timeout");
        return Response.status(200).entity(new String("MetEireann backup service has been requested due to AccuWeather timeout")).build();
    }

    private String longProcessingTask(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING,"AccuWeather task has been interrupted.");
        }
        return null;
    }

}

class Dog {
    public String name;
    public int age;
    public boolean bitable;
}