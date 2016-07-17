package com.pumba30.soundcloudplayer.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.player.Player;
import com.pumba30.soundcloudplayer.ui.adapters.ViewPagerAdapter;
import com.pumba30.soundcloudplayer.utils.PreferencesManager;


public class LoggedUserFragment extends Fragment {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private AppBarLayout.LayoutParams mParams;

    public static LoggedUserFragment newInstance() {
        return new LoggedUserFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.public_tracks);

        mParams = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
        mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_logged, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.soundcloud_view_pager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.soundcloud_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabId = tab.getPosition();
                if (tabId == 0) {
                    mToolbar.setTitle(R.string.public_tracks);
                } else if (tabId == 1) {
                    mToolbar.setTitle(R.string.like_tracks);
                    // TODO: 16.07.2016  update adapter after adding the track to collection
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
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_logged_user, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            startHomeDemoTracksFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startHomeDemoTracksFragment() {
        mParams.setScrollFlags(0);
        mToolbar.setTitle(R.string.app_name);
        PreferencesManager.getInstance(getActivity()).clearToken();
        PreferencesManager.getInstance(getActivity()).logoutUser(false);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction
                .remove(this)
                .add(R.id.fragment_container_main, DemoTracksFragment.newInstance())
                .commit();
    }


    private void setupViewPager(ViewPager viewPager) {
        mViewPagerAdapter = new ViewPagerAdapter(getActivity(),
                getActivity().getSupportFragmentManager());
        viewPager.setAdapter(mViewPagerAdapter);
    }

    private void playerStop() {
        if (Player.getInstance(getActivity()).isPlayingPlayer()) {
            Player.getInstance(getActivity()).stopPlayer();
        }
    }


}
