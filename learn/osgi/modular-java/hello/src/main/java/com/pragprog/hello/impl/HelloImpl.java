package com.pragprog.hello.service.impl;

import com.pragprog.hello.service.HelloService;

public class HelloImpl implements HelloService {
    public String getHelloMessage() {
	return "Bonjour!" ;
    }
    public String getGoodbyeMessage() {
	return "Arrivederci!" ;
    }
}
