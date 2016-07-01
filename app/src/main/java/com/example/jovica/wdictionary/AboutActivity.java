package com.example.jovica.wdictionary;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import com.example.jovica.wdictionary.helpers.UI;
import com.example.jovica.wdictionary.helpers.Utils;

/**
 * Created by Jovica on 01-Jul-16.
 */
public class AboutActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setTitle(getResources().getString(R.string.menu_about));
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (getResources().getConfiguration().orientation == 2) {

            RelativeLayout app = (RelativeLayout) findViewById(R.id.app);
            RelativeLayout.LayoutParams appParams = (RelativeLayout.LayoutParams) app.getLayoutParams();
            appParams.topMargin = 100;
            app.setLayoutParams(appParams);

            RelativeLayout site = (RelativeLayout) findViewById(R.id.site);
            RelativeLayout.LayoutParams siteParams = (RelativeLayout.LayoutParams) site.getLayoutParams();
            siteParams.topMargin = 60;
            site.setLayoutParams(siteParams);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return UI.goBack(this);
            case R.id.exit:
                return UI.exitApp(this);
            case R.id.about:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void visitWebSite(View v) {
        String url = Utils.getProperty("website", AboutActivity.this);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void sendEmail(View v) {

        String email = Utils.getProperty("suggestion_email", AboutActivity.this);
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.question_suggestion_for_wdictionary));
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");

        startActivity(intent);
    }
}
