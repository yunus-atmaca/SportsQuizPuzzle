package com.sportsquizpuzzle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sportsquizpuzzle.utils.Constants;
import com.sportsquizpuzzle.utils.SharedValues;

import java.util.ArrayList;

public class Levels extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "Levels-Fragment";

    private View root;

    private ArrayList<ImageView> levels;

    private int currentLevel = -1;

    private LevelListener listener;

    public Levels(LevelListener listener) {
        this.listener = listener;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getDialog() == null)
            return;

        getDialog().getWindow().setWindowAnimations(R.style.anim_slide);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.settings, container, false);

        init();

        return root;
    }

    private void init() {
        String language = Constants.LAN_ENG;

        if (getContext() != null){
            currentLevel = SharedValues.getInt(getContext(), Constants.KEY_CURRENT_LEVEL, -1);
            language = SharedValues.getString(getContext(), Constants.KEY_LANGUAGE, Constants.LAN_ENG);
        }

        ((ImageView) root.findViewById(R.id.levels)).setImageResource(language.equals(Constants.LAN_ENG) ?
                R.drawable.ic_levels : R.drawable.ic_levels_ru);

        levels = new ArrayList<>();
        levels.add(root.findViewById(R.id.level_1));
        levels.add(root.findViewById(R.id.level_2));
        levels.add(root.findViewById(R.id.level_3));
        levels.add(root.findViewById(R.id.level_4));
        levels.add(root.findViewById(R.id.level_5));
        levels.add(root.findViewById(R.id.level_6));
        levels.add(root.findViewById(R.id.level_7));
        levels.add(root.findViewById(R.id.level_8));

        root.findViewById(R.id.close).setOnClickListener(this);

        int index = 1;
        for (ImageView imageView : levels) {
            imageView.setOnClickListener(this);

            if (index <= currentLevel) {
                imageView.setImageResource(getDrawableId(index));
            }
            ++index;
            imageView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.level_1) {
            if (1 <= currentLevel) {

            }
        } else if (view.getId() == R.id.level_2) {
            if (2 <= currentLevel) {

            }
        } else if (view.getId() == R.id.level_3) {
            if (3 <= currentLevel) {

            }
        } else if (view.getId() == R.id.level_4) {
            if (4 <= currentLevel) {

            }
        } else if (view.getId() == R.id.level_5) {
            if (5 <= currentLevel) {

            }
        } else if (view.getId() == R.id.level_6) {
            if (6 <= currentLevel) {

            }
        } else if (view.getId() == R.id.level_7) {
            if (7 <= currentLevel) {

            }
        } else if (view.getId() == R.id.level_8) {
            if (8 <= currentLevel) {

            }
        } else if (view.getId() == R.id.close) {

        } else {
            Log.d(TAG, "Unimplemented call");
        }
    }

    private int getDrawableId(int index) {
        int id = R.id.level_1;

        if (index == 2) {
            id = R.id.level_2;
        } else if (index == 3) {
            id = R.id.level_3;
        } else if (index == 4) {
            id = R.id.level_4;
        } else if (index == 5) {
            id = R.id.level_5;
        } else if (index == 6) {
            id = R.id.level_6;
        } else if (index == 7) {
            id = R.id.level_7;
        } else if (index == 8) {
            id = R.id.level_8;
        }
        return id;
    }

    public interface LevelListener {
        void onLevelSelected(int level);
    }
}
