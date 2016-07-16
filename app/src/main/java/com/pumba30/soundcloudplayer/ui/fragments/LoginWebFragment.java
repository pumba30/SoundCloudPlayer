package com.pumba30.soundcloudplayer.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.rest.AuthoritySoundCloud;
import com.pumba30.soundcloudplayer.ui.activity.MainActivity;
import com.pumba30.soundcloudplayer.ui.dialogFragments.LoadPageProgressBar;
import com.pumba30.soundcloudplayer.utils.Utils;

public class LoginWebFragment extends Fragment {

    public static final String LOG_TAG = LoginWebFragment.class.getSimpleName();

    public static final String CODE = "code";
    public static final String SOUNDCLOUDPLAYER_CALLBACK = "soundcloudplayer://callback";
    public static final String PROGRESS_BAR = "progressBar";
    private LoadPageProgressBar mProgressBar;
    private MainActivity mMainActivity;

    public static Fragment newInstance() {
        return new LoginWebFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null && context instanceof MainActivity) {
            mMainActivity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_web, container, false);
        WebView webView = (WebView) view.findViewById(R.id.webView_login);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        String urlConnect = AuthoritySoundCloud.getUrlConnect();
        if (!TextUtils.isEmpty(urlConnect)) {
            webView.loadUrl(urlConnect);
        } else {
            Log.d(LOG_TAG, "urlConnect isEmpty or NULL");
        }
        mProgressBar = new LoadPageProgressBar();
        mProgressBar.show(getActivity().getSupportFragmentManager(), PROGRESS_BAR);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                if (url.startsWith(SOUNDCLOUDPLAYER_CALLBACK)) {

                    String code = Uri.parse(url).getQueryParameter(CODE);
                    if (!TextUtils.isEmpty(code)) {
                        AuthoritySoundCloud.authorization(code, getActivity());
                        mMainActivity.startUserLoggedFragment();
                    } else {
                        Log.d(LOG_TAG, "Code is empty or NULL. Url: " + url);
                    }
                }
                mProgressBar.dismiss();
            }


        });


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressBar != null) {
            mProgressBar.dismiss();
            mProgressBar = null;
        }
    }
}
