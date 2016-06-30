package com.example.jovica.wdictionary.helpers;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * Created by Jovica on 30-Jun-16.
 */
public class Utils {

    public static void emptyAudioFolder(Context context) {
        Log.d("RWS", "Destroying");
        if (context.getFilesDir() != null) {
            for (File f : context.getFilesDir().listFiles()) {
                Log.d("RWS DESTROYING", f.getAbsolutePath());
                f.delete();
            }
        }
    }
}
