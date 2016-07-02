package com.example.jovica.wdictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jovica.wdictionary.helpers.Utils;

/**
 * Created by Jovica on 02-Jul-16.
 */
public class InternetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
    }

    public void turnOnWifi(View v) {
        Utils.turnOnWifi(InternetActivity.this);
        Intent intent = new Intent(InternetActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void proceedAnyway(View v) {
        Intent intent = new Intent(InternetActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void turnOnMobileData(View v) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
        startActivity(intent);
    }

}
