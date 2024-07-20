package com.example.xcreatine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ScheduleFragment extends Fragment {

    private CardView cardEdit;
    private GridLayout notEditableGridLayout;
    private GridLayout editableGridLayout;
    private CardView ok_btn;

    private TextView sunday;
    private TextView monday;
    private TextView tuesday;
    private TextView wednesday;
    private TextView thursday;
    private TextView friday;
    private TextView saturday;
    private EditText newSunday;
    private EditText newMonday;
    private EditText newTuesday;
    private EditText newWednesday;
    private EditText newThursday;
    private EditText newFriday;
    private EditText newSaturday;

    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        sunday = rootView.findViewById(R.id.sunday_plan);
        newSunday = rootView.findViewById(R.id.new_sunday_plan);

        monday = rootView.findViewById(R.id.monday_plan);
        newMonday = rootView.findViewById(R.id.new_monday_plan);

        tuesday = rootView.findViewById(R.id.tuesday_plan);
        newTuesday = rootView.findViewById(R.id.new_tuesday_plan);

        wednesday = rootView.findViewById(R.id.wednesday_plan);
        newWednesday = rootView.findViewById(R.id.new_wednesday_plan);

        thursday = rootView.findViewById(R.id.thursday_plan);
        newThursday = rootView.findViewById(R.id.new_thursday_plan);

        friday = rootView.findViewById(R.id.friday_plan);
        newFriday = rootView.findViewById(R.id.new_friday_plan);

        saturday = rootView.findViewById(R.id.saturday_plan);
        newSaturday = rootView.findViewById(R.id.new_saturday_plan);

        cardEdit = rootView.findViewById(R.id.edit_plan_btn);
        ok_btn = rootView.findViewById(R.id.ok_plan_btn);

        notEditableGridLayout = rootView.findViewById(R.id.not_editable_gridLayout);
        editableGridLayout = rootView.findViewById(R.id.editable_gridLayout);

        // Get the default SharedPreferences instance
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Load the saved text from SharedPreferences and set it to both EditText and TextView
        sunday.setText(sharedPreferences.getString("sunday_plan_key", ""));
        monday.setText(sharedPreferences.getString("monday_plan_key", ""));
        tuesday.setText(sharedPreferences.getString("tuesday_plan_key", ""));
        wednesday.setText(sharedPreferences.getString("wednesday_plan_key", ""));
        thursday.setText(sharedPreferences.getString("thursday_plan_key", ""));
        friday.setText(sharedPreferences.getString("friday_plan_key", ""));
        saturday.setText(sharedPreferences.getString("saturday_plan_key", ""));

        // Listen for changes in the EditText and save them to SharedPreferences
        newSunday.addTextChangedListener(createTextWatcher("sunday_plan_key", sunday));
        newMonday.addTextChangedListener(createTextWatcher("monday_plan_key", monday));
        newTuesday.addTextChangedListener(createTextWatcher("tuesday_plan_key", tuesday));
        newWednesday.addTextChangedListener(createTextWatcher("wednesday_plan_key", wednesday));
        newThursday.addTextChangedListener(createTextWatcher("thursday_plan_key", thursday));
        newFriday.addTextChangedListener(createTextWatcher("friday_plan_key", friday));
        newSaturday.addTextChangedListener(createTextWatcher("saturday_plan_key", saturday));

        cardEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the visibility of the cardView
                if (notEditableGridLayout.getVisibility() == View.VISIBLE) {
                    notEditableGridLayout.setVisibility(View.GONE); // Hide the cardView
                    editableGridLayout.setVisibility(View.VISIBLE);
                } else {
                    notEditableGridLayout.setVisibility(View.VISIBLE);
                    editableGridLayout.setVisibility(View.GONE);// Show the cardView
                }
            }
        });

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle the visibility of the cardView
                if (notEditableGridLayout.getVisibility() == View.GONE) {
                    notEditableGridLayout.setVisibility(View.VISIBLE);
                    editableGridLayout.setVisibility(View.GONE);
                } else {
                    notEditableGridLayout.setVisibility(View.GONE);
                    editableGridLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        return rootView;
    }

    private TextWatcher createTextWatcher(final String key, final TextView textView) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Save the text to SharedPreferences as the user types
                sharedPreferences.edit().putString(key, charSequence.toString()).apply();
                // Update the TextView as the user types
                textView.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }
}

