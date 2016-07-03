package com.example.jovica.wdictionary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.jovica.wdictionary.adapters.DefinitionAdapter;
import com.example.jovica.wdictionary.helpers.DictionaryAPI;
import com.example.jovica.wdictionary.helpers.UI;
import com.example.jovica.wdictionary.helpers.Utils;
import com.example.jovica.wdictionary.model.AudioResult;
import com.example.jovica.wdictionary.model.DefinitionsResult;
import com.example.jovica.wdictionary.model.DownloadAudioResult;
import com.example.jovica.wdictionary.model.ResultStatus;

/**
 * Created by Jovica on 27-Jun-16.
 */
public class DefinitionsActivity extends Activity {
    
    private Toolbar toolbar;
    private ProgressDialog progressDialog;

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

        ImageView speaker = (ImageView) toolbar.findViewById(R.id.speaker);

        speaker.setVisibility(View.VISIBLE);
        speaker.setTag(definitionsResult.getDefinitions().get(0).getWord());
        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSpeakerClicked(v);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.emptyAudioFolder(DefinitionsActivity.this);
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
                return UI.goBack(this);
            case R.id.exit:
                return UI.exitApp(this);
            case R.id.about:
                Intent intent = new Intent(DefinitionsActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSpeakerClicked(View v) {

        if (!Utils.hasInternetAccess(DefinitionsActivity.this)) {
            UI.showToastMessage(DefinitionsActivity.this, getResources().getString(R.string.no_internet_acces));
            return;
        }

        new GetAudio().execute((String)v.getTag());
    }

    private class GetAudio extends AsyncTask<String, Void, AudioResult> {

        @Override
        protected  void onPreExecute() {
            progressDialog = ProgressDialog.show(DefinitionsActivity.this, getResources().getString(R.string.progress_dialog_title),
                    getResources().getString(R.string.related_words_progress_dialog_content_audio));
        }

        @Override
        protected AudioResult doInBackground(String... params) {
            String word = params[0];
            AudioResult result = DictionaryAPI.getAudio(word, DefinitionsActivity.this);
            return result;
        }

        @Override
        protected void onPostExecute(AudioResult result) {


            if (result.getResultStatus() == ResultStatus.Ok && !result.getFileUrl().equals("")) {
                new DownloadAudio().execute(result);
            } else if (result.getResultStatus() == ResultStatus.Ok && result.getFileUrl().equals("")) {
                progressDialog.dismiss();
                UI.showToastMessage(DefinitionsActivity.this, getResources().getString(R.string.audio_not_found));
            } else if (result.getResultStatus() == ResultStatus.ServerError || result.getResultStatus() == ResultStatus.BadData) {
                progressDialog.dismiss();
                UI.showToastMessage(DefinitionsActivity.this, getResources().getString(R.string.server_error));
            }
        }
    }

    private class DownloadAudio extends AsyncTask<AudioResult, Void, DownloadAudioResult> {

        @Override
        protected  void onPreExecute() {
        }

        @Override
        protected DownloadAudioResult doInBackground(AudioResult... params) {
            AudioResult param = params[0];
            DownloadAudioResult result = DictionaryAPI.downloadAudio(param, DefinitionsActivity.this);
            return result;
        }

        @Override
        protected void onPostExecute(DownloadAudioResult result) {
            progressDialog.dismiss();

            if (result.getResultStatus() == ResultStatus.ServerError) {
                UI.showToastMessage(DefinitionsActivity.this, getResources().getString(R.string.server_error));
            } else {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(result.getFile()), "audio/*");
                startActivity(intent);
            }
        }
    }
}
