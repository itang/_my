package test.test_xtend_maven

import com.google.inject.AbstractModule

class MyModule extends AbstractModule{
	  override void configure() {
            bind(typeof(PersonExtensions))
      }
}