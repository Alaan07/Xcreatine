package com.example.xcreatine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DietFragment extends Fragment {

    CardView weightLoss;
    CardView weightGain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diet, container, false);


        weightLoss = rootView.findViewById(R.id.weight_loss);

        weightLoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoUrl("https://www.youtube.com/@indianweightlossdiet/videos");
            }
        });

        weightGain = rootView.findViewById(R.id.weight_gain);

        weightGain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoUrl("https://www.youtube.com/@primeweightgain/videos");
            }
        });


        return rootView;
    }

    private void gotoUrl(String s) {

        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}