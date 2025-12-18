package com.soa.ejbvehicle.service.impl;

import com.soa.ejbvehicle.service.HelloService;
import javax.ejb.Stateless;

@Stateless
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello() {
        return "Hello From EJB Vehicle";
    }
}
