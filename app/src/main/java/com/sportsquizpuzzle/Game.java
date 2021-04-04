package com.sportsquizpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sportsquizpuzzle.listener.DragListener;
import com.sportsquizpuzzle.listener.TouchListener;
import com.sportsquizpuzzle.puzzle.Level;
import com.sportsquizpuzzle.puzzle.Levels;
import com.sportsquizpuzzle.puzzle.Piece;
import com.sportsquizpuzzle.utils.SystemUtils;

public class Game extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GAME-ACTIVITY";

    private int currentLevel;
    private Level level;

    private LinearLayoutCompat piecesHolder;
    private LinearLayoutCompat targetHolder;
    private View highlightedArea;

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
        findViewById(R.id.close).setOnClickListener(this);

        piecesHolder = findViewById(R.id.piece_holder);
        targetHolder = findViewById(R.id.target_holder);
        highlightedArea = findViewById(R.id.highlighted_view);

        DragListener listener = new DragListener(this, level.getPieces(), highlightedArea);

        piecesHolder.addView(getLayoutInflater().inflate(level.getPiecesLayoutId(), null));
        targetHolder.addView(getLayoutInflater().inflate(level.getTargetLayoutId(), null));

        for(Piece piece: level.getPieces()){
            targetHolder.findViewById(piece.getTargetHolderId()).setOnDragListener(listener);
            piecesHolder.findViewById(piece.getId()).setOnTouchListener(new TouchListener());
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.close) {
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