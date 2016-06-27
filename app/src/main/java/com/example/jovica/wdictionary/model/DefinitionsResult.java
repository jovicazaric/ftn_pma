package com.example.jovica.wdictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jovica on 27-Jun-16.
 */
public class DefinitionsResult extends Result implements Parcelable {

    private List<WordDefinition> definitions;

    public DefinitionsResult() {
        super();
        this.resultStatus = ResultStatus.Ok;
        this.definitions = new ArrayList<WordDefinition>();
    }

    protected DefinitionsResult(Parcel in) {
        definitions = in.createTypedArrayList(WordDefinition.CREATOR);
    }

    public static final Creator<DefinitionsResult> CREATOR = new Creator<DefinitionsResult>() {
        @Override
        public DefinitionsResult createFromParcel(Parcel in) {
            return new DefinitionsResult(in);
        }

        @Override
        public DefinitionsResult[] newArray(int size) {
            return new DefinitionsResult[size];
        }
    };

    public List<WordDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<WordDefinition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(definitions);
    }
}
