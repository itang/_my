package com.example.android.rssfeed;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyListFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_rsslist_overview, container, false);
    Button button = (Button) view.findViewById(R.id.button1);
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        updateDetail();
      }
    });
    return view;
  }

  // May also be triggered from the Activity
  public void updateDetail() {
    String newTime = String.valueOf(System.currentTimeMillis());
    DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
    if (fragment != null && fragment.isInLayout() && fragment.isVisible()) {
      fragment.setText(newTime);
    } else {
      Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
      intent.putExtra("value", newTime);
      startActivity(intent);
    }
  }
}
