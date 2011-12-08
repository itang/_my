package test.test_xtend_maven;

import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Main {
  public static void main(final String[] args) {
      InputOutput.<String>println("Hello");
      char _charAt = "\u00AB".charAt(0);
      InputOutput.<Integer>println(Integer.valueOf(((int) _charAt)));
      char _charAt_1 = "\u00BB".charAt(0);
      InputOutput.<Integer>println(Integer.valueOf(((int) _charAt_1)));
  }
}
