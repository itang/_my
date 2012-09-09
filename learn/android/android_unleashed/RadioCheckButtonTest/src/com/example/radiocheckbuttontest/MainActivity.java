package com.example.radiocheckbuttontest;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class MainActivity extends Activity {
  TextView textView, textView1;
  RadioGroup radioGroup;
  RadioButton radio1, radio2, radio3, radio4;
  CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
  private static final String[] countries = { "O型", "A型", "B型", "C型", "其他" };
  Spinner spinner;

  private static final String[] autoString = { "a2", "abf", "abe", "abcde", "abc2", "abcd3", "abced2", "abc2", "abcd2",
      "abcde2" };

  private DatePicker datePicker;
  private TimePicker timePicker;
  Button dpButton;
  Button dtButton;
  Calendar c;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    textView = (TextView) findViewById(R.id.textView1);
    textView1 = (TextView) findViewById(R.id.textView2);
    radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);

    radio1 = getRadio(R.id.radio0);
    radio1.setChecked(false);
    radio2 = getRadio(R.id.radio1);
    radio3 = getRadio(R.id.radio2);
    radio4 = getRadio(R.id.radio3);

    // /////////////////////////////////////////////////////////////////////////////
    checkBox1 = getCheckBox(R.id.checkBox1);
    checkBox2 = getCheckBox(R.id.checkBox2);
    checkBox3 = getCheckBox(R.id.checkBox3);
    checkBox4 = getCheckBox(R.id.checkBox4);

    spinner = (Spinner) findViewById(R.id.spinner1);

    radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == radio2.getId()) {
          Toast.makeText(MainActivity.this, "正确答案:" + radio2.getText(), Toast.LENGTH_LONG).show();
        } else {
          Toast.makeText(MainActivity.this, "回答错误", Toast.LENGTH_SHORT).show();
        }
      }
    });

    setOnCheckedChangeListers(checkBox1, checkBox2, checkBox3, checkBox4);

    // //////////////////////////////////////////////////////////////////////////////
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
    spinner.setAdapter(adapter);

    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, "你的血型是: " + countries[position], Toast.LENGTH_SHORT).show();
        view.setVisibility(View.VISIBLE);
      }

      public void onNothingSelected(AdapterView<?> parent) {
      }
    });

    // /////////////////////////////////////////////////////////////////////////////////
    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
        autoString);
    AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
    acTextView.setAdapter(adapter1);

    MultiAutoCompleteTextView macTextView = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
    macTextView.setAdapter(adapter1);
    macTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    // ////////////////////////////
    c = Calendar.getInstance();
    dpButton = (Button) findViewById(R.id.button2);
    dtButton = (Button) findViewById(R.id.button3);
    datePicker = (DatePicker) findViewById(R.id.datePicker1);
    timePicker = (TimePicker) findViewById(R.id.timePicker1);

    datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
        new OnDateChangedListener() {
          public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
          }
        });

    timePicker.setIs24HourView(true);

    timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
      public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

      }
    });

    dpButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        new DatePickerDialog(MainActivity.this, new OnDateSetListener() {
          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //
          }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
      }
    });

    dtButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        new TimePickerDialog(MainActivity.this, new OnTimeSetListener() {
          public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
          }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
      }
    });
  }

  private void setOnCheckedChangeListers(CheckBox... checkBoxs) {
    for (CheckBox cb : checkBoxs) {
      cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          if (buttonView.isChecked()) {
            Toast.makeText(MainActivity.this, "你选择了:" + buttonView.getText(), Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }

  private CheckBox getCheckBox(int id) {
    return (CheckBox) findViewById(id);
  }

  private RadioButton getRadio(int id) {
    return (RadioButton) findViewById(id);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

  public void onClick(View view) {
    int sum = 0;
    for (CheckBox cb : new CheckBox[] { checkBox1, checkBox2, checkBox3, checkBox4 }) {
      if (cb.isChecked()) {
        sum++;
      }
    }
    Toast.makeText(MainActivity.this, "谢谢参与, 你共选择了" + sum + "项!", Toast.LENGTH_SHORT).show();
  }
}
