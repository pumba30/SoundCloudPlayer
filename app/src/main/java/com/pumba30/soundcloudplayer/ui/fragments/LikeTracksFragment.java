package com.pumba30.soundcloudplayer.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.api.rest.RestServiceManager;
import com.pumba30.soundcloudplayer.player.PlayerActivity;
import com.pumba30.soundcloudplayer.ui.adapters.OneAndManyTrackListAdapter;
import com.pumba30.soundcloudplayer.utils.DividerItemDecoration;

import java.util.List;

/**
 * Created by pumba30 on 15.07.2016.
 */
public class LikeTracksFragment extends Fragment {
    private OneAndManyTrackListAdapter mAdapter;

    public static LikeTracksFragment newInstance() {
        return new LikeTracksFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like_tracks, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_like_tracks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity());
        recyclerView.addItemDecoration(itemDecoration);

        mAdapter = new OneAndManyTrackListAdapter(getActivity(), PlayerActivity.TypeListTrack.MANY_TRACK);
        recyclerView.setAdapter(mAdapter);

        App.getAppInstance().getRestServiceManager()
                .loadPublicTracks(new RestServiceManager.RestCallback<List<Track>>() {
                    @Override
                    public void onSuccess(List<Track> response) {
                        mAdapter.setTrackList(response);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int errorCode) {

                    }
                });


        return view;

    }
}
