package test.test_xtend_maven;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class MyClass {
  @Nullable
  private String name;
  
  public MyClass(final String name) {
    this.name = name;
  }
  
  public String first(final List<String> elements) {
    String _get = elements.get(0);
    return _get;
  }
  
  public void throwException() throws Exception {
    Exception _exception = new Exception();
    throw _exception;
  }
  
  public void sneakyThrowException() {
    try {
      Exception _exception = new Exception();
      throw _exception;
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  protected Comparable<? extends Object> _p(final String arg) {
    return arg;
  }
  
  protected Comparable<? extends Object> _p(final int arg) {
    return arg;
  }
  
  public Comparable<? extends Object> p(final Comparable<? extends Object> arg) {
    if (arg instanceof Integer) {
      return _p((Integer)arg);
    } else if (arg instanceof String) {
      return _p((String)arg);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(arg).toString());
    }
  }
}
