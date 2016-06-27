package com.example.jovica.wdictionary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.jovica.wdictionary.adapters.DefinitionAdapter;
import com.example.jovica.wdictionary.model.DefinitionsResult;

/**
 * Created by Jovica on 27-Jun-16.
 */
public class DefinitionsActivity extends Activity {

    private static final String activityName = "DEFINITIONS_ACTIVITY";
    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definitions);

        DefinitionsResult definitionsResult = getIntent().getExtras().getParcelable("definitions");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setTitle(getResources().getString(R.string.toolbar_definitions_for) + " " + definitionsResult.getDefinitions().get(0).getWord());
        getActionBar().setDisplayHomeAsUpEnabled(true);

        ListView definitionListView = (ListView) findViewById(R.id.definitions_listview);
        DefinitionAdapter definitionAdapter = new DefinitionAdapter(DefinitionsActivity.this, R.layout.definition_item, definitionsResult.getDefinitions());
        definitionListView.setAdapter(definitionAdapter);

        Log.d(activityName, definitionsResult.getDefinitions().size() + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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
