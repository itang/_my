package test.test_xtend_maven

import java.math.BigDecimal
import java.util.ArrayList
import java.util.List

class Literals {
	def string(){
	    val s = 'hello,world'
	    val s1 = "hello,world"
	    val s2 = "hello\"\",world"
	    val s3 ="hello
                world"
	}
	
	def integers(){
	    val a = 1
	    val a1 = 11111111
	}
	
	def longs(){
        val long i = 1
        val long i1 = 1111111111
    }
    
	def booleans(){
	    val b  = true
	    val b1 = false
	}
	
	
	def nulls(){
	    val n = null
	    val Void n1 = null
	    //val Void n2 = 1
	}
	
	def functions(){
	    val (String) => boolean f = [x | x == "itang"]
	}
	
	def type_casts(){
	    val a = "ss"
	    a as String
	}
	
	def OperatorOverloading(){
	    val x=new BigDecimal('2.71')
        val y=new BigDecimal('3.14')
        x+y // calls BigDecimalExtension.operator plus(x,y
	}
	
	def Typing(){
	    val List<String> list = new ArrayList()
	}
	
	def Null_Safe(){
	    val  a = "ss"
	    a?.length
	}
	
	def get_templates(Button b){
	    '''«b.name»'''
	}
	def closure(){
	    val func = [String s | s.length>3]
	     val List<String> list = new ArrayList()
	     list.findFirst(func)
	     list.findFirst(x | x?.length < 4)
	     
	     newArrayList(" Foo", " Bar" ).filter(e | e ==" Bar" )
	     
	     val c = [|" foo" ] // closure without parameters
	     
	     /*
	      In order to allow seamless integration with existing Java libraries such as the JDK or
        Google Guava (formerly known as Google Collect) closures are automatically coerced to
        expected types if those types declare only one method (methods from java.lang.Object do
            not count).
	      */
	      
	      new Button().onClick(b|println('''hello,«b» - «get_templates(b)»''')) 
	      
	      //In combination with the implicit it parameter, skipping empty
        //parentheses, 
        
        
        val listOfStrings = newArrayList("a")
        listOfStrings.map(e| {
                if (e==null)
                    return " NULL"
                e.toUpperCase
            })
	}
	
	
	
}