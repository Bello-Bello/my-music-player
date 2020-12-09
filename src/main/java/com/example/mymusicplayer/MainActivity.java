package com.example.mymusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    //MediaPlayer player = MediaPlayer.create(this, R.raw.mysong);
    Button play, pause, stop;
    SeekBar seekBar;
    Handler seekHandler;
    Runnable seekRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* get id of button */
        //play=(Button)findViewById(R.id.play);
        pause=(Button)findViewById(R.id.pause);
        stop=(Button)findViewById(R.id.stop);

        seekBar=(SeekBar)findViewById(R.id.seekBar);
        //seekBar.setMax(player.getDuration());

        seekHandler = new Handler();
        int maxValue=seekBar.getMax();
        int seekBarValue= seekBar.getProgress();
        seekBar.setMax(150);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    progressValue = progress;
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(progressValue);
                Toast.makeText(MainActivity.this, "progress:" + progressValue, Toast.LENGTH_SHORT).show();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "pausing", Toast.LENGTH_LONG).show();
                if(player.isPlaying()){
                    player.pause();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayer();
                Toast.makeText(getApplicationContext(), "stopping", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void play(View v){
        Toast.makeText(getApplicationContext(), "play", Toast.LENGTH_LONG).show();
        if(player == null){
            player = MediaPlayer.create(this, R.raw.mysong);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer player) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }

    private void stopPlayer(){
        if(player.isPlaying()){
            player.release();
            player = null;
            Toast.makeText(this, "Player released", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        player.reset();
        stopPlayer();
    }
}