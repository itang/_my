package me.itang.android.android_lifecycle;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class BaseActivity extends Activity {

  private String tag = this.getClass().getSimpleName();

  protected void info(String msg) {
    Log.v(tag, msg);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    info("onCreate");
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onDestroy() {
    info("onDestroy");
    super.onDestroy();
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    info("onKeyDown" + ", keyCode:" + keyCode + ", event:" + event);
    return super.onKeyDown(keyCode, event);
  }

  @Override
  protected void onPause() {
    info("onPause");
    super.onPause();
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    info("onPostCreate");
    super.onPostCreate(savedInstanceState);
  }

  @Override
  protected void onPostResume() {
    info("onPostResume");
    super.onPostResume();
  }

  @Override
  protected void onRestart() {
    info("onRestart");
    super.onRestart();
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    info("onRestoreInstanceState");
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  protected void onResume() {
    info("onResume");
    super.onResume();
  }

  @Override
  protected void onStart() {
    info("onStart");
    super.onStart();
  }

  @Override
  protected void onStop() {
    info("onStop");
    super.onStop();
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    info("onTouchEvent" + ", event:" + event.getAction());
    return super.onTouchEvent(event);
  }

  protected void toActivity(Class<? extends Activity> clazz, Map<String, Object> extraData) {
    Intent intent = new Intent(this, clazz);
    if (extraData != null) {
      for (Map.Entry<String, Object> it : extraData.entrySet()) {
        intent.getExtras().putString(it.getKey(), it.getValue().toString());

      }
    }
    this.startActivity(intent);
  }
}
