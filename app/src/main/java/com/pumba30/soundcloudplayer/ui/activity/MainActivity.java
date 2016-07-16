package com.pumba30.soundcloudplayer.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.ui.fragments.DemoTracksFragment;
import com.pumba30.soundcloudplayer.ui.fragments.LoggedUserFragment;

public class MainActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!App.isUserLogged()) {
            startReplaceDemoTracksFragment();
        } else {
            startUserLoggedFragment();
        }
    }

    public void startReplaceDemoTracksFragment() {
        Fragment demoFragment = DemoTracksFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, demoFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void startUserLoggedFragment() {
        Fragment loggedUserFragment = LoggedUserFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, loggedUserFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
