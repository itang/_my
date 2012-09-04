package de.vogella.android.socialapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class OverviewActivity extends Activity {
  protected Object mActionMode;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_overview);

    final View view = findViewById(R.id.myView);
    view.setOnLongClickListener(new View.OnLongClickListener() {

      public boolean onLongClick(View v) {
        if (mActionMode != null) {
          return false;
        }
        mActionMode = OverviewActivity.this.startActionMode(mActionModeCallback);
        view.setSelected(true);
        return true;
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.mainmenu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case R.id.menuitem1:
      Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT).show();
      break;
    case R.id.menuitem2:
      Toast.makeText(this, "Menu item 2 selected", Toast.LENGTH_SHORT).show();
      break;
    default:
      Toast.makeText(this, "Just a test", Toast.LENGTH_SHORT).show();
      break;
    }
    return true;
  }

  private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
      return false;
    }

    public void onDestroyActionMode(ActionMode mode) {
      mActionMode = null;
    }

    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
      MenuInflater inflater = mode.getMenuInflater();
      inflater.inflate(R.menu.contextual, menu);
      return true;
    }

    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
      switch (item.getItemId()) {
      case R.id.toast:
        Toast.makeText(OverviewActivity.this, "Selected menu", Toast.LENGTH_LONG).show();
        mode.finish(); // Action picked, so close the CAB
        return true;
      default:
        return false;
      }
    }
  };
}
