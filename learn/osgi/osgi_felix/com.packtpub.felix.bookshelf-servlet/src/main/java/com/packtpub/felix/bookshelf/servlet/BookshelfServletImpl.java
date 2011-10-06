package com.packtpub.felix.bookshelf.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.packtpub.felix.bookshelf.inventory.api.Book;
import com.packtpub.felix.bookshelf.inventory.api.BookNotFoundException;
import com.packtpub.felix.bookshelf.log.api.BookshelfLogHelper;
import com.packtpub.felix.bookshelf.service.api.BookshelfService;
import com.packtpub.felix.bookshelf.service.api.InvalidCredentialsException;

public class BookshelfServletImpl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String alias;
	private BookshelfService service;
	private BookshelfLogHelper logger;
	private String sessionId;

	private static final String PARAM_OP = "op";
	private static final String OP_CATEGORIES = "categories";
	private static final String OP_BYCATEGORY = "byCategory";
	private static final String OP_BYAUTHOR = "byAuthor";

	private static final String PARAM_CATEGORY = "category";
	private static final String PARAM_AUTHOR = "author";

	private static final String OP_ADDBOOKFORM = "addBookForm";

	private static final String PARAM_ISBN = "isbn";
	private static final String PARAM_TITLE = "title";
	private static final String PARAM_RATING = "rating";

	private static final String OP_ADDBOOK = "addBook";
	private static final String OP_LOGINFORM = "loginForm";
	private static final String OP_LOGIN = "login";
	private static final String PARAM_USER = "user";
	private static final String PARAM_PASS = "pass";

	public void init(ServletConfig config) {
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {
		String op = req.getParameter(PARAM_OP);
		resp.setContentType("text/html");
		this.logger.debug("op = " + op + ", session = " + this.sessionId);
		if (OP_LOGIN.equals(op)) {
			String user = req.getParameter(PARAM_USER);
			String pass = req.getParameter(PARAM_PASS);
			try {
				doLogin(user, pass);
				htmlMainPage(resp.getWriter());
			} catch (InvalidCredentialsException e) {
				htmlLoginForm(resp.getWriter(), e.getMessage());
			}
			return;
		} else if (OP_LOGINFORM.equals(op) || !sessionIsValid()) {
			htmlLoginForm(resp.getWriter(), null);
			return;
		}
		try {
			if (op == null) {
				htmlMainPage(resp.getWriter());
			} else if (OP_CATEGORIES.equals(op)) {
				htmlCategories(resp.getWriter());
			} else if (OP_BYCATEGORY.equals(op)) {
				String category = req.getParameter(PARAM_CATEGORY);
				htmlByCategory(resp.getWriter(), category);
			} else if (OP_BYAUTHOR.equals(op)) {
				String author = req.getParameter(PARAM_AUTHOR);
				htmlByAuthor(resp.getWriter(), author);
			} else if (OP_ADDBOOKFORM.equals(op)) {
				htmlAddBookForm(resp.getWriter());
			} else if (OP_ADDBOOK.equals(op)) {
				htmlTop(resp.getWriter());
				doAddBook(req, resp);
				htmlBottom(resp.getWriter());
			}

			else {
				htmlMainPage(resp.getWriter());
			}
		} catch (InvalidCredentialsException e) {
			resp.getWriter().write(e.getMessage());
		}
	}

	private void htmlCateories(PrintWriter printWriter)
			throws InvalidCredentialsException {
		htmlTop(printWriter);
		printWriter.println("<h3>Categories:</h3>");
		printWriter.println("<ul>");
		for (String category : this.service.getCategories(sessionId)) {
			printWriter.println("<li><a href=\""
					+ browseByCategoryUrl(category) + "\">" + category
					+ "</li>");
		}
		printWriter.println("</ul>");
		htmlBottom(printWriter);
	}

	private String browseByCategoryUrl(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	private void htmlBottom(PrintWriter printWriter) {
		// TODO Auto-generated method stub

	}

	private void htmlAddBookForm(PrintWriter writer) {
		// TODO Auto-generated method stub

	}

	private void htmlByAuthor(PrintWriter printWriter, String author)
			throws InvalidCredentialsException {
		htmlTop(printWriter);
		printWriter.println("<h3>Books:</h3>");
		printWriter.println("<ul>");
		for (String isbn : this.service.searchBooksByAuthor(sessionId, author)) {
			try {
				printWriter.println("<li>" + this.service.getBook(sessionId, isbn) + "</li>");
			} catch (BookNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		printWriter.println("</ul>");
		htmlBottom(printWriter);
	}

	private void htmlByCategory(PrintWriter writer, String category) {
		// TODO Auto-generated method stub
	}

	private void htmlCategories(PrintWriter writer) {
		// TODO Auto-generated method stub
	}

	private boolean sessionIsValid() {
		return this.sessionId != null;
	}

	private void htmlLoginForm(PrintWriter writer, String message) {
		StringBuilder sb = new StringBuilder("<div>");
		sb.append("<div class='message'>" + message + "</div>");
		sb.append("<form action='/bookshelf'>");
		sb.append("<input type='hidden' name='op' value='login'/>");
		sb.append("<p>");
		sb.append("用户:");
		sb.append("<input type='text' name='user'/>");
		sb.append("</p>");
		sb.append("<p>");
		sb.append("密码:");
		sb.append("<input type='text' name='pass'/>");
		sb.append("</p>");
		sb.append("<p><input type='submit' value='登录'/>");
		sb.append("/<form>");

		writer.print(sb.append("</div>").toString());
	}

	private void htmlMainPage(PrintWriter writer) {
		if (!sessionIsValid()) {
			htmlLoginForm(writer, "你还未登录!");
			return;
		}
		try {
			this.htmlByAuthor(writer, "%");
			// htmlCateories(writer);
		} catch (InvalidCredentialsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doLogin(String user, String pass)
			throws InvalidCredentialsException {
		this.sessionId = this.service.login(user, pass.toCharArray());
	}

	private void htmlTop(PrintWriter printWriter) {
		printWriter.print("<h1>osgi http servlet </h1>");
	}

	private void doAddBook(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String isbn = req.getParameter(PARAM_ISBN);
		String category = req.getParameter(PARAM_CATEGORY);
		String author = req.getParameter(PARAM_AUTHOR);
		String title = req.getParameter(PARAM_TITLE);
		String ratingStr = req.getParameter(PARAM_RATING);
		int rating = 0;
		try {
			rating = Integer.parseInt(ratingStr);
		} catch (NumberFormatException e) {
			resp.getWriter().println(e.getMessage());
			return;
		}
		try {
			this.service.addBook(sessionId, isbn, title, author, category,
					rating);
		} catch (Exception e) {
			resp.getWriter().println(e.getMessage());
			return;
		}
		resp.getWriter().println("Added!");
	}

	private String addBookUrl() {
		return "?" + PARAM_OP + "=" + OP_ADDBOOK;
	}

}
