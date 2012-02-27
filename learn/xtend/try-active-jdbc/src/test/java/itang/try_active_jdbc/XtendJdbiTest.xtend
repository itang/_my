package itang.try_active_jdbc

import org.junit.Test
import static org.hamcrest.core.IsEqual.*
import static org.junit.Assert.*

import org.skife.jdbi.v2.DBI
import org.skife.jdbi.v2.util.StringMapper

class XtendJdbiTest{
	@Test
	def void testCallback(){
		val DBI dbi = new DBI("jdbc:h2:~/activejdbc-test-db", "sa", "")
    
    dbi.<Void>withHandle(handle | {
    	handle.execute("drop table if exists test")
    	handle.execute("create table test( name varchar(255))")
    	handle.execute("insert into test (name) values(?)", "itang")
    	for(/*Map<String, Object>*/ it : handle.select("select * from test")){
    		println(it.get("name"))
    	}
    	
    	val name = handle.createQuery("select name from test")
    	 .map(StringMapper::FIRST)
    	 .first()
    	assertThat(name, equalTo("itang"))
    	
    	val User user = handle.createQuery("select name from test")
    	   .map(UserMapper::INSTANCE)
    	   .first()
    	assertThat(user.name, equalTo("itang"))
    	
		// handle.createQuery("select name from test")
    	//   .map(a,b,x | new User(b.getString("name")))
    	//   .first() 
		
    	null //let xtend compiler happy
    })
	}
}
