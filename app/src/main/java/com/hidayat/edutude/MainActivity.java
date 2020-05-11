package com.hidayat.edutude;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends YouTubeBaseActivity {
    private static final String TAG = "MainActivity";

private FirebaseAuth mFirebaseAuth;
private TextView mTvEmail;

YouTubePlayerView  mYouTubePlayerView, nYoutubePlayerView;
Button btn_play1, btn_play2;
YouTubePlayer.OnInitializedListener mOnInitializedListener, nOnInitializedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvEmail = findViewById(R.id.tv_email);
        mFirebaseAuth = FirebaseAuth.getInstance();
        btn_play1 = (Button) findViewById(R.id.btn_play);
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.view_YouTubePlay);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadPlaylist("PLYVR0A4acpNYSCN-0f7f9TB8XgHKNmRhz");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        btn_play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mYouTubePlayerView.initialize(YoutubeConfig.getApiKey(), mOnInitializedListener);
            }
        });


        //=================
        btn_play2 = (Button) findViewById(R.id.btn_play2);
        nYoutubePlayerView = (YouTubePlayerView) findViewById(R.id.view_YouTubePlay2);

        nOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadPlaylist("PLFt_AvWsXl0f4c56CbvYi038zmCmoZ4CQ");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        btn_play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nYoutubePlayerView.initialize(YoutubeConfig.getApiKey(), nOnInitializedListener);
            }
        });
        //=================

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mFirebaseUser =  mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser!=null) {
            //someone logged in
            mTvEmail.setText(mFirebaseUser.getEmail());
        } else {
            //no one logged in
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public void logout(View view) {
        mFirebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }
}
