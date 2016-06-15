package com.example.jovica.wdictionary;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RelatedWordsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.related_words_fragment, container, false);
        initLimitPerRelationshipTypeSpinner(view);
        initRelationshipTypeSpinner(view);
        return view;
    }

    public void initLimitPerRelationshipTypeSpinner(View view){
        Spinner limit_per_relationship = (Spinner) view.findViewById(R.id.sp_limit_per_relationship_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.words_limit_spinner_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        limit_per_relationship.setAdapter(adapter);
    }

    public void initRelationshipTypeSpinner(View view){
        Spinner relationship_type = (Spinner) view.findViewById(R.id.sp_relationship_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.relationship_type_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationship_type.setAdapter(adapter);
    }
}
