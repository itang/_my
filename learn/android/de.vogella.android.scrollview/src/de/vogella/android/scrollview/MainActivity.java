package de.vogella.android.scrollview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView view = (TextView) findViewById(R.id.TextView02);
    String s = "";
    for (int i = 0; i < 100; i++) {
      s += "vogella.com ";
    }
    view.setText(s);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }
}
