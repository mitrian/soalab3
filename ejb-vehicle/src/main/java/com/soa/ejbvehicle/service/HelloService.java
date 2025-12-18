package com.soa.ejbvehicle.service;

import javax.ejb.Remote;

@Remote
public interface HelloService {
    String hello();
}
