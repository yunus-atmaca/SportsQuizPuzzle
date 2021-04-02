package com.sportsquizpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sportsquizpuzzle.utils.SystemUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Settings.SettingListener {

    private static final String TAG = "Main-Activity";

    private ImageView play;
    private ImageView levelList;
    private ImageView settings;

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
        play = findViewById(R.id.play);
        play.setOnClickListener(this);
        levelList = findViewById(R.id.level_list);
        levelList.setOnClickListener(this);
        settings = findViewById(R.id.settings);
        settings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.play){
            onPlayClick();
        }else if(view.getId() == R.id.settings){
            onSettingsClick();
        }else if(view.getId() == R.id.level_list){
            onLevelListClick();
        }else{
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
        if(hasFocus){
            SystemUtils.enableFullScreenUI(this);
        }
    }

    @Override
    public void onLanguageChanged(String lan) {

    }

    @Override
    public void onMusicClicked(boolean music) {

    }
}