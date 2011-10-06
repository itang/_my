package me.itang.test.test_restesay.resources;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;

import com.google.common.collect.Lists;

public class BookResource implements BookStore {

	@Override
	public Collection<Book> getBooks() {
		return books;
	}

	@Override
	public void addBook(@Mapped(attributesAsElements = { "title" }) Book book) {
		System.out.println(book);
		books.add(book);
	}

	@Override
	public Book getBook(String id) {
		for (Book book : books) {
			if (book.getTitle().equalsIgnoreCase("id")) {
				return book;
			}
		}
		return null;
	}

	@Override
	public void updateBook(String id, Book book) {
		Book target = getBook(id);
		if (target != null) {
			target.setAuthor(book.getAuthor());
			// target.setTitle(book.getTitle());
		}

	}

	@Override
	public void deleteBook(String id) {
		for (Iterator<Book> it = books.iterator(); it.hasNext();) {
			Book book = it.next();
			if (book.getTitle().equalsIgnoreCase("id")) {
				it.remove();
			}
		}
	}

	private List<Book> books = Lists.newArrayList(new Book("book1", "itang"),
			new Book("book2", "tqibm"));

}
