package com.example.edittexttest;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

  private TextView textView;
  private EditText editText;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    textView = (TextView) findViewById(R.id.textView1);
    editText = (EditText) findViewById(R.id.editText1);

    textView.setText("");// clear

    editText.setHint("请输入帐号:");
    editText.setOnKeyListener(new OnKeyListener() {
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        textView.setText("文本框的内容是:" + editText.getText().toString());
        return false;
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }
}
