package com.example.jovica.wdictionary.model;

/**
 * Created by Jovica on 22-Jun-16.
 */
public class RelatedWordsSearch extends Search {

    private String word;
    private String relationshipType;
    private int limitPerRelationshipType;
    private boolean useCanonical;

    public RelatedWordsSearch() {
        super();
    }

    public RelatedWordsSearch(String word, String relationshipType, int limitPerRelationshipType, boolean useCanonical) {
        super();
        this.word = word;
        this.relationshipType = relationshipType;
        this.limitPerRelationshipType = limitPerRelationshipType;
        this.useCanonical = useCanonical;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getRelationshipType() {
        return this.relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public int getLimitPerRelationshipType() {
        return this.limitPerRelationshipType;
    }

    public void setLimitPerRelationshipType(int limitPerRelationshipType) {
        this.limitPerRelationshipType = limitPerRelationshipType;
    }

    public boolean getUseCanonical() {
        return this.useCanonical;
    }

    public void setUseCanonical(boolean useCanonical) {
        this.useCanonical = useCanonical;
    }

    public String toString() {
        return "RelatedWordsSearch: word=" + this.word + ", relationshipType=" + this.relationshipType +
                ", limitPerRelationshipType=" + this.limitPerRelationshipType +
                ", useCanonical=" + this.useCanonical;
    }
}
