package com.example.jovica.wdictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jovica on 28-Jun-16.
 */
public class RelatedWordsResult extends Result implements Parcelable{

    private String word;
    List<RelationshipTypeResult> relationshipTypes;

    public RelatedWordsResult() {
        super();
        this.resultStatus = ResultStatus.Ok;
        this.relationshipTypes = new ArrayList<RelationshipTypeResult>();
    }

    public RelatedWordsResult(ResultStatus status, String word, List<RelationshipTypeResult> relationshipTypes) {
        super();
        this.resultStatus = status;
        this.word = word;
        this.relationshipTypes = relationshipTypes;
    }

    protected RelatedWordsResult(Parcel in) {
        word = in.readString();
        relationshipTypes = in.createTypedArrayList(RelationshipTypeResult.CREATOR);
    }

    public static final Creator<RelatedWordsResult> CREATOR = new Creator<RelatedWordsResult>() {
        @Override
        public RelatedWordsResult createFromParcel(Parcel in) {
            return new RelatedWordsResult(in);
        }

        @Override
        public RelatedWordsResult[] newArray(int size) {
            return new RelatedWordsResult[size];
        }
    };

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<RelationshipTypeResult> getRelationshipTypes() {
        return relationshipTypes;
    }

    public void setRelationshipTypes(List<RelationshipTypeResult> relationshipTypes) {
        this.relationshipTypes = relationshipTypes;
    }

    public void addRelationshipType(RelationshipTypeResult relationshipTypeResult) {
        this.relationshipTypes.add(relationshipTypeResult);
    }

    @Override
    public String toString() {
        String relationshipTypes = "";
        for (RelationshipTypeResult r : this.relationshipTypes) {
            relationshipTypes += r.toString() + " | ";
        }

        return "RelatedWordsResult: word=" + this.word
                + ", relationshipTypes=" + relationshipTypes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeTypedList(relationshipTypes);
    }
}
