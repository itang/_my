package me.itang.android.android_lifecycle;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends BaseActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

  public void onToActivity2(View view) {
    this.toActivity(Activity2.class, null);
  }

  public void onExit(View view) {
    this.finish();
  }
}
