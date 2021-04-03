package com.sportsquizpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.sportsquizpuzzle.utils.Constants;
import com.sportsquizpuzzle.utils.SPService;
import com.sportsquizpuzzle.utils.SharedValues;
import com.sportsquizpuzzle.utils.SystemUtils;
import com.sportsquizpuzzle.utils.i18n;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Levels.LevelListener {

    private static final String TAG = "Main-Activity";

    private SPService spService = null;
    private boolean serviceBound;

    private int currentLevel = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtils.enableFullScreenUI(this);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*/

        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Intent intent = new Intent(this, SPService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        //Set language user selected
        String lan = SharedValues.getString(getApplicationContext(), Constants.KEY_LANGUAGE, Constants.LAN_ENG);
        i18n.loadLanguage(this, lan);

        currentLevel = SharedValues.getInt(getApplicationContext(), Constants.KEY_CURRENT_LEVEL, 1);

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
        //Log.d(TAG, "onSettingsClick");
        if(serviceBound)
            spService.play(Constants.BUTTON);

        Settings settingsFrag = new Settings();
        settingsFrag.show(getSupportFragmentManager(), "Setting-Page");
    }

    private void onLevelListClick() {
        //Log.d(TAG, "onLevelListClick");
        if(serviceBound)
            spService.play(Constants.BUTTON);

        Levels levels = new Levels(this);
        levels.show(getSupportFragmentManager(), "Levels-Page");
    }

    private void onPlayClick() {
        //Log.d(TAG, "onPlayClick");
        if(serviceBound)
            spService.play(Constants.START_GAME);

        Intent intent = new Intent(MainActivity.this, Game.class);
        intent.putExtra("level", currentLevel);
        MainActivity.this.startActivity(intent);
    }

    @Override
    public void onLevelSelected(int level) {
        currentLevel = level;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            SystemUtils.enableFullScreenUI(this);
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        serviceBound = false;
        super.onDestroy();
    }

    private final ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            SPService.SPBinder binder = (SPService.SPBinder) service;
            spService = binder.getService();
            serviceBound = true;
        }
        public void onServiceDisconnected(ComponentName arg0) {
            serviceBound = false;
        }
    };
}