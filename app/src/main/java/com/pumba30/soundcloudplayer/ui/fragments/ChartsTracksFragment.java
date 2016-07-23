package com.pumba30.soundcloudplayer.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.managers.RestServiceManager;
import com.pumba30.soundcloudplayer.player.Player;
import com.pumba30.soundcloudplayer.ui.adapters.ChartTracksAdapter;
import com.pumba30.soundcloudplayer.utils.GenreMusic;
import com.pumba30.soundcloudplayer.utils.Utils;

import java.util.List;


public class ChartsTracksFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Spinner.OnItemSelectedListener {
    public static final String LOG_TAG = ChartsTracksFragment.class.getSimpleName();

    private ChartTracksAdapter mAdapter;
    private Player mPlayer;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RestServiceManager mManager = App.sAppInstance.getRestServiceManager();


    public static ChartsTracksFragment newInstance() {
        return new ChartsTracksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.charts);
        initPlayer();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts_tracks, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,
                Color.YELLOW,
                Color.GREEN);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.charts_tracks_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ChartTracksAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        loadMusicByGenre("all-music");

        return view;
    }

    private void loadMusicByGenre(String genreMusic) {
        mPlayer.stopPlayer();

        mManager.loadMusicByGenre(genreMusic, new RestServiceManager.RestCallback<List<Track>>() {
            @Override
            public void onSuccess(List<Track> tracks) {
                if (tracks != null) {
                    Log.d(LOG_TAG, "traks:" + tracks.toString());
                    mAdapter.setTracksList(tracks);
                    mAdapter.notifyDataSetChanged();
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Utils.toast(getActivity(), R.string.refresh_ok);
                    }

                } else {
                    Log.d(LOG_TAG, "Error load track");
                }
            }

            @Override
            public void onError(int errorCode) {
                Log.d(LOG_TAG, "Error request code: " + String.valueOf(errorCode));
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        mPlayer.stopPlayer();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stopPlayer();
            mPlayer = null;
        }
    }

    private void initPlayer() {
        if (mPlayer != null) {
            mPlayer.stopPlayer();
            mPlayer = null;
        } else {
            mPlayer = Player.getInstance(getActivity());
        }
    }


    @Override
    public void onRefresh() {
        // FIXME: 18.07.2016 not properly work
        // loadMusicByGenre(GenreMusic.ALL_MUSIC);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.spinner_filter, menu);
        createSpinnerFilterGenreMusic(menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void createSpinnerFilterGenreMusic(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.context_menu_filter);
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        Spinner spinner = (Spinner) layoutInflater.inflate(R.layout.spinner_menu, null);
        spinner.setOnItemSelectedListener(this);
        spinner.setDropDownVerticalOffset(45);

        CharSequence[] charSequence = getActivity().getResources().getStringArray(R.array.genres_array);
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_item_main,
                android.R.id.text1,
                charSequence);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        LinearLayout linearLayout = (LinearLayout) MenuItemCompat.getActionView(menuItem);
        linearLayout.addView(spinner);
        spinner.setAdapter(spinnerAdapter);
    }

    //handling click spinner's item
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String genreMusic = GenreMusic.getGenre(i);
        Log.d(LOG_TAG, "Genre Music: " + genreMusic);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {/*empty*/}
}
