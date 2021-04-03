package com.sportsquizpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sportsquizpuzzle.utils.SystemUtils;

public class Game extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GAME-ACTIVITY";

    private int level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtils.enableFullScreenUI(this);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        level = intent.getIntExtra("level", 1);

        Log.d(TAG, "Level --- " + level);

        init();
    }

    private void init() {

        findViewById(R.id.close).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.close){
            this.finish();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            SystemUtils.enableFullScreenUI(this);
        }
    }
}