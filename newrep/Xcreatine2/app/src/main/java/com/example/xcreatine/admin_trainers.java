package com.example.xcreatine;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class admin_trainers extends AppCompatActivity {

    private RecyclerView trainerRecyclerView;
    private List<User> trainerList;
    private UserAdapter trainerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_trainers);

        trainerRecyclerView = findViewById(R.id.trainerRecyclerView);
        trainerList = new ArrayList<>();
        trainerAdapter = new UserAdapter(trainerList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        trainerRecyclerView.setLayoutManager(layoutManager);
        trainerRecyclerView.setAdapter(trainerAdapter);

        // Query users with role "trainer" from the Realtime Database
        Query trainerQuery = FirebaseDatabase.getInstance().getReference().child("user")
                .orderByChild("role").equalTo("trainer");

        trainerQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                trainerList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User trainer = userSnapshot.getValue(User.class);
                    if (trainer != null) {
                        trainerList.add(trainer);
                    }
                }
                trainerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error, if any
            }
        });
    }
}
