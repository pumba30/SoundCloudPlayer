package com.pumba30.soundcloudplayer.ui.dialogFragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.rest.WebRequest;

public class DeletePlaylistDialog extends DialogFragment {

    private static final String KEY_PLAYLIST_ID = "playlistId";
    private String mPlaylistId;

    public static DeletePlaylistDialog newInstance(String playlistId) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PLAYLIST_ID, playlistId);
        DeletePlaylistDialog dialog = new DeletePlaylistDialog();
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPlaylistId = getArguments().getString(KEY_PLAYLIST_ID);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.are_you_sure_delete)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        WebRequest.getInstance().deletePlaylist(mPlaylistId);
                        dismiss();
                    }
                });

        return builder.create();
    }


}
