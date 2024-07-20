package com.example.xcreatine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {



    private FirebaseAuth mAuth;
    private DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;

    AboutFragment aboutFragment = new AboutFragment();
    DietFragment dietFragment = new DietFragment();
    HomeFragment homeFragment = new HomeFragment();
    MembershipFragment membershipFragment = new MembershipFragment();
    ShopFragment shopFragment = new ShopFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home_bottom) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.membership_bottom) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, membershipFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.shop_bottom) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, shopFragment).commit();
                    return true;
                }
                return false;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);

        drawerLayout = findViewById(R.id.drawable_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);





        // Find the header view in the navigation drawer
        View navigationHeader = navigationView.getHeaderView(0);
        TextView nameTextView = navigationHeader.findViewById(R.id.nav_name);
        TextView roleTextView = navigationHeader.findViewById(R.id.nav_role);
        TextView mailTextView = navigationHeader.findViewById(R.id.nav_mail);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Get a reference to the "user" child in the database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user").child(userId);

            // Add a ValueEventListener to fetch the user data
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get the user data from the snapshot
                    String userName = dataSnapshot.child("name").getValue(String.class);
                    String userMail = dataSnapshot.child("email").getValue(String.class);
                    String userRole = dataSnapshot.child("role").getValue(String.class);

                    // Update the TextViews in the navigation header
                    nameTextView.setText(userName);
                    mailTextView.setText(userMail);
                    roleTextView.setText(userRole);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle any errors
                }
            });
        }








        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
                } else if (itemId == R.id.nav_about) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, aboutFragment).commit();
                } else if (itemId == R.id.nav_shop) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, shopFragment).commit();
                } else if (itemId == R.id.nav_membership) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, membershipFragment).commit();
                } else if (itemId == R.id.nav_Diet) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, dietFragment).commit();
                } else if (itemId == R.id.nav_logout) {
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(MainActivity.this, login.class);
                    startActivity(i);
                    Toast.makeText(MainActivity.this, "Logout!", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }



}
