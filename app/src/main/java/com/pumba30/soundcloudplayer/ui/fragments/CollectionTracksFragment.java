package com.pumba30.soundcloudplayer.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.managers.QueryManager;
import com.pumba30.soundcloudplayer.player.PlayerActivity;
import com.pumba30.soundcloudplayer.player.playerEventBus.TrackCollectionEvent;
import com.pumba30.soundcloudplayer.ui.adapters.OneAndManyTrackAdapter;
import com.pumba30.soundcloudplayer.utils.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class CollectionTracksFragment extends Fragment {
    private static final String LOG_TAG = CollectionTracksFragment.class.getSimpleName();
    private OneAndManyTrackAdapter mAdapter;
    private View mView;


    public static CollectionTracksFragment newInstance() {
        return new CollectionTracksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_collection, container, false);

        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view_like_tracks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity());
        recyclerView.addItemDecoration(itemDecoration);

        mAdapter = new OneAndManyTrackAdapter(getActivity(), PlayerActivity.TypeListTrack.MANY_TRACK);
        recyclerView.setAdapter(mAdapter);

        QueryManager.getInstance().getCollectionList();

        return mView;
    }

    @Subscribe
    public void updateAdapter(TrackCollectionEvent event) {
        String message = event.getMessage();

        if (message.equals(QueryManager.TRACK_ADDED)
                || message.equals(QueryManager.TRACK_DELETED)) {
            QueryManager.getInstance().getCollectionList();

        } else if (message.equals(QueryManager.LIST_COLLECTION_TRACK_LOADED)) {
            mAdapter.setTrackList((List<Track>) event.getObject());
            mAdapter.notifyDataSetChanged();
        }
    }
}
