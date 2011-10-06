package com.pragprog.hello.service.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import com.pragprog.hello.service.HelloService;

public class HelloPublisher implements BundleActivator {
    private ServiceRegistration registration;
    public void start(BundleContext context) throws Exception {
	registration = context.registerService(HelloService.class.getName(),
					       new HelloImpl(), null);
    }
    public void stop(BundleContext context) throws Exception {
	registration.unregister();
    }
}
