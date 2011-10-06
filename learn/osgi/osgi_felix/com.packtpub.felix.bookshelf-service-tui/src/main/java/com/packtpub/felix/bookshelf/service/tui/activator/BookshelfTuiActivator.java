package com.packtpub.felix.bookshelf.service.tui.activator;

//import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

//import com.packtpub.felix.bookshelf.service.tui.BookshelfServiceProxy;

public class BookshelfTuiActivator implements BundleActivator {

	//@SuppressWarnings("unchecked")
	public void start(BundleContext bc) {
//		@SuppressWarnings("rawtypes")
//		Hashtable props = new Hashtable();
//		props.put("osgi.command.scope", BookshelfServiceProxy.SCOPE);
//		props.put("osgi.command.function", BookshelfServiceProxy.FUNCTIONS);
//		bc.registerService(BookshelfServiceProxy.class.getName(),
//				new BookshelfServiceProxy(bc), props);
	}

	public void stop(BundleContext bc) {
	}

}
