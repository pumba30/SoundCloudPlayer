package com.pumba30.soundcloudplayer.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pumba30.soundcloudplayer.R;

/**
 * Created by pumba30 on 15.07.2016.
 */
public class PlaylistsFragment extends Fragment {
    public static PlaylistsFragment newInstance() {
        return new PlaylistsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_play_lists, container, false);

    }
}
