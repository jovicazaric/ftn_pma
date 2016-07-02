package com.example.jovica.wdictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.jovica.wdictionary.helpers.Utils;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jovica on 30-Jun-16.
 */
public class SplashScreenActivity extends Activity {

    private int dots;
    private int interval;
    private int iterations;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        dots = 0;
        interval = getResources().getInteger(R.integer.loading_simulator_interval);
        iterations = getResources().getInteger(R.integer.loading_simulator_iteration);

        Random random = new Random();
        iterations = iterations + (Math.abs(random.nextInt()) % 11) - 5;
        Log.d("SPLASH", iterations + "");
        timer = new Timer();

        timerTask = new TimerTask() {
            @Override
            public void run() {

                if (iterations < 0) {
                    stop();
                }
                if (dots == 3) {
                    removeAllDots();
                } else {
                    addDot();
                }
                iterations--;

                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        timer.schedule(timerTask, 0, interval);
    }

    private void stop() {
        timer.cancel();

        if (Utils.hasInternetAccess(SplashScreenActivity.this)) {
            Intent intent = new Intent(SplashScreenActivity.this, SearchActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashScreenActivity.this, InternetActivity.class);
            startActivity(intent);
        }

    }
    private void addDot() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView loading = (TextView) findViewById(R.id.dots);
                loading.setText(loading.getText().toString() + ".");
            }
        });
        this.dots++;

    }

    private void removeAllDots() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView loading = (TextView) findViewById(R.id.dots);
                String text = loading.getText().toString();
                text = text.replace("...", "");
                loading.setText(text);
            }
        });
        this.dots = 0;
    }


}
