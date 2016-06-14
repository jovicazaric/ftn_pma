package com.example.jovica.wdictionary;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DefinitionsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.definitions_fragment, container, false);
        initWordsLimitSpinner(view);
        initPartOfSpeechSpinner(view);
        return view;
    }

    public void initWordsLimitSpinner(View view){
        Spinner wordsLimitSpinner = (Spinner) view.findViewById(R.id.sp_words_limit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.words_limit_spinner_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordsLimitSpinner.setAdapter(adapter);
    }

    public void initPartOfSpeechSpinner(View view){
        Spinner partOfSpeechSpinner = (Spinner) view.findViewById(R.id.sp_part_of_speech);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.part_of_speech_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partOfSpeechSpinner.setAdapter(adapter);
    }


}
