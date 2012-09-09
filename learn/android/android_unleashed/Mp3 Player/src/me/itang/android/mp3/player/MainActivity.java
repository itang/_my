package me.itang.android.mp3.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

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

  public void onStart(View view) {
    this.startService(new Intent("me.itang.android.MUSIC"));
  }

  public void onStop(View view) {
    this.stopService(new Intent("me.itang.android.MUSIC"));
  }
}
