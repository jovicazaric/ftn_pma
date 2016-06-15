package com.example.jovica.wdictionary.helpers;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.jovica.wdictionary.R;

public class UI {

    public static void initPartOfSpeechSpinner(Activity activity, View view){
        Spinner partOfSpeechSpinner = (Spinner) view.findViewById(R.id.sp_part_of_speech);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.part_of_speech_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partOfSpeechSpinner.setAdapter(adapter);
    }
}
