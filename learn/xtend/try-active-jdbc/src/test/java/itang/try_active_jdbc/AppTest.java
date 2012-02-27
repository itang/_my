package itang.try_active_jdbc;

import itang.try_active_jdbc.models.Employee;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppTest extends TestCase {
  final static Logger logger = LoggerFactory.getLogger(AppTest .class);

  public AppTest(String testName) {

    super(testName);
  }

  public void testActivejdbc() {
    Base.open("org.h2.Driver", "jdbc:h2:~/activejdbc-test-db", "sa", "");

    createEmployee();
    logger.info("=========> Created employee:");
    selectEmployee();
    updateEmployee();
    logger.info("=========> Updated employee:");
    selectAllEmployees();
    deleteEmployee();
    logger.info("=========> Deleted employee:");
    selectAllEmployees();
    createEmployee();
    logger.info("=========> Created employee:");
    selectEmployee();
    deleteAllEmployees();
    logger.info("=========> Deleted all employees:");
    selectAllEmployees();

    Base.close();
  }

  private static void createEmployee() {
    Employee e = new Employee();
    e.set("first_name", "John");
    e.set("last_name", "Doe");
    e.saveIt();
  }

  private static void selectEmployee() {
    Employee e = (Employee) Employee.findFirst("first_name = ?", "John");
    logger.info(e.toString());
  }

  private static void updateEmployee() {
    Employee e = (Employee) Employee.findFirst("first_name = ?", "John");
    e.set("last_name", "Steinbeck").saveIt();
  }

  private static void deleteEmployee() {
    Employee e = (Employee) Employee.findFirst("first_name = ?", "John");
    e.delete();
  }

  private static void deleteAllEmployees() {
    Employee.deleteAll();
  }

  private static void selectAllEmployees() {
    logger.info("Employees list: " + Employee.findAll());
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(AppTest.class);
  }

  /**
   * Rigourous Test :-)
   */
  public void testApp() {
    assertTrue(true);
  }
}
