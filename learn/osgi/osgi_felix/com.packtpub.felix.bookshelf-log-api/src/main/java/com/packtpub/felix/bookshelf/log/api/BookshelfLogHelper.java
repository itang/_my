package com.packtpub.felix.bookshelf.log.api;

public interface BookshelfLogHelper {
	void debug(String pattern, Object... args);

	void debug(String pattern, Throwable throwable, Object... args);

	void info(String pattern, Object... args);

	void warn(String pattern, Object... args);

	void warn(String pattern, Throwable throwable, Object... args);

	void error(String pattern, Object... args);

	void error(String pattern, Throwable throwable, Object... args);

}
