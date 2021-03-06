package com.pumba30.soundcloudplayer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.managers.PreferencesManager;
import com.pumba30.soundcloudplayer.player.Player;
import com.pumba30.soundcloudplayer.ui.adapters.ViewPagerAdapter;
import com.pumba30.soundcloudplayer.ui.dialogFragments.CreatePlaylistDialog;

public class MainActivity extends BaseDrawerActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String KEY_TITLE = "keyTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mToolbar.setTitle(savedInstanceState.getString(KEY_TITLE));
            mToolbar.setTitleTextAppearance(getApplicationContext(), R.style.TextAppearanceToolbar);
        } else {
            mToolbar.setTitle(R.string.charts);
        }

        if (!App.getInstance().getSessionManager().isUserLoggedIn()) {
            Log.d(LOG_TAG, "Start Login Activity");
            startLoginActivity(getApplicationContext());
        } else {

            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                    | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                    | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);

            final ViewPager viewPager = (ViewPager) findViewById(R.id.soundcloud_view_pager);
            setupViewPager(viewPager);

            mTabLayout.setupWithViewPager(viewPager);

            mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int tabId = tab.getPosition();
                    switch (tabId) {
                        case 0:
                            viewPager.setCurrentItem(0);
                            mToolbar.setTitle(R.string.charts);
                            break;
                        case 1:
                            viewPager.setCurrentItem(1);
                            mToolbar.setTitle(R.string.like_tracks);
                            playerStop();
                            break;
                        case 2:
                            viewPager.setCurrentItem(2);
                            mToolbar.setTitle(R.string.playlists);
                            playerStop();
                            break;
                        case 3:
                            viewPager.setCurrentItem(3);
                            mToolbar.setTitle(R.string.stations);
                            playerStop();
                            break;

                        default:
                            viewPager.setCurrentItem(tab.getPosition());
                            mToolbar.setTitle(R.string.charts);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_main_search) {
            startSearchActivity();
            hideActionBar();
            PreferencesManager.setLauchedSearchActivity(getApplicationContext(), true);
            return true;
        } else if (itemId == R.id.menu_create_playlist) {
            CreatePlaylistDialog dialog = CreatePlaylistDialog.newInstance();
            dialog.show(getSupportFragmentManager(), "createPlaylistDialog");
            return true;
        }
        return false;
    }

    private void startSearchActivity() {
        Intent intent = SearchActivity.newIntent(getApplicationContext());
        startActivity(intent);
    }

    private void hideActionBar() {
        mToolbar.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String title = (String) mToolbar.getTitle();
        outState.putString(KEY_TITLE, title);
        super.onSaveInstanceState(outState);
    }

}
