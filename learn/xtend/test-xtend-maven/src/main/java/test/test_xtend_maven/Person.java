package test.test_xtend_maven;

public class Person {
  private String name;
  private String forename;

  public Person(String name, String forename) {
    super();
    this.name = name;
    this.forename = forename;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getForename() {
    return forename;
  }

  public void setForename(String forename) {
    this.forename = forename;
  }

}
