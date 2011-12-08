package test.test_xtend_maven;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.InputOutput;
import test.test_xtend_maven.MyModule;
import test.test_xtend_maven.Person;
import test.test_xtend_maven.PersonExtensions;

@SuppressWarnings("all")
public class ExtensionsTest {
  @Inject
  private PersonExtensions _personExtensions;
  
  public static void main(final String[] args) {
    ExtensionsTest _extensionsTest = new ExtensionsTest();
    _extensionsTest.doSome();
  }
  
  public String doSome() {
    String _xblockexpression = null;
    {
      MyModule _myModule = new MyModule();
      Injector _createInjector = Guice.createInjector(_myModule);
      _createInjector.injectMembers(this);
      Person _person = new Person("itang", "ss");
      String _fullName = this._personExtensions.getFullName(_person);
      String _format = String.format("%s, %s", "hello", _fullName);
      InputOutput.<String>println(_format);
      final String a = null;
      String _format_1 = String.format(a, "ss");
      _xblockexpression = (_format_1);
    }
    return _xblockexpression;
  }
}
