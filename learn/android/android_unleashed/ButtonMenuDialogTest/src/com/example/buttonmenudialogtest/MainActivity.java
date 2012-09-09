package com.example.buttonmenudialogtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

  Button button1, button2;

  ProgressDialog progressDialog;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    buttonTest();
    dialogTest();
  }

  private void dialogTest() {
    Dialog dialog = new AlertDialog.Builder(this).setTitle("登录提示").setMessage("这里还需要登录!")
        .setPositiveButton("确定", new OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            LayoutInflater factory = LayoutInflater.from(MainActivity.this);
            final View dialogView = factory.inflate(R.layout.dialog, null);

            AlertDialog dlg = new AlertDialog.Builder(MainActivity.this).setTitle("登录框").setView(dialogView)
                .setPositiveButton("确定", new OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                    progressDialog = ProgressDialog.show(MainActivity.this, "请等待...", "正在为你登录...", true);
                    new Thread() {
                      public void run() {
                        try {
                          sleep(300);
                        } catch (Exception e) {
                          e.printStackTrace();
                        } finally {
                          progressDialog.dismiss();
                        }
                      }
                    }.start();

                  }
                }).setNegativeButton("取消", new OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.finish();
                  }
                }).create();
            dlg.show();
          }
        }).setNegativeButton("退出", new OnClickListener() {

          public void onClick(DialogInterface dialog, int which) {
            MainActivity.this.finish();
          }
        }).create();

    dialog.show();
  }

  private void buttonTest() {
    button1 = (Button) findViewById(R.id.button1);
    button2 = (Button) findViewById(R.id.button2);
    button1.setWidth(150);
    button2.setWidth(100);
    button1.setTextColor(Color.GREEN);
    button2.setTextColor(Color.RED);

    button1.setTextSize(30);
    button2.setTextSize(20);

    button1.setOnClickListener(new android.view.View.OnClickListener() {
      public void onClick(View v) {
        Toast toast = Toast.makeText(MainActivity.this, "你点击了\"" + button1.getText() + "\"按钮", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 150);
        toast.show();
      }
    });

    button2.setOnClickListener(new android.view.View.OnClickListener() {
      public void onClick(View v) {
        Toast toast = Toast.makeText(MainActivity.this, "你点击了\"" + button2.getText() + "\"按钮", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 150);
        toast.show();
        MainActivity.this.finish();
      }
    });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // 通过资源文件构建
    getMenuInflater().inflate(R.menu.menu, menu);

    // 编程方式构建菜单
    menu.add(0, 100, 1, "关于1");
    menu.add(0, 101, 2, "退出1");

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    switch (itemId) {
    case R.id.item1:
    case 100:
      about();
      break;
    case R.id.item2:
    case 101:
      exit();
      break;
    }

    return true;
  }

  private void exit() {
    this.finish();
  }

  private void about() {
    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
    MainActivity.this.startActivity(intent);
    this.finish();
  }
}
