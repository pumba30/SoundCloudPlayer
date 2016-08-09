package com.pumba30.soundcloudplayer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Playlist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    public static final String LOG_TAG = PlaylistAdapter.class.getSimpleName();
    private List<Playlist> mPlaylists;
    private LayoutInflater mInflater;

    public PlaylistAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_my_playlists_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Playlist playlist = mPlaylists.get(position);
        holder.bindPlaylist(playlist);
        holder.mPlayPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get a list and send it into player to play, add interface listener
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPlaylists == null ? 0 : mPlaylists.size();
    }

    public void setPlaylists(List<Playlist> playlists) {
        mPlaylists = playlists;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitlePlaylist;
        private TextView mTrackCount;
        private ImageView mLogoPlaylist;
        private ImageView mPlayPlaylist;

        public ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            mTitlePlaylist = (TextView) itemView.findViewById(R.id.title_playlist);
            mTrackCount = (TextView) itemView.findViewById(R.id.count_tracks);
            mLogoPlaylist = (ImageView) itemView.findViewById(R.id.logo_playlist);
            mPlayPlaylist = (ImageView) itemView.findViewById(R.id.play_playlist);

        }

        public void bindPlaylist(Playlist playlist) {
            mTitlePlaylist.setText(playlist.getTitle());
            mTrackCount.setText(String.valueOf(playlist.getTrackCount()));

            String urlLogo = (String) playlist.getArtworkUrl();
            Picasso.with(itemView.getContext())
                    .load(urlLogo)
                    .placeholder(R.mipmap.ic_placeholder)
                    .into(mLogoPlaylist);
        }
    }
}
