package me.itang.test.test_restesay.resources;

import me.itang.test.test_restesay.tag.Html;
import me.itang.test.test_restesay.tag.Node;
import me.itang.test.test_restesay.tag.Tag;

public final class Template {
	public static String template(Node body) {
		Tag ul = new Tag("ul");
		for (Tag a : links()) {
			ul.child(new Tag("li").child(a));
		}
		return new Html()
				.child(new Tag("head")
						.child("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>"))
				.child(new Tag("body").child(new Tag("h1").text("RESTEasy 入门"))
						.child(ul).child(body)).toString();
	}

	public static String template(final String body) {
		return template(new Node() {
			public String str() {
				return body;
			}
		});
	}

	private static Tag[] links() {
		return new Tag[] {
				link("http://localhost:8080/hello", "home"),
				link("http://localhost:8080/hello/form", "form"),
				link("http://localhost:8080/hello/itang", "hello"),
				link("http://localhost:8080/hello/users/user-itang", "user"),
				link("http://localhost:8080/hello/queryparams?name=itang",
						"/queryparams"),
				link("http://localhost:8080/hello/queryparams?name=",
						"http://localhost:8080/hello/queryparams?name="),
				link("http://localhost:8080/hello/queryparams?",
						"http://localhost:8080/hello/queryparams?"),
				link("http://localhost:8080/hello/matrixs;name=itang;msg=hello,world 你好世界！",
						"/matrixs"),
				link("http://localhost:8080/hello/requests?name=itang",
						"http://localhost:8080/hello/requests?name=itang"),

				link("http://localhost:8080/hello/negotiation",
						"http://localhost:8080/hello/negotiation"),
				link("http://localhost:8080/hello/negotiation.html",
						"http://localhost:8080/hello/negotiation.html"),
				link("http://localhost:8080/hello/negotiation.txt",
						"http://localhost:8080/hello/negotiation.txt"),
				link("http://localhost:8080/book/books",
						"http://localhost:8080/book/books"),
				link("http://localhost:8080/book/books.xml",
						"http://localhost:8080/book/books.xml"),
				link("http://localhost:8080/book/books.json",
						"http://localhost:8080/book/books.json")

		};
	}

	private static Tag link(String href, String text) {
		return new Tag("a").attr("href", href).text(text);
	}
}
