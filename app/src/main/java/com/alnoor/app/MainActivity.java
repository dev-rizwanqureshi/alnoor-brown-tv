package com.alnoor.app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    WebView webView;
    int secondsIn1Hours = 3600;
    int secondsIn3Hours = 3 * secondsIn1Hours;
    int currentSeconds = 0;
    TimerTask timerTask;
    Timer timer;
    boolean shouldPlayWhite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
//        Bugfender.d("Test", "Hello world!"); // you can also use Bugfender to log messages
        fullScreencall();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setWebView();
        startTimer();
        setWebUrlForWhiteOrBrown();
    }

    private void setWebView() {
        webView = findViewById(R.id.webView);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new HelloWebViewClient());
    }


    private void stopTimer() {
        if (timer != null && timerTask != null) {
            timer.cancel();
            timerTask.cancel();
        }
    }

    private void startTimer() {
        timer = new Timer(false);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                updateTickTick();
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    private void updateTickTick() {
        currentSeconds++;
        Logger.debug("updateTickTick-  currentSeconds " + currentSeconds + " secondsIn3Hours " + secondsIn3Hours);
        if (currentSeconds == secondsIn3Hours) {
            Logger.debug("updateTickTick-  setWebUrlForWhiteOrBrown restart at " + currentSeconds);
            currentSeconds = 0;
            setWebUrlForWhiteOrBrown();
        }
    }

    private void setWebUrlForWhiteOrBrown() {
        String urlAlNoorWhite = "https://alnoor-white.themux.tech/";
        String urlAlNoorBrown = "https://alnoor-brown.themux.tech/";
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (shouldPlayWhite) {
                    webView.loadUrl(urlAlNoorWhite);
                    Logger.debug("setWebUrlForWhiteOrBrown-  true " + urlAlNoorWhite);
                } else {
                    webView.loadUrl(urlAlNoorBrown);
                    Logger.debug("setWebUrlForWhiteOrBrown-  false " + urlAlNoorBrown);
                }
            }
        });

    }


    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Logger.debug(" url onPageStarted - " + url);


        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Logger.debug(" url onPageFinished - " + url);

        }
    }
    public void fullScreencall() {

        if(Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}