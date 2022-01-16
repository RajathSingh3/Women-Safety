package com.example.womensafety;

import android.content.Context;
import android.media.MediaPlayer;

public class MySiren {

    static int decidingNumber;
    private static MediaPlayer player = null;
    private static MySiren single_inst = null;
    private static MySirenInterface playerInterface = null;

    public interface MySirenInterface{
        public void onPlayClicked();
        public void onPauseClicked();
    }

    private MySiren(Context context,MySirenInterface playerInterface) {
        player = MediaPlayer.create(context, R.raw.policesiren);
        if(playerInterface!=null)
            this.playerInterface = playerInterface;
    }

    public static MySiren getInstance(Context context, MySirenInterface playerInterface) {
        if (single_inst == null) {
            single_inst = new MySiren(context,playerInterface);
        }
        return single_inst;
    }

    public void play() {
        if (player != null) {
            player.setLooping(true);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
            player.start();
            if(playerInterface != null)
                playerInterface.onPlayClicked();
        }
        decidingNumber = 1;
    }

    void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            if(playerInterface != null)
                playerInterface.onPauseClicked();
        }
        single_inst = null;
        decidingNumber = 2;
    }

}

