package com.example.xcreatine;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

public class MembershipFragment extends Fragment {

    EditText message;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private String currentUserId;

    private CardView cardViewBasic;
    private CardView cardViewSilver;
    private CardView cardViewGold;
    private CardView cardViewTrainer;





    private Button buy_basic;
    private Button buy_silver;
    private Button buy_gold;




    private Button toggleButtonMoreBasic;
    private Button toggleButtonMoreSilver;
    private Button toggleButtonMoreGold;
    private Button toggleButtonMoreTrainer;

    private Button toggleButtonLessBasic;
    private Button toggleButtonLessSilver;
    private Button toggleButtonLessGold;
    private Button toggleButtonLessTrainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_membership, container, false);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        } else {
            // Handle the case when the user is not signed in or the session has expired
            // For example, you might redirect the user to the sign-in page or show an error message.
        }

        message = rootView.findViewById(R.id.mail_message);


        buy_basic = rootView.findViewById(R.id.basic_plan_buy_btn);
        buy_silver = rootView.findViewById(R.id.silver_plan_buy_btn);
        buy_gold = rootView.findViewById(R.id.gold_plan_buy_btn);





        buy_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePlanInDatabase("basic");
            }
        });

        buy_silver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePlanInDatabase("silver");
            }
        });

        buy_gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePlanInDatabase("gold");
            }
        });







        cardViewBasic = rootView.findViewById(R.id.plan_details_basic);
        toggleButtonMoreBasic = rootView.findViewById(R.id.basic_btn_more);
        toggleButtonLessBasic = rootView.findViewById(R.id.basic_btn_less);

        // Set up click listeners for the toggle buttons (Basic)
        toggleButtonMoreBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCardVisibility(cardViewBasic, toggleButtonMoreBasic, toggleButtonLessBasic);
            }
        });

        toggleButtonLessBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCardVisibility(cardViewBasic, toggleButtonMoreBasic, toggleButtonLessBasic);
            }
        });

//        ***********************silver***************

        cardViewSilver = rootView.findViewById(R.id.plan_details_silver);
        toggleButtonMoreSilver = rootView.findViewById(R.id.silver_btn_more);
        toggleButtonLessSilver = rootView.findViewById(R.id.silver_btn_less);


        toggleButtonMoreSilver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toggleCardVisibility(cardViewSilver, toggleButtonMoreSilver, toggleButtonLessSilver);
            }
        });

        toggleButtonLessSilver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCardVisibility(cardViewSilver, toggleButtonMoreSilver, toggleButtonLessSilver);
            }
        });

//        **********************goldStart**********************************

        cardViewGold = rootView.findViewById(R.id.plan_details_gold);
        toggleButtonMoreGold = rootView.findViewById(R.id.gold_btn_more);
        toggleButtonLessGold = rootView.findViewById(R.id.gold_btn_less);


        toggleButtonMoreGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toggleCardVisibility(cardViewGold, toggleButtonMoreGold, toggleButtonLessGold);
            }
        });

        toggleButtonLessGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCardVisibility(cardViewGold, toggleButtonMoreGold, toggleButtonLessGold);
            }
        });
//***************************trainer start*****************************

        cardViewTrainer = rootView.findViewById(R.id.plan_details_trainer);
        toggleButtonMoreTrainer = rootView.findViewById(R.id.trainer_btn_more);
        toggleButtonLessTrainer = rootView.findViewById(R.id.trainer_btn_less);


        toggleButtonMoreTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toggleCardVisibility(cardViewTrainer, toggleButtonMoreTrainer, toggleButtonLessTrainer);
            }
        });

        toggleButtonLessTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toggleCardVisibility(cardViewTrainer, toggleButtonMoreTrainer, toggleButtonLessTrainer);
            }
        });



        Button sendButton = rootView.findViewById(R.id.support_btn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_mail();
            }
        });

        return rootView;


    }





    private void updatePlanInDatabase(String plan) {
        Log.d("Firebase", "updatePlanInDatabase called with plan: " + plan);
        DatabaseReference currentUserRef = databaseReference.child("users").child(currentUserId).child("plan");
        currentUserRef.setValue(plan)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "Plan updated successfully");
                        // Check if the Fragment is attached to an Activity before showing the Toast
                        if (getActivity() != null) {
                            Intent intent = new Intent(getActivity(), login.class);
                            startActivity(intent);
                            Toast.makeText(getActivity(), "Plan updated successfully", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Firebase", "Failed to update plan: " + e.getMessage());
                        // Check if the Fragment is attached to an Activity before showing the Toast
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "Failed to update plan", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }








    // Method to toggle card visibility and update button visibility accordingly
    private void toggleCardVisibility(CardView cardView, Button moreButton, Button lessButton) {
        if (cardView.getVisibility() == View.VISIBLE) {
            cardView.setVisibility(View.GONE);
            moreButton.setVisibility(View.VISIBLE);
            lessButton.setVisibility(View.GONE);
        } else {
            cardView.setVisibility(View.VISIBLE);
            moreButton.setVisibility(View.GONE);
            lessButton.setVisibility(View.VISIBLE);
        }
    }




    public void send_mail() {
        String mail_message = message.getText().toString().trim();
        String fixedEmailAddress = "silentsparrow007@gmail.com";
        String subject = "Questions";

        // Retrieve the user's email from the database using ValueEventListener
        databaseReference.child("users").child(currentUserId).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the user's email as a String
                String userEmail = dataSnapshot.getValue(String.class);

                // Construct the email intent
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822"); // Set the MIME type of the email

                // Set the sender (from) as the user's email from the real-time database
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{userEmail});

                // Set the recipient (to) as the fixed email address
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{fixedEmailAddress});

                intent.putExtra(Intent.EXTRA_SUBJECT, subject); // Set the subject of the email
                intent.putExtra(Intent.EXTRA_TEXT, mail_message); // Set the body of the email

                // Start the email intent with a chooser dialog
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intent, "Send Email"));
                } else {
                    Toast.makeText(requireContext(), "No email app installed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error, if any
                Toast.makeText(requireContext(), "Error retrieving user email", Toast.LENGTH_SHORT).show();
            }
        });
    }
}