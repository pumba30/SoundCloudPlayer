package com.pumba30.soundcloudplayer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.interfaces.OnEventItemListener;
import com.pumba30.soundcloudplayer.ui.activity.SearchActivity;
import com.pumba30.soundcloudplayer.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements View.OnClickListener {


    private OnEventItemListener<Track, Integer> mListener;
    private LayoutInflater mLayoutInflater;
    private List<Track> mTrackList;
    private Track mTrack;

    public SearchAdapter(SearchActivity activity) {
        mLayoutInflater = LayoutInflater.from(activity);
        mTrackList = new ArrayList<>();
        mListener = activity;
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recycler_view_search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        mTrack = mTrackList.get(position);
        holder.bindTrack(mTrack);
        holder.mPlay.setOnClickListener(this);
        holder.mAddToPlayList.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.search_image_button_play_like_track) {
            mListener.onHandleEvent(mTrack, R.id.search_image_button_play_like_track);
        } else if (view.getId() == R.id.search_image_button_add_to_playlist) {
            mListener.onHandleEvent(mTrack, R.id.search_image_button_add_to_playlist);
        }
    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    public void setTrackList(List<Track> trackList) {
        mTrackList.clear();
        mTrackList.addAll(trackList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageTitle;
        private TextView mTrackUserName;
        private TextView mTitleTrack;
        private ImageButton mPlay;
        private ImageButton mAddToPlayList;

        public ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            mImageTitle = (ImageView) itemView.findViewById(R.id.search_track_item_art_work);
            mTrackUserName = (TextView) itemView.findViewById(R.id.search_item_title_track_user_name);
            mTitleTrack = (TextView) itemView.findViewById(R.id.search_item_title_track);
            mPlay = (ImageButton) itemView.findViewById(R.id.search_image_button_play_like_track);
            mAddToPlayList = (ImageButton) itemView.findViewById(R.id.search_image_button_add_to_playlist);
        }


        public void bindTrack(Track track) {
            mTrackUserName.setText(track.getUser().getUserName());
            mTitleTrack.setText(track.getTitle());

            String urlToImage = track.getArtworkUrl();
            Picasso.with(itemView.getContext())
                    .load(urlToImage)
                    .placeholder(R.mipmap.ic_placeholder)
                    .into(mImageTitle);
        }
    }
}
