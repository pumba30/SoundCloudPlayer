package com.pumba30.soundcloudplayer.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.player.blurPlayerView.BlurPlayerView;
import com.pumba30.soundcloudplayer.ui.adapters.OneAndManyTrackAdapter;
import com.pumba30.soundcloudplayer.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = PlayerActivity.class.getSimpleName();
    private static final String PLAY_DATA = "playData";
    private static final String SIZE_PICTURE_LARGE = "large";
    private static final CharSequence SIZE_PICTURE_T300X300 = "t300x300";

    private BlurPlayerView mBlurPlayerView;
    private ImageView mPopupContextMenu;
    private ImageView mImageViewPlay;
    private Track mTrack;
    private Player mPlayer;
    private TextView mUserName;
    private TextView mTitleTrack;
    private RecyclerView mRecyclerView;

    //If playlist has one track - ONE_TRACK, and if more than one - MANY_TRACK
    public enum TypeListTrack {
        ONE_TRACK, MANY_TRACK
    }

    public static Intent newIntent(Context context, List<Track> track) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putParcelableArrayListExtra(PLAY_DATA, (ArrayList<? extends Parcelable>) track);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mPlayer = Player.getInstance(getApplicationContext());
        List<Track> tracks = getIntent().getParcelableArrayListExtra(PLAY_DATA);
        if (tracks != null) {
            //for only one track
            mTrack = tracks.get(0);
        }
        initView();
        setTitlePlyer();
        setCover(mTrack);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

//        OneAndManyTrackAdapter adapter =
//                new OneAndManyTrackAdapter(TypeListTrack.ONE_TRACK);
//        mRecyclerView.setAdapter(adapter);
//
//        adapter.setTrackList(tracks);

        // TODO: 12.07.2016 make reward and forward play music, correct pause

        mBlurPlayerView.setProgress(0);
        mBlurPlayerView.setMax(mTrack.getDuration() / 1000);

        mBlurPlayerView.start();
        mPlayer.playPlayer(mTrack);
        mImageViewPlay.setBackgroundResource(R.drawable.pause);
    }

    private void setTitlePlyer() {
        String title = mTrack.getTitle();
        String createdAt = mTrack.getCreatedAt().replace(" +0000", "");
        mTitleTrack.setText(title);
        mUserName.setText(createdAt);
    }

    //set picture 300*300 to circle cover of player and 50*500 to image background player
    private void setCover(Track track) {
        String pathArtWork = track.getArtworkUrl();
        if (pathArtWork != null) {
            pathArtWork = pathArtWork.replace(SIZE_PICTURE_LARGE, SIZE_PICTURE_T300X300);
            mBlurPlayerView.setCoverURL(pathArtWork);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.arrow_to_back) {
            super.onBackPressed();
            mPlayer.stopPlayer();
        } else if (viewId == R.id.popup_context_menu) {
            showPopupMenu(mPopupContextMenu);
        } else if (viewId == R.id.control) {
            if (!mBlurPlayerView.isPlaying()) {
                mBlurPlayerView.start();
                mPlayer.playPlayer(mTrack);
                mImageViewPlay.setBackgroundResource(R.drawable.pause);
            } else {
                mBlurPlayerView.setProgress(0);
                mBlurPlayerView.stop();
                // TODO: 12.07.2016 make pause
                mPlayer.stopPlayer();
                mImageViewPlay.setBackgroundResource(R.drawable.play);
            }
        }
    }


    private void initView() {
        ImageView toBack = (ImageView) findViewById(R.id.arrow_to_back);
        ImageView imageViewBackgroundPlayer = (ImageView) findViewById(R.id.background_player);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewBottom);
        mBlurPlayerView = (BlurPlayerView) findViewById(R.id.blur_player);
        mTitleTrack = (TextView) findViewById(R.id.text_view_title_track_2);
        mUserName = (TextView) findViewById(R.id.text_view_user_name_2);
        mPopupContextMenu = (ImageView) findViewById(R.id.popup_context_menu);
        mImageViewPlay = (ImageView) findViewById(R.id.control);

        toBack.setOnClickListener(this);
        mImageViewPlay.setOnClickListener(this);
        mPopupContextMenu.setOnClickListener(this);
    }

    private void showPopupMenu(View view) {
        Context wrapper = new ContextThemeWrapper(getApplicationContext(), R.style.PopupMenu);
        android.widget.PopupMenu popup = new android.widget.PopupMenu(wrapper, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_in_player, popup.getMenu());
        popup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_item_test_1) {
                    //test toast
                    Utils.toast(getApplicationContext(), R.string.menu_test_item_1);
                    return true;
                } else if (itemId == R.id.menu_item_test_2) {
                    //test toast
                    Utils.toast(getApplicationContext(), R.string.menu_test_item_2);
                    return true;
                }
                return true;
            }
        });
        popup.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mPlayer.stopPlayer();
        if (mPlayer != null) {
            mPlayer = null;
        }
    }

}
