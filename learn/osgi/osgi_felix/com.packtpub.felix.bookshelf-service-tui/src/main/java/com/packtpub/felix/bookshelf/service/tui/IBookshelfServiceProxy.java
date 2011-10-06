package com.packtpub.felix.bookshelf.service.tui;

import java.util.Set;

import org.apache.felix.service.command.Descriptor;

import com.packtpub.felix.bookshelf.inventory.api.Book;
import com.packtpub.felix.bookshelf.inventory.api.BookAlreadyExistsException;
import com.packtpub.felix.bookshelf.inventory.api.InvalidBookException;
import com.packtpub.felix.bookshelf.service.api.InvalidCredentialsException;

public interface IBookshelfServiceProxy {

	//
	// public BookshelfServiceProxy(BundleContext context) {
	// this.context = context;
	// }
	@Descriptor("Search books by author, title, or category")
	public abstract Set<Book> search(
			@Descriptor("username") String username,
			@Descriptor("password") String password,
			@Descriptor("search on attribute: author, title, or category") String attribute,
			@Descriptor("match like (use % at the beginning or end of <like>"
					+ " for wild-card)") String filter)
			throws InvalidCredentialsException;

	@Descriptor("Search books by rating")
	public abstract Set<Book> search(@Descriptor("username") String username,
			@Descriptor("password") String password,
			@Descriptor("search on attribute: rating") String attribute,
			@Descriptor("lower rating limit (inclusive)") int lower,
			@Descriptor("upper rating limit (inclusive)") int upper)
			throws InvalidCredentialsException;

	@Descriptor("Add a  books")
	public abstract String add(@Descriptor("username") String username,
			@Descriptor("password") String password,
			@Descriptor("ISBN") String isbn, @Descriptor("Title") String title,
			@Descriptor("Author") String author,
			@Descriptor("Category") String category,
			@Descriptor("Rating (0..10)") int rating)
			throws InvalidCredentialsException, BookAlreadyExistsException,
			InvalidBookException;

}