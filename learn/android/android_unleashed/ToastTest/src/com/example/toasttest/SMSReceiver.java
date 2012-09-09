package com.example.toasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    Bundle bundle = intent.getExtras();
    Object messages[] = (Object[]) bundle.get("pdus");
    SmsMessage smsMessages[] = new SmsMessage[messages.length];
    for (int n = 0; n < messages.length; n++) {
      smsMessages[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
    }

    // 产生一个Toast
    Toast toast;
    toast = Toast.makeText(context, "短信内容: " + smsMessages[0].getMessageBody(),
    /*
     * new String(smsMessages[0].getMessageBody().getBytes("ISO8859-1"),
     * "UTF-8"),
     */Toast.LENGTH_LONG);
    toast.show();
  }
}
