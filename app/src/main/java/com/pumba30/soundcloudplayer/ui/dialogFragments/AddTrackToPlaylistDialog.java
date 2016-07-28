package com.pumba30.soundcloudplayer.ui.dialogFragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Playlist;
import com.pumba30.soundcloudplayer.managers.QueryManager;
import com.pumba30.soundcloudplayer.player.playerEventBus.ObjectsBusEvent;
import com.pumba30.soundcloudplayer.ui.adapters.AddTrackPlaylistAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class AddTrackToPlaylistDialog extends BaseDialogFragment implements View.OnClickListener {


    private static final String KEY_PLAYLISTS = "keyPlaylist";
    private static final String KEY_TRACK_ID = "trackId";
    private List<Playlist> mPlaylists;
    private String mTrackId;

    public static AddTrackToPlaylistDialog newInstance(List<Playlist> playlists, String trackId) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_PLAYLISTS, (ArrayList<? extends Parcelable>) playlists);
        bundle.putString(KEY_TRACK_ID, trackId);
        AddTrackToPlaylistDialog playlistDialog = new AddTrackToPlaylistDialog();
        playlistDialog.setArguments(bundle);
        return playlistDialog;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getArguments() != null) {
            mPlaylists = getArguments().getParcelableArrayList(KEY_PLAYLISTS);
            mTrackId = getArguments().getString(KEY_TRACK_ID);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_frag_add_track_to_playlist, null);

        LinearLayout addNewPlaylist = (LinearLayout) view.findViewById(R.id.linear_layout_add_playlist);
        addNewPlaylist.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_add_tracs_to_playlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        AddTrackPlaylistAdapter playlistAdapter = new AddTrackPlaylistAdapter(getContext());
        playlistAdapter.setPlaylist(mPlaylists);
        playlistAdapter.setTrackId(mTrackId);
        recyclerView.setAdapter(playlistAdapter);

        builder.setView(view)
                .setTitle("Add track to playlist")
                .setCancelable(true)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });


        return builder.create();

    }

    @Subscribe
    public void trackAddedToPlayList(ObjectsBusEvent event) {
        if (event.getMessage().equals(QueryManager.TRACK_ADDED)) {
            dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.linear_layout_add_playlist) {
            CreatePlaylistDialog createPlaylistDialog = CreatePlaylistDialog.newInstance();
            createPlaylistDialog.show(getFragmentManager(), " playlistDialog");
            dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        EventBus.getDefault().unregister(this);
    }
}
