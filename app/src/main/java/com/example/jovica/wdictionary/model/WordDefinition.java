package com.example.jovica.wdictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jovica on 27-Jun-16.
 */

public class WordDefinition implements Parcelable {

    @SerializedName("word")
    private String word;

    @SerializedName("partOfSpeech")
    private String partOfSpeech;

    @SerializedName("text")
    private String text;

    public WordDefinition() {
        super();
    }

    public WordDefinition(String word, String partOfSpeech, String text) {
        super();
        this.word = word;
        this.partOfSpeech = partOfSpeech;
        this.text = text;
    }


    protected WordDefinition(Parcel in) {
        word = in.readString();
        partOfSpeech = in.readString();
        text = in.readString();
    }

    public static final Creator<WordDefinition> CREATOR = new Creator<WordDefinition>() {
        @Override
        public WordDefinition createFromParcel(Parcel in) {
            return new WordDefinition(in);
        }

        @Override
        public WordDefinition[] newArray(int size) {
            return new WordDefinition[size];
        }
    };

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "WordDefinition: word=" + this.word +
                ", partOfSpeech=" + this.partOfSpeech +
                ", text=" + this.text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(partOfSpeech);
        dest.writeString(text);
    }
}
