package com.example.jovica.wdictionary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jovica.wdictionary.R;

import java.util.List;

/**
 * Created by Jovica on 29-Jun-16.
 */
public class RelatedWordsAdapter extends ArrayAdapter<String> {

    private int resource;
    public RelatedWordsAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout itemLayout;
        String word = getItem(position);

        if (convertView == null) {
            itemLayout = new RelativeLayout(getContext());
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(resource, itemLayout, true);
        } else {
            itemLayout = (RelativeLayout) convertView;
        }

        TextView wordTextView = (TextView) itemLayout.findViewById(R.id.related_word);
        ImageView searchImageView = (ImageView) itemLayout.findViewById(R.id.search);
        ImageView speakerImageView = (ImageView) itemLayout.findViewById(R.id.speaker);

        searchImageView.setTag(word);
        speakerImageView.setTag(word);
        wordTextView.setText(word);

        return itemLayout;
    }
}
