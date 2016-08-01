package com.pumba30.soundcloudplayer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Playlist;
import com.pumba30.soundcloudplayer.managers.QueryManager;

import java.util.List;


public class AddTrackPlaylistAdapter extends RecyclerView.Adapter<AddTrackPlaylistAdapter.ViewHolder> {
    public static final String LOG_TAG = AddTrackPlaylistAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Playlist> mPlaylists;
    private String mTrackId;
    private String mPlaylistId;

    public AddTrackPlaylistAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_add_track_to_playlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Playlist playlist = mPlaylists.get(position);
        holder.bind(playlist);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Get playlist by id");
                mPlaylistId = String.valueOf(playlist.getId());
                QueryManager.getInstance().getPlaylistById(mPlaylistId);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mPlaylists == null) {
            return 0;
        } else {
            return mPlaylists.size();
        }
    }

    public void setPlaylist(List<Playlist> playlist) {
        mPlaylists = playlist;
    }

    public String getPlaylistId() {
        return mPlaylistId;
    }

    public void setTrackId(String trackId) {
        mTrackId = trackId;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final String LOG_TAG = ViewHolder.class.getSimpleName();
        private TextView mPlaylistName;
        private TextView mNumbersTracks;


        public ViewHolder(View itemView) {
            super(itemView);

            initView();
        }

        private void initView() {
            mPlaylistName = (TextView) itemView.findViewById(R.id.playlist_name);
            mNumbersTracks = (TextView) itemView.findViewById(R.id.numbers_tracks);
        }


        public void bind(Playlist playlist) {
            mPlaylistName.setText(playlist.getTitle());
            String trackCount = String.valueOf(playlist.getTrackCount());
            mNumbersTracks.setText(trackCount);
        }
    }
}

