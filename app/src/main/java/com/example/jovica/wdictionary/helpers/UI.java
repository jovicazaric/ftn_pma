package com.example.jovica.wdictionary.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jovica.wdictionary.R;

public class UI {

    public static void initPartOfSpeechSpinner(Activity activity, View view) {
        Spinner partOfSpeechSpinner = (Spinner) view.findViewById(R.id.sp_part_of_speech);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.part_of_speech_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partOfSpeechSpinner.setAdapter(adapter);
    }

    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static boolean goBack(Activity activity) {
        activity.finish();
        return true;
    }

    public static boolean exitApp(Activity activity) {
        Utils.emptyAudioFolder(activity);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        return true;
    }

}
