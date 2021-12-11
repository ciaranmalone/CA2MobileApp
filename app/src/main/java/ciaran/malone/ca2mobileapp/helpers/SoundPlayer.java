package ciaran.malone.ca2mobileapp.helpers;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import ciaran.malone.ca2mobileapp.R;

public class SoundPlayer {

    private AudioAttributes audioAttributes;
    private static SoundPool soundPool;
    private static int spinSound, tickSound;

    public SoundPlayer(Context context) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                .setUsage(audioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

            soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(5)
                .build();
        } else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        spinSound = soundPool.load(context, R.raw.spin_sound,1);
        tickSound = soundPool.load(context, R.raw.tick, 1);
    }

    public void playSpinSound() {
        soundPool.play(spinSound, 1f, 1f, 1, 0, 1f);
    }

    public void playTickSound() {
        soundPool.play(tickSound, 1f, 1f, 1, 0, 1f);
    }

}
