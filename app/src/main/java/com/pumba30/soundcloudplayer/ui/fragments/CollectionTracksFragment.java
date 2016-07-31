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

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Playlist;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.managers.QueryManager;
import com.pumba30.soundcloudplayer.player.PlayerActivity;
import com.pumba30.soundcloudplayer.player.playerEventBus.LoadPlaylistComplete;
import com.pumba30.soundcloudplayer.player.playerEventBus.ObjectsBusEvent;
import com.pumba30.soundcloudplayer.player.playerEventBus.PlaylistCreatedEvent;
import com.pumba30.soundcloudplayer.ui.adapters.OneAndManyTrackAdapter;
import com.pumba30.soundcloudplayer.ui.dialogFragments.AddTrackToPlaylistDialog;
import com.pumba30.soundcloudplayer.ui.dialogFragments.CreatePlaylistDialog;
import com.pumba30.soundcloudplayer.utils.DividerItemDecoration;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class CollectionTracksFragment extends Fragment {
    private static final String LOG_TAG = CollectionTracksFragment.class.getSimpleName();
    private OneAndManyTrackAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Playlist> mPlaylists;


    public static CollectionTracksFragment newInstance() {
        return new CollectionTracksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_like_tracks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity());
        mRecyclerView.addItemDecoration(itemDecoration);

        mAdapter = new OneAndManyTrackAdapter(getActivity(), PlayerActivity.TypeListTrack.MANY_TRACK);
        mRecyclerView.setAdapter(mAdapter);

        QueryManager.getInstance().getCollectionList();

        return view;
    }


    @Subscribe
    public void playlistCreated(PlaylistCreatedEvent event) {
        Utils.toast(getActivity(), "Create PPLAYLIST");
        Playlist playlist = event.getPlaylist();
        mPlaylists.add(playlist);
        mAdapter.setPlaylist(mPlaylists);
    }

    @Subscribe
    public void loadPlaylistsComplete(LoadPlaylistComplete event) {
        mPlaylists = event.getPlaylists();

        if (mPlaylists.size() == 0 || mPlaylists.get(0) == null) {
            Log.d(LOG_TAG, "Playlist null, size: " + mPlaylists.size());
            CreatePlaylistDialog playlistDialog = CreatePlaylistDialog.newInstance();
            playlistDialog.show(getActivity().getSupportFragmentManager(), "createDialog");

        } else {
            Log.d(LOG_TAG, "Playlist size: " + mPlaylists.size());
            mAdapter.setPlaylist(mPlaylists);

        }
    }


    @Subscribe
    public void updateAdapter(ObjectsBusEvent event) {
        String message = event.getMessage();

        if (message.equals(QueryManager.TRACK_ADDED)
                || message.equals(QueryManager.TRACK_DELETED)) {
            QueryManager.getInstance().getCollectionList();
            mAdapter.notifyDataSetChanged();

        } else if (message.equals(QueryManager.LIST_COLLECTION_TRACK_LOADED)) {
            mAdapter.setTrackList((List<Track>) event.getObject());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
