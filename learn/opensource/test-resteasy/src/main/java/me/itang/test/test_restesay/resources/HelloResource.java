package me.itang.test.test_restesay.resources;

import static me.itang.test.test_restesay.resources.Template.template;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import me.itang.test.test_restesay.tag.Tag;

import org.jboss.resteasy.annotations.Form;

@Resource
@Path("/hello")
public class HelloResource {
	// http://localhost:8080/hello
	// http://localhost:8080/hello?
	@GET
	@Produces("text/html")
	public String index() {
		return template("");
	}

	// http://localhost:8080/hello/itang
	@GET
	@Path("/{name}")
	@Produces("text/html")
	public String hello(@PathParam("name") String name) {
		return template(new Tag("h1").text("Hello" + ", " + name));
	}

	// http://localhost:8080/hello/users/user-itang
	@GET
	@Path("/users/user-{name:.*}")
	@Produces("text/html")
	public String user(@PathParam("name") String name) {
		return template(new Tag("h1").text("Hello man: "
				+ (name.isEmpty() ? "Someone" : name)));
	}

	@GET
	@Path("/queryparams")
	@Produces("text/html")
	public String queryParams(
			@QueryParam("name") @DefaultValue("tqibm") Name name) {
		return template(new Tag("h1").text(name.toString()));
	}

	@GET
	@Path("/headers")
	@Produces("text/html")
	public String headers(@HeaderParam("name") Name name) {
		return template(new Tag("h1").text(name.toString()));
	}

	@GET
	@Path("/matrixs")
	@Produces("text/html")
	public String matrixs(@MatrixParam("name") String name,
			@MatrixParam("msg") String msg) {
		return template(new Tag("h1").text(String.format("Hello,%s, %s", name,
				msg)));
	}

	@GET
	@Path("/cookies")
	@Produces("text/html")
	public String cookies(@CookieParam("sessionid") String sessionId) {
		return template(new Tag("h1").text("SessionId: " + sessionId));
	}

	@GET
	@Path("/form")
	@Produces("text/html")
	public String formGet() {
		return template(new Tag("form")
				.attr("method", "POST")
				.attr("action", "/hello/form")
				.child("First name 姓名")
				.child(new Tag("input").attr("name", "name"))
				.child("Message 消息")
				.child(new Tag("input").attr("name", "msg"))
				.child(new Tag("input").attr("type", "submit").attr("value",
						"submit")));
	}

	@POST
	@Path("/form")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/html")
	public String form(@FormParam("name") String name,
			@FormParam("msg") String msg, @Form MsgForm form) {
		return template(new Tag("h1").text(name + ", " + msg + " , FORM:"
				+ form));
	}

	@GET
	@Path("/requests")
	@Produces("text/html")
	public String requests(@Context HttpServletRequest request) {
		return template("request URI:" + request.getRequestURI());
	}

	@GET
	@Path("/negotiation")
	@Produces({ "text/html", "text/plain" })
	public String negotiation(@Context HttpServletRequest request) {
		return template("request URI:" + request.getRequestURI());
	}

	public static class MsgForm {
		@FormParam("msg")
		private String msg;
		@FormParam("name")
		private String name;

		public MsgForm() {
			//
		}

		public MsgForm(String name, String msg) {
			this.name = name;
			this.msg = msg;
		}

		@Override
		public String toString() {
			return name + ", " + msg;
		}
	}

	public static class Name {
		private String name;

		public Name(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Name:[" + name + "]";
		}
	}
}
