package com.example.jovica.wdictionary;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.jovica.wdictionary.model.DefinitionsSearch;

import static com.example.jovica.wdictionary.helpers.UI.initPartOfSpeechSpinner;

public class DefinitionsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.definitions_fragment, container, false);
        initWordsLimitSpinner(view);
        initPartOfSpeechSpinner(this.getActivity(), view);
        return view;
    }

    public void initWordsLimitSpinner(View view){
        Spinner wordsLimitSpinner = (Spinner) view.findViewById(R.id.sp_words_limit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.words_limit_spinner_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordsLimitSpinner.setAdapter(adapter);
    }

    public DefinitionsSearch getSearchParams(){
        DefinitionsSearch definitionsSearch = new DefinitionsSearch();
        Spinner wordLimitSpinner = (Spinner) getActivity().findViewById(R.id.sp_words_limit);
        definitionsSearch.setWordLimit(Integer.parseInt((String)wordLimitSpinner.getSelectedItem()));

        Spinner partOfSpeechSpinner = (Spinner) getActivity().findViewById(R.id.sp_part_of_speech);
        int partOfSpeechPosition = partOfSpeechSpinner.getSelectedItemPosition();
        String[] partOfSpeechKeys = getResources().getStringArray(R.array.part_of_speech_keys);
        definitionsSearch.setPartOfSpeech(partOfSpeechKeys[partOfSpeechPosition]);

        CheckBox useCanonicalCheckBox = (CheckBox) getActivity().findViewById(R.id.cb_use_canonical);
        definitionsSearch.setUseCanonical(useCanonicalCheckBox.isChecked());
        return definitionsSearch;
    }
}
