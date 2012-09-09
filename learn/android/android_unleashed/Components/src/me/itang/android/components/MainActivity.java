package me.itang.android.components;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView textView = (TextView) findViewById(R.id.textview);
    textView.setTextColor(Color.RED);
    textView.setTextSize(24);
    textView.setBackgroundColor(Color.BLUE);

    textView.setText("控件, nihao!");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }
  
  public void toList(View view) {
    this.toActivity(Activity2.class);
  }
}
