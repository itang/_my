package test.test_xtend_maven;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.BigDecimalExtensions;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IntegerExtensions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import test.test_xtend_maven.Button;
import test.test_xtend_maven.ClickListener;

@SuppressWarnings("all")
public class Literals {
  public void string() {
      final String s = "hello,world";
      final String s1 = "hello,world";
      final String s2 = "hello\"\",world";
      final String s3 = "hello\n                world";
  }
  
  public void integers() {
      final int a = 1;
      final int a1 = 11111111;
  }
  
  public void longs() {
      final long i = 1;
      final long i1 = 1111111111;
  }
  
  public void booleans() {
      final boolean b = true;
      final boolean b1 = false;
  }
  
  public void nulls() {
      final Object n = null;
      final Void n1 = null;
  }
  
  public void functions() {
    final Function1<String,Boolean> _function = new Function1<String,Boolean>() {
        public Boolean apply(final String x) {
          boolean _operator_equals = ObjectExtensions.operator_equals(x, "itang");
          return Boolean.valueOf(_operator_equals);
        }
      };
    final Function1<? super String,? extends Boolean> f = _function;
  }
  
  public String type_casts() {
    String _xblockexpression = null;
    {
      final String a = "ss";
      _xblockexpression = (((String) a));
    }
    return _xblockexpression;
  }
  
  public BigDecimal OperatorOverloading() {
    BigDecimal _xblockexpression = null;
    {
      BigDecimal _bigDecimal = new BigDecimal("2.71");
      final BigDecimal x = _bigDecimal;
      BigDecimal _bigDecimal_1 = new BigDecimal("3.14");
      final BigDecimal y = _bigDecimal_1;
      BigDecimal _operator_plus = BigDecimalExtensions.operator_plus(x, y);
      _xblockexpression = (_operator_plus);
    }
    return _xblockexpression;
  }
  
  public void Typing() {
    ArrayList<String> _arrayList = new ArrayList<String>();
    final List<String> list = _arrayList;
  }
  
  public int Null_Safe() {
    int _xblockexpression = (int) 0;
    {
      final String a = "ss";
      int _length = a==null?0:a.length();
      _xblockexpression = (_length);
    }
    return _xblockexpression;
  }
  
  public CharSequence get_templates(final Button b) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = b.getName();
    _builder.append(_name, "");
    return _builder;
  }
  
  public List<String> closure() {
    List<String> _xblockexpression = null;
    {
      final Function1<String,Boolean> _function = new Function1<String,Boolean>() {
          public Boolean apply(final String s) {
            int _length = s.length();
            boolean _operator_greaterThan = IntegerExtensions.operator_greaterThan(_length, 3);
            return _operator_greaterThan;
          }
        };
      final Function1<String,Boolean> func = _function;
      ArrayList<String> _arrayList = new ArrayList<String>();
      final List<String> list = _arrayList;
      IterableExtensions.<String>findFirst(list, func);
      final Function1<String,Boolean> _function_1 = new Function1<String,Boolean>() {
          public Boolean apply(final String x) {
            int _length = x==null?0:x.length();
            boolean _operator_lessThan = IntegerExtensions.operator_lessThan(_length, 4);
            return Boolean.valueOf(_operator_lessThan);
          }
        };
      IterableExtensions.<String>findFirst(list, _function_1);
      ArrayList<String> _newArrayList = CollectionLiterals.<String>newArrayList(" Foo", " Bar");
      final Function1<String,Boolean> _function_2 = new Function1<String,Boolean>() {
          public Boolean apply(final String e) {
            boolean _operator_equals = ObjectExtensions.operator_equals(e, " Bar");
            return Boolean.valueOf(_operator_equals);
          }
        };
      IterableExtensions.<String>filter(_newArrayList, _function_2);
      final Function0<String> _function_3 = new Function0<String>() {
          public String apply() {
            return " foo";
          }
        };
      final Function0<String> c = _function_3;
      Button _button = new Button();
      final Procedure1<Button> _function_4 = new Procedure1<Button>() {
          public void apply(final Button b) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("hello,");
            _builder.append(b, "");
            _builder.append(" - ");
            CharSequence __templates = Literals.this.get_templates(b);
            _builder.append(__templates, "");
            InputOutput.<CharSequence>println(_builder);
          }
        };
      _button.onClick(new ClickListener() {
          public void perform(Button p0) {
            _function_4.apply(p0);
          }
      });
      ArrayList<String> _newArrayList_1 = CollectionLiterals.<String>newArrayList("a");
      final ArrayList<String> listOfStrings = _newArrayList_1;
      final Function1<String,String> _function_5 = new Function1<String,String>() {
          public String apply(final String e) {
            String _xblockexpression = null;
            {
              boolean _operator_equals = ObjectExtensions.operator_equals(e, null);
              if (_operator_equals) {
                return " NULL";
              }
              String _upperCase = e.toUpperCase();
              _xblockexpression = (_upperCase);
            }
            return _xblockexpression;
          }
        };
      List<String> _map = ListExtensions.<String, String>map(listOfStrings, _function_5);
      _xblockexpression = (_map);
    }
    return _xblockexpression;
  }
}
