package com.example.sajagjain.mework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AlarmAlert extends AppCompatActivity {

    TextView alarmWindowTaskName;
    TextView alarmWindowTaskDescription;
    Button alarmWindowAlarmSushButton;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm_alert);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        //Vibrate Service
        Vibrator vibrate=(Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrate.vibrate(3000);

        //Linking Views
        alarmWindowTaskName=findViewById(R.id.alarm_screen_name_value);
        alarmWindowTaskDescription=findViewById(R.id.alarm_screen_desc_value);
        alarmWindowAlarmSushButton=findViewById(R.id.alarm_screen_sush_button);

        //Values From Intent
        String name=getIntent().getStringExtra("name");
        String desc=getIntent().getStringExtra("desc");

        //Plays Ringtone
        AudioManager audio = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        player = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        try {
            player.setVolume((float) (audio.getStreamVolume(AudioManager.STREAM_RING) / 7.0),
                    (float) (audio.getStreamVolume(AudioManager.STREAM_RING) / 7.0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        player.start();



        //Setting text values to views
        alarmWindowTaskName.setText(name);
        alarmWindowTaskDescription.setText(desc);

        //setOnClickListener to cancel sound
        alarmWindowAlarmSushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                AlarmAlert.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
        AlarmAlert.this.finish();
    }
}
