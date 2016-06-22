package com.example.jovica.wdictionary.model;

/**
 * Created by Jovica on 22-Jun-16.
 */
public class DefinitionsSearch extends Search {

    private String word;
    private int wordLimit;
    private String partOfSpeech;
    private boolean useCanonical;

    public DefinitionsSearch(){
        super();
    }

    public DefinitionsSearch(String word, int wordLimit, String partOfSpeech, boolean useCanonical) {
        super();
        this.word = word;
        this.wordLimit = wordLimit;
        this.partOfSpeech = partOfSpeech;
        this.useCanonical = useCanonical;
    }

    public String getWord(){
        return this.word;
    }

    public void setWord(String word){
        this.word = word;
    }

    public int getWordLimit(){
        return this.wordLimit;
    }

    public void setWordLimit(int wordLimit){
        this.wordLimit = wordLimit;
    }

    public String getPartOfSpeech(){
        return this.partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech){
        this.partOfSpeech = partOfSpeech;
    }

    public boolean getUseCanonical(){
        return this.useCanonical;
    }

    public void setUseCanonical(boolean useCanonical){
        this.useCanonical = useCanonical;
    }

    public String toString() {
        return "DefinitionsSearch: word=" + this.word + ", wordLimit=" + this.wordLimit +
                ", partOfSpeech=" + this.partOfSpeech + ", useCanonical=" + this.useCanonical;
    }

}
