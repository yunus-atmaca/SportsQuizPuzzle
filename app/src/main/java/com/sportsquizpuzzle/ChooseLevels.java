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
import com.sportsquizpuzzle.utils.SPController;
import com.sportsquizpuzzle.utils.SharedValues;

import java.util.ArrayList;
import java.util.Objects;

public class ChooseLevels extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "Levels-Fragment";

    private View root;

    private int completedLevel = 1;

    private SPController spController;

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
        root = inflater.inflate(R.layout.choose_levels, container, false);

        init();

        return root;
    }

    private void init() {
        spController = SPController.getInstance(getContext());

        String language = Constants.LAN_ENG;

        if (getContext() != null) {
            completedLevel = SharedValues.getInt(getContext(), Constants.KEY_COMPLETED_LEVEL, 1);

            language = SharedValues.getString(getContext(), Constants.KEY_LANGUAGE, Constants.LAN_ENG);
        }

        ((ImageView) root.findViewById(R.id.levels)).setImageResource(language.equals(Constants.LAN_ENG) ?
                R.drawable.ic_levels : R.drawable.ic_levels_ru);

        ArrayList<ImageView> levels = new ArrayList<>();
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

            if (index <= completedLevel) {
                imageView.setImageResource(getDrawableId(index, true));
            } else {
                imageView.setImageResource(getDrawableId(index, false));
            }
            ++index;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.level_1) {
            if (1 <= completedLevel) {
                applyChanges(1);
            }
        } else if (view.getId() == R.id.level_2) {
            if (2 <= completedLevel) {
                applyChanges(2);
            }
        } else if (view.getId() == R.id.level_3) {
            if (3 <= completedLevel) {
                applyChanges(3);
            }
        } else if (view.getId() == R.id.level_4) {
            if (4 <= completedLevel) {
                applyChanges(4);
            }
        } else if (view.getId() == R.id.level_5) {
            if (5 <= completedLevel) {
                applyChanges(5);
            }
        } else if (view.getId() == R.id.level_6) {
            if (6 <= completedLevel) {
                applyChanges(6);
            }
        } else if (view.getId() == R.id.level_7) {
            if (7 <= completedLevel) {
                applyChanges(7);
            }
        } else if (view.getId() == R.id.level_8) {
            if (8 <= completedLevel) {
                applyChanges(8);
            }
        } else if (view.getId() == R.id.close) {
            spController.play(Constants.BUTTON);

            dismiss();
            onDestroy();
        } else {
            Log.d(TAG, "Unimplemented call");
        }
    }

    private int getDrawableId(int index, boolean completed) {
        int id = completed ? R.drawable.ic_level_1_completed :
                R.drawable.ic_level_1_not_completed;

        if (index == 2) {
            id = completed ? R.drawable.ic_level_2_completed :
                    R.drawable.ic_level_2_not_completed;
        } else if (index == 3) {
            id = completed ? R.drawable.ic_level_3_completed :
                    R.drawable.ic_level_3_not_completed;
        } else if (index == 4) {
            id = completed ? R.drawable.ic_level_4_completed :
                    R.drawable.ic_level_4_not_completed;
        } else if (index == 5) {
            id = completed ? R.drawable.ic_level_5_completed :
                    R.drawable.ic_level_5_not_completed;
        } else if (index == 6) {
            id = completed ? R.drawable.ic_level_6_completed :
                    R.drawable.ic_level_6_not_completed;
        } else if (index == 7) {
            id = completed ? R.drawable.ic_level_7_completed :
                    R.drawable.ic_level_7_not_completed;
        } else if (index == 8) {
            id = completed ? R.drawable.ic_level_8_completed :
                    R.drawable.ic_level_8_not_completed;
        }
        return id;
    }

    private void applyChanges(int level) {
        spController.play(Constants.BUTTON);

        if (getContext() != null)
            SharedValues.setInt(getContext(), Constants.KEY_CURRENT_LEVEL, level);

        dismiss();
        onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

        Objects.requireNonNull(getDialog()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
