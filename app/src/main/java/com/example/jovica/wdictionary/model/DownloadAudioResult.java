package com.example.jovica.wdictionary.model;

import java.io.File;

/**
 * Created by Jovica on 30-Jun-16.
 */
public class DownloadAudioResult extends Result {

    private File file;

    public DownloadAudioResult() {
        super();
        this.resultStatus = ResultStatus.Ok;
    }

    public DownloadAudioResult(ResultStatus resultStatus, File file) {
        super();
        this.resultStatus = resultStatus;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
