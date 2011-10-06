package com.itang.learn.diveintojava.classloader;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ClassLoaderTest extends Assert {
	@Test
	public void test_base() {
		ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
		ClassLoader contextClassLoader = Thread.currentThread()
				.getContextClassLoader();
		assertEquals(classLoader, contextClassLoader);

		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
		assertEquals(classLoader, systemClassLoader);

		assertEquals(2, flatClassLoader().length);
		assertEquals(flatClassLoader()[0], systemClassLoader);
		assertEquals(flatClassLoader()[1], systemClassLoader.getParent());

		// Bootstrap classLoader
		assertNull(systemClassLoader.getParent().getParent());

		for (ClassLoader cl : flatClassLoader()) {
			System.out.println(cl);// sun.misc.Launcher$AppClassLoader@7d772e
									// sun.misc.Launcher$ExtClassLoader@11b86e7
		}
	}

	private ClassLoader[] flatClassLoader() {
		List<ClassLoader> result = new ArrayList<ClassLoader>();
		ClassLoader cl = this.getClass().getClassLoader();
		while (cl != null) {
			result.add(cl);
			cl = cl.getParent();
		}
		return result.toArray(new ClassLoader[result.size()]);
	}

	@Test
	public void test_class_init() {
		assertEquals(1, Class1.count);
		class InnerClass1 {
			private Class1 class1Instance;
		}
		assertEquals(1, Class1.count);

		class InnerClass2 {
			private Class2 class2Instance;
		}
		// 此时 Class2类未加载

		// 如果一个Java类只是被引用了，但是并没有被真正用到，那么这个类有可能就不会被解析
		new InnerClass2();// 此时加载Class2，即访问(引用)某类时才加载
		assertEquals(1, Class2.count);
	}

	@Test
	public void test_custom_classloader() {
		MyClassLoader myClassLoader = new MyClassLoader();
		try {
			@SuppressWarnings("unchecked")
			Class<Hello> helloClass = (Class<Hello>) myClassLoader
					.loadClass("com.itang.learn.diveintojava.classloader.Hello");
			helloClass.newInstance().sayHello();
		} catch (ClassNotFoundException e) {
			fail();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	static class Class1 {
		static {
			System.out.println(Class1.class + "init...");
		}
		public static int count = 0;
		static {
			count++;
		}
	}

	static class Class2 {
		public static int count = 0;
		static {
			System.out.println("Class2 init");
			count++;
		}
	}
}
