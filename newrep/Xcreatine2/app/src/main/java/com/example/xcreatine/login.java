package com.example.xcreatine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {


    EditText mail;
    EditText password;
    private Button log_btn;
    private Button R_btn;

    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    VideoView log_vid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log_vid = findViewById(R.id.login_vid);

        String path = "android.resource://com.example.xcreatine/" + R.raw.loginvid;
        Uri u = Uri.parse(path);
        log_vid.setVideoURI(u);
        log_vid.start();

        log_vid.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });


        mail = findViewById(R.id.login_mail);
        password = findViewById(R.id.log_password);
        log_btn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progress_bar_login);
        mAuth = FirebaseAuth.getInstance();


        R_btn = findViewById(R.id.reg_btn);
        R_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, registration.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        log_vid.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        log_vid.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        log_vid.stopPlayback();
        super.onDestroy();
    }

    public void login_here(View view) {

        progressBar.setVisibility(View.VISIBLE);
        String email = mail.getText().toString();
        String log_password = password.getText().toString();


        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(log_password)) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_LONG).show();
        } else {

            mAuth.signInWithEmailAndPassword(email, log_password)
                    .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                mail.setText("");
                                password.setText("");
                                Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG).show();


                                if (email.equals("admin@gmail.com") && log_password.equals("admin007")) {
                                    Intent intent = new Intent(login.this, admin.class);
                                    startActivity(intent);
                                } else {
                                    Intent i = new Intent(login.this, MainActivity.class);
                                    startActivity(i);
                                }
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                mail.setText("");
                                password.setText("");
                                Toast.makeText(getApplicationContext(), "Error!!! Invalid credentials", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


        }
    }
}