package com.hidayat.edutude;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

private FirebaseAuth mFirebaseAuth;
private TextView mTvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvEmail = findViewById(R.id.tv_email);
        mFirebaseAuth = FirebaseAuth.getInstance();
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
