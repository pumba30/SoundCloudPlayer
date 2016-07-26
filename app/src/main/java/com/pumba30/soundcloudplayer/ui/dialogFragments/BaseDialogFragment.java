package com.pumba30.soundcloudplayer.ui.dialogFragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.WindowManager;

public class BaseDialogFragment extends DialogFragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
