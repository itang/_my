package itang.try_active_jdbc

import java.sql.ResultSet
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

class UserMapper implements ResultSetMapper<User>{
  public static UserMapper INSTANCE = new UserMapper()
  override User map(int index, ResultSet r, StatementContext ctx){
    return new User( r.getString("name"))
  }
}