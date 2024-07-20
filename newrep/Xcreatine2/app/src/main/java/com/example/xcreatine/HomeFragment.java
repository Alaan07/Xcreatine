package com.example.xcreatine;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    ViewPager2 viewPager2;
    ImageView membershipImage;
    private String currentUserId;
    private DatabaseReference databaseReference;
    private Handler slideHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager2 = rootView.findViewById(R.id.viewPager); // Find ViewPager2 by ID from the rootView

        List<SlideItem> sliderItem = new ArrayList<>();
        sliderItem.add(new SlideItem(R.drawable.add1));
        sliderItem.add(new SlideItem(R.drawable.add2));
        sliderItem.add(new SlideItem(R.drawable.add3));

        viewPager2.setAdapter(new SlideAdapter(sliderItem, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositionTransform = new CompositePageTransformer();
        compositionTransform.addTransformer(new MarginPageTransformer(40));
        compositionTransform.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.18f);
            }
        });

        viewPager2.setPageTransformer(compositionTransform);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                slideHandler.removeCallbacks(sliderRunnable);
                slideHandler.postDelayed(sliderRunnable, 2000);
            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        } else {
            // Handle the case when the user is not signed in or the session has expired
            // For example, you might redirect the user to the sign-in page or show an error message.
        }

        membershipImage = rootView.findViewById(R.id.membership_img);

        // Initialize databaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Get the plan from the real-time database for the current user
        DatabaseReference currentUserRef = databaseReference.child("users").child(currentUserId).child("plan");
        currentUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the plan as a String from the snapshot
                String plan = dataSnapshot.getValue(String.class);

                // Set the tint color based on the plan
                if (plan != null) {
                    switch (plan) {
                        case "basic":
                            membershipImage.setColorFilter(getResources().getColor(R.color.black));
                            break;
                        case "silver":
                            membershipImage.setColorFilter(getResources().getColor(R.color.silver));
                            break;
                        case "gold":
                            membershipImage.setColorFilter(getResources().getColor(R.color.gold));
                            break;
                        default:
                            // Handle the case when plan is not one of the expected values
                            membershipImage.setColorFilter(getResources().getColor(R.color.black));
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error, if any
            }
        });





        CardView membership_card = rootView.findViewById(R.id.membership_card);

        membership_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MembershipFragment());
            }
        });

        CardView about_card = rootView.findViewById(R.id.about_card);

        about_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AboutFragment());
            }
        });


        CardView shop_card = rootView.findViewById(R.id.shop_card);

        shop_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ShopFragment());
            }
        });


        CardView diet_card = rootView.findViewById(R.id.diet_card);

        diet_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DietFragment());
            }
        });

        CardView schedule_card = rootView.findViewById(R.id.schedule_card);

        schedule_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ScheduleFragment());
            }
        });



        return rootView; // Return the initialized rootView
    }














    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 3000);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void logOut_card(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(requireActivity(), login.class));
    }



}
