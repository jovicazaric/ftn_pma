package com.example.jovica.wdictionary;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

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
        Toast.makeText(SearchActivity.this, "aaa", Toast.LENGTH_LONG).show();
        Log.d(activityName, "onSearchButtonClicked");
    }

    public void onSearchTypeChanged(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.rb_definitions:
                if (checked) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    DefinitionsFragment definitionsFragment = new DefinitionsFragment();
                    transaction.replace(R.id.search_options_fragment_container, definitionsFragment);
                    transaction.commit();
                }
                break;
            case R.id.rb_related_words:
                if (checked) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    RelatedWordsFragment relatedWordsFragment = new RelatedWordsFragment();
                    transaction.replace(R.id.search_options_fragment_container, relatedWordsFragment);
                    transaction.commit();
                }
                break;
            case R.id.rb_random_word:
                if (checked) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    RandomWordFragment randomWordFragment = new RandomWordFragment();
                    transaction.replace(R.id.search_options_fragment_container, randomWordFragment);
                    transaction.commit();
                }
                break;
        }

        Log.d(activityName, "onSeachTypeChanged");
    }
}
