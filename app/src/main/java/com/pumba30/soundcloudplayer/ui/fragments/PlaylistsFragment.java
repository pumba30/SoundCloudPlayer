package com.pumba30.soundcloudplayer.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Playlist;
import com.pumba30.soundcloudplayer.api.rest.WebRequest;
import com.pumba30.soundcloudplayer.events.LoadPlaylistCompleteEvent;
import com.pumba30.soundcloudplayer.events.ObjectsBusEvent;
import com.pumba30.soundcloudplayer.managers.RestServiceManager;
import com.pumba30.soundcloudplayer.ui.adapters.PlaylistAdapter;
import com.pumba30.soundcloudplayer.ui.dialogFragments.DeletePlaylistDialog;
import com.pumba30.soundcloudplayer.utils.DividerItemDecoration;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class PlaylistsFragment extends Fragment implements PlaylistAdapter.OnHahdleEvent {

    private static final String LOG_TAG = PlaylistsFragment.class.getSimpleName();
    private PlaylistAdapter mPlaylistAdapter;
    private List<Playlist> mPlaylists;
    private String mPlaylistId;

    public static PlaylistsFragment newInstance() {
        return new PlaylistsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        EventBus.getDefault().register(this);
        loadingPlaylists();
    }

    private void loadingPlaylists() {
        WebRequest.getInstance().getMePlaylists();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlists, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_my_playlists);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity());
        recyclerView.addItemDecoration(itemDecoration);

        mPlaylistAdapter = new PlaylistAdapter(this);
        recyclerView.setAdapter(mPlaylistAdapter);

        return view;
    }

    @Subscribe
    public void loadPlaylists(LoadPlaylistCompleteEvent complete) {
        mPlaylists = complete.mPlaylists;
        mPlaylistAdapter.setPlaylists(mPlaylists);
        mPlaylistAdapter.notifyDataSetChanged();
        Log.d(LOG_TAG, "Load playlist complete");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_playlist_fragment, menu);
    }

    @Override
    public void onPressButtonPlay(Playlist playlist) {
        Utils.toast(getContext(), "Play");
    }

    @Override
    public void onLongClickIemList(Playlist playlist) {
        Utils.toast(getContext(), String.valueOf(playlist.getId()));
        mPlaylistId = String.valueOf(playlist.getId());
        DeletePlaylistDialog deletePlaylistDialog = DeletePlaylistDialog.newInstance(mPlaylistId);
        deletePlaylistDialog.show(getActivity().getSupportFragmentManager(), "deletePlaylistDialog");
    }

    @Subscribe
    public void deletePlaylist(ObjectsBusEvent<List<Playlist>> event) {
        if (event.mMessage.equals(WebRequest.TRACK_DELETED)) {
            mPlaylistAdapter.setPlaylists(event.mObject);
            mPlaylistAdapter.notifyDataSetChanged();
        }
    }
}

























