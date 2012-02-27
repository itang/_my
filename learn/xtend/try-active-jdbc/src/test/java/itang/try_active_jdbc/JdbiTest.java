package itang.try_active_jdbc;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.ResultIterator;
import org.skife.jdbi.v2.util.StringMapper;

public class JdbiTest {

  @Test
  public void test() {
    DBI dbi = new DBI("jdbc:h2:~/activejdbc-test-db", "sa", "");
    Handle handle = dbi.open();
    handle.execute("insert into employees (first_name, last_name) values(?, ?)", "itang", "tang");
    handle.createStatement("insert into employees(first_name, last_name) values (:first_name, :last_name)")
        .bind("first_name", "tqibm").bind("last_name", "tang").execute();

    System.out.println("//////////////////////");
    for (Map<String, Object> it : handle.select("select * from employees")) {
      System.out.println(it.get("id") + ":" + it.get("first_name") + ":" + it.get("last_name"));
    }

    System.out.println("/////////////");
    String firstName = handle.createQuery("select first_name from employees where last_name = :last_name")
        .bind("last_name", "tang").map(StringMapper.FIRST).first();
    System.out.println("first_name:" + firstName);
    System.out.println("//////////////////");

    Query<Map<String, Object>> q = handle.createQuery("select last_name from employees order by id");
    Query<String> q2 = q.map(StringMapper.FIRST);
    List<String> rs = q2.list();
    for (String lastName : rs) {
      System.out.println(lastName);
    }

    System.out.println("//////////////////");
    ResultIterator<String> resultIterator = handle.createQuery("select last_name from employees order by id")
        .map(StringMapper.FIRST).iterator();
    while (resultIterator.hasNext()) {
      System.out.println(resultIterator.next());
    }

    // clean
    handle.execute("delete from employees");

    // make sure to close it!
    handle.close();
  }

}
