package itang.try_active_jdbc;

@SuppressWarnings("all")
public class User {
  private String name;
  
  public User(final String name) {
    this.name = name;
  }
  
  public String getName() {
    return this.name;
  }
}
