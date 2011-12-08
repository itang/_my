package test.test_xtend_maven;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.IsEqual.*;

import org.junit.Test;

public class AddTest {

  @Test
  public void test() {
    assertThat(Adds.add(1, 2), equalTo(3));
  }
}
