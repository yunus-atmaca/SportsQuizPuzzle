package com.sportsquizpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.sportsquizpuzzle.utils.Constants;
import com.sportsquizpuzzle.utils.SPHelper;
import com.sportsquizpuzzle.utils.SharedValues;
import com.sportsquizpuzzle.utils.SystemUtils;
import com.sportsquizpuzzle.utils.i18n;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Settings.SettingListener {

    private static final String TAG = "Main-Activity";

    private SPHelper spHelper = null;

    MediaPlayer mediaPlayer;

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
        //Set language user selected
        String lan = SharedValues.getString(getApplicationContext(), Constants.KEY_LANGUAGE, Constants.LAN_ENG);
        boolean soundOn = SharedValues.getBoolean(getApplicationContext(), Constants.KEY_SOUND, true);
        boolean musicOn = SharedValues.getBoolean(getApplicationContext(), Constants.KEY_MUSIC, true);
        i18n.loadLanguage(this, lan);

        if(soundOn){
            spHelper = new SPHelper(getApplicationContext(), new int[]{SPHelper.BUTTON}, true);
        }

        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.level_list).setOnClickListener(this);
        findViewById(R.id.settings).setOnClickListener(this);


        mediaPlayer = MediaPlayer.create(this, R.raw.background);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
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
        //Log.d(TAG, "onSettingsClick");

        if(spHelper != null)
            spHelper.play(SPHelper.BUTTON);

        Settings settingsFrag = new Settings(this);
        settingsFrag.show(getSupportFragmentManager(), "Setting-Page");
    }

    private void onLevelListClick() {
        Log.d(TAG, "onLevelListClick");

        if(spHelper != null)
            spHelper.play(SPHelper.BUTTON);

    }

    private void onPlayClick() {
        Log.d(TAG, "onPlayClick");

        if(spHelper != null)
            spHelper.play(SPHelper.BUTTON);
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

    @Override
    public void onSoundClicked(boolean sound) {
        if(sound){
            spHelper = new SPHelper(getApplicationContext(), new int[]{SPHelper.BUTTON}, true);
        }else{
            spHelper = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}