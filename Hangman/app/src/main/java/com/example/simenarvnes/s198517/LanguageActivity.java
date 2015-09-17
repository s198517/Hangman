package com.example.simenarvnes.s198517;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import java.util.Locale;
import android.content.res.Configuration;

import com.example.simenarvnes.hangman.R;

/**
 * Created by simenarvnes on 07/09/15.
 */
public class LanguageActivity extends AppCompatActivity{

    Button norwgian;
    Button english;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languageactivity);

        norwgian = (Button) findViewById(R.id.norwegian);
        english = (Button)findViewById(R.id.english);

        norwgian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lang = "nb";
                String country = "NO";
                Locale locale = new Locale(lang, country);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());

                Intent intent = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lang = "en";
                String country = "US";
                Locale locale = new Locale(lang, country);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());

                Intent intent = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.language_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (item.getItemId()){
            case R.id.gameactivity:
                finish();
                return true;
            case R.id.rulesactivity:
                Intent j = new Intent(this, RulesActivity.class);
                startActivity(j);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
