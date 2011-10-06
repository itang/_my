package me.itang.test.test_restesay.resources;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;

@Path("/book")
@Consumes({ "application/xml", "application/json" })
@Produces({ "application/xml", "application/json" })
public interface BookStore {
	@AddLinks
	@LinkResource(value = Book.class)
	@GET
	@Path("books")
	public Collection<Book> getBooks();

	@LinkResource
	@POST
	@Path("books")
	@Consumes({ "application/json", "application/xml" })
	public void addBook(Book book);

	@AddLinks
	@LinkResource
	@GET
	@Path("book/{id}")
	public Book getBook(@PathParam("id") String id);

	@LinkResource
	@PUT
	@Path("book/{id}")
	public void updateBook(@PathParam("id") String id, Book book);

	@LinkResource(value = Book.class)
	@DELETE
	@Path("book/{id}")
	public void deleteBook(@PathParam("id") String id);
}
