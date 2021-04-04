package com.sportsquizpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sportsquizpuzzle.listener.DragListener;
import com.sportsquizpuzzle.listener.TouchListener;
import com.sportsquizpuzzle.puzzle.Level;
import com.sportsquizpuzzle.puzzle.Levels;
import com.sportsquizpuzzle.puzzle.Piece;
import com.sportsquizpuzzle.utils.Constants;
import com.sportsquizpuzzle.utils.SPService;
import com.sportsquizpuzzle.utils.SharedValues;
import com.sportsquizpuzzle.utils.SystemUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game extends AppCompatActivity implements View.OnClickListener, DragListener.OnCompleteListener {

    private static final String TAG = "GAME-ACTIVITY";

    private int currentLevel;
    private Level level;

    private LinearLayoutCompat piecesHolder;
    private LinearLayoutCompat targetHolder;
    private View highlightedArea;

    private LinearLayoutCompat buttonContainer;
    private ImageView button_1;
    private ImageView button_2;
    private ImageView button_3;
    private int selectedButton;
    private boolean onComplete;
    private boolean clickableUI;

    private SPService spService = null;
    private boolean serviceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtils.enableFullScreenUI(this);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        currentLevel = intent.getIntExtra("level", 1);
        level = Levels.getLevel(currentLevel);

        //Log.d(TAG, "Level --- " + currentLevel);

        init();
    }

    private void init() {
        Intent intent = new Intent(this, SPService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        findViewById(R.id.close).setOnClickListener(this);

        buttonContainer = findViewById(R.id.button_container);
        piecesHolder = findViewById(R.id.piece_holder);
        targetHolder = findViewById(R.id.target_holder);
        highlightedArea = findViewById(R.id.highlighted_view);

        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);
        button_3 = findViewById(R.id.button_3);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);

        setLevelUI();
    }

    private void setLevelUI() {
        onComplete = false;
        clickableUI = true;

        buttonContainer.setVisibility(View.GONE);

        piecesHolder.removeAllViews();
        targetHolder.removeAllViews();

        button_1.setImageResource(R.drawable.button_normal_background);
        button_2.setImageResource(R.drawable.button_normal_background);
        button_3.setImageResource(R.drawable.button_normal_background);

        DragListener listener = new DragListener(this, level.getPieces(), highlightedArea, this);

        piecesHolder.addView(getLayoutInflater().inflate(level.getPiecesLayoutId(), null));
        targetHolder.addView(getLayoutInflater().inflate(level.getTargetLayoutId(), null));

        for (Piece piece : level.getPieces()) {
            targetHolder.findViewById(piece.getTargetHolderId()).setOnDragListener(listener);
            piecesHolder.findViewById(piece.getId()).setOnTouchListener(new TouchListener());
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.close) {
            if(serviceBound)
                spService.play(Constants.BUTTON);

            this.finish();
        } else if (view.getId() == R.id.button_1) {
            if(!clickableUI)
                return;
            clickableUI = false;

            if (selectedButton == 1) {
                if(serviceBound)
                    spService.play(Constants.WIN);

                button_1.setImageResource(R.drawable.button_green_background);
                new Handler().postDelayed(() -> setNextLevel(true), 750);
            } else {
                if(serviceBound)
                    spService.play(Constants.LOSE);

                button_1.setImageResource(R.drawable.button_red_background);
                new Handler().postDelayed(() -> setNextLevel(false), 750);
            }
        } else if (view.getId() == R.id.button_2) {
            if(!clickableUI)
                return;
            clickableUI = false;

            if (selectedButton == 2) {
                if(serviceBound)
                    spService.play(Constants.WIN);

                button_2.setImageResource(R.drawable.button_green_background);
                new Handler().postDelayed(() -> setNextLevel(true), 750);
            } else {
                if(serviceBound)
                    spService.play(Constants.LOSE);

                button_2.setImageResource(R.drawable.button_red_background);
                new Handler().postDelayed(() -> setNextLevel(false), 750);
            }
        } else if (view.getId() == R.id.button_3) {
            if(!clickableUI)
                return;
            clickableUI = false;

            if (selectedButton == 3) {
                if(serviceBound)
                    spService.play(Constants.WIN);

                button_3.setImageResource(R.drawable.button_green_background);
                new Handler().postDelayed(() -> setNextLevel(true), 750);
            } else {
                if(serviceBound)
                    spService.play(Constants.LOSE);

                button_3.setImageResource(R.drawable.button_red_background);
                new Handler().postDelayed(() -> setNextLevel(false), 750);
            }
        } else {
            Log.d(TAG, "Unimplemented call");
        }
    }

    private void setNextLevel(boolean win){
        if(win){
            if(currentLevel == Constants.MAX_LEVEL){
                onGameCompleted();
                return;
            }

            currentLevel += 1;
            level = Levels.getLevel(currentLevel);
            SharedValues.setInt(this, Constants.KEY_CURRENT_LEVEL, currentLevel);

            int completedLevel = SharedValues.getInt(this, Constants.KEY_COMPLETED_LEVEL, 1);
            if(completedLevel <= currentLevel){
                SharedValues.setInt(this, Constants.KEY_COMPLETED_LEVEL, currentLevel);
            }

        }
        setLevelUI();
    }

    private void onGameCompleted(){
        Log.d(TAG, "onGameCompleted");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            SystemUtils.enableFullScreenUI(this);
        }
    }

    @Override
    public void onComplete() {
        if (onComplete) {
            return;
        }

        onComplete = true;

        Log.d(TAG, "onComplete");
        setButtonText();

        buttonContainer.setVisibility(View.VISIBLE);
    }

    private void setButtonText() {
        ArrayList<String> names = new ArrayList<>(Arrays.asList(Levels.names));
        String levelObjName = level.getObjectName();
        names.remove(levelObjName);

        selectedButton = new Random().nextInt(3) + 1;

        if (selectedButton == 1) {
            ((TextView) findViewById(R.id.text_1)).setText(getI18nString(levelObjName));
        } else {
            int index = new Random().nextInt(names.size() - 1);
            String random = names.remove(index);
            ((TextView) findViewById(R.id.text_1)).setText(getI18nString(random));
        }

        if (selectedButton == 2) {
            ((TextView) findViewById(R.id.text_2)).setText(getI18nString(levelObjName));
        } else {
            int index = new Random().nextInt(names.size() - 1);
            String random = names.remove(index);
            ((TextView) findViewById(R.id.text_2)).setText(getI18nString(random));
        }

        if (selectedButton == 3) {
            ((TextView) findViewById(R.id.text_3)).setText(getI18nString(levelObjName));
        } else {
            int index = new Random().nextInt(names.size() - 1);
            String random = names.remove(index);
            ((TextView) findViewById(R.id.text_3)).setText(getI18nString(random));
        }
    }

    private String getI18nString(String name) {
        switch (name) {
            case "Soccer":
                return getString(R.string.soccer);
            case "Basketball":
                return getString(R.string.basketball);
            case "Volleyball":
                return getString(R.string.volleyball);
            case "Tennis":
                return getString(R.string.tennis);
            case "Golf":
                return getString(R.string.golf);
            case "Bowling":
                return getString(R.string.bowling);
            case "Pool":
                return getString(R.string.pool);
            case "Hockey":
                return getString(R.string.hockey);
            default:
                return "Soccer";
        }
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