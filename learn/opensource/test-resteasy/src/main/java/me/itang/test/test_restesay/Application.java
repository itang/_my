package me.itang.test.test_restesay;

import java.util.Set;

import me.itang.test.test_restesay.resources.BookResource;
import me.itang.test.test_restesay.resources.HelloResource;

import com.google.inject.internal.Sets;

public class Application extends javax.ws.rs.core.Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = Sets.newHashSet();
		classes.add(HelloResource.class);
		classes.add(BookResource.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return super.getSingletons();
	}

}
