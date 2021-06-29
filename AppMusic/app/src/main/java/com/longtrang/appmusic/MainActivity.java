package com.longtrang.appmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int position = 0;
    ArrayList<Song> arraySong;
    TextView txtTitle,txtSongTime,txtTotalTime;
    SeekBar skSong;
    ImageButton btnforwards,btnplay,btnstop,btnbackforwards;
    MediaPlayer mediaPlayer;
    Animation animation;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animation = AnimationUtils.loadAnimation(this,R.anim.anim_backgroundplay);
        AnhXa();
        AddSong();
        CreateMedia();
        SetTimeTotal();
        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });
        btnbackforwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                btnplay.setImageResource(R.drawable.pause);
                if(position < 0){
                    position = arraySong.size() - 1;
                }if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                CreateMedia();
                SetTimeTotal();
                mediaPlayer.start();
            }
        });
        btnforwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                btnplay.setImageResource(R.drawable.pause);
                if(position >= arraySong.size()){
                    position = 0;
                }if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                CreateMedia();
                SetTimeTotal();
                mediaPlayer.start();
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnplay.setImageResource(R.drawable.play);
                CreateMedia();
            }
        });
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnplay.setImageResource(R.drawable.play);
                }
                else{
                    mediaPlayer.start();
                    btnplay.setImageResource(R.drawable.pause);
                }
                SetTimeTotal();
                UpdateTimeSong();
                img.startAnimation(animation);
            }

        });

    }

    private void AddSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Tránh Duyên",R.raw.tranhduyen));
        arraySong.add(new Song("Phải Chăng Em Đã Yêu",R.raw.phaichangemdayeu));
        arraySong.add(new Song("Níu Duyên",R.raw.niuduyen));
        arraySong.add(new Song("Như Gió Với Mây",R.raw.nhugiovoimay));
        arraySong.add(new Song("Ngăm Hoa Lệ Rơi",R.raw.ngamhoaleroi));
        arraySong.add(new Song("Bán Duyên",R.raw.banduyen));
        arraySong.add(new Song("Áng Mây Vô Tình",R.raw.angmayvotinh));

    }
    private void CreateMedia(){
        mediaPlayer = MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
    }
    private void SetTimeTotal(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotalTime.setText(simpleDateFormat.format(mediaPlayer.getDuration()) +"");
        skSong.setMax(mediaPlayer.getDuration());
    }
    private void UpdateTimeSong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                txtSongTime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                skSong.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        btnplay.setImageResource(R.drawable.pause);
                        if(position >= arraySong.size()){
                            position = 0;
                        }if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }
                        CreateMedia();
                        SetTimeTotal();
                        mediaPlayer.start();
                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }
    private void AnhXa() {
        txtTitle        = (TextView) findViewById(R.id.tvsongname);
        txtSongTime     = (TextView) findViewById(R.id.tvsongtime);
        txtTotalTime    = (TextView) findViewById(R.id.tvtotaltime);
        skSong          = (SeekBar) findViewById(R.id.seekBar);
        btnbackforwards = (ImageButton) findViewById(R.id.imgbtnBackwards);
        btnforwards     = (ImageButton) findViewById(R.id.imgbtnForwards);
        btnplay         = (ImageButton) findViewById(R.id.imgbtnPlay);
        btnstop         = (ImageButton) findViewById(R.id.imgbtnStop);
        img = (ImageView) findViewById(R.id.backgroundplay);
    }
}