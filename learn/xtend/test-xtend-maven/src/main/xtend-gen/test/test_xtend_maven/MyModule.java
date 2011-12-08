package test.test_xtend_maven;

import com.google.inject.AbstractModule;
import test.test_xtend_maven.PersonExtensions;

@SuppressWarnings("all")
public class MyModule extends AbstractModule {
  public void configure() {
    this.<PersonExtensions>bind(test.test_xtend_maven.PersonExtensions.class);
  }
}
