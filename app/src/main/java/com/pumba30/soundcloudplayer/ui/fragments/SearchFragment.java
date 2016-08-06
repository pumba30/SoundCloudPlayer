package com.pumba30.soundcloudplayer.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.ui.activity.BaseDrawerActivity;
import com.pumba30.soundcloudplayer.ui.activity.SearchActivity;

public class SearchFragment extends Fragment implements View.OnClickListener {


    private RelativeLayout mLayout;

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
        mLayout = (RelativeLayout) view.findViewById(R.id.search_layout);
        mLayout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.search_layout:
                hideActionBar();
                startSearchActivity();
                break;
        }
    }

    private void hideActionBar() {
        BaseDrawerActivity activity = (BaseDrawerActivity) getActivity();
        activity.mToolbar.setVisibility(View.GONE);
        activity.mTabLayout.setVisibility(View.INVISIBLE);
    }

    private void startSearchActivity() {
        Intent intent = SearchActivity.newIntent(getActivity());
        startActivity(intent);
    }




}
