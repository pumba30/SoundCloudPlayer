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
import com.pumba30.soundcloudplayer.api.rest.WebRequest;
import com.pumba30.soundcloudplayer.interfaces.OnEventItemListener;
import com.pumba30.soundcloudplayer.player.PlayerActivity;
import com.pumba30.soundcloudplayer.events.LoadPlaylistCompleteEvent;
import com.pumba30.soundcloudplayer.events.ObjectsBusEvent;
import com.pumba30.soundcloudplayer.events.PlaylistCreatedEvent;
import com.pumba30.soundcloudplayer.ui.activity.MainActivity;
import com.pumba30.soundcloudplayer.ui.adapters.OneAndManyTrackAdapter;
import com.pumba30.soundcloudplayer.ui.dialogFragments.AddTrackToPlaylistDialog;
import com.pumba30.soundcloudplayer.ui.dialogFragments.CreatePlaylistDialog;
import com.pumba30.soundcloudplayer.ui.dialogFragments.DeleteTrackFromCollectionDialog;
import com.pumba30.soundcloudplayer.utils.DividerItemDecoration;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class CollectionTracksFragment extends Fragment implements OnEventItemListener<List<Playlist>, String> {
    private static final String LOG_TAG = CollectionTracksFragment.class.getSimpleName();
    private OneAndManyTrackAdapter mAdapter;
    private List<Playlist> mPlaylists;


    public static CollectionTracksFragment newInstance() {
        return new CollectionTracksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        EventBus.getDefault().register(this);
        loadCollectionList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_like_tracks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity());
        recyclerView.addItemDecoration(itemDecoration);

        mAdapter = new OneAndManyTrackAdapter(PlayerActivity.TypeListTrack.MANY_TRACK, this);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    private void loadCollectionList() {
        WebRequest.getInstance().getCollectionList();
    }

    @Subscribe
    public void playlistCreated(PlaylistCreatedEvent event) {
        Utils.toast(getActivity(), "Create PPLAYLIST");
        Playlist playlist = event.mPlaylist;
        mPlaylists.add(playlist);
        mAdapter.setPlaylist(mPlaylists);
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void loadPlaylistsComplete(LoadPlaylistCompleteEvent event) {
        mPlaylists = event.mPlaylists;

        if (mPlaylists.size() == 0 || mPlaylists.get(0) == null) {
            Log.d(LOG_TAG, "Playlist null, size: " + mPlaylists.size());
            CreatePlaylistDialog playlistDialog = CreatePlaylistDialog.newInstance();
            playlistDialog.show(getActivity().getSupportFragmentManager(), "createDialog");

        } else {
            Log.d(LOG_TAG, "Playlist size: " + mPlaylists.size());
            mAdapter.setPlaylist(mPlaylists);
            mAdapter.notifyDataSetChanged();

        }
    }


    @Subscribe
    public void updateAdapter(ObjectsBusEvent<List<Track>> event) {
        String message = event.mMessage;

        if (message.equals(WebRequest.TRACK_ADDED)
                || message.equals(WebRequest.TRACK_DELETED)) {
            WebRequest.getInstance().getCollectionList();
            mAdapter.notifyDataSetChanged();

        } else if (message.equals(WebRequest.LIST_COLLECTION_TRACK_LOADED)) {
            mAdapter.setTrackList(event.mObject);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onHandleEvent(List<Playlist> playlists, String trackId) {
        WebRequest.getInstance().getMePlaylists();
        Utils.toast(getContext(), "getMePlaylist");

        AddTrackToPlaylistDialog addTrackToPlaylistDialog
                = AddTrackToPlaylistDialog.newInstance(mPlaylists, trackId);
        addTrackToPlaylistDialog
                .show(getActivity().getSupportFragmentManager(), "addTrackToPlaylistDialog");
    }

    @Override
    public void onHandleEventLongClick(String trackId) {
        DeleteTrackFromCollectionDialog deleteTrack =
                DeleteTrackFromCollectionDialog.newInstance(trackId);
        deleteTrack.show(getActivity().getSupportFragmentManager(),
                "deleteTrackDialog");
    }
}
