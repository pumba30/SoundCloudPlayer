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
import com.pumba30.soundcloudplayer.ui.fragments.SearchFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {

    private OnEventItemListener mListener;
    private List<Track> mStations;
    private LayoutInflater mInflater;

    public interface  OnEventItemListener{
        void onHandleEvent(Track track);
    }

    public StationAdapter(SearchFragment fragment) {
        mInflater = LayoutInflater.from(fragment.getContext());
        mListener = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_view_item_station, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Track track = mStations.get(position);
        holder.bindStation(track);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onHandleEvent(track);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mStations == null ? 0 : mStations.size();
    }

    public void setStations(List<Track> stations) {
        mStations = stations;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private ImageView mImageTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            mImageTitle = (ImageView) itemView.findViewById(R.id.image_station);
            mTitle = (TextView) itemView.findViewById(R.id.text_view_title_station);
        }

        public void bindStation(Track track) {

            mTitle.setText(track.getTitle());

            String urlToTitleImage = track.getArtworkUrl();
            Picasso.with(itemView.getContext())
                    .load(urlToTitleImage)
                    .placeholder(R.mipmap.ic_placeholder)
                    .into(mImageTitle);
        }
    }


}
