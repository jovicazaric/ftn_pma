package com.example.jovica.wdictionary.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jovica on 27-Jun-16.
 */
public class RandomWordResult extends Result {

    @SerializedName("word")
    private String word;

    public RandomWordResult() {
        super();
        this.resultStatus = ResultStatus.Ok;
    }

    public RandomWordResult(ResultStatus resultStatus, String word) {
        super();
        this.resultStatus = resultStatus;
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "RandomWordResult: word=" + this.word;
    }
}
