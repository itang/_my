package itang.try_active_jdbc;

import itang.try_active_jdbc.User;
import java.sql.ResultSet;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

@SuppressWarnings("all")
public class UserMapper implements ResultSetMapper<User> {
  public static UserMapper INSTANCE = new Function0<UserMapper>() {
    public UserMapper apply() {
      UserMapper _userMapper = new UserMapper();
      return _userMapper;
    }
  }.apply();
  
  public User map(final int index, final ResultSet r, final StatementContext ctx) {
    String _string = r.getString("name");
    User _user = new User(_string);
    return _user;
  }
}
