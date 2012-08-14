package com.deftype.tryloop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import loop.Loop;

public class GenJavaSourceTest {

  public static void main(String[] args) throws NoSuchMethodException, SecurityException,
      IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    final String loopFile = "./src-loop/hello.loop";
    long start = System.currentTimeMillis();

    Loop.run(loopFile);

    System.out.println("cost time: " + (System.currentTimeMillis() - start) + "ms");

    Class<?> clazz = Loop.compile(loopFile);
    System.out.println(clazz.getName());
    //Class<?> clazz1 = Loop.compile(loopFile);
    //System.out.println(clazz1.getName());
    Method[] methods = clazz.getMethods();
    for (Method method : methods) {
      System.out.println(method.getName());
    }
    // Method method = clazz.getMethod("main", new Class[] { String[].class });
    // method.invoke(null, null);
  }
}
