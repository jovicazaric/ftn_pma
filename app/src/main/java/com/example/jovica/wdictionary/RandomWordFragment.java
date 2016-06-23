package com.example.jovica.wdictionary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.jovica.wdictionary.model.RandomWordSearch;

import static com.example.jovica.wdictionary.helpers.UI.initPartOfSpeechSpinner;

public class RandomWordFragment extends Fragment {

    String[] minValues = null;
    String[] maxValues = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.random_word_fragment, container, false);
        initPartOfSpeechSpinner(this.getActivity(), view);
        initMinMaxLengthSpinners(view);
        return view;
    }

    public void initMinMaxLengthSpinners(View view) {
        int minLength = getResources().getInteger(R.integer.min_word_length);
        final int maxLength = getResources().getInteger(R.integer.max_word_length);
        String moreThanMax = getResources().getString(R.string.more_than_max);
        minValues = new String[maxLength-minLength + 1];
        maxValues = new String[maxLength-minLength + 2];

        for (int i = minLength; i <= maxLength; i++) {
            minValues[i-minLength] = i + "";
            maxValues[i-minLength] = i + "";
        }

        maxValues[maxValues.length - 1] = moreThanMax;

        Spinner minLengthSpinner = (Spinner) view.findViewById(R.id.sp_min_length);
        ArrayAdapter<String> minAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, minValues);
        minAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minLengthSpinner.setAdapter(minAdapter);
        minLengthSpinner.setSelection(0);

        minLengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] newMaxValues = new String[maxValues.length - position];

                for (int i = position; i < maxValues.length; i++) {
                    newMaxValues[i - position] = maxValues[i];
                }

                Spinner maxLengthSpinner = (Spinner) view.getRootView().findViewById(R.id.sp_max_length);
                String maxValueSelected = (String) maxLengthSpinner.getSelectedItem();

                ArrayAdapter<String> maxAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, newMaxValues);
                maxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                maxLengthSpinner.setAdapter(maxAdapter);


                boolean selected = false;
                for (int i = 0; i < newMaxValues.length; i++) {
                    if (newMaxValues[i] == maxValueSelected) {
                        maxLengthSpinner.setSelection(i);
                        selected = true;
                        break;
                    }
                }

                if (!selected) {
                    maxLengthSpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        
        Spinner maxLengthSpinner = (Spinner) view.findViewById(R.id.sp_max_length);
        ArrayAdapter<String> maxAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, maxValues);
        maxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxLengthSpinner.setAdapter(maxAdapter);
        maxLengthSpinner.setSelection(maxValues.length - 2);
    }

    public RandomWordSearch getSearchParams() {
        RandomWordSearch randomWordSearch = new RandomWordSearch();

        CheckBox hasDictionaryDefinitionCheckBox = (CheckBox) getActivity().findViewById(R.id.cb_has_dictionary_definition);
        randomWordSearch.setHasDictionaryDefinition(hasDictionaryDefinitionCheckBox.isChecked());

        Spinner partOfSpeechSpinner = (Spinner) getActivity().findViewById(R.id.sp_part_of_speech);
        int partOfSpeechPosition = partOfSpeechSpinner.getSelectedItemPosition();
        String[] partOfSpeechKeys = getResources().getStringArray(R.array.part_of_speech_keys);
        randomWordSearch.setPartOfSpeech(partOfSpeechKeys[partOfSpeechPosition]);

        Spinner minLengthSpinner = (Spinner) getActivity().findViewById(R.id.sp_min_length);
        randomWordSearch.setMinLength(Integer.parseInt((String) minLengthSpinner.getSelectedItem()));

        Spinner maxLengthSpinner = (Spinner) getActivity().findViewById(R.id.sp_max_length);
        String moreThanMax = getResources().getString(R.string.more_than_max);
        String selectedItem = (String) maxLengthSpinner.getSelectedItem();

        if (selectedItem == moreThanMax) {
            randomWordSearch.setMaxLength(-1);
        } else {
            randomWordSearch.setMaxLength(Integer.parseInt(selectedItem));
        }

        return randomWordSearch;
    }
}
