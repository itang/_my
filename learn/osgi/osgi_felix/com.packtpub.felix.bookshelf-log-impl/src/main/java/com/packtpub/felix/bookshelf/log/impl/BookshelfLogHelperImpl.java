package com.packtpub.felix.bookshelf.log.impl;

import java.text.MessageFormat;

import org.osgi.service.log.LogService;

import com.packtpub.felix.bookshelf.log.api.BookshelfLogHelper;

public class BookshelfLogHelperImpl implements BookshelfLogHelper {
	private LogService log;

	@Override
	public void debug(String pattern, Object... args) {
		String message = MessageFormat.format(pattern, args);
		this.log.log(LogService.LOG_DEBUG, message);
	}

	@Override
	public void debug(String pattern, Throwable throwable, Object... args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(String pattern, Object... args) {
		String message = MessageFormat.format(pattern, args);
		this.log.log(LogService.LOG_INFO, message);
	}

	@Override
	public void warn(String pattern, Object... args) {
		String message = MessageFormat.format(pattern, args);
		this.log.log(LogService.LOG_WARNING, message);
	}

	@Override
	public void warn(String pattern, Throwable throwable, Object... args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String pattern, Object... args) {
		String message = MessageFormat.format(pattern, args);
		this.log.log(LogService.LOG_ERROR, message);

	}

	@Override
	public void error(String pattern, Throwable throwable, Object... args) {
		// TODO Auto-generated method stub

	}
}