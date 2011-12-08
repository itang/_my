package test.test_xtend_maven;

import org.eclipse.xtext.xbase.lib.DoubleExtensions;
import org.eclipse.xtext.xbase.lib.IntegerExtensions;

@SuppressWarnings("all")
public class Adds {
  public static int add(final int x1, final int x2) {
    int _operator_plus = IntegerExtensions.operator_plus(x1, x2);
    return _operator_plus;
  }
  
  public static double add(final double x1, final double x2) {
    double _operator_plus = DoubleExtensions.operator_plus(x1, x2);
    return _operator_plus;
  }
}
