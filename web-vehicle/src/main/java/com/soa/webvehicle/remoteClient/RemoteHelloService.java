package com.soa.webvehicle.remoteClient;

import com.soa.ejbvehicle.service.HelloService;
import fish.payara.ejb.http.client.RemoteEJBContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class RemoteHelloService {
    private final String ejbHost;

    public RemoteHelloService(String ejbHost) {
        this.ejbHost = ejbHost;
    }

    protected HelloService lookupRemoteBean() throws NamingException {
        Properties props = new Properties();

        props.put(Context.INITIAL_CONTEXT_FACTORY, RemoteEJBContextFactory.class.getName());

        props.put(Context.PROVIDER_URL, "http://" + ejbHost + ":8080/ejb-invoker");

        Context ctx = new InitialContext(props);

        String jndiName = "java:global/ejb-vehicle-1.0-SNAPSHOT/HelloServiceImpl!com.soa.ejbvehicle.service.HelloService";

        return (HelloService) ctx.lookup(jndiName);
    }

    public String helloTalker() throws NamingException {
        HelloService remote = lookupRemoteBean();
        return remote.hello();
    }
}
