package com.example.jovica.wdictionary;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.jovica.wdictionary.helpers.DictionaryAPI;
import com.example.jovica.wdictionary.model.DefinitionsSearch;
import com.example.jovica.wdictionary.model.RandomWordSearch;
import com.example.jovica.wdictionary.model.RelatedWordsSearch;
import com.example.jovica.wdictionary.model.Search;

public class SearchActivity extends FragmentActivity {

    private static final String activityName = "SEARCH_ACTIVITY";

    private ProgressDialog progressDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (findViewById(R.id.search_options_fragment_container) != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            DefinitionsFragment definitionsFragment = new DefinitionsFragment();
            transaction.replace(R.id.search_options_fragment_container, definitionsFragment);
            transaction.commit();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setTitle(getResources().getString(R.string.app_name));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void onSearchButtonClicked(View view) {
        Search search = getSearch();
        if (search != null) {
            if (search instanceof DefinitionsSearch) {
                //DictionaryAPI.getDefinitions((DefinitionsSearch) search);
                new GetDefinitions().execute((DefinitionsSearch) search);
            } else if (search instanceof RandomWordSearch) {
                DictionaryAPI.getRandomWord((RandomWordSearch) search);
            }
        }
    }

    public void onSearchTypeChanged(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.rb_definitions:
                if (checked) {
                    DefinitionsFragment definitionsFragment = new DefinitionsFragment();
                    transaction.replace(R.id.search_options_fragment_container, definitionsFragment);
                }
                break;
            case R.id.rb_related_words:
                if (checked) {
                    RelatedWordsFragment relatedWordsFragment = new RelatedWordsFragment();
                    transaction.replace(R.id.search_options_fragment_container, relatedWordsFragment);
                }
                break;
            case R.id.rb_random_word:
                if (checked) {
                    RandomWordFragment randomWordFragment = new RandomWordFragment();
                    transaction.replace(R.id.search_options_fragment_container, randomWordFragment);
                    ;
                }
                break;
        }

        transaction.commit();
    }

    public Search getSearch() {

        Search search = null;
        EditText wordForSearchEditText = null;
        RadioGroup SearchTypesRadioGroup = (RadioGroup) findViewById(R.id.rg_search_types);

        switch (SearchTypesRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_definitions:

                if (isWordForSearchValid()) {
                    DefinitionsFragment definitionsFragment = (DefinitionsFragment) getSupportFragmentManager().findFragmentById(R.id.search_options_fragment_container);
                    DefinitionsSearch definitionsSearch = definitionsFragment.getSearchParams();

                    wordForSearchEditText = (EditText) findViewById(R.id.et_word_for_search);
                    definitionsSearch.setWord(wordForSearchEditText.getText().toString());
                    search = definitionsSearch;
                }

                break;
            case R.id.rb_related_words:

                if (isWordForSearchValid()) {
                    RelatedWordsFragment relatedWordsFragment = (RelatedWordsFragment) getSupportFragmentManager().findFragmentById(R.id.search_options_fragment_container);
                    RelatedWordsSearch relatedWordsSearch = relatedWordsFragment.getSearchParams();

                    wordForSearchEditText = (EditText) findViewById(R.id.et_word_for_search);
                    relatedWordsSearch.setWord(wordForSearchEditText.getText().toString());
                    search = relatedWordsSearch;
                }

                break;
            case R.id.rb_random_word:
                RandomWordFragment randomWordFragment = (RandomWordFragment) getSupportFragmentManager().findFragmentById(R.id.search_options_fragment_container);
                RandomWordSearch randomWordSearch = randomWordFragment.getSearchParams();
                search = randomWordSearch;
                break;
        }

        return search;
    }

    public boolean isWordForSearchValid() {
        EditText wordForSearchEditText = (EditText) findViewById(R.id.et_word_for_search);

        if (wordForSearchEditText.getText().toString().equals("")) {
            String errorMessage = getResources().getString(R.string.search_word_required);
            Toast.makeText(SearchActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private class GetDefinitions extends AsyncTask<DefinitionsSearch, Void, String> {

        @Override
        protected  void onPreExecute() {
            progressDialog = ProgressDialog.show(SearchActivity.this, "Please wait", "Getting definitions");
        }

        @Override
        protected String doInBackground(DefinitionsSearch... params) {
            DefinitionsSearch search = params[0];
            DictionaryAPI.getDefinitions(search);

            return "done";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(activityName, result.toUpperCase());
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }
}
