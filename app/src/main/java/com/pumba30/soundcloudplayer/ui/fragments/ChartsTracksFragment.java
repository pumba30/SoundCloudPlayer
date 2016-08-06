package com.pumba30.soundcloudplayer.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.pumba30.soundcloudplayer.events.ObjectsBusEvent;
import com.pumba30.soundcloudplayer.managers.PreferencesManager;
import com.pumba30.soundcloudplayer.api.rest.WebRequest;
import com.pumba30.soundcloudplayer.player.Player;
import com.pumba30.soundcloudplayer.ui.adapters.ChartTracksAdapter;
import com.pumba30.soundcloudplayer.utils.GenreMusic;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class ChartsTracksFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Spinner.OnItemSelectedListener {
    public static final String LOG_TAG = ChartsTracksFragment.class.getSimpleName();
    public static final int VERTICAL_OFFSET = 45;

    private ChartTracksAdapter mAdapter;
    private Player mPlayer;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Spinner mSpinner;
    private GenreMusic mGenreMusic;

    public static ChartsTracksFragment newInstance() {
        return new ChartsTracksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        int savedItemSpinner = PreferencesManager.getChoicedItemSpinner(getActivity());
        if (savedItemSpinner != -1) {
            mGenreMusic = GenreMusic.takeGenreMusic(savedItemSpinner);
        } else {
            mGenreMusic = GenreMusic.ALL_MUSIC;
        }

        loadTracks();
        initPlayer();


    }

    private void loadTracks() {
        WebRequest.getInstance().loadMusicByGenre(mGenreMusic.getGenre());
        Log.d(LOG_TAG, "Genre " + mGenreMusic.getGenre());
    }

    @Override
    public void onStart() {
        super.onStart();
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
    public void onStop() {
        super.onStop();
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
        loadTracks();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.spinner_filter, menu);
        createSpinnerFilterGenreMusic(menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void createSpinnerFilterGenreMusic(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.context_menu_filter);
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        mSpinner = (Spinner) layoutInflater.inflate(R.layout.spinner_chart_fragment, null);
        mSpinner.setOnItemSelectedListener(this);
        mSpinner.setDropDownVerticalOffset(VERTICAL_OFFSET);

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
        int savedItemSpinner = PreferencesManager.getChoicedItemSpinner(getActivity());
        if (savedItemSpinner != -1) {
            mSpinner.setSelection(savedItemSpinner, true);
        }
    }

    //handling click spinner's item
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mPlayer.stopPlayer();

        int choicedItem = mSpinner.getSelectedItemPosition();
        PreferencesManager.saveChoicedItemSpinner(getActivity(), choicedItem);
        mGenreMusic = GenreMusic.takeGenreMusic(choicedItem);
        loadTracks();

        Log.d(LOG_TAG, "Genre Music: " + mGenreMusic);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {/*empty*/}

    @Subscribe
    public void updateAdapter(ObjectsBusEvent<List<Track>> event) {
        if (event.mMessage.equals(WebRequest.LIST_TRACK_LOADED)) {
            mAdapter.setTracksList(event.mObject);
            mAdapter.notifyDataSetChanged();
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
                Utils.toast(getActivity(), R.string.refresh_ok);
            }
        }
    }


}
