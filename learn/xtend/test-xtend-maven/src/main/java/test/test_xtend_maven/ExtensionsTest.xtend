package test.test_xtend_maven

import com.google.inject.Guice
import javax.inject.Inject

import static extension java.lang.String.*
class ExtensionsTest {
    @Inject extension PersonExtensions
	def static void main(String[]args){
	    new ExtensionsTest().doSome
	}
	
	def doSome(){
	   // val instance = new ExtensionsTest()
	    Guice::createInjector(new MyModule()).injectMembers(this)
	    println ("%s, %s".format("hello", new Person("itang","ss").getFullName))
	    val String a = null
	    a.format("ss")
	}
}
