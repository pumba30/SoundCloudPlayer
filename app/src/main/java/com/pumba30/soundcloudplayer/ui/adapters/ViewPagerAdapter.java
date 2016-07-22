package com.pumba30.soundcloudplayer.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.ui.fragments.ChartsTracksFragment;
import com.pumba30.soundcloudplayer.ui.fragments.CollectionTracksFragment;
import com.pumba30.soundcloudplayer.ui.fragments.PlayListsFragment;
import com.pumba30.soundcloudplayer.ui.fragments.PublicTrackFragment;
import com.pumba30.soundcloudplayer.ui.fragments.SearchFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int PAGE_COUNT = 4;
    private final int[] iconTabsResId = {
            R.drawable.ic_home,
            R.drawable.ic_like,
            R.drawable.ic_play_list,
            R.drawable.ic_search
    };

    private Context mContext;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ChartsTracksFragment.newInstance();
            case 1:
                return CollectionTracksFragment.newInstance();
            case 2:
                return PlayListsFragment.newInstance();
            case 3:
                return SearchFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = ContextCompat.getDrawable(mContext, iconTabsResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }


}
