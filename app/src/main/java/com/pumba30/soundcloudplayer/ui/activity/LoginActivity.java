package com.pumba30.soundcloudplayer.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.rest.AuthoritySoundCloud;
import com.pumba30.soundcloudplayer.ui.dialogFragments.LoadPageProgressBarDialog;

public class LoginActivity extends AppCompatActivity {

    public static final String LOG_TAG = LoginActivity.class.getSimpleName();

    public static final String CODE = "code";
    public static final String SOUNDCLOUDPLAYER_CALLBACK = "soundcloudplayer://callback";
    public static final String PROGRESS_BAR = "progressBar";
    private LoadPageProgressBarDialog mProgressBar;


    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        hideShadowToolbar(actionBar);


        WebView webView = (WebView) findViewById(R.id.webView_login);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        String urlConnect = AuthoritySoundCloud.getUrlConnect();
        if (!TextUtils.isEmpty(urlConnect)) {
            webView.loadUrl(urlConnect);
        } else {
            Log.d(LOG_TAG, "urlConnect isEmpty or NULL");
        }

        mProgressBar = new LoadPageProgressBarDialog();
        mProgressBar.show(getSupportFragmentManager(), PROGRESS_BAR);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(SOUNDCLOUDPLAYER_CALLBACK)) {
                    String code = Uri.parse(url).getQueryParameter(CODE);
                    if (!TextUtils.isEmpty(code)) {
                        AuthoritySoundCloud.authorization(code);

                    } else {
                        Log.d(LOG_TAG, "Code is empty or NULL. Url: " + url);
                    }
                    return true;
                }
                mProgressBar.dismiss();
                return false;
            }
        });

        webView.loadUrl(urlConnect);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideShadowToolbar(ActionBar bar) {
        if (bar != null) {
            bar.setElevation(0);
        }
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
