package com.hidayat.edutude;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView mTvSignUp, mTvLogin;
    private EditText mEtEmail, mEtPassword;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEtEmail = findViewById(R.id.et_login_email);
        mEtPassword = findViewById(R.id.et_login_password);
        mTvLogin = findViewById(R.id.tv_login_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEtEmail.getText().toString();
                String pass = mEtPassword.getText().toString();

                if (!email.isEmpty()&&!pass.isEmpty()) {

                    mFirebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Login success
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            } else {
                              //Something went wrong
                                Toast.makeText(LoginActivity.this, "Something went wrong! Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Toast.makeText(LoginActivity.this, "Log in canceled! Please try again", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    //Field(s) blank
                    Toast.makeText(LoginActivity.this, "Please fill the blank field(s)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mTvSignUp = findViewById(R.id.tv_login_signup);
        mTvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
    }
}
