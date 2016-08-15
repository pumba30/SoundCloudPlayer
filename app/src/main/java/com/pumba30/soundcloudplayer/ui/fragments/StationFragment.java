package com.pumba30.soundcloudplayer.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Playlist;
import com.pumba30.soundcloudplayer.api.models.Playlists;
import com.pumba30.soundcloudplayer.api.models.Stations;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.api.rest.WebRequest;
import com.pumba30.soundcloudplayer.events.LoadPlaylistCompleteEvent;
import com.pumba30.soundcloudplayer.events.ObjectsBusEvent;
import com.pumba30.soundcloudplayer.player.PlayerActivity;
import com.pumba30.soundcloudplayer.ui.adapters.StationAdapter;
import com.pumba30.soundcloudplayer.ui.dialogFragments.AddTrackToPlaylistDialog;
import com.pumba30.soundcloudplayer.utils.MapHelper;
import com.pumba30.soundcloudplayer.utils.SpaceItemGrid;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StationFragment extends Fragment implements StationAdapter.OnEventItemListener {
    private static final String LOG_TAG = StationFragment.class.getSimpleName();
    private static final int SPAN_COUNT = 2;
    private static final int SPACING = 16;
    public static final int DIRECTION = 1;
    public static final int POSITION_TO_SCROLL = 0;
    private StationAdapter mStationAdapter;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private Map<String, String> mMapRequestUrl;
    private List<Playlist> mPlaylists;

    public static StationFragment newInstance() {
        return new StationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        EventBus.getDefault().register(this);
        loadStation();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadStation();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_grid_station);
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        SpaceItemGrid spaceItemGrid = new SpaceItemGrid(SPAN_COUNT, SPACING, true);
        mRecyclerView.addItemDecoration(spaceItemGrid);
        mStationAdapter = new StationAdapter(this);
        mRecyclerView.setAdapter(mStationAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(DIRECTION)) {
                    loadMoreStation(mMapRequestUrl);
                }

            }
        });
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar_station);
        return view;
    }

    private void loadStation() {
        WebRequest.getInstance().loadStation();
    }

    private void loadMoreStation(Map<String, String> mapRequestUrl) {
        mProgressBar.setVisibility(View.VISIBLE);
        WebRequest.getInstance().loadMoreStation(mapRequestUrl);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void loadStations(ObjectsBusEvent<Stations> event) {
        if (event.mMessage.equals(WebRequest.STATION_LOADED)
                || event.mMessage.equals(WebRequest.STATION_MORE_LOADED)) {
            List<Track> tracks = event.mObject.getCollection();

            //Callback url from server for loading next page
            String nextPageUrlStations = event.mObject.getNextHref();
            mMapRequestUrl = MapHelper.createMapRequestUrlNextPage(nextPageUrlStations);

            mStationAdapter.setStations(tracks);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mStationAdapter.notifyDataSetChanged();

            mRecyclerView.postOnAnimation(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.smoothScrollToPosition(POSITION_TO_SCROLL);
                }
            });
        }
    }

    @Subscribe
    public void loadPlayLists(LoadPlaylistCompleteEvent event) {
        mPlaylists = event.mPlaylists;
    }

    @Override
    public void onHandleEvent(Track track) {
        startPlayTrack(track);
    }

    @Override
    public void onClickContextMenu(Track track, int resId) {
        if (resId == R.id.menu_add_station_to_playlist) {
            WebRequest.getInstance().getMePlaylists();
            String trackId = String.valueOf(track.getId());
            AddTrackToPlaylistDialog dialog = AddTrackToPlaylistDialog.newInstance(mPlaylists, trackId);
            dialog.show(getActivity().getSupportFragmentManager(),
                    "addTrackToPlaylistDialog");
        } else if (resId == R.id.menu_play_station) {
            startPlayTrack(track);
        }
    }

    private void startPlayTrack(Track track) {
        List<Track> tracks = new ArrayList<>();
        tracks.add(track);
        Intent intent = PlayerActivity.newIntent(getActivity(), tracks);
        startActivity(intent);
    }
}
