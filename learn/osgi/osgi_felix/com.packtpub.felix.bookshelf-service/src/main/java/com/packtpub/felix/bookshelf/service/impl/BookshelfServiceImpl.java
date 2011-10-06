package com.packtpub.felix.bookshelf.service.impl;

import static com.packtpub.felix.bookshelf.inventory.api.BookInventory.SearchCriteria.AUTHOR_LIKE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.packtpub.felix.bookshelf.inventory.api.Book;
import com.packtpub.felix.bookshelf.inventory.api.BookAlreadyExistsException;
import com.packtpub.felix.bookshelf.inventory.api.BookInventory;
import com.packtpub.felix.bookshelf.inventory.api.BookInventory.SearchCriteria;
import com.packtpub.felix.bookshelf.inventory.api.BookNotFoundException;
import com.packtpub.felix.bookshelf.inventory.api.InvalidBookException;
import com.packtpub.felix.bookshelf.inventory.api.MutableBook;
import com.packtpub.felix.bookshelf.log.api.BookshelfLogHelper;
import com.packtpub.felix.bookshelf.service.api.BookshelfService;
import com.packtpub.felix.bookshelf.service.api.InvalidCredentialsException;

public class BookshelfServiceImpl implements BookshelfService {
	private String sessionId;
	// private BundleContext context;

	// injected by ipojo
	private BookInventory inventory;
	private BookshelfLogHelper logger;

	public BookshelfServiceImpl() {

	}

	// public BookshelfServiceImpl(BundleContext context) {
	// this.context = context;
	// }

	public String login(String username, char[] password)
			throws InvalidCredentialsException {
		if ("admin".equals(username)
				&& Arrays.equals(password, "admin".toCharArray())) {
			this.sessionId = Long.toString(System.currentTimeMillis());
			return this.sessionId;
		}
		throw new InvalidCredentialsException(username);
	}

	public void logout(String sessionId) {
		checkSession(sessionId);
		this.sessionId = null;
	}

	public boolean sessionIsValid(String sessionId) {
		return this.sessionId != null && this.sessionId.equals(sessionId);
	}

	protected void checkSession(String sessionId) {
		if (!sessionIsValid(sessionId)) {
			throw new SessionNotValidRuntimeException(sessionId);
		}
	}

	public Set<String> getGroups(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addBook(String session, String isbn, String title,
			String author, String category, int rating)
			throws BookAlreadyExistsException, InvalidBookException {
		checkSession(session);

		getLogger().debug(LoggerConstants.LOG_ADD_BOOK, isbn, title, author,
				category, rating);

		MutableBook book = lookupBookInventory().createBook(isbn);
		book.setAuthor(author);
		book.setCategory(category);
		book.setRating(rating);
		book.setTitle(title);

		getLogger().debug(LoggerConstants.LOG_STORE_BOOK, isbn);
		lookupBookInventory().storeBook(book);
	}

	public void modifyBookCategory(String session, String isbn, String category)
			throws BookNotFoundException, InvalidBookException {
		// TODO Auto-generated method stub

	}

	public void modifyBookRating(String session, String isbn, int rating)
			throws BookNotFoundException, InvalidBookException {
		// TODO Auto-generated method stub

	}

	public void removeBook(String session, String isbn)
			throws BookNotFoundException {
		// TODO Auto-generated method stub

	}

	public Book getBook(String session, String isbn)
			throws BookNotFoundException {
		return this.lookupBookInventory().loadBook(isbn);
	}

	public Set<String> searchBooksByCategory(String session, String categoryLike) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> searchBooksByAuthor(String session, String authorLike) {
		Map<SearchCriteria, String> criteria = new HashMap<SearchCriteria, String>();
		criteria.put(AUTHOR_LIKE, authorLike);

		return this.lookupBookInventory().searchBooks(criteria);
	}

	public Set<String> searchBooksByTitle(String session, String titleLike) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> searchBooksByRating(String session, int ratingLower,
			int ratingUpper) {
		// TODO Auto-generated method stub
		return null;
	}

	public MutableBook getBookForEdit(String sessionId, String isbn)
			throws BookNotFoundException {
		getLogger().debug(LoggerConstants.LOG_EDIT_BY_ISBN, isbn);
		checkSession(sessionId);
		MutableBook book = this.inventory.loadBookForEdit(isbn);
		getLogger().debug("Got book for edit: " + book);
		return book;

	}

	public Set<String> getCategories(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	private BookInventory lookupBookInventory() {
		// String name = BookInventory.class.getName();
		// ServiceReference ref = this.context.getServiceReference(name);
		// if (ref == null) {
		// throw new BookInventoryNotRegisteredRuntimeException(name);
		// }
		// return (BookInventory) this.context.getService(ref);

		return this.inventory;
	}

	private BookshelfLogHelper getLogger() {
		return this.logger;
	}

}
