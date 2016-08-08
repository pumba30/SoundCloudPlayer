package com.pumba30.soundcloudplayer.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pumba30.soundcloudplayer.api.models.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pumba30 on 08.08.2016.
 */
public class StationAdapter extends BaseAdapter {

    private Context mContext;
    private List<Track> mStations;

    public StationAdapter(Context context) {
        mContext = context;
        mStations = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    public void setStations(List<Track> stations) {
        mStations.clear();
        mStations.addAll(stations);
    }


    public static class ViewHolder {

    }

}
