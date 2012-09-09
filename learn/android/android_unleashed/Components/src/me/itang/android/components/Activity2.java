package me.itang.android.components;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout.LayoutParams;

public class Activity2 extends BaseActivity {

  LinearLayout linearLayout;
  ListView listView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // setContentView(R.layout.activity_activity2);
    linearLayout = new LinearLayout(this);
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    linearLayout.setBackgroundColor(Color.BLACK);

    listView = new ListView(this);
    LayoutParams param = new LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);

    listView.setBackgroundColor(Color.BLACK);

    linearLayout.addView(listView, param);

    Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
    this.startManagingCursor(cursor);

    ListAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] {
        PhoneLookup.DISPLAY_NAME/*, PhoneLookup.NUMBER*/ }, new int[] { android.R.id.text1/*, android.R.id.text2*/ });

    listView.setAdapter(adapter);
    listView.setOnItemSelectedListener(new OnItemSelectedListener() {

      public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        tip("滚动到第" + arg0.getSelectedItemId() + "项");
      }

      public void onNothingSelected(AdapterView<?> arg0) {

      }
    });

    listView.setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        tip("选中了第" + (arg2 + 1) + "项");
      }
    });
    setContentView(linearLayout);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_activity2, menu);
    return true;
  }
}
