package com.example.jovica.wdictionary.model;

/**
 * Created by Jovica on 29-Jun-16.
 */
public class AudioResult extends Result {

    private String word;
    private String fileUrl;

    public AudioResult() {
        super();
        this.resultStatus = ResultStatus.Ok;
        this.fileUrl = "";
    }

    public AudioResult(String word, String fileUrl) {
        this.word = word;
        this.fileUrl = fileUrl;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "AudioResult: word=" + this.word
                + ", fileUrl=" + this.fileUrl;
    }
}
