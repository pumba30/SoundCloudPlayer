package com.pumba30.soundcloudplayer.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.managers.PreferencesManager;
import com.pumba30.soundcloudplayer.managers.QueryManager;
import com.pumba30.soundcloudplayer.player.Player;
import com.pumba30.soundcloudplayer.player.playerEventBus.ObjectsBusEvent;
import com.pumba30.soundcloudplayer.ui.adapters.ChartTracksAdapter;
import com.pumba30.soundcloudplayer.utils.GenreMusic;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class ChartsTracksFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Spinner.OnItemSelectedListener {
    public static final String LOG_TAG = ChartsTracksFragment.class.getSimpleName();

    private ChartTracksAdapter mAdapter;
    private Player mPlayer;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Spinner mSpinner;
    private String mGenreMusic;

    public static ChartsTracksFragment newInstance() {
        return new ChartsTracksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        initPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts_tracks, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,
                Color.YELLOW,
                Color.GREEN);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.charts_tracks_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ChartTracksAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        mPlayer.stopPlayer();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stopPlayer();
            mPlayer = null;
        }
    }

    private void initPlayer() {
        if (mPlayer != null) {
            mPlayer.stopPlayer();
            mPlayer = null;
        } else {
            mPlayer = Player.getInstance(getActivity());
        }
    }


    @Override
    public void onRefresh() {
        // FIXME: 18.07.2016 not properly work
        // loadMusicByGenre(GenreMusic.ALL_MUSIC);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.spinner_filter, menu);
        createSpinnerFilterGenreMusic(menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    // TODO: 23.07.2016 save title in spinner
    private void createSpinnerFilterGenreMusic(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.context_menu_filter);
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        mSpinner = (Spinner) layoutInflater.inflate(R.layout.spinner_chart_fragment, null);
        mSpinner.setOnItemSelectedListener(this);
        mSpinner.setDropDownVerticalOffset(45);

        //set to spinner a list genres of music
        CharSequence[] charSequence = getActivity().getResources().getStringArray(R.array.genres_array);
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_item_chart_fragment,
                android.R.id.text1,
                charSequence);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        LinearLayout linearLayout = (LinearLayout) MenuItemCompat.getActionView(menuItem);
        linearLayout.addView(mSpinner);
        mSpinner.setAdapter(spinnerAdapter);

        getLastSpinnerItem();

    }

    private void getLastSpinnerItem() {
        int savedItemSpinner = PreferencesManager.getInstance(getActivity()).getChoicedItemSpinner();
        if (savedItemSpinner != -1) {
            mSpinner.setSelection(savedItemSpinner, true);
            mGenreMusic = GenreMusic.getGenre(savedItemSpinner);
        }
    }

    //handling click spinner's item
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mPlayer.stopPlayer();

        int choicedItem = mSpinner.getSelectedItemPosition();
        PreferencesManager.getInstance(getContext()).saveChoicedItemSpinner(choicedItem);

        Log.d(LOG_TAG, "Genre Music: " + mGenreMusic);
        QueryManager.getInstance().loadMusicByGenre(mGenreMusic);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {/*empty*/}

    @Subscribe
    public void updateAdapter(ObjectsBusEvent event) {
        if (event.getMessage().equals(QueryManager.LIST_TRACK_LOADED)) {
            mAdapter.setTracksList((List<Track>) event.getObject());
            mAdapter.notifyDataSetChanged();
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
                Utils.toast(getActivity(), R.string.refresh_ok);
            }
        }
    }


}
