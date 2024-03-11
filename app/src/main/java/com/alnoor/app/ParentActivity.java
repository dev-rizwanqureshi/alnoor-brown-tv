package com.alnoor.app;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

public abstract class ParentActivity extends AppCompatActivity implements View.OnClickListener {

    protected DisplayMetrics displayMetrics;
    protected float density;
    protected int heightDP;
    protected int widthDP;
    protected int APILevel;
    public String device_id = "";
    protected String OSVersion;
    protected String model;
    protected String manufacturer;
    protected String localIpAddress = "";
    protected String msg;
    public boolean isDebugDevice = false;
    protected int densityDpi;
    protected Fragment childFragment;

    public EditText getEditText(int resId) {
        return (EditText) findViewById(resId);
    }

    public TextView getTextView(int resId) {
        return (TextView) findViewById(resId);
    }

    public CircularProgressIndicator getCircularProgressIndicator(int resId) {
        return (CircularProgressIndicator) findViewById(resId);
    }

    public Spinner getSpinner (int resId){
        return (Spinner) findViewById(resId);
    }

    protected View getView(int resId) {
        return (View) findViewById(resId);
    }

    public ImageView getImageView(int resId) {
        return (ImageView) findViewById(resId);
    }
    public VideoView getVideoView(int resId) {
        return (VideoView) findViewById(resId);
    }
    public SeekBar getSeekBar(int resId) {
        return (SeekBar) findViewById(resId);
    }

    protected Button getButton(int resId) {
        return (Button) findViewById(resId);
    }

    public ConstraintLayout getConstraintLayout(int resId) { return (ConstraintLayout) findViewById(resId); }
    public RadioButton getRadioButton(int resId) { return (RadioButton) findViewById(resId); }


    public CheckBox getCheckBox(int resId) {
        return (CheckBox) findViewById(resId);
    }

    protected RecyclerView getRecyclerView(int resId) {
        return (RecyclerView) findViewById(resId);
    }

    public RelativeLayout getRelativeLayout(int resId) {
        return (RelativeLayout) findViewById(resId);
    }

    protected LinearLayout getLinearLayout(int resId) {
        return (LinearLayout) findViewById(resId);
    }

    protected abstract int getActivityLayoutRes();

    protected abstract void setVariables();

    protected abstract void setContents();

    protected abstract void setActions();

    public AppCompatActivity getActivity() {
        return this;
    }

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        density = displayMetrics.density;
        densityDpi = (int)(density * 160f);
        heightDP = (int) (displayMetrics.heightPixels/density);
        widthDP = (int) (displayMetrics.widthPixels/density);
        APILevel = Build.VERSION.SDK_INT;
        OSVersion = Build.VERSION.RELEASE;
        model = Build.MODEL;
        manufacturer = Build.MANUFACTURER +" "+model;
        String localIpAddress = getLocalIpAddress();
        if (localIpAddress != null) {
            this.localIpAddress = localIpAddress;
        }
        if (getContentResolver() != null) {
            device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        Logger.debug("Device Configurations: " +
                " \ndevice_ip: " + localIpAddress +
                " \ndevice_name: " + manufacturer +
                " \ndevice_no: " + device_id +
                " \ndevice_screen_height: " + heightDP +
                " \ndevice_screen_width: " + widthDP );
        this.isDebugDevice = false;
        if (device_id.equalsIgnoreCase("402c664dd7fe64f4")
                || device_id.equalsIgnoreCase("3e1ec384203442ec")) {
            device_id = "5e52bffd9b6c316a";
            this.isDebugDevice = true;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        fullScreenCall();
        setContentView(getActivityLayoutRes());
        setVariables();
        setContents();
        setActions();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setApplicationLocale(String locale) {
        if (MyApplication.getAppContext() == null)
            return;
        Resources resources = MyApplication.getAppContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(new Locale(locale.toLowerCase()));
        resources.updateConfiguration(config, dm);
        getResources().updateConfiguration(config, dm);
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void fullScreenCall() {
        if(Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


}