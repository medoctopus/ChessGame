package com.example.chessgame;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    // 0: white
    // 1: black
    // 2: empty
    private int player = 0;
    private int[] gameState = {2,2,2,2,2,2,2,2,2};
    private int[][] winConditions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    private boolean gameContinue = true;

    public void play(View view) {
        ImageView imageView = (ImageView) view;

        int gridIndex = Integer.parseInt(imageView.getTag().toString());

        if(gameState[gridIndex]==2 && gameContinue) {

            gameState[gridIndex] = player;

            imageView.setTranslationY(-1500);
            if(player==0) {
                imageView.setImageResource(R.drawable.white);
                player = 1;
            } else {
                imageView.setImageResource(R.drawable.black);
                player = 0;
            }

            imageView.animate().rotation(360*5).translationYBy(1500).setDuration(100);

            for(int[] winCondition: winConditions) {
                if(gameState[winCondition[0]]==gameState[winCondition[1]] && gameState[winCondition[1]]==gameState[winCondition[2]] && gameState[winCondition[0]]!=2) {
                    String winner = "";
                    if(player==1) {
                        winner = "SpongeBob";
                    } else {
                        winner = "Patrick";
                    }
                    Toast.makeText(this, winner+" wins the game!", Toast.LENGTH_LONG).show();
                    gameContinue = false;

                    ImageView imageViewBob = (ImageView) findViewById(R.id.imageViewBob);
                    imageViewBob.setVisibility(View.INVISIBLE);
                    ImageView imageViewPatrick = (ImageView) findViewById(R.id.imageViewPatrick);
                    imageViewPatrick.setVisibility(View.INVISIBLE);

                    VideoView videoView = (VideoView) findViewById(R.id.videoView);
                    videoView.setVisibility(View.VISIBLE);
                    if(winner.equals("Patrick")) {
                        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video1);
                    } else {
                        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video2);
                    }
                    MediaController mediaController = new MediaController(this);
                    mediaController.setAnchorView(videoView);
                    videoView.setMediaController(mediaController);
                    videoView.start();
                }
            }
        }
    }

    public void playVoicePatrick(View view) {
        MediaPlayer mediaPlayerPatrick = MediaPlayer.create(this, R.raw.patrickvoice);
        mediaPlayerPatrick.start();
    }

    public void playVoiceBob(View view) {
        MediaPlayer mediaPlayerBob = MediaPlayer.create(this, R.raw.spongebobvoice);
        mediaPlayerBob.start();
    }

    public void reset(View view) {
        finish();
        startActivity(getIntent());
    }

//    public void playAgain(View view) {
//        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
//        for(int i=0; i<gridLayout.getChildCount(); i++) {
//            ImageView grid = (ImageView) gridLayout.getChildAt(i);
//            grid.setImageDrawable(null);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}