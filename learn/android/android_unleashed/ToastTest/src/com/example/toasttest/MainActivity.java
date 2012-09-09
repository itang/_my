package com.example.toasttest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
  private TextView textView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    textView = (TextView) findViewById(R.id.textView1);

    String string = "Toast示例, 当收到短信时, 我们会提示,欢迎使用! ";
    textView.setText(string);
    textView.setTextSize(30);

    Button button = (Button) findViewById(R.id.button1);
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        DisplayToast("短信内容在这里显示");
      }
    });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

  private void DisplayToast(String string) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
  }
}
