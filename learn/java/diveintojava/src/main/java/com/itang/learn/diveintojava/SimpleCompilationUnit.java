package com.itang.learn.diveintojava;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public abstract class SimpleCompilationUnit implements Compilable {
	public static String DEFAULT_PACKAGE = "com.itang.learn.diveintojava.test";

	protected abstract String getSimpleName();

	protected abstract String getClassBodySource();

	// 类fullname
	public String getName() {
		return getPackageName() + "." + getSimpleName();
	}

	protected String getSource() {
		return new StringBuilder(getPackageDeclareString())
				.append("public class " + getSimpleName() + "{")
				.append(getClassBodySource()).append("}").toString();
	}

	protected String getPackageName() {
		return DEFAULT_PACKAGE;
	}

	@Override
	public boolean compile() {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);
		// 指定编译输出路径
		Iterable<String> options = Arrays.asList("-d", "./target/classes");
		CompilationTask task = compiler.getTask(null, fileManager, null,
				options, null, getSourceObjects());
		boolean result = task.call();
		try {
			fileManager.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	protected String getPackageDeclareString() {
		return "package " + this.getPackageName() + ";";
	}

	private Iterable<? extends JavaFileObject> getSourceObjects() {
		try {
			return Arrays.asList(new StringSourceJavaObject(getName(),
					getSource()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

}
