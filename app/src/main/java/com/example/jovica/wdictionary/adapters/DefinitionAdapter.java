package com.example.jovica.wdictionary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jovica.wdictionary.R;
import com.example.jovica.wdictionary.model.WordDefinition;

import java.util.List;

/**
 * Created by Jovica on 27-Jun-16.
 */
public class DefinitionAdapter extends ArrayAdapter<WordDefinition> {

    private int resource;
    private String[] partOfSpeechKeys;
    private String[] partOfSpeechValues;

    public DefinitionAdapter(Context context, int resource, List<WordDefinition> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.partOfSpeechKeys = context.getResources().getStringArray(R.array.part_of_speech_keys);
        this.partOfSpeechValues = context.getResources().getStringArray(R.array.part_of_speech_values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RelativeLayout itemLayout;
        WordDefinition wordDefinition = getItem(position);

        if (convertView == null) {
            itemLayout = new RelativeLayout(getContext());
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(resource, itemLayout, true);
        } else {
            itemLayout = (RelativeLayout) convertView;
        }

        TextView definitionTextView = (TextView) itemLayout.findViewById(R.id.definition);
        TextView partOfSpeechTextView = (TextView) itemLayout.findViewById(R.id.part_of_speech);

        definitionTextView.setText(wordDefinition.getText());
        partOfSpeechTextView.setText(findPartOfSpeech(wordDefinition.getPartOfSpeech()));

        return itemLayout;
    }

    private String findPartOfSpeech(String partOfSpeechKey) {
        String partOfSpeechValue = "";

        for (int i = 0; i < this.partOfSpeechKeys.length; i++) {
            if (this.partOfSpeechKeys[i].equals(partOfSpeechKey)) {
                partOfSpeechValue = this.partOfSpeechValues[i];
                break;
            }
        }

        if (partOfSpeechValue.equals("")) {
            partOfSpeechValue = partOfSpeechKey;
        }

        return partOfSpeechValue;
    }
}
