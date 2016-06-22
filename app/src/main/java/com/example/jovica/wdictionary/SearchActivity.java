package com.example.jovica.wdictionary;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jovica.wdictionary.model.DefinitionsSearch;
import com.example.jovica.wdictionary.model.Search;

public class SearchActivity extends FragmentActivity {

    private static final String activityName = "SEARCH_ACTIVITY";

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
    }

    public void onSearchButtonClicked(View view) {
        EditText wordForSearchEditText = (EditText)findViewById(R.id.et_word_for_search);
        String word = wordForSearchEditText.getText().toString();

        if (word.length() > 0) {
            Search search = getSearch();
        } else {
            String errorMessage = getResources().getString(R.string.search_word_required);
            Toast.makeText(SearchActivity.this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public void onSearchTypeChanged(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch(view.getId()) {
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
                    transaction.replace(R.id.search_options_fragment_container, randomWordFragment);;
                }
                break;
        }

        transaction.commit();
    }

    public Search getSearch() {

        Search search = null;
        RadioGroup SearchTypesRadioGroup = (RadioGroup)findViewById(R.id.rg_search_types);

        switch (SearchTypesRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_definitions:
                DefinitionsFragment definitionsFragment = (DefinitionsFragment) getSupportFragmentManager().findFragmentById(R.id.search_options_fragment_container);
                DefinitionsSearch definitionsSearch = definitionsFragment.getSearchParams();

                EditText wordForSearchEditText = (EditText)findViewById(R.id.et_word_for_search);
                definitionsSearch.setWord(wordForSearchEditText.getText().toString());
                search = definitionsSearch;

                break;
            case R.id.rb_related_words:
                Toast.makeText(SearchActivity.this, "2", Toast.LENGTH_LONG).show();
                break;
            case R.id.rb_random_word:
                Toast.makeText(SearchActivity.this, "3", Toast.LENGTH_LONG).show();
                break;
        }

        return search;
    }
}
