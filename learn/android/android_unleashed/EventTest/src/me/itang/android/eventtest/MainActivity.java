package me.itang.android.eventtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button ok = (Button) findViewById(R.id.ok);
    ok.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        info("点击了OK按钮");
      }
    });
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    switch (keyCode) {
    case KeyEvent.KEYCODE_DPAD_CENTER:
      info("按下：中键");
      break;
    case KeyEvent.KEYCODE_DPAD_UP:
      info("按下：上方向键");
      break;
    case KeyEvent.KEYCODE_DPAD_DOWN:
      info("按下：下方向键");
      break;
    case KeyEvent.KEYCODE_DPAD_LEFT:
      info("按下：左方向键");
      break;
    case KeyEvent.KEYCODE_DPAD_RIGHT:
      info("按下：右方向键");

      // 构建 返回键功能
      KeyEvent key = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
      return super.onKeyDown(key.getKeyCode(), key);
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
    return super.onKeyMultiple(keyCode, repeatCount, event);
  }

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    info("谈起:" + keyCode);
    return super.onKeyUp(keyCode, event);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int iAction = event.getAction();
    if (iAction == MotionEvent.ACTION_CANCEL || iAction == MotionEvent.ACTION_DOWN
        || iAction == MotionEvent.ACTION_MOVE) {
      return false;
    }
    int x = (int) event.getX();
    int y = (int) event.getY();

    info(String.format("触笔点击坐标:(%d, %d)", x, y));
    return super.onTouchEvent(event);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

  private void info(String string) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
  }
}
