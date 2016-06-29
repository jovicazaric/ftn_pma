package com.example.jovica.wdictionary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jovica.wdictionary.R;
import com.example.jovica.wdictionary.model.RelationshipTypeResult;

import java.util.List;

/**
 * Created by Jovica on 28-Jun-16.
 */
public class RelatedWordsTypesAdapter extends ArrayAdapter<RelationshipTypeResult> {

    private int resource;
    private String[] relationshipTypesKeys;
    private String[] relationshipTypesValues;

    public RelatedWordsTypesAdapter(Context context, int resource, List<RelationshipTypeResult> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.relationshipTypesKeys = getContext().getResources().getStringArray(R.array.relationship_type_keys);
        this.relationshipTypesValues = getContext().getResources().getStringArray(R.array.relationship_type_values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RelativeLayout itemLayout;
        RelationshipTypeResult relationshipTypeResult = getItem(position);

        if (convertView == null) {
            itemLayout = new RelativeLayout(getContext());
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(resource, itemLayout, true);
        } else {
            itemLayout = (RelativeLayout) convertView;
        }

        TextView relationshipType = (TextView) itemLayout.findViewById(R.id.relationship_type);
        relationshipType.setText(findRelationshipTypeValue(relationshipTypeResult.getRelationshipType()) + " - " + relationshipTypeResult.getWords().size());

        ListView relatedWordsList = (ListView) itemLayout.findViewById(R.id.related_words_by_type);

        RelatedWordsAdapter relatedWordsAdapter = new RelatedWordsAdapter(getContext(), R.layout.related_word, relationshipTypeResult.getWords());

        int height = getContext().getResources().getInteger(R.integer.related_word_height);
        int totalHeight = height * relationshipTypeResult.getWords().size();

        ViewGroup.LayoutParams params = relatedWordsList.getLayoutParams();
        params.height = totalHeight;
        relatedWordsList.setLayoutParams(params);
        relatedWordsList.setAdapter(relatedWordsAdapter);

        return itemLayout;
    }

    private String findRelationshipTypeValue(String relationshipTypeKey) {
        String value = "";

        for (int i = 0; i < this.relationshipTypesKeys.length; i++) {
            if (this.relationshipTypesKeys[i].equals(relationshipTypeKey)) {
                value = this.relationshipTypesValues[i];
                break;
            }
        }

        if (value.equals("")) {
            value = relationshipTypeKey;
        }
        return value;
    }
}
