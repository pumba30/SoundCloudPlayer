package com.pumba30.soundcloudplayer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.api.rest.WebRequest;
import com.pumba30.soundcloudplayer.events.LoadPlaylistCompleteEvent;
import com.pumba30.soundcloudplayer.events.ObjectsBusEvent;
import com.pumba30.soundcloudplayer.managers.PreferencesManager;
import com.pumba30.soundcloudplayer.player.PlayerActivity;
import com.pumba30.soundcloudplayer.ui.adapters.SearchAdapter;
import com.pumba30.soundcloudplayer.ui.dialogFragments.AddTrackToPlaylistDialog;
import com.pumba30.soundcloudplayer.utils.DividerItemDecoration;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        SearchAdapter.OnEventItemListener {
    private static final String LOG_TAG = SearchActivity.class.getSimpleName();
    private static final float ELEVATION = 0;

    private SearchAdapter mSearchAdapter;
    private ProgressBar mProgressBar;
    private String mIdTrack;

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);

        setActivityBackGroundTransparent();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        EventBus.getDefault().register(this);

        PreferencesManager.setLauchedSearchActivity(getApplicationContext(), true);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_search_tracks);
        recyclerView.addItemDecoration(decoration);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        mSearchAdapter = new SearchAdapter(this);
        recyclerView.setAdapter(mSearchAdapter);

        mProgressBar = (ProgressBar) findViewById(R.id.search_progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
    }


    private void setActivityBackGroundTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (getSupportActionBar() != null) {
                ActionBar toolbar = getSupportActionBar();
                toolbar.setElevation(ELEVATION);
            }
            getWindow().setBackgroundDrawable(new ColorDrawable(getResources()
                    .getColor(R.color.gray_transparent)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_activity, menu);
        MenuItem itemSearch = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) itemSearch.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        //when rotated the screen, search doesn't fill  a whole screen
        searchView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(LOG_TAG, "Query text submit " + query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(LOG_TAG, "QueryTextChange: " + newText);
        if (!TextUtils.isEmpty(newText)) {
            getWindow().setBackgroundDrawable(new ColorDrawable(getResources()
                    .getColor(R.color.white)));
            requestSearch(newText);
            mProgressBar.setVisibility(View.VISIBLE);
            return true;
        } else {
            setActivityBackGroundTransparent();
            mSearchAdapter.setTrackList(new ArrayList<Track>());
            mSearchAdapter.notifyDataSetChanged();
            return true;
        }
    }

    private void requestSearch(String query) {
        WebRequest.getInstance().searchTrack(query);
    }


    @Subscribe
    public void searchTracksLoaded(ObjectsBusEvent<List<Track>> event) {
        if (event.mMessage.equals(WebRequest.SEARCH_TRACK_LOADED)) {
            Log.d(LOG_TAG, "Search track loaded");
            mSearchAdapter.setTrackList(event.mObject);
            mSearchAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility((View.INVISIBLE));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnToParent();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        returnToParent();
        super.onBackPressed();
    }


    private void returnToParent() {
        PreferencesManager.setLauchedSearchActivity(getApplicationContext(), false);
        onNavigateUp();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void playlistLoadedComplete(LoadPlaylistCompleteEvent event) {
        mProgressBar.setVisibility(View.INVISIBLE);
        AddTrackToPlaylistDialog addTrackToPlaylistDialog
                = AddTrackToPlaylistDialog.newInstance(event.mPlaylists, mIdTrack);
        addTrackToPlaylistDialog.show(getSupportFragmentManager(), "addTrackToPlaylistDialog");
    }


    private void startPlayTrack(Track track) {
        List<Track> tracks = new ArrayList<>();
        tracks.add(track);
        Intent intent = PlayerActivity.newIntent(SearchActivity.this, tracks);
        startActivity(intent);
    }

    @Override
    public void onHandleEvent(Track track, int resId) {
        if (resId == R.id.search_image_button_play_like_track) {
            startPlayTrack(track);
        } else if (resId == R.id.search_image_button_add_to_playlist) {
            Utils.hideKeyboard(this);
            mProgressBar.setVisibility(View.VISIBLE);
            WebRequest.getInstance().getMePlaylists();
            mIdTrack = String.valueOf(track.getId());
        }
    }

}

