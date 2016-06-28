package com.example.jovica.wdictionary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.jovica.wdictionary.adapters.RelatedWordsTypesAdapter;
import com.example.jovica.wdictionary.model.RelatedWordsResult;

/**
 * Created by Jovica on 28-Jun-16.
 */
public class RelatedWordsActivity extends Activity {

    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_words);

        RelatedWordsResult relatedWordsResult = getIntent().getExtras().getParcelable("relatedWords");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setTitle(getResources().getString(R.string.toolbar_related_words_for) + " " + relatedWordsResult.getWord());
        getActionBar().setDisplayHomeAsUpEnabled(true);

        ListView relatedWordsTypes = (ListView) findViewById(R.id.related_words_types);
        RelatedWordsTypesAdapter relatedWordsTypesAdapter = new RelatedWordsTypesAdapter(RelatedWordsActivity.this, R.layout.relationship_type_item, relatedWordsResult.getRelationshipTypes());
        relatedWordsTypes.setAdapter(relatedWordsTypesAdapter);

        Log.d("RELATEDWORDSACTIVITY", "from related words activity");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
