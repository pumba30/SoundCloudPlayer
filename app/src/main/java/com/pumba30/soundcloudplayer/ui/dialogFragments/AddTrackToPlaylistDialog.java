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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Playlist;
import com.pumba30.soundcloudplayer.api.rest.WebRequest;
import com.pumba30.soundcloudplayer.events.ObjectsBusEvent;
import com.pumba30.soundcloudplayer.interfaces.OnEventItemListener;
import com.pumba30.soundcloudplayer.ui.adapters.AddTrackPlaylistAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class AddTrackToPlaylistDialog extends BaseDialogFragment implements View.OnClickListener,
        AddTrackPlaylistAdapter.OnEventItemListener {
    private static final String LOG_TAG = AddTrackToPlaylistDialog.class.getSimpleName();
    private static final String KEY_PLAYLISTS = "keyPlaylist";
    private static final String KEY_TRACK_ID = "trackId";
    private List<Playlist> mPlaylists;
    private String mTrackId;
    private AddTrackPlaylistAdapter mPlaylistAdapter;
    private String mPlaylistId;

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

        mPlaylistAdapter = new AddTrackPlaylistAdapter(getContext(), this);
        mPlaylistAdapter.setPlaylist(mPlaylists);
        mPlaylistAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mPlaylistAdapter);

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
    public void playlistCompleteLoaded(ObjectsBusEvent<Playlist> event) {
        Playlist playlist;
        if (event.mMessage.equals(WebRequest.PLAYLIST_LOADED)) {
            Log.d(LOG_TAG, "Playlist loaded");
            playlist = event.mObject;

            List<String> tracksIds = new ArrayList<>();
            for (int i = 0; i < playlist.getTrackList().size(); i++) {
                tracksIds.add(String.valueOf(playlist.getTrackList().get(i).getId()));
            }
            tracksIds.add(mTrackId);

            WebRequest.getInstance().addTrackToPlaylist(mPlaylistId, tracksIds);
            dismiss();
        }
    }

    @Subscribe
    public void trackAdded(ObjectsBusEvent<Playlist> event) {
        WebRequest.getInstance().getPlaylistById(String.valueOf(event.mObject.getId()));
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

    @Override
    public void onHandleEvent(Playlist playlist) {
        mPlaylistId = String.valueOf(playlist.getId());
        WebRequest.getInstance().getPlaylistById(mPlaylistId);
    }
}
