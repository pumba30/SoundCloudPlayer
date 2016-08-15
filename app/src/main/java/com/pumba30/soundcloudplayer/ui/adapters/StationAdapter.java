package com.pumba30.soundcloudplayer.ui.adapters;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.ui.fragments.StationFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {

    private OnEventItemListener mListener;
    private List<Track> mStations;
    private LayoutInflater mInflater;

    public interface OnEventItemListener {
        void onHandleEvent(Track track);

        void onClickContextMenu(Track track, int resId);
    }

    public StationAdapter(StationFragment fragment) {
        mInflater = LayoutInflater.from(fragment.getContext());
        mListener = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_view_item_station, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Track track = mStations.get(position);
        holder.bindStation(track);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onHandleEvent(track);
            }
        });

        holder.mStationPopUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(track, view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStations == null ? 0 : mStations.size();
    }

    public void setStations(List<Track> stations) {
        mStations = stations;
    }

    private void showPopupMenu(final Track track, final View view) {
        PopupMenu popup = new PopupMenu(view.getContext(), view, Gravity.LEFT);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_pop_up_station, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                mListener.onClickContextMenu(track, itemId);
                return true;
            }
        });
        popup.show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private ImageView mImageTitle;
        private ImageButton mStationPopUpMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            mImageTitle = (ImageView) itemView.findViewById(R.id.image_station);
            mTitle = (TextView) itemView.findViewById(R.id.text_view_title_station);
            mStationPopUpMenu = (ImageButton) itemView.findViewById(R.id.grid_pop_up_menu);
        }

        public void bindStation(Track track) {
            mTitle.setText(track.getTitle());

            String urlToTitleImage = track.getArtworkUrl();
            Picasso.with(itemView.getContext())
                    .load(urlToTitleImage)
                    .placeholder(R.mipmap.ic_placeholder)
                    .into(mImageTitle);
        }
    }
}
