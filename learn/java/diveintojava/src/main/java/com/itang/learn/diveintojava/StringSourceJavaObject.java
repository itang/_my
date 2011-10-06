package com.itang.learn.diveintojava;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.tools.SimpleJavaFileObject;

/**
 * 字符串表单的Java源代码对象.
 * 
 */
public class StringSourceJavaObject extends SimpleJavaFileObject {
	private String content = null;

	/**
	 * 构造方法.
	 * @param name  类名
	 * @param content 源代码字符串
	 * @throws URISyntaxException
	 */
	public StringSourceJavaObject(String name, String content)
			throws URISyntaxException {
		super(URI.create("string:///" + name.replace('.', '/')
				+ Kind.SOURCE.extension), Kind.SOURCE);
		this.content = content;
	}

	public CharSequence getCharContent(boolean ignoreEncodingErrors)
			throws IOException {
		return content;
	}

}

