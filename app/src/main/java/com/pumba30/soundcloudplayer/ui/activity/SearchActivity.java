package com.pumba30.soundcloudplayer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.utils.Utils;

public class SearchActivity extends AppCompatActivity {


    public static Intent newIntent(Context contenxt) {
        return new Intent(contenxt, SearchActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_activity, menu);

        MenuItem itemSearch = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) itemSearch.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onNavigateUp();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onNavigateUp();
    }
}
