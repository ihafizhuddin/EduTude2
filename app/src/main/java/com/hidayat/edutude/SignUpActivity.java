package com.hidayat.edutude;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEtEmail, mEtPassword, mEtRepeatPassword;
    private TextView mTvSignUp;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mEtEmail = findViewById(R.id.et_signup_email);
        mEtPassword = findViewById(R.id.et_signup_password);
        mEtRepeatPassword = findViewById(R.id.et_signup_password_repeat);
        mTvSignUp = findViewById(R.id.tv_signup);



        mTvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEtEmail.getText().toString();
                String password = mEtPassword.getText().toString();
                String repeatPassword = mEtRepeatPassword.getText().toString();

                if (!email.isEmpty()&&!password.isEmpty()&&!repeatPassword.isEmpty()) {
                    //Got all the values
                    if (password.equals(repeatPassword)) {
                        //Passwords matched
                        if (password.length()>6) {

                            mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        //User created
                                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        //Something went wrong
                                        Toast.makeText(SignUpActivity.this, "Something went wrong, please try again!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Failed
                                    Toast.makeText(SignUpActivity.this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }).addOnCanceledListener(new OnCanceledListener() {
                                @Override
                                public void onCanceled() {
                                    //Canceled
                                    Toast.makeText(SignUpActivity.this, "Process canceled, try again!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            //Password too short
                            Toast.makeText(SignUpActivity.this, "Password must contain 7 characters or more", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        //Passwords didn't match
                        Toast.makeText(SignUpActivity.this, "Passwords didn't match!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //Don't leave field(s) blank
                    Toast.makeText(SignUpActivity.this, "Please fill the blank field(s)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
