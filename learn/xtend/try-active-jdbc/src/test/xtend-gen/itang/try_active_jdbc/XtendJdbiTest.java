package itang.try_active_jdbc;

import itang.try_active_jdbc.User;
import itang.try_active_jdbc.UserMapper;
import java.util.List;
import java.util.Map;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.tweak.HandleCallback;
import org.skife.jdbi.v2.util.StringMapper;

@SuppressWarnings("all")
public class XtendJdbiTest {
  @Test
  public void testCallback() {
      DBI _dBI = new DBI("jdbc:h2:~/activejdbc-test-db", "sa", "");
      final DBI dbi = _dBI;
      final Function1<Handle,Void> _function = new Function1<Handle,Void>() {
          public Void apply(final Handle handle) {
            Void _xblockexpression = null;
            {
              handle.execute("drop table if exists test");
              handle.execute("create table test( name varchar(255))");
              handle.execute("insert into test (name) values(?)", "itang");
              List<Map<String,Object>> _select = handle.select("select * from test");
              for (final Map<String,Object> it : _select) {
                Object _get = it.get("name");
                InputOutput.<Object>println(_get);
              }
              Query<Map<String,Object>> _createQuery = handle.createQuery("select name from test");
              Query<String> _map = _createQuery.<String>map(StringMapper.FIRST);
              String _first = _map.first();
              final String name = _first;
              Matcher<String> _equalTo = IsEqual.<String>equalTo("itang");
              Assert.<String>assertThat(name, _equalTo);
              Query<Map<String,Object>> _createQuery_1 = handle.createQuery("select name from test");
              Query<User> _map_1 = _createQuery_1.<User>map(UserMapper.INSTANCE);
              User _first_1 = _map_1.first();
              final User user = _first_1;
              String _name = user.getName();
              Matcher<String> _equalTo_1 = IsEqual.<String>equalTo("itang");
              Assert.<String>assertThat(_name, _equalTo_1);
              _xblockexpression = (null);
            }
            return _xblockexpression;
          }
        };
      dbi.<Void>withHandle(new HandleCallback<Void>() {
          public Void withHandle(Handle handle) {
            return _function.apply(handle);
          }
      });
  }
}
