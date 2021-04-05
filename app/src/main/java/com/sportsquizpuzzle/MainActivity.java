package com.sportsquizpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sportsquizpuzzle.utils.Constants;
import com.sportsquizpuzzle.utils.SPController;
import com.sportsquizpuzzle.utils.SharedValues;
import com.sportsquizpuzzle.utils.SystemUtils;
import com.sportsquizpuzzle.utils.i18n;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Main-Activity";

    private int currentLevel = 1;
    private boolean onStartGame;

    private LinearLayoutCompat root;
    private SPController spController;
    private boolean firstInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtils.enableFullScreenUI(this);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        //Set language user selected
        firstInit = true;

        String lan = SharedValues.getString(getApplicationContext(), Constants.KEY_LANGUAGE, Constants.LAN_ENG);
        i18n.loadLanguage(this, lan);

        currentLevel = SharedValues.getInt(getApplicationContext(), Constants.KEY_CURRENT_LEVEL, 1);

        root = findViewById(R.id.root);

        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.level_list).setOnClickListener(this);
        findViewById(R.id.settings).setOnClickListener(this);

        onStartGame = false;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.play) {
            onStartGame = true;
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
        spController.play(Constants.BUTTON);

        Settings settingsFrag = new Settings();
        settingsFrag.show(getSupportFragmentManager(), "Setting-Page");
    }

    private void onLevelListClick() {
        //Log.d(TAG, "onLevelListClick");
        spController.play(Constants.BUTTON);

        ChooseLevels levels = new ChooseLevels();
        levels.show(getSupportFragmentManager(), "Levels-Page");
    }

    private void onPlayClick() {
        //Log.d(TAG, "onPlayClick");
        currentLevel = SharedValues.getInt(this, Constants.KEY_CURRENT_LEVEL, 1);
        spController.play(Constants.START_GAME);

        Intent intent = new Intent(MainActivity.this, Game.class);
        intent.putExtra("level", currentLevel);
        MainActivity.this.startActivity(intent);
    }

    /*private void enableViews(View v, boolean enabled) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                enableViews(vg.getChildAt(i), enabled);
            }
        }
        v.setEnabled(enabled);
    }*/

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            SystemUtils.enableFullScreenUI(this);
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        spController.releaseSP();
        super.onDestroy();

        finishAndRemoveTask();
        System.exit(0);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");

        if(onStartGame){

        }else{
            spController.releaseSP();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        onStartGame = false;

        if(firstInit){
            firstInit = false;
            spController = SPController.getInstance(getApplicationContext());
        }else{
            boolean music = SharedValues.getBoolean(this, Constants.KEY_MUSIC, true);
            spController.setBackgroundMusic(music);
        }

        super.onResume();
    }
}