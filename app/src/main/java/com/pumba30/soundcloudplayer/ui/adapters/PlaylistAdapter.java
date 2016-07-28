package com.pumba30.soundcloudplayer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Playlist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pumba30 on 28.07.2016.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    public static final String LOG_TAG = PlaylistAdapter.class.getSimpleName();
    private List<Playlist> mPlaylists;
    private LayoutInflater mInflater;

    public PlaylistAdapter(Context context) {
        mPlaylists = new ArrayList<>();
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
        //get a list and send it into player to play
        holder.bindPlaylist(playlist);
    }

    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindPlaylist(Playlist playlist) {

        }
    }
}
