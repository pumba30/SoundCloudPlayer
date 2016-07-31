package com.pumba30.soundcloudplayer.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Playlist;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.managers.QueryManager;
import com.pumba30.soundcloudplayer.ui.activity.MainActivity;
import com.pumba30.soundcloudplayer.ui.dialogFragments.AddTrackToPlaylistDialog;
import com.pumba30.soundcloudplayer.ui.dialogFragments.DeleteTrackFromCollectionDialog;
import com.pumba30.soundcloudplayer.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.pumba30.soundcloudplayer.player.PlayerActivity.TypeListTrack;
import static com.pumba30.soundcloudplayer.player.PlayerActivity.newIntent;

public class OneAndManyTrackAdapter extends RecyclerView.Adapter<OneAndManyTrackAdapter.ViewHolder> {
    private static final String LOG_TAG = OneAndManyTrackAdapter.class.getSimpleName();

    private static final String SIZE_PICTURE_T67X67 = "t67x67";
    public static final String SIZE_PICTURE_LARGE = "large";
    private List<Track> mTrackList;
    private LayoutInflater mInflater;
    private Context mContext;
    private TypeListTrack mTypeListTrack;
    private Track mTrack;
    private String mTrackId;
    private List<Playlist> mPlaylists;


    public OneAndManyTrackAdapter(Context context, TypeListTrack typeListTrack) {
        mTrackList = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mTypeListTrack = typeListTrack;
        mPlaylists = new ArrayList<>();
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mTrack = mTrackList.get(position);

        if (mTypeListTrack == TypeListTrack.ONE_TRACK) {
            holder.bindForTypeOneTrack(mTrack);
        } else if (mTypeListTrack == TypeListTrack.MANY_TRACK) {
            holder.bindForTypeManyTracks(mTrack);
            holder.mPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startPlayTrack(mTrack);
                }
            });

            final int adapterPosition = holder.getAdapterPosition();

            holder.mAddToPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTrackId = gettingTrackId(adapterPosition);

                    QueryManager.getInstance().getMePlaylists();
                    Utils.toast(mContext, "getMePlaylist");

                    AddTrackToPlaylistDialog addTrackToPlaylistDialog
                            = AddTrackToPlaylistDialog.newInstance(mPlaylists, mTrackId);
                    addTrackToPlaylistDialog
                            .show(((MainActivity) mContext).getSupportFragmentManager(), "addTrackToPlaylistDialog");


                    Log.d(LOG_TAG, "TrackId: " + mTrackId);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    String trackId = gettingTrackId(adapterPosition);
                    DeleteTrackFromCollectionDialog deleteTrack =
                            DeleteTrackFromCollectionDialog.newInstance(trackId);
                    deleteTrack.show(((MainActivity) mContext).getSupportFragmentManager(),
                            "deleteTrackDialog");
                    return true;
                }
            });
        }
    }

    @NonNull
    private String gettingTrackId(int adapterPosition) {
        return String.valueOf(mTrackList.get(adapterPosition).getId());
    }

    private void startPlayTrack(Track track) {
        List<Track> tracks = new ArrayList<>();
        tracks.add(track);
        Intent intent = newIntent(mContext, tracks);
        mContext.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return mTrackList.size();

    }

    public void setTrackList(List<Track> trackList) {
        mTrackList.clear();
        mTrackList.addAll(trackList);
        this.notifyItemRangeChanged(0, mTrackList.size() - 1);
    }

    public void setPlaylist(List<Playlist> playlist) {
        mPlaylists.clear();
        mPlaylists.addAll(playlist);
        this.notifyItemRangeChanged(0, mPlaylists.size() - 1);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mViewArtWork;
        private ImageButton mPlay;
        private TextView mUserName;
        private TextView mTitleTrack;

        private TextView mTitleTrackPlayer;
        private TextView mGenreTrack;
        private TextView mDurationTrack;
        private TextView mDownloadTrack;
        private TextView mUserNameTrack;
        private TextView mDescriptionTrack;
        private ImageButton mAddToPlaylist;


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

        public void bindForTypeOneTrack(Track track) {
            mTitleTrackPlayer.setText(track.getTitle());
            if (!TextUtils.isEmpty(track.getGenre())) {
                mGenreTrack.setText(track.getGenre());
            }

            // TODO: 15.07.2016 change strings to constants
            mDurationTrack.setText(String.format(Locale.getDefault(),
                    "Duration of the track %d min.", (track.getDuration() / 1000) / 60));
            mDownloadTrack.setText(String.format("Download track: %s", track.getDownloadable()));
            String userNameText = itemView.getContext().getString(R.string.user_name_track);
            mUserNameTrack.setText(new StringBuilder()
                    .append(userNameText)
                    .append(track.getUser().getUserName())
                    .toString());
            if (!TextUtils.isEmpty(track.getDescription())) {
                mDescriptionTrack.setText(track.getDescription());
            } else {
                mDescriptionTrack.setText(R.string.description_is_empty);
            }
        }

        private void initViewForManyTrack(final View itemView) {
            mViewArtWork = (ImageView) itemView.findViewById(R.id.track_item_art_work);
            mPlay = (ImageButton) itemView.findViewById(R.id.image_button_play_like_track);
            mAddToPlaylist = (ImageButton) itemView.findViewById(R.id.image_button_add_to_playlist);
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
                    .into(mViewArtWork);
        }

    }
}

