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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class registration extends AppCompatActivity {

    EditText username;
    EditText reg_mail;
    EditText contact;
    EditText password;
    EditText c_password;

    ProgressBar progressBar;
    private Button reg_btn;
    private FirebaseAuth mAuth;
    VideoView reg_vid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_registration);

        reg_vid = findViewById(R.id.regis_vid);

        String path = "android.resource://com.example.xcreatine/" + R.raw.regvid;
        Uri u = Uri.parse(path);
        reg_vid.setVideoURI(u);
        reg_vid.start();

        reg_vid.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        reg_btn = findViewById(R.id.register_btn);
        username = findViewById(R.id.reg_username);
        reg_mail = findViewById(R.id.reg_email);
        contact = findViewById(R.id.number);
        password = findViewById(R.id.reg_password);
        c_password = findViewById(R.id.confirm_password);
        progressBar = findViewById(R.id.progress_bar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        reg_vid.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        reg_vid.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        reg_vid.stopPlayback();
        super.onDestroy();
    }

    public void signup_here(View view) {
        progressBar.setVisibility(View.VISIBLE);
        final String plan = "basic";
        final String role = "user";
        final String email = reg_mail.getText().toString();
        final String reg_password = password.getText().toString();
        final String name = username.getText().toString();
        final String confirm_password = c_password.getText().toString();
        final String num = contact.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(reg_password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(confirm_password) || TextUtils.isEmpty(num)) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_LONG).show();
        } else if (reg_password.length() < 6) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Password must be at least 6 characters long", Toast.LENGTH_LONG).show();
        } else if (reg_password.equals(confirm_password)) {
            // Create or update user data in the database after successful registration
            mAuth.createUserWithEmailAndPassword(email, reg_password)
                    .addOnCompleteListener(registration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final String userId = mAuth.getCurrentUser().getUid();

                                // Query to fetch the latest ID from the database
                                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("user");
                                Query query = usersRef.orderByChild("id").limitToLast(1);

                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        int lastId = 0;

                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            if (snapshot.hasChild("id")) {
                                                lastId = Integer.parseInt(snapshot.child("id").getValue().toString());
                                            }
                                        }

                                        // Increment the lastId to get the new user ID
                                        int newId = lastId + 1;

                                        // Save user data to the database
                                        HashMap<String, Object> userData = new HashMap<>();
                                        userData.put("id", newId);
                                        userData.put("name", name);
                                        userData.put("contact", num);
                                        userData.put("email", email);
                                        userData.put("password", reg_password);
                                        userData.put("role", role);
                                        userData.put("plan", plan);


                                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user").child(userId);
                                        userRef.setValue(userData);

                                        progressBar.setVisibility(View.INVISIBLE);
                                        reg_mail.setText("");
                                        username.setText("");
                                        password.setText("");
                                        c_password.setText("");
                                        contact.setText("");
                                        Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(registration.this, login.class);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        reg_mail.setText("");
                                        username.setText("");
                                        password.setText("");
                                        c_password.setText("");
                                        contact.setText("");
                                        Toast.makeText(getApplicationContext(), "Error!!! not registered", Toast.LENGTH_LONG).show();
                                    }
                                });

                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                reg_mail.setText("");
                                username.setText("");
                                password.setText("");
                                c_password.setText("");
                                contact.setText("");
                                Toast.makeText(getApplicationContext(), "Error!!! not registered", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        } else {
            progressBar.setVisibility(View.INVISIBLE);
            reg_mail.setText("");
            username.setText("");
            password.setText("");
            c_password.setText("");
            contact.setText("");
            Toast.makeText(getApplicationContext(), "Password not matching", Toast.LENGTH_LONG).show();
        }
    }
}
