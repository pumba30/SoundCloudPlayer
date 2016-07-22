package com.pumba30.soundcloudplayer.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.managers.RestServiceManager;
import com.pumba30.soundcloudplayer.player.Player;
import com.pumba30.soundcloudplayer.ui.adapters.PublicTracksListAdapter;

import java.util.List;

public class PublicTrackFragment extends Fragment {

    private static final String LOG_TAG = PublicTrackFragment.class.getSimpleName();
    private PublicTracksListAdapter mAdapter;
    private Player mPlayer;


    public static PublicTrackFragment newInstance() {
        return new PublicTrackFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPlayer();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_public_tracks_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.tracks_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PublicTracksListAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        loadPublickTracks();
        return view;
    }

    public void loadPublickTracks() {
        App.sAppInstance.getRestServiceManager()
                .loadPublicTracks(new RestServiceManager.RestCallback<List<Track>>() {
                    @Override
                    public void onSuccess(List<Track> tracks) {
                        mAdapter.setTracksList(tracks);
                        mAdapter.notifyDataSetChanged();
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

}
