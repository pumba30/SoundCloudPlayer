package com.pumba30.soundcloudplayer.player;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.api.rest.RestServiceManager;
import com.pumba30.soundcloudplayer.player.playerEvents.TrackToCollectionEvent;
import com.pumba30.soundcloudplayer.player.playerEvents.PlayerEvent;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * This class is responsible for handling  buttons in CardView item
 */
public class CardViewControlPlayer extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String LOG_TAG = CardViewControlPlayer.class.getSimpleName();

    private ImageButton mPopupMenu;
    private ProgressBar mProgressBar;
    private Player mPlayer;
    private Track mTrack;
    private Context mContext;

    public CardViewControlPlayer(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mPlayer = Player.getInstance(itemView.getContext());
        initView();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void makeProgressBarInvisible(PlayerEvent event) {
        if (event.isPlaying()) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
        if (!event.isPlaying()) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }


    private void
    initView() {
        ImageButton buttonPlay = (ImageButton) itemView.findViewById(R.id.image_button_play_public_track);
        ImageButton buttonStop = (ImageButton) itemView.findViewById(R.id.image_button_stop);
        ImageButton buttonLike = (ImageButton) itemView.findViewById(R.id.image_button_like);
        ImageButton buttonAddToPlayList = (ImageButton) itemView.findViewById(R.id.image_button_add_to_playlist);
        ImageButton buttonShare = (ImageButton) itemView.findViewById(R.id.image_button_share);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.card_view_progress_bar);
        mPopupMenu = (ImageButton) itemView.findViewById(R.id.pop_up_menu);

        //change color progressBar
        mProgressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(itemView.getContext(),
                        R.color.orange_light),
                        PorterDuff.Mode.SRC_IN);
        if (buttonLike != null) {
            buttonLike.setOnClickListener(this);
        }
        if (buttonAddToPlayList != null) {
            buttonAddToPlayList.setOnClickListener(this);
        }
        if (buttonShare != null) {
            buttonShare.setOnClickListener(this);
        }

        mPopupMenu.setOnClickListener(this);
        buttonPlay.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
    }

    //popup menu in card view
    private void showPopupMenu(final View view) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_in_card_view, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_item_test_1) {
                    //test toast
                    Utils.toast(view.getContext(), R.string.menu_test_item_1);
                    return true;
                } else if (itemId == R.id.menu_item_test_2) {
                    //test toast
                    Utils.toast(view.getContext(), R.string.menu_test_item_2);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.image_button_like:
                addToMyCollection();
                break;
            case R.id.image_button_add_to_playlist:
                Utils.toast(view.getContext(), R.string.add_to_play_list);
                break;
            case R.id.image_button_share:
                Utils.toast(view.getContext(), R.string.share);
                break;
            case R.id.pop_up_menu:
                showPopupMenu(mPopupMenu);
                break;
            case R.id.image_button_play_public_track:
                mProgressBar.setVisibility(View.VISIBLE);
                mPlayer.playPlayer(mTrack);
                break;
            case R.id.image_button_stop:
                mPlayer.stopPlayer();
                break;
        }
    }

    public void setTrack(Track track) {
        if (track != null) {
            mTrack = track;
        } else {
            Log.d(LOG_TAG, "Track is null");
        }
    }


    private void addToMyCollection() {
        int idTrack = mTrack.getId();
        App.getAppInstance().getRestServiceManager().toMyCollection(idTrack, new RestServiceManager.RestCallback<Track>() {
            @Override
            public void onSuccess(Track response) {
                Utils.toast(mContext, R.string.added_to_collection);
                EventBus.getDefault().post(new TrackToCollectionEvent(true));

            }

            @Override
            public void onError(int errorCode) {
                Utils.toast(mContext, R.string.failed_added);
            }
        });


    }


}
