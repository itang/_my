package me.itang.test.test_restesay;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter {

	private static final String ENCODING = "utf-8";

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("EncodingFilter init");
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("encoding filter...");
		System.out.println("before: " + resp.getCharacterEncoding());
		((HttpServletRequest) req).setCharacterEncoding(ENCODING);
		((HttpServletResponse) resp).setCharacterEncoding(ENCODING);

		System.out.println("after: " + resp.getCharacterEncoding());

		chain.doFilter(req, new ResponseW(((HttpServletResponse) resp)));
	}

	public void destroy() {

	}

	public static class ResponseW implements HttpServletResponse {
		private HttpServletResponse target;

		public ResponseW(HttpServletResponse target) {
			this.target = target;
		}

		@Override
		public String getCharacterEncoding() {
			return target.getCharacterEncoding();
		}

		@Override
		public String getContentType() {
			return target.getContentType();
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return target.getOutputStream();
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			return target.getWriter();
		}

		@Override
		public void setCharacterEncoding(String charset) {
			System.out.println("setCharacterEncoding:" + charset);
			target.setCharacterEncoding(charset);
		}

		@Override
		public void setContentLength(int len) {
			target.setContentLength(len);
		}

		@Override
		public void setContentType(String type) {
			System.out.println("setContentType:" + type);
			target.setContentType(type);
		}

		@Override
		public void setBufferSize(int size) {
			target.setBufferSize(size);
		}

		@Override
		public int getBufferSize() {
			return target.getBufferSize();
		}

		@Override
		public void flushBuffer() throws IOException {
			target.flushBuffer();
		}

		@Override
		public void resetBuffer() {
			target.resetBuffer();
		}

		@Override
		public boolean isCommitted() {
			return target.isCommitted();
		}

		@Override
		public void reset() {
			target.reset();
		}

		@Override
		public void setLocale(Locale loc) {
			target.setLocale(loc);
		}

		@Override
		public Locale getLocale() {
			return target.getLocale();
		}

		@Override
		public void addCookie(Cookie cookie) {
			target.addCookie(cookie);
		}

		@Override
		public boolean containsHeader(String name) {
			return target.containsHeader(name);
		}

		@Override
		public String encodeURL(String url) {
			return target.encodeURL(url);
		}

		@Override
		public String encodeRedirectURL(String url) {
			return target.encodeRedirectURL(url);
		}

		@SuppressWarnings("deprecation")
		@Override
		public String encodeUrl(String url) {
			return target.encodeUrl(url);
		}

		@SuppressWarnings("deprecation")
		@Override
		public String encodeRedirectUrl(String url) {
			return target.encodeRedirectUrl(url);
		}

		@Override
		public void sendError(int sc, String msg) throws IOException {
			target.sendError(sc, msg);
		}

		@Override
		public void sendError(int sc) throws IOException {
			target.sendError(sc);
		}

		@Override
		public void sendRedirect(String location) throws IOException {
			target.sendRedirect(location);
		}

		@Override
		public void setDateHeader(String name, long date) {
			target.setDateHeader(name, date);
		}

		@Override
		public void addDateHeader(String name, long date) {
			target.addDateHeader(name, date);
		}

		@Override
		public void setHeader(String name, String value) {
			target.setHeader(name, value);
		}

		@Override
		public void addHeader(String name, String value) {
			target.addHeader(name, value);
		}

		@Override
		public void setIntHeader(String name, int value) {
			target.setIntHeader(name, value);
		}

		@Override
		public void addIntHeader(String name, int value) {
			target.addIntHeader(name, value);
		}

		@Override
		public void setStatus(int sc) {
			target.setStatus(sc);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void setStatus(int sc, String sm) {
			target.setStatus(sc, sm);
		}
	}

}
