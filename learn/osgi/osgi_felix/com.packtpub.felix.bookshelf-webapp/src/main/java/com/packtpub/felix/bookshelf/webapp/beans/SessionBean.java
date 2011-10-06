package com.packtpub.felix.bookshelf.webapp.beans;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.packtpub.felix.bookshelf.service.api.BookshelfService;
import com.packtpub.felix.bookshelf.service.api.InvalidCredentialsException;

public class SessionBean {
	static final String OSGI_BUNDLECONTEXT = "osgi-bundlecontext";
	private BundleContext ctx;
	private String sessionId;

	public void initialize(ServletContext context) {
		this.ctx = (BundleContext) context.getAttribute(OSGI_BUNDLECONTEXT);
	}

	public BookshelfService getBookshelf() {
		ServiceReference ref = ctx.getServiceReference(BookshelfService.class
				.getName());
		BookshelfService bookshelf = (BookshelfService) ctx.getService(ref);
		return bookshelf;
	}

	public boolean sessionIsValid() {
		return getBookshelf().sessionIsValid(getSessionId());
	}

	public String getSessionId() {
		if(sessionId == null){
			try {
				this.sessionId = getBookshelf().login("admin","admin".toCharArray());
			} catch (InvalidCredentialsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.sessionId;
	}

}