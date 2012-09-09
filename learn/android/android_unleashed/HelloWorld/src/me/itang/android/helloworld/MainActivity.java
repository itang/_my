package me.itang.android.helloworld;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
  private TextView view;

  @Override
  public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Log.v(MainActivity.class.getSimpleName(), "VERBOSE");
    Log.d(MainActivity.class.getSimpleName(), "DEBUG");
    Log.i(MainActivity.class.getSimpleName(), "INFO");
    Log.w(MainActivity.class.getSimpleName(), "WARN");
    Log.e(MainActivity.class.getSimpleName(), "ERROR");

    // 获取资源
    Resources res = this.getResources();
    // 获取字符资源
    String myName = res.getString(R.string.my_name);

    view = (TextView) findViewById(R.id.txtHello);
    view.setText(view.getText() + ", " + myName
        + "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");

    Button next = (Button) findViewById(R.id.btnNext);
    next.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, Activity2.class);
        // intent.getExtras().putString("msg", view.getText().toString());
        MainActivity.this.startActivity(intent);
      }
    });

    StringBuilder sb = new StringBuilder("tels:");
    ContentResolver cr = this.getContentResolver();
    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, new String[] {

    }, null, null, null);
    while (cursor.moveToNext()) {
      // 名字
      int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
      String contact = cursor.getString(nameFieldColumnIndex);

      // 电话号码
      int numberFieldColumnIndex = cursor.getColumnIndex(PhoneLookup.NUMBER);
      String number = numberFieldColumnIndex > -1 ? cursor.getString(numberFieldColumnIndex) : "";
      sb.append(contact).append(": ").append(number).append("\n");
    }
    cursor.close();

    TextView result = (TextView) findViewById(R.id.result);
    result.setText(sb.toString());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }
}