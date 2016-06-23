package com.example.jovica.wdictionary.model;

/**
 * Created by Jovica on 23-Jun-16.
 */
public class RandomWordSearch extends Search {

    public boolean hasDictionaryDefinition;
    public String partOfSpeech;
    public int minLength;
    public int maxLength;

    public RandomWordSearch() {
        super();
    }

    public RandomWordSearch(boolean hasDictionaryDefinition, String partOfSpeech, int minLength, int maxLength) {
        super();
        this.hasDictionaryDefinition = hasDictionaryDefinition;
        this.partOfSpeech = partOfSpeech;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public boolean getHasDictionaryDefinition() {
        return this.hasDictionaryDefinition;
    }

    public void setHasDictionaryDefinition(boolean hasDictionaryDefinition) {
        this.hasDictionaryDefinition = hasDictionaryDefinition;
    }

    public String getPartOfSpeech() {
        return this.partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public int getMinLength() {
        return this.minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String toString() {
        return "RandomWordSearch: hasDictionaryDefinition=" + this.hasDictionaryDefinition +
                ", partOfSpeech=" + this.partOfSpeech +
                ", minLength=" + this.minLength +
                ", maxLength=" + this.maxLength;
    }
}
