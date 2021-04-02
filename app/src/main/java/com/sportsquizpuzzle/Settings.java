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

import com.sportsquizpuzzle.utils.Constants;
import com.sportsquizpuzzle.utils.SharedValues;
import com.sportsquizpuzzle.utils.SystemUtils;

public class Settings extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "Setting-Fragment";

    private View root;

    private ImageView music;
    private ImageView sound;
    private ImageView language;
    private ImageView remove;
    private ImageView close;

    private boolean soundOn;
    private boolean musicOn;
    private String lan;

    private SettingListener listener;

    public Settings(SettingListener listener){
        this.listener = listener;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.SettingsAnim;
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

        getAppValues();
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

        music.setImageResource(musicOn ? R.drawable.ic_music_on : R.drawable.ic_music_off);
        sound.setImageResource(soundOn ? R.drawable.ic_sound_on : R.drawable.ic_sound_off);
        language.setImageResource(lan.equals(Constants.LAN_ENG) ? R.drawable.ic_lan_en : R.drawable.ic_lan_ru);
    }

    private void getAppValues() {
        soundOn = SharedValues.getBoolean(getContext(), Constants.KEY_SOUND, true);
        musicOn = SharedValues.getBoolean(getContext(), Constants.KEY_MUSIC, true);
        lan = SharedValues.getString(getContext(), Constants.KEY_LANGUAGE, Constants.LAN_ENG);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.music) {
            onClickMusic();
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
        if (musicOn) {
            musicOn = false;
            music.setImageResource(R.drawable.ic_music_off);
        } else {
            musicOn = true;
            music.setImageResource(R.drawable.ic_music_on);
        }

        listener.onMusicClicked(musicOn);
    }

    private void onClickSound() {
        if (soundOn) {
            soundOn = false;
            sound.setImageResource(R.drawable.ic_sound_off);
        } else {
            soundOn = true;
            sound.setImageResource(R.drawable.ic_sound_on);
        }
    }

    private void onClickLanguage() {
        if (lan.equals(Constants.LAN_ENG)) {
            lan = Constants.LAN_RU;
            language.setImageResource(R.drawable.ic_lan_ru);
        } else {
            lan = Constants.LAN_ENG;
            language.setImageResource(R.drawable.ic_lan_en);
        }

        listener.onLanguageChanged(lan);
    }

    private void onClickRemove() {

    }

    public interface SettingListener {
        void onLanguageChanged(String lan);
        void onMusicClicked(boolean music);
    }

    @Override
    public void onDestroy() {
        SharedValues.setBoolean(getContext(), Constants.KEY_SOUND, soundOn);
        SharedValues.setBoolean(getContext(), Constants.KEY_MUSIC, musicOn);
        SharedValues.setString(getContext(), Constants.KEY_LANGUAGE, lan);
        super.onDestroy();
    }
}
