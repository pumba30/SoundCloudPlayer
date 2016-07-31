package com.pumba30.soundcloudplayer.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.ui.activity.SearchActivity;

/**
 * Created by pumba30 on 15.07.2016.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.search_layout);
        layout.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.search_layout:
                startSearchActivity();
                break;
        }
    }


    private void startSearchActivity() {
        Intent intent = SearchActivity.newIntent(getActivity());
        startActivity(intent);
    }
}
