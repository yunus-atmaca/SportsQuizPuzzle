package com.sportsquizpuzzle.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

public class SPController implements SoundPool.OnLoadCompleteListener {

    private static final String TAG = "SPController";
    private static final int NUMBER_OF_SOUND = 6;
    private static SPController ins = null;

    private SoundPool sp;
    private int background, button, delete, lose, startGame, win;

    private int backgroundMusic;
    private boolean isBackgroundPlaying;
    private boolean isLoaded;

    private boolean musicOn;
    private boolean soundOn;

    private Context context;

    private SPController(Context context){

        Log.d(TAG, "SPController -CONS");
        musicOn = SharedValues.getBoolean(context, Constants.KEY_MUSIC, true);
        soundOn = SharedValues.getBoolean(context, Constants.KEY_SOUND, true);
        isBackgroundPlaying = false;
        isLoaded = true;
        this.context = context;

        initializeSP();
    }

    public static SPController getInstance(Context context){
        if (ins == null)
            ins = new SPController(context);

        return ins;
    }

    private void initializeSP(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        sp = new SoundPool.Builder()
                .setMaxStreams(NUMBER_OF_SOUND)
                .setAudioAttributes(attributes)
                .build();

        sp.setOnLoadCompleteListener(this);

        button = sp.load(context, Constants.BUTTON, 1);
        delete = sp.load(context, Constants.DELETE, 1);
        lose = sp.load(context, Constants.LOSE, 1);
        win = sp.load(context, Constants.WIN, 1);
        startGame = sp.load(context, Constants.START_GAME, 1);
        background = sp.load(context, Constants.BACKGROUND, 1);
    }

    public void setBackgroundMusic(boolean music) {
        if(sp == null){
            initializeSP();
            musicOn = music;
            return;
        }

        if(!isLoaded)
            return;

        if (music) {
            if(!isBackgroundPlaying) {
                isBackgroundPlaying = true;
                backgroundMusic = sp.play(background, 1, 1, 0, -1, 1);
            }

            musicOn = true;
        } else {
            if(isBackgroundPlaying){
                isBackgroundPlaying = false;
                sp.stop(backgroundMusic);
            }
            musicOn = false;
        }
    }

    public void setSoundOn(boolean sound){
        soundOn = sound;
    }

    public void play(int sound) {
        if(!soundOn)
            return;

        if(sp == null){
            initializeSP();
        }

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

    public void releaseSP(){
        if (sp != null) {
            sp.release();
            sp = null;
            isBackgroundPlaying = false;
            isLoaded = false;
        }
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int i, int i1) {
        if(i == background){
            isLoaded = true;
            if(musicOn){
                if(!isBackgroundPlaying) {
                    Log.d(TAG, "onLoadComplete-backgroundMusic");
                    isBackgroundPlaying = true;
                    backgroundMusic = sp.play(background, 1, 1, 0, -1, 1);
                }
            }
        }
    }
}
