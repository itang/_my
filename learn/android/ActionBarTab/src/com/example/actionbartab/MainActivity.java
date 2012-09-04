package com.example.actionbartab;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActionBar actionBar = getActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    actionBar.setDisplayShowTitleEnabled(false);

    Tab tab = actionBar.newTab().setText("First tab")
        .setTabListener(new MyTabListener<DetailFragment>(this, "artist", DetailFragment.class));
    actionBar.addTab(tab);

    tab = actionBar.newTab().setText("Second Tab")
        .setTabListener(new MyTabListener<ImageFragment>(this, "album", ImageFragment.class));
    actionBar.addTab(tab);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

  public static class MyTabListener<T extends Fragment> implements TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;

    public MyTabListener(Activity activity, String tag, Class<T> clz) {
      mActivity = activity;
      mTag = tag;
      mClass = clz;
    }

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
      if (mFragment == null) {
        mFragment = Fragment.instantiate(mActivity, mClass.getName());
        ft.add(android.R.id.content, mFragment, mTag);
      } else {
        ft.setCustomAnimations(android.R.animator.fade_in, R.animator.slide_in);
        ft.attach(mFragment);
      }
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
      if (mFragment != null) {
        ft.setCustomAnimations(android.R.animator.fade_in, R.animator.slide_in);
        ft.detach(mFragment);
      }
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
  }

}
