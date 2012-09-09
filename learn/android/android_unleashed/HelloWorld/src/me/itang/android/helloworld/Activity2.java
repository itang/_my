package me.itang.android.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Activity2 extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity2);
    
    Intent intent = this.getIntent();
    TextView view = (TextView)this.findViewById(R.id.editText1);
    //view.setText(intent.getExtras().getString("msg"));
  }
}
