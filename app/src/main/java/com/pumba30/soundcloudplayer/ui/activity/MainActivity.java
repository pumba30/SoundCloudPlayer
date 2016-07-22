package com.pumba30.soundcloudplayer.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.player.Player;
import com.pumba30.soundcloudplayer.ui.adapters.ViewPagerAdapter;

public class MainActivity extends BaseDrawerActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String KEY_TITLE = "keyTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!App.sAppInstance.getSessionManager().isUserLoggedIn()) {
            Log.d(LOG_TAG, "Start Login Activity");
            startLoginActivity(getApplicationContext());
        } else {
            mToolbar.setTitle(R.string.charts);

            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                    | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                    | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);

            ViewPager viewPager = (ViewPager) findViewById(R.id.soundcloud_view_pager);
            setupViewPager(viewPager);


            TabLayout tabLayout = (TabLayout) findViewById(R.id.soundcloud_tabs);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setupWithViewPager(viewPager);

            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int tabId = tab.getPosition();
                    if (tabId == 0) {
                        mToolbar.setTitle(R.string.charts);
                    } else if (tabId == 1) {
                        mToolbar.setTitle(R.string.like_tracks);
                        playerStop();
                    } else if (tabId == 2) {
                        mToolbar.setTitle(R.string.playlists);
                        playerStop();
                    } else if (tabId == 3) {
                        mToolbar.setTitle(R.string.search);
                        playerStop();
                    }
                }
            });
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(),
                getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void playerStop() {
        if (Player.getInstance(getApplicationContext()).isPlayingPlayer()) {
            Player.getInstance(getApplicationContext()).stopPlayer();
        }
    }


}
