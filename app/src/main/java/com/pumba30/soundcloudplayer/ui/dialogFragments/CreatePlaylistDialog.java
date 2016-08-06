package com.pumba30.soundcloudplayer.ui.dialogFragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.rest.WebRequest;
import com.pumba30.soundcloudplayer.utils.Utils;

public class CreatePlaylistDialog extends BaseDialogFragment implements RadioGroup.OnCheckedChangeListener,
        TextView.OnEditorActionListener {
    public static final String LOG_TAG = CreatePlaylistDialog.class.getSimpleName();
    public static final String PLAYLIST_PRIVATE = "private";
    public static final String PLAYLIST_PUBLIC = "private";
    private String mTitle;
    private String mSharing;

    public static CreatePlaylistDialog newInstance() {
        return new CreatePlaylistDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_frag_create_playlist, null);

        final EditText playlistTitle = (EditText) view.findViewById(R.id.enter_playlist_title);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_select_type_playlist);

        radioGroup.setOnCheckedChangeListener(this);
        playlistTitle.setOnEditorActionListener(this);

        builder.setView(view)
                .setTitle(R.string.create_playlist)
                .setCancelable(true)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTitle = playlistTitle.getText().toString();
                        createPlaylist(mTitle, mSharing);
                        dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.radioButton_private) {
            mSharing = PLAYLIST_PRIVATE;
        } else if (i == R.id.radioButton_public) {
            mSharing = PLAYLIST_PUBLIC;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            createPlaylist(mTitle, mSharing);
        }

        return true;
    }

    private void createPlaylist(String title, String sharing) {
        if (TextUtils.isEmpty(title)) {
            Log.d(LOG_TAG, "Title is empty. Dialog dissmis");
            Utils.toast(getActivity(), R.string.enter_valid_title);
            dismiss();
        } else {
            WebRequest.getInstance().createPlaylist(title, sharing);
            Utils.toast(getActivity(), R.string.playlist_created);
            Log.d(LOG_TAG, "Attempt create playlist");
        }
    }
}
