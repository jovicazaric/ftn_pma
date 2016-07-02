package com.example.jovica.wdictionary.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

    public static String getProperty(String propertyKey, Context context) {

        Properties properties = new Properties();;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        String value = "";
        try {
            inputStream = assetManager.open("app.properties");
            properties.load(inputStream);
            value = properties.getProperty(propertyKey);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static boolean hasInternetAccess(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void turnOnWifi(Context context) {

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
    }
}
