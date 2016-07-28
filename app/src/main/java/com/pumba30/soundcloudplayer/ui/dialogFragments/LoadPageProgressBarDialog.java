package com.pumba30.soundcloudplayer.ui.dialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.pumba30.soundcloudplayer.R;

public class LoadPageProgressBarDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.loading_connect_page)
                .setCancelable(false)
                .setView(R.layout.progress_dialog_indeterminate)
                .create();
    }
}