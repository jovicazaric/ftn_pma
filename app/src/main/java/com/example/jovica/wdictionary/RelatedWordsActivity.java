package com.example.jovica.wdictionary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.jovica.wdictionary.adapters.RelatedWordsTypesAdapter;
import com.example.jovica.wdictionary.helpers.DictionaryAPI;
import com.example.jovica.wdictionary.helpers.UI;
import com.example.jovica.wdictionary.model.AudioResult;
import com.example.jovica.wdictionary.model.DefinitionsResult;
import com.example.jovica.wdictionary.model.DefinitionsSearch;
import com.example.jovica.wdictionary.model.RelatedWordsResult;
import com.example.jovica.wdictionary.model.ResultStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
        DefinitionsSearch definitionsSearch = makeDefinitionsSearch((String)v.getTag());
        new GetDefinitions().execute(definitionsSearch);
    }

    public void onSpeakerClick(View v) {
        new GetAudio().execute((String)v.getTag());
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

    private class GetAudio extends AsyncTask<String, Void, AudioResult> {

        @Override
        protected  void onPreExecute() {
            progressDialog = ProgressDialog.show(RelatedWordsActivity.this, getResources().getString(R.string.progress_dialog_title),
                    getResources().getString(R.string.related_words_progress_dialog_content_audio));
        }

        @Override
        protected AudioResult doInBackground(String... params) {
            String word = params[0];
            AudioResult result = DictionaryAPI.getAudio(word);
            return result;
        }

        @Override
        protected void onPostExecute(AudioResult result) {


            if (result.getResultStatus() == ResultStatus.Ok && !result.getFileUrl().equals("")) {

                Log.d("RELATEDWORDACTIVITY", result.toString());
                new DownloadAudio().execute(result.getFileUrl(), result.getWord());
                /*Intent intent = new Intent(RelatedWordsActivity.this, DefinitionsActivity.class);
                intent.putExtra("definitions", result);
                startActivity(intent);*/
            } else if (result.getResultStatus() == ResultStatus.Ok && result.getFileUrl().equals("")) {
                progressDialog.dismiss();
                UI.showToastMessage(RelatedWordsActivity.this, getResources().getString(R.string.audio_not_found));
            } else if (result.getResultStatus() == ResultStatus.ServerError || result.getResultStatus() == ResultStatus.BadData) {
                progressDialog.dismiss();
                UI.showToastMessage(RelatedWordsActivity.this, getResources().getString(R.string.server_error));
            }
        }
    }

    private class DownloadAudio extends AsyncTask<String, Void, String> {

        @Override
        protected  void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String fileUrl = params[0];
            String word = params[1];

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL(fileUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "";
                }

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/storage/emulated/0/Temp/" + word + ".mp3" );

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return "/storage/emulated/0/Temp/" + word + ".mp3";
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();

            if (result.equals("")) {
                UI.showToastMessage(RelatedWordsActivity.this, getResources().getString(R.string.audio_not_found));
            } else {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                File file = new File(result);
                intent.setDataAndType(Uri.fromFile(file), "audio/*");
                startActivity(intent);
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
