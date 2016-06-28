package com.example.jovica.wdictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jovica on 28-Jun-16.
 */
public class RelationshipTypeResult implements Parcelable{

    @SerializedName("relationshipType")
    private String relationshipType;

    @SerializedName("words")
    private List<String> words;

    public RelationshipTypeResult() {
        super();
        this.words = new ArrayList<String>();
    }

    public RelationshipTypeResult(String relationshipType, List<String> words) {
        super();
        this.relationshipType = relationshipType;
        this.words = words;
    }

    protected RelationshipTypeResult(Parcel in) {
        relationshipType = in.readString();
        words = in.createStringArrayList();
    }

    public static final Creator<RelationshipTypeResult> CREATOR = new Creator<RelationshipTypeResult>() {
        @Override
        public RelationshipTypeResult createFromParcel(Parcel in) {
            return new RelationshipTypeResult(in);
        }

        @Override
        public RelationshipTypeResult[] newArray(int size) {
            return new RelationshipTypeResult[size];
        }
    };

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        String words = "";
        for (String word : this.words) {
            words += word + " ";
        }
        return "RelationshipTypeResult: relationshipType=" + this.relationshipType
                + ", words=" + words;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(relationshipType);
        dest.writeStringList(words);
    }
}
