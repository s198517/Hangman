package com.example.simenarvnes.s198517;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.simenarvnes.hangman.R;


public class RulesActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rulesactivity);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.rules_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (item.getItemId()){
            case R.id.gameactivity:
                finish();
                return true;
            case R.id.languageactivity:
                Intent j = new Intent(this, LanguageActivity.class);
                startActivity(j);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
