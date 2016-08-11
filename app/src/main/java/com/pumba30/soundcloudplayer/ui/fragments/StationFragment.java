package com.pumba30.soundcloudplayer.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.api.rest.WebRequest;
import com.pumba30.soundcloudplayer.events.ObjectsBusEvent;
import com.pumba30.soundcloudplayer.ui.adapters.StationAdapter;
import com.pumba30.soundcloudplayer.utils.SpaceItemGrid;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class StationFragment extends Fragment implements StationAdapter.OnEventItemListener {
    private static final String LOG_TAG = StationFragment.class.getSimpleName();
    public static final int SPAN_COUNT = 2;
    public static final int SPACING = 16;
    private StationAdapter mStationAdapter;

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

    // TODO: 08.08.2016 add method loadMore() - gives per page 10 items
    private void loadStation() {
        WebRequest.getInstance().loadStation();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station, container, false);

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
    public void onHandleEvent(Track track) {
        Log.d(LOG_TAG, track.getTitle());
        Utils.toast(getActivity(), "play track");
    }
}
