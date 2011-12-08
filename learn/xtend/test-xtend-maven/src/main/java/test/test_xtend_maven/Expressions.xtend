package test.test_xtend_maven

class Expressions {
	def method1(){
	    val msg = "Hello,world"
	    val data = try{
	        msg
	    }catch(Exception e){
	        "error"
	    }
	    println(data)
	}
}