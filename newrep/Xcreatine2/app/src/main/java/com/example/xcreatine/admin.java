package com.example.xcreatine;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.cardview.widget.CardView;

public class admin extends AppCompatActivity {

    private CardView adminUsersCardView;
    private CardView adminTrainersCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminUsersCardView = findViewById(R.id.admin_users);
        adminTrainersCardView = findViewById(R.id.admin_trainers);

        adminUsersCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(admin.this, admin_users.class);
                startActivity(i);

            }
        });

        adminTrainersCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(admin.this, admin_trainers.class);
                startActivity(i);


            }
        });
    }
}
