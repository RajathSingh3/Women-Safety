package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class siren extends AppCompatActivity implements MySiren.MySirenInterface {

    Button stop, play;
    TextView stopText, playText;

    public static final String CHANNEL_ID = "siren_channel";
    public static final String CHANNEL_NAME = "siren";
    public static final String CHANNEL_DESC= "play_pause_siren";
    NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siren);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        playText = findViewById(R.id.txt_view3);
        stopText = findViewById(R.id.txt_view4);
        play = findViewById(R.id.b5);
        stop = findViewById(R.id.b6);

        decide(MySiren.decidingNumber);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        decide(MySiren.decidingNumber);
        MySiren.getInstance(this,this);
    }

    private void displaySirenNotification(){
        Intent intent = new Intent(this, siren.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent = new Intent(this, NotificationReceiver.class);
        playIntent.setAction("play_clicked");
        PendingIntent playPendingIntent =
                PendingIntent.getBroadcast(getApplicationContext(),
                        2,
                        playIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pauseIntent = new Intent(this, NotificationReceiver.class);
        pauseIntent.setAction("pause_clicked");
        PendingIntent pausePendingIntent =
                PendingIntent.getBroadcast(getApplicationContext(),
                        2,
                        pauseIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat
                .Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Siren")
                .setContentText("Play or Pause siren")
                .setPriority(Notification.PRIORITY_LOW)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.pause,"PLAY",playPendingIntent)
                .addAction(R.drawable.pause,"PAUSE",pausePendingIntent);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,mBuilder.build());
    }

    public void decide(int number) {
        if (number == 1) {
            play.setVisibility(View.INVISIBLE);
            playText.setVisibility(View.INVISIBLE);

            stop.setVisibility(View.VISIBLE);
            stopText.setVisibility(View.VISIBLE);

        } else {
            stop.setVisibility(View.INVISIBLE);
            stopText.setVisibility(View.INVISIBLE);

            play.setVisibility(View.VISIBLE);
            playText.setVisibility(View.VISIBLE);
        }
    }

    public void play(View view) {
        MySiren.getInstance(this,this).play();
        MySiren.decidingNumber = 1;
        decide(MySiren.decidingNumber);

        displaySirenNotification();
    }

    public void stop(View v) {
        MySiren.getInstance(this,this).stopPlayer();
        MySiren.decidingNumber = 0;
        decide(MySiren.decidingNumber);
    }

    @Override
    public void onPlayClicked() {
        decide(1);
    }

    @Override
    public void onPauseClicked() {
        Toast.makeText(this,"Siren Paused",Toast.LENGTH_SHORT).show();
        decide(2);
    }
}