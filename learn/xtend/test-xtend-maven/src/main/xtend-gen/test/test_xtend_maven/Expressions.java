package test.test_xtend_maven;

import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Expressions {
  public String method1() {
    String _xblockexpression = null;
    {
      final String msg = "Hello,world";
      String _xtrycatchfinallyexpression = null;
      try {
        _xtrycatchfinallyexpression = msg;
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
          _xtrycatchfinallyexpression = "error";
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      final String data = _xtrycatchfinallyexpression;
      String _println = InputOutput.<String>println(data);
      _xblockexpression = (_println);
    }
    return _xblockexpression;
  }
}
