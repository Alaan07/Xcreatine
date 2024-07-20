package com.example.xcreatine;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ShopFragment extends Fragment {

    private CardView product1;
    private CardView product2;
    private CardView product3;
    private CardView product4;
    private CardView product5;


    private Dialog protein;
    private Dialog gloves;

    private Dialog shoes;

    private Dialog wrist_support;
    private Dialog hand_grips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shop, container, false);

        product1 = rootView.findViewById(R.id.product_1);
        protein = new Dialog(getContext());

        product1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                protein.setContentView(R.layout.whey_protine);
                protein.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                protein.show();
            }
        });

        product2 = rootView.findViewById(R.id.product_2);
        gloves = new Dialog(getContext());

        product2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gloves.setContentView(R.layout.gloves);
                gloves.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                gloves.show();
            }
        });


        product3 = rootView.findViewById(R.id.product_3);
        shoes = new Dialog(getContext());

        product3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoes.setContentView(R.layout.shoes);
                shoes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                shoes.show();
            }
        });

        product4 = rootView.findViewById(R.id.product_4);
        wrist_support = new Dialog(getContext());

        product4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrist_support.setContentView(R.layout.wrist_support);
                wrist_support.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                wrist_support.show();
            }
        });

        product5 = rootView.findViewById(R.id.product_5);
        hand_grips = new Dialog(getContext());

        product5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hand_grips.setContentView(R.layout.hand_grips);
                hand_grips.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                hand_grips.show();
            }
        });


        return rootView;
    }
}