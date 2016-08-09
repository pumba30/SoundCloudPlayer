package com.pumba30.soundcloudplayer.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.api.rest.WebRequest;
import com.pumba30.soundcloudplayer.events.ObjectsBusEvent;
import com.pumba30.soundcloudplayer.ui.activity.BaseDrawerActivity;
import com.pumba30.soundcloudplayer.ui.activity.SearchActivity;
import com.pumba30.soundcloudplayer.ui.adapters.StationAdapter;
import com.pumba30.soundcloudplayer.utils.SpaceItemGrid;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class SearchFragment extends Fragment implements View.OnClickListener,
        StationAdapter.OnEventItemListener {
    private static final String LOG_TAG = SearchFragment.class.getSimpleName();
    public static final int SPAN_COUNT = 2;
    public static final int SPACING = 16;
    private StationAdapter mStationAdapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        EventBus.getDefault().register(this);
        loadStation();
    }

    // TODO: 08.08.2016 add method loadMore() - gives per page 10 items
    private void loadStation() {
        WebRequest.getInstance().loadStation();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.search_layout);
        layout.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_grid_station);
        GridLayoutManager manager = new GridLayoutManager(this.getContext(), 2);

        recyclerView.setLayoutManager(manager);

        SpaceItemGrid spaceItemGrid = new SpaceItemGrid(SPAN_COUNT, SPACING, true);
        recyclerView.addItemDecoration(spaceItemGrid);

        mStationAdapter = new StationAdapter(this);
        recyclerView.setAdapter(mStationAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void loadStations(ObjectsBusEvent<List<Track>> event) {
        if (event.mMessage.equals(WebRequest.STATION_LOADED)) {
            mStationAdapter.setStations(event.mObject);
            mStationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.search_layout:
                hideActionBar();
                startSearchActivity();
                break;
        }
    }

    private void hideActionBar() {
        BaseDrawerActivity activity = (BaseDrawerActivity) getActivity();
        activity.mToolbar.setVisibility(View.GONE);
        activity.mTabLayout.setVisibility(View.INVISIBLE);
    }

    private void startSearchActivity() {
        Intent intent = SearchActivity.newIntent(getActivity());
        startActivity(intent);
    }


    @Override
    public void onHandleEvent(Track track) {
        Log.d(LOG_TAG, track.getTitle());
        Utils.toast(getActivity(), "play track");
    }
}
