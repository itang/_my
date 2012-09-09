package me.itang.android.components;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BaseActivity extends Activity {

  private String tag = this.getClass().getSimpleName();

  protected void info(String msg) {
    Log.v(tag, msg);
  }

  protected void tip(String string) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
  }

  protected void toActivity(Class<? extends Activity> clazz) {
    this.toActivity(clazz, null);
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
