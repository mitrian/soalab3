package com.soa.webvehicle.remoteClient;

import com.soa.ejbvehicle.service.VehicleService;
import fish.payara.ejb.http.client.RemoteEJBContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class RemoteVehicleService {
    private final String ejbHost;

    public RemoteVehicleService(String ejbHost) {
        this.ejbHost = ejbHost;
    }

    protected VehicleService lookupRemoteBean() throws NamingException {
        Properties props = new Properties();

        props.put(Context.INITIAL_CONTEXT_FACTORY, RemoteEJBContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, "http://" + ejbHost + ":8080/ejb-invoker");

        Context ctx = new InitialContext(props);

        String jndiName = "java:global/ejb-vehicle-1.0-SNAPSHOT/VehicleServiceImpl!com.soa.ejbvehicle.service.VehicleService";

        return (VehicleService) ctx.lookup(jndiName);
    }

    public VehicleService getVehicleService() throws NamingException {
        return lookupRemoteBean();
    }
}

