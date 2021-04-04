package com.sportsquizpuzzle;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sportsquizpuzzle.customViews.Modal;
import com.sportsquizpuzzle.utils.Constants;
import com.sportsquizpuzzle.utils.SPService;
import com.sportsquizpuzzle.utils.SharedValues;
import com.sportsquizpuzzle.utils.i18n;

import java.util.Objects;

public class Settings extends DialogFragment implements View.OnClickListener, Modal.ModalListener {

    private static final String TAG = "Setting-Fragment";

    private View root;

    private ImageView music;
    private ImageView sound;
    private ImageView language;

    private boolean soundOn;
    private boolean musicOn;
    private String lan;

    private SPService spService;
    private boolean serviceBound;

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

        getAppValues();
        init();

        return root;
    }

    private void init() {
        Intent intent = new Intent(getContext(), SPService.class);
        getContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);

        music = root.findViewById(R.id.music);
        sound = root.findViewById(R.id.sound);
        language = root.findViewById(R.id.language);

        root.findViewById(R.id.remove).setOnClickListener(this);
        root.findViewById(R.id.close).setOnClickListener(this);

        music.setOnClickListener(this);
        sound.setOnClickListener(this);
        language.setOnClickListener(this);

        music.setImageResource(musicOn ? R.drawable.ic_music_on : R.drawable.ic_music_off);
        sound.setImageResource(soundOn ? R.drawable.ic_sound_on : R.drawable.ic_sound_off);
        language.setImageResource(lan.equals(Constants.LAN_ENG) ? R.drawable.ic_lan_en : R.drawable.ic_lan_ru);
    }

    private void getAppValues() {
        if (getContext() == null)
            return;

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
            if(serviceBound)
                spService.play(Constants.BUTTON);

            dismiss();
            onDestroy();
        } else {
            Log.d(TAG, "Unimplemented call");
        }
    }

    private void onClickMusic() {
        if(serviceBound)
            spService.play(Constants.BUTTON);

        if (musicOn) {
            musicOn = false;
            music.setImageResource(R.drawable.ic_music_off);

            if(serviceBound)
                spService.setBackgroundMusic(false);
        } else {
            musicOn = true;
            music.setImageResource(R.drawable.ic_music_on);

            if(serviceBound)
                spService.setBackgroundMusic(true);
        }
    }

    private void onClickSound() {
        if (soundOn) {
            if(serviceBound)
                spService.setSoundOn(false);

            soundOn = false;
            sound.setImageResource(R.drawable.ic_sound_off);
        } else {
            if(serviceBound) {
                spService.setSoundOn(true);
                spService.play(Constants.BUTTON);
            }

            soundOn = true;
            sound.setImageResource(R.drawable.ic_sound_on);
        }
    }

    private void onClickLanguage() {
        if(serviceBound)
            spService.play(Constants.BUTTON);

        if (lan.equals(Constants.LAN_ENG)) {
            lan = Constants.LAN_RU;
            language.setImageResource(R.drawable.ic_lan_ru);

            i18n.loadLanguage(getActivity(), Constants.LAN_RU);
        } else {
            lan = Constants.LAN_ENG;
            language.setImageResource(R.drawable.ic_lan_en);

            i18n.loadLanguage(getActivity(), Constants.LAN_ENG);
        }
    }

    private void onClickRemove() {
        if(serviceBound)
            spService.play(Constants.BUTTON);

        Modal modal = new Modal(getString(R.string.remove_warning), this);
        modal.show(getChildFragmentManager(), "Modal-Remove");
    }

    @Override
    public void onModalResult(boolean res) {
        if (res) {
            if(serviceBound)
                spService.play(Constants.DELETE);

            if(getContext() == null)
                return;

            SharedValues.setInt(getContext(), Constants.KEY_CURRENT_LEVEL, 1);
        }else{
            if(serviceBound)
                spService.play(Constants.BUTTON);
        }
    }

    @Override
    public void onDestroy() {
        if (getContext() == null) {
            super.onDestroy();
            return;
        }

        SharedValues.setBoolean(getContext(), Constants.KEY_SOUND, soundOn);
        SharedValues.setBoolean(getContext(), Constants.KEY_MUSIC, musicOn);
        SharedValues.setString(getContext(), Constants.KEY_LANGUAGE, lan);

        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

        Objects.requireNonNull(getDialog()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
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
