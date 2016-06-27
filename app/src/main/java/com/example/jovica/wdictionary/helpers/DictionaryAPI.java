package com.example.jovica.wdictionary.helpers;

import android.util.Log;

import com.example.jovica.wdictionary.model.DefinitionsResult;
import com.example.jovica.wdictionary.model.DefinitionsSearch;
import com.example.jovica.wdictionary.model.RandomWordSearch;
import com.example.jovica.wdictionary.model.ResultStatus;
import com.example.jovica.wdictionary.model.WordDefinition;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class DictionaryAPI {

    public static final String api_key = "f7a0b965ab8d6d61d700608bb8202da8a818e42379cf27cc4";
    private static final String BASE_URL = "http://api.wordnik.com:80/v4/";

    private static SyncHttpClient client = new SyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.put("api_key", api_key);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static DefinitionsResult getDefinitions(DefinitionsSearch definitionsSearch) {
        String url = "word.json/" + definitionsSearch.getWord() + "/definitions";
        RequestParams params = new RequestParams();
        params.put("limit", definitionsSearch.getWordLimit());

        if (!definitionsSearch.getPartOfSpeech().equals("")) {
            params.put("partOfSpeech", definitionsSearch.getPartOfSpeech());
        }

        params.put("useCanonical", definitionsSearch.getUseCanonical());

        final DefinitionsResult result = new DefinitionsResult();
        final List<WordDefinition> definitions = new ArrayList<WordDefinition>();

        get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Gson gson = new Gson();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        definitions.add(gson.fromJson(response.get(i).toString(), WordDefinition.class));
                    } catch (JSONException e) {
                        result.setResultStatus(ResultStatus.BadData);
                        e.printStackTrace();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                result.setResultStatus(ResultStatus.ServerError);
            }
        });

        if (result.getResultStatus().equals(ResultStatus.Ok)) {
            result.setDefinitions(definitions);
        }

        return result;
    }

    public static void getRandomWord(RandomWordSearch randomWordSearch) {
        String url = "words.json/randomWord";
        RequestParams params = new RequestParams();
        params.put("hasDictionaryDef", randomWordSearch.getHasDictionaryDefinition());

        if (!randomWordSearch.getPartOfSpeech().equals("")) {
            params.put("includePartOfSpeech", randomWordSearch.getPartOfSpeech());
        }

        params.put("minLength", randomWordSearch.getMinLength());
        params.put("maxLength", randomWordSearch.getMaxLength());

        get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONObject def = (JSONObject) response;
                    Log.d("DICTIONARYAPI", def.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject def = (JSONObject) response.get(i);
                        Log.d("DICTIONARYAPI", def.toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
