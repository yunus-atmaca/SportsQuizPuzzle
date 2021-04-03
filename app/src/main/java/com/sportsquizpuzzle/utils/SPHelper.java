package com.sportsquizpuzzle.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

import com.sportsquizpuzzle.R;


//SoundPoolHelper
public class SPHelper {

    private static final String TAG = "SP-HELPER";

    public static final int BUTTON = R.raw.button;
    public static final int DELETE = R.raw.delete;
    public static final int LOSE = R.raw.lose;
    public static final int START_GAME = R.raw.start_game;
    public static final int WIN = R.raw.win;

    private int button, delete, lose, startGame, win;

    private SoundPool pool = null;
    private final Context context;
    private final int[] sounds;
    private boolean soundOn;

    private final int UNDEFINED = -1;

    public SPHelper(Context context, int[] sounds, boolean soundOn) {
        this.context = context;
        this.sounds = sounds;
        this.soundOn = soundOn;

        initSP();
    }

    private void initSP() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        pool = new SoundPool.Builder()
                .setMaxStreams(sounds.length)
                .setAudioAttributes(attributes)
                .build();

        button = isContain(BUTTON, sounds) ? pool.load(context, BUTTON, 1) : UNDEFINED;
        delete = isContain(DELETE, sounds) ? pool.load(context, DELETE, 1) : UNDEFINED;
        lose = isContain(LOSE, sounds) ? pool.load(context, LOSE, 1) : -1;
        startGame = isContain(START_GAME, sounds) ? pool.load(context, START_GAME, 1) : UNDEFINED;
        win = isContain(WIN, sounds) ? pool.load(context, WIN, 1) : UNDEFINED;
    }

    public void setSoundOn(boolean soundOn){
        this.soundOn = soundOn;
    }

    public void play(int sound) {
        if(!this.soundOn)
            return;

        if (pool == null)
            initSP();

        switch (sound) {
            case BUTTON:
                if (button != UNDEFINED)
                    pool.play(button, 1, 1, 0, 0, 1);
                break;
            case DELETE:
                if (delete != UNDEFINED)
                    pool.play(delete, 1, 1, 0, 0, 1);
                break;
            case LOSE:
                if (lose != UNDEFINED)
                    pool.play(lose, 1, 1, 0, 0, 1);
                break;
            case START_GAME:
                if (startGame != UNDEFINED)
                    pool.play(startGame, 1, 1, 0, 0, 1);
                break;
            case WIN:
                if (win != UNDEFINED)
                    pool.play(win, 1, 1, 0, 0, 1);
                break;
            default:
                Log.d(TAG, "Undefined sound");
                break;
        }
    }

    public void release() {
        if (pool != null) {
            pool.release();
            pool = null;
        }
    }

    private boolean isContain(int sound, int[] sounds) {
        for (int s : sounds) {
            if (s == sound) {
                return true;
            }
        }
        return false;
    }
}
