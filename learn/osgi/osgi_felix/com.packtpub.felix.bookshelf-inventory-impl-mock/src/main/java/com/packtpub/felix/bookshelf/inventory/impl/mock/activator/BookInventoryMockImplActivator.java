package com.packtpub.felix.bookshelf.inventory.impl.mock.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.packtpub.felix.bookshelf.inventory.api.BookInventory;
import com.packtpub.felix.bookshelf.inventory.impl.mock.BookInventoryMockImpl;

public class BookInventoryMockImplActivator implements BundleActivator {

//	private ServiceRegistration reg;

	public void start(BundleContext context) throws Exception {
//		System.out.println("\nStarting Book Inventory Mock Impl");
//		this.reg = context.registerService(BookInventory.class.getName(),
//				new BookInventoryMockImpl(), null);
	}

	public void stop(BundleContext context) throws Exception {
//		System.out.println("\nStoping Book Inventory Mock Impl");
//		if (this.reg != null) {
//			context.ungetService(reg.getReference());
//			this.reg = null;
//		}
	}

}
