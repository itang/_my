package com.itang.learn.diveintojava;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

public class CompilerTest extends Assert {

	@Test
	public void test_classload_equals() {
		assertEquals(Thread.currentThread().getContextClassLoader(),
				CompilerTest.class.getClassLoader());
	}

	@Test
	public void test_compiler_stringSource() {
		final SimpleCompilationUnit compileUnit = new MySimpleCompilationUnit();

		boolean result = compileUnit.compile();
		assertTrue(result);

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		try {
			// 加载类
			Class<?> clazz = classLoader.loadClass(compileUnit.getName());
			// Class<?> clazz =
			// classLoader.loadClass("com.itang.learn.diveintojava.Main");

			// 执行public static void main(String[] args)方法
			invokeMethod(clazz, "main", new Class<?>[] { String[].class },
					null, new Object[] { new String[] {} });
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void test_compiler_stringSource_expr() {
		int result = exprCompute("1 + 1", Integer.class);
		assertEquals(2, result);

		assertEquals("hello, world",
				exprCompute("\"hello\" + \", world\"", String.class));
	}

	@SuppressWarnings("unchecked")
	private <T> T exprCompute(String expr, Class<T> resultClass) {
		final SimpleCompilationUnit compileUnit = new ExprSimpleCompileUnit(
				expr);
		boolean result = compileUnit.compile();
		assertTrue(result);

		ClassLoader classLoader = CompilerTest.class.getClassLoader();
		try {
			Class<?> clazz = classLoader.loadClass(compileUnit.getName());
			// classLoader.
			Object executionResult = invokeMethod(clazz, "compute",
					new Class<?>[] {}, clazz.newInstance(), new Object[] {});
			return (T) executionResult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error", e);
		}
	}

	private static Object invokeMethod(Class<?> clazz, String method,
			Class<?>[] argsTypes, Object target, Object[] args) {
		try {
			Method mainMethod = clazz.getMethod(method, argsTypes);
			return mainMethod.invoke(target, args);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error", e);
		}
	}

	private static class MySimpleCompilationUnit extends SimpleCompilationUnit {

		@Override
		protected String getSimpleName() {
			return "Main";
		}

		@Override
		public String getClassBodySource() {
			return new StringBuilder("public static void main(String []args){")
					.append("System.out.println(\"hello, world\");")
					.append("}").toString();
		}

	}

	private static class ExprSimpleCompileUnit extends SimpleCompilationUnit {
		private static int index = 0;

		private final String shortName = "Expr" + index++;
		private String expr;

		public ExprSimpleCompileUnit(String expr) {
			this.expr = expr;
		}

		@Override
		public String getClassBodySource() {
			String str = " public Object compute(){ return ";
			str += this.expr + "; }  ";
			return str;
		}

		@Override
		public String getSimpleName() {
			return shortName;
		}
	}

}
