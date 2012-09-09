package me.itang.android.android_lifecycle;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Activity2 extends BaseActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_activity2);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_activity2, menu);
    return true;
  }

  public void onToActivity1(View view) {
    this.toActivity(MainActivity.class, null);
  }
}
