package com.example.jovica.wdictionary.helpers;

import android.content.Context;

import com.example.jovica.wdictionary.model.AudioResult;
import com.example.jovica.wdictionary.model.DefinitionsResult;
import com.example.jovica.wdictionary.model.DefinitionsSearch;
import com.example.jovica.wdictionary.model.DownloadAudioResult;
import com.example.jovica.wdictionary.model.RandomWordResult;
import com.example.jovica.wdictionary.model.RandomWordSearch;
import com.example.jovica.wdictionary.model.RelatedWordsResult;
import com.example.jovica.wdictionary.model.RelatedWordsSearch;
import com.example.jovica.wdictionary.model.RelationshipTypeResult;
import com.example.jovica.wdictionary.model.ResultStatus;
import com.example.jovica.wdictionary.model.WordDefinition;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class DictionaryAPI {

    private static SyncHttpClient client = new SyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler, Context context) {
        params.put("api_key", Utils.getProperty("api_key", context));
        String absoluteUrl = Utils.getProperty("api_base_url", context) + url;
        client.get(absoluteUrl, params, responseHandler);
    }

    public static DefinitionsResult getDefinitions(DefinitionsSearch definitionsSearch, Context context) {
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
                    } catch (JSONException | JsonParseException e) {
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
        }, context);

        if (result.getResultStatus().equals(ResultStatus.Ok)) {
            result.setDefinitions(definitions);
        }

        return result;
    }

    public static RandomWordResult getRandomWord(RandomWordSearch randomWordSearch, Context context) {
        String url = "words.json/randomWord";
        RequestParams params = new RequestParams();

        if (randomWordSearch.getHasDictionaryDefinition()) {
            params.put("hasDictionaryDef", randomWordSearch.getHasDictionaryDefinition());
        }

        if (!randomWordSearch.getPartOfSpeech().equals("")) {
            params.put("includePartOfSpeech", randomWordSearch.getPartOfSpeech());
        }

        params.put("minLength", randomWordSearch.getMinLength());
        params.put("maxLength", randomWordSearch.getMaxLength());

        final RandomWordResult result = new RandomWordResult();
        get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Gson gson = new Gson();
                try {
                    RandomWordResult tempResult = gson.fromJson(response.toString(), RandomWordResult.class);
                    result.setWord(tempResult.getWord());
                } catch (JsonParseException e) {
                    result.setResultStatus(ResultStatus.BadData);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                result.setResultStatus(ResultStatus.ServerError);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                result.setResultStatus(ResultStatus.ServerError);
            }
        }, context);

        return result;
    }

    public static RelatedWordsResult getRelatedWords(RelatedWordsSearch relatedWordsSearch, Context context) {
        String url = "word.json/" + relatedWordsSearch.getWord() + "/relatedWords";
        RequestParams params = new RequestParams();

        if (!relatedWordsSearch.getRelationshipType().equals("")) {
            params.put("relationshipTypes", relatedWordsSearch.getRelationshipType());
        }

        params.put("limitPerRelationshipType", relatedWordsSearch.getLimitPerRelationshipType());
        params.put("useCanonical", relatedWordsSearch.getUseCanonical());

        final RelatedWordsResult result = new RelatedWordsResult();
        result.setWord(relatedWordsSearch.getWord());

        get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Gson gson = new Gson();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = (JSONObject) response.get(i);
                        RelationshipTypeResult relationshipTypeResult = gson.fromJson(obj.toString(), RelationshipTypeResult.class);
                        result.addRelationshipType(relationshipTypeResult);
                    } catch (JSONException | JsonParseException e) {
                        result.setResultStatus(ResultStatus.BadData);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                result.setResultStatus(ResultStatus.ServerError);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                result.setResultStatus(ResultStatus.ServerError);
            }
        }, context);

        return result;

    }

    public static AudioResult getAudio(String word, Context context) {
        String url = "word.json/" + word + "/audio";
        RequestParams params = new RequestParams();
        params.put("limit", 1);

        final AudioResult result = new AudioResult();
        result.setWord(word);

        get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if (response.length() == 1) {
                    try {
                        JSONObject obj = (JSONObject) response.get(0);
                        String fileUrl = obj.getString("fileUrl");
                        result.setFileUrl(fileUrl);
                    } catch (JSONException e) {
                        result.setResultStatus(ResultStatus.BadData);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                result.setResultStatus(ResultStatus.ServerError);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                result.setResultStatus(ResultStatus.ServerError);
            }
        }, context);

        return result;

    }

    public static DownloadAudioResult downloadAudio(AudioResult param, Context context) {

        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;

        DownloadAudioResult result = new DownloadAudioResult();

        try {
            URL url = new URL(param.getFileUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                result.setResultStatus(ResultStatus.ServerError);

                if (connection != null)
                    connection.disconnect();
                return result;
            }

            input = connection.getInputStream();

            if (context.getFilesDir() == null) {
                result.setResultStatus(ResultStatus.ServerError);
            } else {
                File file = new File(context.getFilesDir(), param.getWord() + ".mp3");
                file.createNewFile();
                file.setReadable(true, false);
                result.setFile(file);
                output = new FileOutputStream(file);

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
            }

        } catch (Exception e) {
            result.setResultStatus(ResultStatus.ServerError);

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

        return result;
    }
}
