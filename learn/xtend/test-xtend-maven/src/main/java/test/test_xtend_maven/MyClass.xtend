package test.test_xtend_maven

import java.util.List
import javax.annotation.Nullable

class MyClass {
    @Nullable
	String name
	new(String name){
	    this.name = name
	}
	
	def String first(List<String> elements){
	    elements.get(0);
	}
	
	def void throwException() throws Exception  {
        throw new Exception()
    }
    
	def void sneakyThrowException()  {
        throw new Exception()
    }
    
    def dispatch p(String arg){
        arg
    }
    
    def dispatch p(int arg){
       arg
    }
}
