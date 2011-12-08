package test.test_xtend_maven;

import org.eclipse.xtext.xbase.lib.StringExtensions;
import test.test_xtend_maven.Person;

@SuppressWarnings("all")
public class PersonExtensions {
  public String getFullName(final Person p) {
    String _forename = p.getForename();
    String _operator_plus = StringExtensions.operator_plus(_forename, " ");
    String _name = p.getName();
    String _operator_plus_1 = StringExtensions.operator_plus(_operator_plus, _name);
    return _operator_plus_1;
  }
}
