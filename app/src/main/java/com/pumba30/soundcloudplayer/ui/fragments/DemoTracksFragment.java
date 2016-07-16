package com.pumba30.soundcloudplayer.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.api.rest.RestServiceManager;
import com.pumba30.soundcloudplayer.player.Player;
import com.pumba30.soundcloudplayer.ui.adapters.PublicTracksListAdapter;

import java.util.List;


public class DemoTracksFragment extends Fragment {
    public static final String LOG_TAG = DemoTracksFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private PublicTracksListAdapter mAdapter;
    private Player mPlayer;

    public static DemoTracksFragment newInstance() {
        return new DemoTracksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        initPlayer();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_public_tracks, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.public_tracks_list_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new PublicTracksListAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        App.getAppInstance().getRestServiceManager()
                .loadPublicTracks(new RestServiceManager.RestCallback<List<Track>>() {
                    @Override
                    public void onSuccess(List<Track> tracks) {
                        if (tracks != null) {
                            mAdapter.setTracksList(tracks);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(LOG_TAG, "Error load track");
                        }
                    }

                    @Override
                    public void onError(int errorCode) {
                        Log.d(LOG_TAG, "Error request code: " + String.valueOf(errorCode));
                    }
                });

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_demo_track_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sign_up) {
            startReplaceLoginWebFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startReplaceLoginWebFragment() {
        Fragment loginWebFragment = LoginWebFragment.newInstance();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, loginWebFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
