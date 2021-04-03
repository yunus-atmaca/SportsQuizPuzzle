package com.sportsquizpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sportsquizpuzzle.utils.Constants;
import com.sportsquizpuzzle.utils.SharedValues;
import com.sportsquizpuzzle.utils.SystemUtils;
import com.sportsquizpuzzle.utils.i18n;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Settings.SettingListener {

    private static final String TAG = "Main-Activity";

    private String lan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SystemUtils.enableFullScreenUI(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        String lan = SharedValues.getString(getApplicationContext(), Constants.KEY_LANGUAGE, Constants.LAN_ENG);
        Log.d(TAG, lan);
        i18n.loadLanguage(this, lan);

        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.level_list).setOnClickListener(this);
        findViewById(R.id.settings).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.play) {
            onPlayClick();
        } else if (view.getId() == R.id.settings) {
            onSettingsClick();
        } else if (view.getId() == R.id.level_list) {
            onLevelListClick();
        } else {
            Log.d(TAG, "Unimplemented call");
        }
    }

    private void onSettingsClick() {
        Log.d(TAG, "onSettingsClick");

        Settings settingsFrag = new Settings(this);
        settingsFrag.show(getSupportFragmentManager(), "Setting-Page");
    }

    private void onLevelListClick() {
        Log.d(TAG, "onLevelListClick");
    }

    private void onPlayClick() {
        Log.d(TAG, "onPlayClick");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            SystemUtils.enableFullScreenUI(this);
        }
    }

    @Override
    public void onMusicClicked(boolean music) {

    }
}