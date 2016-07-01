package com.example.jovica.wdictionary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toolbar;

import com.example.jovica.wdictionary.helpers.DictionaryAPI;
import com.example.jovica.wdictionary.helpers.UI;
import com.example.jovica.wdictionary.helpers.Utils;
import com.example.jovica.wdictionary.model.DefinitionsResult;
import com.example.jovica.wdictionary.model.DefinitionsSearch;
import com.example.jovica.wdictionary.model.RandomWordResult;
import com.example.jovica.wdictionary.model.RandomWordSearch;
import com.example.jovica.wdictionary.model.RelatedWordsResult;
import com.example.jovica.wdictionary.model.RelatedWordsSearch;
import com.example.jovica.wdictionary.model.ResultStatus;
import com.example.jovica.wdictionary.model.Search;
import com.example.jovica.wdictionary.model.WordDefinition;

public class SearchActivity extends FragmentActivity {

    private static final String activityName = "SEARCH_ACTIVITY";

    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private EditText wordForSearchEditText;

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

        wordForSearchEditText = (EditText) findViewById(R.id.et_word_for_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return UI.goBack(this);
            case R.id.exit:
                return UI.exitApp(this);
            case R.id.about:
                Intent intent = new Intent(SearchActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSearchButtonClicked(View view) {

        if (!Utils.hasInternetAccess(SearchActivity.this)) {
            UI.showToastMessage(SearchActivity.this, getResources().getString(R.string.no_internet_acces));
            return;
        }

        Search search = getSearch();
        if (search != null) {
            if (search instanceof DefinitionsSearch) {
                new GetDefinitions().execute((DefinitionsSearch) search);
            } else if (search instanceof RandomWordSearch) {
                new GetRandomWord().execute((RandomWordSearch) search);
            } else if (search instanceof RelatedWordsSearch) {
                new GetRelatedWords().execute((RelatedWordsSearch) search);
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
                    wordForSearchEditText.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rb_related_words:
                if (checked) {
                    RelatedWordsFragment relatedWordsFragment = new RelatedWordsFragment();
                    transaction.replace(R.id.search_options_fragment_container, relatedWordsFragment);
                    wordForSearchEditText.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rb_random_word:
                if (checked) {
                    RandomWordFragment randomWordFragment = new RandomWordFragment();
                    transaction.replace(R.id.search_options_fragment_container, randomWordFragment);
                    wordForSearchEditText.setVisibility(View.INVISIBLE);
                }
                break;
        }

        transaction.commit();
    }

    public Search getSearch() {

        Search search = null;
        RadioGroup SearchTypesRadioGroup = (RadioGroup) findViewById(R.id.rg_search_types);

        switch (SearchTypesRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_definitions:

                if (isWordForSearchValid()) {
                    DefinitionsFragment definitionsFragment = (DefinitionsFragment) getSupportFragmentManager().findFragmentById(R.id.search_options_fragment_container);
                    DefinitionsSearch definitionsSearch = definitionsFragment.getSearchParams();

                    definitionsSearch.setWord(wordForSearchEditText.getText().toString());
                    search = definitionsSearch;
                }

                break;
            case R.id.rb_related_words:

                if (isWordForSearchValid()) {
                    RelatedWordsFragment relatedWordsFragment = (RelatedWordsFragment) getSupportFragmentManager().findFragmentById(R.id.search_options_fragment_container);
                    RelatedWordsSearch relatedWordsSearch = relatedWordsFragment.getSearchParams();

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

        if (wordForSearchEditText.getText().toString().equals("")) {
            String errorMessage = getResources().getString(R.string.search_word_required);
            UI.showToastMessage(SearchActivity.this, errorMessage);
            return false;
        }

        return true;
    }

    private class GetDefinitions extends AsyncTask<DefinitionsSearch, Void, DefinitionsResult> {

        @Override
        protected  void onPreExecute() {
            progressDialog = ProgressDialog.show(SearchActivity.this, getResources().getString(R.string.progress_dialog_title),
                    getResources().getString(R.string.definitions_search_progress_dialog_content));
        }

        @Override
        protected DefinitionsResult doInBackground(DefinitionsSearch... params) {
            DefinitionsSearch search = params[0];
            DefinitionsResult result = DictionaryAPI.getDefinitions(search, SearchActivity.this);
            return result;
        }

        @Override
        protected void onPostExecute(DefinitionsResult result) {
            progressDialog.dismiss();

            Log.d(activityName, result.getResultStatus() + ", " + result.getDefinitions().size());
            for (WordDefinition w : result.getDefinitions()) {
                Log.d(activityName, w.toString());
            }

            if (result.getResultStatus() == ResultStatus.Ok && result.getDefinitions().size() > 0) {
                Intent intent = new Intent(SearchActivity.this, DefinitionsActivity.class);
                intent.putExtra("definitions", result);
                startActivity(intent);
            } else if (result.getResultStatus() == ResultStatus.Ok && result.getDefinitions().size() == 0) {
                UI.showToastMessage(SearchActivity.this, getResources().getString(R.string.definitions_not_found));
            } else if (result.getResultStatus() == ResultStatus.ServerError || result.getResultStatus() == ResultStatus.BadData) {
                UI.showToastMessage(SearchActivity.this, getResources().getString(R.string.server_error));
            }
        }
    }

    private class GetRandomWord extends AsyncTask<RandomWordSearch, Void, RandomWordResult> {

        private RandomWordSearch randomWordSearch;

        @Override
        protected  void onPreExecute() {
            progressDialog = ProgressDialog.show(SearchActivity.this, getResources().getString(R.string.progress_dialog_title),
                    getResources().getString(R.string.random_word_search_progress_dialog_content));
        }

        @Override
        protected RandomWordResult doInBackground(RandomWordSearch... params) {
            RandomWordSearch search = params[0];
            randomWordSearch = search;
            RandomWordResult result = DictionaryAPI.getRandomWord(search, SearchActivity.this);
            return result;
        }

        @Override
        protected void onPostExecute(RandomWordResult result) {
            progressDialog.dismiss();
            Log.d(activityName, result.toString());

            if (result.getResultStatus() == ResultStatus.Ok) {
                DefinitionsSearch definitionsSearch = makeDefinitionsSearch(randomWordSearch, result);
                new GetDefinitions().execute(definitionsSearch);
            } else {
                UI.showToastMessage(SearchActivity.this, getResources().getString(R.string.server_error));
            }
        }
    }

    private class GetRelatedWords extends AsyncTask<RelatedWordsSearch, Void, RelatedWordsResult> {

        @Override
        protected  void onPreExecute() {
            progressDialog = ProgressDialog.show(SearchActivity.this, getResources().getString(R.string.progress_dialog_title),
                    getResources().getString(R.string.related_words_search_progress_dialog_content));
        }

        @Override
        protected RelatedWordsResult doInBackground(RelatedWordsSearch... params) {
            RelatedWordsSearch search = params[0];
            RelatedWordsResult result = DictionaryAPI.getRelatedWords(search, SearchActivity.this);
            return result;
        }

        @Override
        protected void onPostExecute(RelatedWordsResult result) {
            progressDialog.dismiss();

            if (result.getResultStatus() == ResultStatus.Ok && result.getRelationshipTypes().size() > 0) {

                Log.d(activityName, result.toString());
                Log.d(activityName, "OK");

                Intent intent = new Intent(SearchActivity.this, RelatedWordsActivity.class);
                intent.putExtra("relatedWords", result);
                startActivity(intent);

            } else if (result.getResultStatus() == ResultStatus.Ok && result.getRelationshipTypes().size() == 0) {
                UI.showToastMessage(SearchActivity.this, getResources().getString(R.string.related_words_not_found));
            } else {
                UI.showToastMessage(SearchActivity.this,  getResources().getString(R.string.server_error));
            }
        }
    }

    private DefinitionsSearch makeDefinitionsSearch(RandomWordSearch randomWordSearch, RandomWordResult randomWordResult) {
        DefinitionsSearch definitionsSearch = new DefinitionsSearch();
        definitionsSearch.setWord(randomWordResult.getWord());
        definitionsSearch.setUseCanonical(false);
        definitionsSearch.setPartOfSpeech(randomWordSearch.getPartOfSpeech());
        definitionsSearch.setWordLimit(getResources().getInteger(R.integer.default_limit_for_random_word));
        return definitionsSearch;
    }
}
