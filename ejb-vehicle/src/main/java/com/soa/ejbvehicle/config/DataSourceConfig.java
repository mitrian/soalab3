package com.soa.ejbvehicle.config;

import javax.annotation.PostConstruct;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Singleton
@Startup
@DataSourceDefinition(
        name = "java:global/jdbc/__default",
        className = "org.postgresql.xa.PGXADataSource",
        user = "vehicle_user",
        password = "vehicle_password",
        serverName = "postgres",
        portNumber = 5432,
        databaseName = "vehicle_db",
        minPoolSize = 5,
        maxPoolSize = 20,
        initialPoolSize = 5
)
public class DataSourceConfig {
    
}

