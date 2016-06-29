package com.example.jovica.wdictionary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.jovica.wdictionary.adapters.RelatedWordsTypesAdapter;
import com.example.jovica.wdictionary.helpers.DictionaryAPI;
import com.example.jovica.wdictionary.helpers.UI;
import com.example.jovica.wdictionary.model.DefinitionsResult;
import com.example.jovica.wdictionary.model.DefinitionsSearch;
import com.example.jovica.wdictionary.model.RelatedWordsResult;
import com.example.jovica.wdictionary.model.ResultStatus;

/**
 * Created by Jovica on 28-Jun-16.
 */
public class RelatedWordsActivity extends Activity {

    private ProgressDialog progressDialog;
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

    public void onSearchClick(View v) {
        Toast.makeText(RelatedWordsActivity.this, (String)v.getTag(), Toast.LENGTH_LONG).show();
        DefinitionsSearch definitionsSearch = makeDefinitionsSearch((String)v.getTag());
        new GetDefinitions().execute(definitionsSearch);
    }

    private class GetDefinitions extends AsyncTask<DefinitionsSearch, Void, DefinitionsResult> {

        @Override
        protected  void onPreExecute() {
            progressDialog = ProgressDialog.show(RelatedWordsActivity.this, getResources().getString(R.string.progress_dialog_title),
                    getResources().getString(R.string.definitions_search_progress_dialog_content));
        }

        @Override
        protected DefinitionsResult doInBackground(DefinitionsSearch... params) {
            DefinitionsSearch search = params[0];
            DefinitionsResult result = DictionaryAPI.getDefinitions(search);
            return result;
        }

        @Override
        protected void onPostExecute(DefinitionsResult result) {
            progressDialog.dismiss();

            if (result.getResultStatus() == ResultStatus.Ok && result.getDefinitions().size() > 0) {
                Intent intent = new Intent(RelatedWordsActivity.this, DefinitionsActivity.class);
                intent.putExtra("definitions", result);
                startActivity(intent);
            } else if (result.getResultStatus() == ResultStatus.Ok && result.getDefinitions().size() == 0) {
                UI.showToastMessage(RelatedWordsActivity.this, getResources().getString(R.string.definitions_not_found));
            } else if (result.getResultStatus() == ResultStatus.ServerError || result.getResultStatus() == ResultStatus.BadData) {
                UI.showToastMessage(RelatedWordsActivity.this, getResources().getString(R.string.server_error));
            }
        }
    }

    private DefinitionsSearch makeDefinitionsSearch(String word) {
        DefinitionsSearch definitionsSearch = new DefinitionsSearch();
        definitionsSearch.setWord(word);
        definitionsSearch.setUseCanonical(false);
        definitionsSearch.setPartOfSpeech("");
        definitionsSearch.setWordLimit(getResources().getInteger(R.integer.default_limit_for_random_word));
        return definitionsSearch;
    }
}
