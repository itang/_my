package com.example.android.rssfeed;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends Activity {
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      this.finish();
      return;
    }
    setContentView(R.layout.activity_detail);
    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      String s = extras.getString("value");
      TextView view = (TextView) this.findViewById(R.id.detailsText);
      view.setText(s);
    }
  }
}
