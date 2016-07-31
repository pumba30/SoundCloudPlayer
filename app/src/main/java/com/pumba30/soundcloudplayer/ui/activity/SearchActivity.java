package com.pumba30.soundcloudplayer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.managers.PreferencesManager;
import com.pumba30.soundcloudplayer.player.playerEventBus.SearchActivityRunnedEvent;

import org.greenrobot.eventbus.EventBus;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String LOG_TAG = SearchActivity.class.getSimpleName();

    public TextView mTextView;

    public static Intent newIntent(Context contenxt) {
        return new Intent(contenxt, SearchActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (getSupportActionBar() != null) {
                ActionBar toolbar = getSupportActionBar();
                toolbar.setElevation(0);
            }
            getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray_transparent)));
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        PreferencesManager.getInstance(getApplicationContext()).setRunnedSearchActivity(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_activity, menu);

        MenuItem itemSearch = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) itemSearch.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        //when the rotate screen doesn't fill a whole screen
        searchView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(LOG_TAG, "Query text submit " + query);
        updateItems();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(LOG_TAG, "QueryTextChange: " + newText);
        return false;
    }

    private void updateItems() {/*empty*/}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onNavigateUp();
                PreferencesManager.getInstance(getApplicationContext())
                        .setRunnedSearchActivity(false);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        PreferencesManager.getInstance(getApplicationContext())
                .setRunnedSearchActivity(false);
        onNavigateUp();
        super.onBackPressed();
    }

}

