package me.itang.android.mp3.player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {

  private MediaPlayer player;

  @Override
  public IBinder onBind(Intent arg0) {

    return null;
  }

  public void onStart(Intent intent, int startId) {
    super.onStart(intent, startId);
    player = MediaPlayer.create(this, R.raw.a);
    player.start();
  }

  public void onDestroy() {
    super.onDestroy();
    player.stop();
  }
}
