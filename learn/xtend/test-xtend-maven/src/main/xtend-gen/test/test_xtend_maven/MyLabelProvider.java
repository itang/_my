package test.test_xtend_maven;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import test.test_xtend_maven.AbstractLabelProvider;

@SuppressWarnings("all")
public class MyLabelProvider extends AbstractLabelProvider {
  protected String _label(final Method it) {
    return "method";
  }
  
  protected String _label(final Field it) {
    return "field";
  }
  
  public String label(final Object it) {
    if (it instanceof Field) {
      return _label((Field)it);
    } else if (it instanceof Method) {
      return _label((Method)it);
    } else if (it != null) {
      return _label(it);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(it).toString());
    }
  }
}
