package com.soa.webvehicle.resource;

import com.soa.webvehicle.remoteClient.RemoteHelloService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.naming.NamingException;

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {
    private final RemoteHelloService client =
            new RemoteHelloService("payara-ejb");

    @GET
    public String ping() throws NamingException {
        return client.helloTalker();
    }
}
