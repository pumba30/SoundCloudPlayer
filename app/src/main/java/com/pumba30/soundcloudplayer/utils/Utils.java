package com.pumba30.soundcloudplayer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.pumba30.soundcloudplayer.R;

/**
 * Created by pumba30 on 02.07.2016.
 */
public class Utils {

    public static void toast(Context context, int textId) {
        Toast toast = Toast.makeText(context, textId, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_custom_frame);
        toast.show();
    }

    // TODO: 03.07.2016   later remove
    public static void toast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_custom_frame);
        toast.show();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
