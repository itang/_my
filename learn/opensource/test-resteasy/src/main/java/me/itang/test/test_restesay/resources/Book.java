package me.itang.test.test_restesay.resources;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;
import org.jboss.resteasy.links.RESTServiceDiscovery;

import com.google.inject.internal.ToStringBuilder;

@XmlRootElement(name = "book")
@Mapped(namespaceMap = @XmlNsMap(jsonName = "atom", namespace = "http://www.w3.org/2005/Atom"), attributesAsElements = { "title" })
@XmlAccessorType(XmlAccessType.NONE)
// @BadgerFish
public class Book {
	@XmlAttribute
	private String author;

	@XmlID
	@XmlAttribute
	public String title;

	@XmlElementRef
	private RESTServiceDiscovery rest;

	@XmlElement
	private String type = "default";

	public Book() {
		//
	}

	public Book(String title, String author) {
		this(title, author, null);
	}

	public Book(String title, String author, String type) {
		this.title = title;
		this.author = author;
		if (type != null) {
			this.type = type;
		}
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public RESTServiceDiscovery getRest() {
		return rest;
	}

	public void setRest(RESTServiceDiscovery rest) {
		this.rest = rest;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(Book.class).add("title", title)
				.add("author", author).toString();
	}

}
