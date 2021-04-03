package com.sportsquizpuzzle.utils;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class SPService extends Service implements SoundPool.OnLoadCompleteListener {
    private static final String TAG = "SP-Service";
    private static final int NUMBER_OF_SOUND = 6;

    private final IBinder binder = new SPBinder();

    private SoundPool sp;
    private int background, button, delete, lose, startGame, win;
    private int backgroundMusic;
    private boolean musicOn;
    private boolean soundOn;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "||SP-Service Created||");

        //get app settings
        musicOn = SharedValues.getBoolean(this, Constants.KEY_MUSIC, true);
        soundOn = SharedValues.getBoolean(this, Constants.KEY_SOUND, true);

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        sp = new SoundPool.Builder()
                .setMaxStreams(NUMBER_OF_SOUND)
                .setAudioAttributes(attributes)
                .build();

        sp.setOnLoadCompleteListener(this);

        background = sp.load(this, Constants.BACKGROUND, 1);
        button = sp.load(this, Constants.BUTTON, 1);
        delete = sp.load(this, Constants.DELETE, 1);
        lose = sp.load(this, Constants.LOSE, 1);
        win = sp.load(this, Constants.WIN, 1);
        startGame = sp.load(this, Constants.START_GAME, 1);

        Log.d(TAG, "MUSIC---> " + musicOn);
    }

    public void play(int sound) {
        if(!soundOn)
            return;

        switch (sound) {
            case Constants.BUTTON:
                sp.play(button, 1, 1, 0, 0, 1);
                break;
            case Constants.DELETE:
                sp.play(delete, 1, 1, 0, 0, 1);
                break;
            case Constants.LOSE:
                sp.play(lose, 1, 1, 0, 0, 1);
                break;
            case Constants.START_GAME:
                sp.play(startGame, 1, 1, 0, 0, 1);
                break;
            case Constants.WIN:
                sp.play(win, 1, 1, 0, 0, 1);
                break;
            default:
                Log.d(TAG, "Undefined sound");
                break;
        }
    }

    public void setBackgroundMusic(boolean music) {
        if (music) {
            backgroundMusic = sp.play(background, 1, 1, 0, -1, 1);
            musicOn = true;
        } else {
            sp.stop(backgroundMusic);
            musicOn = false;
        }
    }

    public void setSoundOn(boolean sound){
        soundOn = sound;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        if (sp != null) {
            sp.release();
            sp = null;
        }

        Log.d(TAG, "||SP-Service Destroyed||");
        super.onDestroy();
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int i, int i1) {
        if(i == background){
            if(musicOn){
                backgroundMusic = sp.play(background, 1, 1, 0, -1, 1);
            }
        }
    }

    public class SPBinder extends Binder {
        public SPService getService() {
            return SPService.this;
        }
    }
}
