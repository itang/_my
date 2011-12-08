package test.test_xtend_maven

import java.lang.reflect.Field
import java.lang.reflect.Method

class MyLabelProvider extends AbstractLabelProvider {
    def dispatch label(Method it) {
      "method"
    }
    def dispatch label(Field it) {
       "field"
    }
}