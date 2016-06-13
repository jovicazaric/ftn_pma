package com.example.jovica.wdictionary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class SearchActivity extends Activity {

    private static final String activityName = "SEARCH_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
                    Log.d(activityName, "rb_definitions checked");
                }
                break;
            case R.id.rb_related_words:
                if (checked) {
                    Log.d(activityName, "rb_related_words checked");
                }
                break;
            case R.id.rb_random_word:
                if (checked) {
                    Log.d(activityName, "rb_random_word checked");
                }
                break;
        }

        Log.d(activityName, "onSeachTypeChanged");

    }
}
