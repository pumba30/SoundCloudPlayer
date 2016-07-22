package com.pumba30.soundcloudplayer.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.managers.RestServiceManager;
import com.pumba30.soundcloudplayer.player.Player;
import com.pumba30.soundcloudplayer.ui.adapters.PublicTracksListAdapter;
import com.pumba30.soundcloudplayer.utils.GenreMusic;
import com.pumba30.soundcloudplayer.utils.Utils;

import java.util.List;


public class ChartsTracksFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String LOG_TAG = ChartsTracksFragment.class.getSimpleName();

    private PublicTracksListAdapter mAdapter;
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
                Color.GREEN,
                Color.CYAN);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.charts_tracks_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PublicTracksListAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        loadMusicByGenre(GenreMusic.ALL_MUSIC);

        return view;
    }

    private void loadMusicByGenre(String genreMusic) {
        mPlayer.stopPlayer();

        mManager.loadMusicByGenre(genreMusic, new RestServiceManager.RestCallback<List<Track>>() {
            @Override
            public void onSuccess(List<Track> tracks) {
                if (tracks != null) {
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
        loadMusicByGenre(mManager.getGenre());
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_all_genres) {
//            mToolbar.setTitle(R.string.all_music);
//            loadMusicByGenre(GenreMusic.ALL_MUSIC);
//
//        } else if (id == R.id.action_alternative_rock) {
//            mToolbar.setTitle(R.string.alternative_rock);
//            loadMusicByGenre(GenreMusic.ALTERNATIVE_ROCK);
//
//        } else if (id == R.id.action_ambient) {
//            mToolbar.setTitle(R.string.ambient);
//            loadMusicByGenre(GenreMusic.AMBIENT);
//
//        } else if (id == R.id.action_classical) {
//            mToolbar.setTitle(R.string.classical);
//            loadMusicByGenre(GenreMusic.CLASSICAL);
//
//        } else if (id == R.id.action_country) {
//            mToolbar.setTitle(R.string.country);
//            loadMusicByGenre(GenreMusic.COUNTRY);
//
//        } else if (id == R.id.action_danceedm) {
//            mToolbar.setTitle(R.string.danceedm);
//            loadMusicByGenre(GenreMusic.DANCE_EDM);
//
//        } else if (id == R.id.action_dancehall) {
//            mToolbar.setTitle(R.string.dancehall);
//            loadMusicByGenre(GenreMusic.DANCEHALL);
//
//        } else if (id == R.id.action_deephouse) {
//            mToolbar.setTitle(R.string.deephouse);
//            loadMusicByGenre(GenreMusic.DEEP_HOUSE);
//
//        } else if (id == R.id.action_disco) {
//            mToolbar.setTitle(R.string.disco);
//            loadMusicByGenre(GenreMusic.DISCO);
//
//        } else if (id == R.id.action_drumbass) {
//            mToolbar.setTitle(R.string.drumbass);
//            loadMusicByGenre(GenreMusic.DRUM_BASS);
//
//        } else if (id == R.id.action_dubstep) {
//            mToolbar.setTitle(R.string.dubstep);
//            loadMusicByGenre(GenreMusic.DUBSTEP);
//        }
        // TODO: 18.07.2016 add the remaining genres menu item


        return super.onOptionsItemSelected(item);
    }

}