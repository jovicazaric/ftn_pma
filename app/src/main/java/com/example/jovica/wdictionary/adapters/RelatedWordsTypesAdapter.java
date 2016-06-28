package com.example.jovica.wdictionary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    public RelatedWordsTypesAdapter(Context context, int resource, List<RelationshipTypeResult> objects) {
        super(context, resource, objects);
        this.resource = resource;
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

        relationshipType.setText(relationshipTypeResult.getRelationshipType() + " - " + relationshipTypeResult.getWords().size());

        return itemLayout;
    }
}
