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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.rest.WebRequest;
import com.pumba30.soundcloudplayer.events.LoadPlaylistCompleteEvent;
import com.pumba30.soundcloudplayer.ui.adapters.PlaylistAdapter;
import com.pumba30.soundcloudplayer.ui.dialogFragments.CreatePlaylistDialog;
import com.pumba30.soundcloudplayer.utils.DividerItemDecoration;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class PlaylistsFragment extends Fragment {

    private static final String LOG_TAG = PlaylistsFragment.class.getSimpleName();
    private PlaylistAdapter mPlaylistAdapter;

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
    public void onPause() {
        super.onPause();
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

        mPlaylistAdapter = new PlaylistAdapter(getContext());
        recyclerView.setAdapter(mPlaylistAdapter);

        return view;
    }

    @Subscribe
    public void loadPlaylists(LoadPlaylistCompleteEvent complete) {
        mPlaylistAdapter.setPlaylists(complete.mPlaylists);
        mPlaylistAdapter.notifyDataSetChanged();
        Log.d(LOG_TAG, "Load playlist complete");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_playlist_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_create_playlist) {
            Utils.toast(getActivity(), "Create playlist");
            CreatePlaylistDialog dialog = CreatePlaylistDialog.newInstance();
            dialog.show(getActivity().getSupportFragmentManager(), "createPlaylistDialog");
        }
        return true;
    }
}

























