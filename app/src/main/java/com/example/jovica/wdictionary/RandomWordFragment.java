package com.example.jovica.wdictionary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import static com.example.jovica.wdictionary.helpers.UI.initPartOfSpeechSpinner;

public class RandomWordFragment extends Fragment {

    String[] min_values = null;
    String[] max_values = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.random_word_fragment, container, false);
        initPartOfSpeechSpinner(this.getActivity(), view);
        initMinMaxLengthSpinners(view);
        return view;
    }

    public void initMinMaxLengthSpinners(View view) {
        int min_length = getResources().getInteger(R.integer.min_word_length);
        final int max_length = getResources().getInteger(R.integer.max_word_length);
        String more_than_max = getResources().getString(R.string.more_than_max);
        min_values = new String[max_length-min_length + 1];
        max_values = new String[max_length-min_length + 2];

        for (int i = min_length; i <= max_length; i++) {
            min_values[i-min_length] = i + "";
            max_values[i-min_length] = i + "";
        }

        max_values[max_values.length - 1] = more_than_max;

        Spinner min_length_spinner = (Spinner) view.findViewById(R.id.sp_min_length);
        ArrayAdapter<String> min_adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, min_values);
        min_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        min_length_spinner.setAdapter(min_adapter);
        min_length_spinner.setSelection(0);

        min_length_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] new_max_values = new String[max_values.length - position];

                for (int i = position; i < max_values.length; i++) {
                    new_max_values[i - position] = max_values[i];
                }

                Spinner max_length_spinner = (Spinner) view.getRootView().findViewById(R.id.sp_max_length);
                String max_value_selected = (String) max_length_spinner.getSelectedItem();

                ArrayAdapter<String> max_adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, new_max_values);
                max_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                max_length_spinner.setAdapter(max_adapter);


                boolean selected = false;
                for (int i = 0; i < new_max_values.length; i++) {
                    if (new_max_values[i] == max_value_selected) {
                        max_length_spinner.setSelection(i);
                        selected = true;
                        break;
                    }
                }

                if (!selected) {
                    max_length_spinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Spinner max_length_spinner = (Spinner) view.findViewById(R.id.sp_max_length);
        ArrayAdapter<String> max_adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, max_values);
        max_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        max_length_spinner.setAdapter(max_adapter);
        max_length_spinner.setSelection(max_values.length - 2);

    }
}
