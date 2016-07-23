package com.pumba30.soundcloudplayer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.player.CardViewControlPlayer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChartTracksAdapter extends RecyclerView.Adapter<ChartTracksAdapter.ViewHolder> {
    private static final String LOG_TAG = ChartTracksAdapter.class.getSimpleName();

    private List<Track> mTracksList;
    private LayoutInflater mInflater;
    private CardViewControlPlayer mCardViewController;


    public ChartTracksAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = mInflater.inflate(R.layout.card_item_list_public_tracks, parent, false);

        //handling  buttons in CardView item
        mCardViewController = new CardViewControlPlayer(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Track track = mTracksList.get(position);
        holder.bindTrack(track);
        mCardViewController.setTrack(track);
    }

    @Override
    public int getItemCount() {
        if (mTracksList == null) {
            return 0;
        } else {
            return mTracksList.size();
        }
    }

    public void setTracksList(List<Track> tracksList) {
        if (tracksList != null) {
            mTracksList = tracksList;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mUserNamePostedTrack;
        private TextView mCreatedAt;
        private TextView mUserName;
        private TextView mTitleTrack;
        private ImageView mAvatar;
        private ImageView mArtWork;

        public ViewHolder(View itemView) {
            super(itemView);
            initializationView(itemView);
        }

        private void initializationView(View itemView) {
            mUserNamePostedTrack = (TextView) itemView.findViewById(R.id.text_view_user_name_posted_track);
            mCreatedAt = (TextView) itemView.findViewById(R.id.text_view_created_at);
            mUserName = (TextView) itemView.findViewById(R.id.text_view_user_name);
            mTitleTrack = (TextView) itemView.findViewById(R.id.text_view_title_track);
            mAvatar = (ImageView) itemView.findViewById(R.id.image_view_avatarUser);
            mArtWork = (ImageView) itemView.findViewById(R.id.track_art_work);
        }


        public void bindTrack(Track track) {
            mUserNamePostedTrack.setText(track.getUser().getUserName());
            mUserName.setText(track.getUser().getUserName());
            mCreatedAt.setText(track.getCreatedAt());
            mTitleTrack.setText(track.getTitle());

            String pathAvatar = track.getUser().getAvatarUrl();
            String pathArtWork = track.getArtworkUrl();

            Picasso.with(itemView.getContext()).load(pathAvatar).into(mAvatar);
            Picasso.with(itemView.getContext()).load(pathArtWork)
                    .placeholder(R.drawable.ic_soundcloud)
                    .error(R.drawable.placeholder_background)
                    .into(mArtWork);
        }
    }
}
