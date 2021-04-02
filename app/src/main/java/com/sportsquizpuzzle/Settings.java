package com.sportsquizpuzzle;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.sportsquizpuzzle.utils.SystemUtils;

public class Settings extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "Setting-Fragment";

    private View root;

    private ImageView music;
    private ImageView sound;
    private ImageView language;
    private ImageView remove;
    private ImageView close;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.SettingsAnim;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //SystemUtils.enableFullScreenUI((AppCompatActivity) getActivity());
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.settings, container, false);

        //SystemUtils.enableFullScreenUI((AppCompatActivity) getActivity());

        init();

        return root;
    }

    private void init() {
        music = root.findViewById(R.id.music);
        sound = root.findViewById(R.id.sound);
        language = root.findViewById(R.id.language);
        remove = root.findViewById(R.id.remove);
        close = root.findViewById(R.id.close);

        music.setOnClickListener(this);
        sound.setOnClickListener(this);
        language.setOnClickListener(this);
        remove.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.music) {
            onClickMusic();
            Log.d(TAG, "onClick");
        } else if (view.getId() == R.id.sound) {
            onClickSound();
        } else if (view.getId() == R.id.language) {
            onClickLanguage();
        } else if (view.getId() == R.id.remove) {
            onClickRemove();
        } else if (view.getId() == R.id.close) {
            dismiss();
            onDestroy();
        } else {
            Log.d(TAG, "Unimplemented call");
        }
    }

    private void onClickMusic() {
        if (music.getBackground() == ContextCompat.getDrawable(getContext(), R.drawable.ic_music_off)) {
            music.setImageResource(R.drawable.ic_music_on);
        } else {
            music.setImageResource(R.drawable.ic_music_off);
        }
    }

    private void onClickSound() {
        if (sound.getBackground() == ContextCompat.getDrawable(getContext(), R.drawable.ic_sound_off)) {
            sound.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_sound_on));
        } else {
            sound.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_sound_off));
        }
    }

    private void onClickLanguage() {
        if (language.getBackground() == ContextCompat.getDrawable(getContext(), R.drawable.ic_lan_en)) {
            language.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_lan_ru));
        } else {
            language.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_lan_en));
        }
    }

    private void onClickRemove() {

    }

    public interface SettingListener {
        void onClickMusic();

        void onClickSound();

        void onClickLanguage();

        void onClickRemove();
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
