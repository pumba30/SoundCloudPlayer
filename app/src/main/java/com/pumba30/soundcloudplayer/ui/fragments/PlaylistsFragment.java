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
import com.pumba30.soundcloudplayer.managers.QueryManager;
import com.pumba30.soundcloudplayer.player.playerEventBus.LoadPlaylistComplete;
import com.pumba30.soundcloudplayer.ui.adapters.PlaylistAdapter;
import com.pumba30.soundcloudplayer.utils.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class PlaylistsFragment extends Fragment {

    private PlaylistAdapter mPlaylistAdapter;

    public static PlaylistsFragment newInstance() {
        return new PlaylistsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
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


        QueryManager.getInstance().getMePlaylists();

        return view;
    }

    @Subscribe
    public void loadPlaylists(LoadPlaylistComplete complete) {
        mPlaylistAdapter.setPlaylists(complete.getPlaylists());
    }

}
