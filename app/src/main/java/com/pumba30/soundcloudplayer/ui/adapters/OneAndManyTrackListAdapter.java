package com.pumba30.soundcloudplayer.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.player.PlayerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.pumba30.soundcloudplayer.player.PlayerActivity.*;

/**
 * Created by pumba30 on 10.07.2016.
 */
public class OneAndManyTrackListAdapter extends RecyclerView.Adapter<OneAndManyTrackListAdapter.ViewHolder> {
    private static final String LOG_TAG = OneAndManyTrackListAdapter.class.getSimpleName();

    private static final String SIZE_PICTURE_T67X67 = "t67x67";
    public static final String SIZE_PICTURE_LARGE = "large";
    private List<Track> mTrackList;
    private LayoutInflater mInflater;
    private Context mContext;
    private TypeListTrack mTypeListTrack;

    public OneAndManyTrackListAdapter(Context context, TypeListTrack typeListTrack) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mTypeListTrack = typeListTrack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (mTypeListTrack == TypeListTrack.ONE_TRACK) {
            view = mInflater.inflate(R.layout.activity_player_content_one_track, parent, false);
        } else if (mTypeListTrack == TypeListTrack.MANY_TRACK) {
            view = mInflater.inflate(R.layout.activity_player_content_many_track, parent, false);
        }

        return new ViewHolder(view, mTypeListTrack);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Track track = mTrackList.get(position);

        if (mTypeListTrack == TypeListTrack.ONE_TRACK) {
            holder.bindForTypeOneTrack(track);
        } else if (mTypeListTrack == TypeListTrack.MANY_TRACK) {
            holder.bindForTypeManyTracks(track);
            holder.mImageButtonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Track> tracks = new ArrayList<>();
                    tracks.add(track);
                    Intent intent = newIntent(mContext, tracks);
                    mContext.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (mTrackList == null) {
            return 0;
        } else {
            return mTrackList.size();
        }
    }

    public void setTrackList(List<Track> trackList) {
        mTrackList = trackList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewArtWork;
        private ImageButton mImageButtonPlay;
        private TextView mUserName;
        private TextView mTitleTrack;

        private TextView mTitleTrackPlayer;
        private TextView mGenreTrack;
        private TextView mDurationTrack;
        private TextView mDownloadTrack;
        private TextView mUserNameTrack;
        private TextView mDescriptionTrack;

        public ViewHolder(View itemView, TypeListTrack typeListTrack) {
            super(itemView);
            if (typeListTrack == TypeListTrack.ONE_TRACK) {
                initViewForOneTrack(itemView);
            } else if (typeListTrack == TypeListTrack.MANY_TRACK) {
                initViewForManyTrack(itemView);
            }
        }

        private void initViewForOneTrack(View itemView) {
            mTitleTrackPlayer = (TextView) itemView.findViewById(R.id.text_view_item_title_track_player);
            mGenreTrack = (TextView) itemView.findViewById(R.id.text_view_item_genre_track);
            mDurationTrack = (TextView) itemView.findViewById(R.id.text_view_item_duration_track);
            mDownloadTrack = (TextView) itemView.findViewById(R.id.text_view_item_download_track);
            mUserNameTrack = (TextView) itemView.findViewById(R.id.text_view_item_user_name_track);
            mDescriptionTrack = (TextView) itemView.findViewById(R.id.text_view_item_description);
        }

        // texts is temporary, may be changed
        public void bindForTypeOneTrack(Track track) {
            mTitleTrackPlayer.setText(track.getTitle());
            if (!TextUtils.isEmpty(track.getGenre())) {
                mGenreTrack.setText(track.getGenre());
            }

            // TODO: 15.07.2016 change strings to constants
            mDurationTrack.setText(String.format(Locale.getDefault(),
                    "Duration of the track %d min.", (track.getDuration() / 1000) / 60));
            mDownloadTrack.setText(String.format("Download track: %s", track.getDownloadable()));
            mUserNameTrack.setText(String.format("User name: Name"));
            if (!TextUtils.isEmpty(track.getDescription())) {
                mDescriptionTrack.setText(track.getDescription());
            }
        }

        private void initViewForManyTrack(View itemView) {
            mImageViewArtWork = (ImageView) itemView.findViewById(R.id.track_item_art_work);
            mImageButtonPlay = (ImageButton) itemView.findViewById(R.id.image_button_play_like_track);
            mUserName = (TextView) itemView.findViewById(R.id.text_view_item_title_track_user_name);
            mTitleTrack = (TextView) itemView.findViewById(R.id.text_view_item_title_track);
        }

        public void bindForTypeManyTracks(Track track) {
            mUserName.setText(track.getUser().getUserName());
            mTitleTrack.setText(track.getTitle());

            String pathArtWork = track.getArtworkUrl();
            if (pathArtWork != null) {
                pathArtWork = pathArtWork.replace(SIZE_PICTURE_LARGE, SIZE_PICTURE_T67X67);
            }
            Picasso.with(itemView.getContext()).load(pathArtWork)
                    .placeholder(R.drawable.ic_soundcloud)
                    .error(R.drawable.placeholder_background)
                    .into(mImageViewArtWork);
        }
    }
}