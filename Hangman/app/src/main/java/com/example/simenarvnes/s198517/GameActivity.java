package com.example.simenarvnes.s198517;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.simenarvnes.hangman.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class GameActivity extends AppCompatActivity{


    ///////////////////////////
    //                       //
    //       VARIABLES       //
    //                       //
    ///////////////////////////


    private final int ENG_LANG = 26;
    private final int NO_LANG  = 29;
    private final int NUMBER_OF_ATTEMPTS = 6;

    protected int wins = 0;
    protected int losses = 0;
    private int rightGuess;
    private int wrongGuess;

    private char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','Æ','Ø','Å'};
    private char[] displayWord;

    private Button[] buttons;
    private String[] wordsFromXmlFile;

    private String hangmanWord;
    private int hangmanWordIndex;
    private int whatLanguage;

    private ArrayList<String> words;
    private ArrayList<String> usedWords = new ArrayList<>();
    private ArrayList<String> guessedLetters = new ArrayList<>();
    private ArrayList<String> rightLetters = new ArrayList<>();

    TextView textView;

    Hangman hangman;


    ///////////////////////////
    //                       //
    //   ON CREATE METHODS   //
    //                       //
    ///////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gameactivity);

        //Check what language is being used
        checkLanguage();

        //Fill words-array
        fillWordArray();

        //Add buttons to buttons-array
        addButtons();

        //Generate buttons to layout
        generateButtons();

        //Generate hangmanWord and display underscores
        initiateGame();

        //Update word info and wins/losses
        updateDisplayWord();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        //Restore state
        hangman.round = savedInstanceState.getInt("hangmanRound");
        whatLanguage = savedInstanceState.getInt("whatLanguage");
        wins = savedInstanceState.getInt("wins");
        losses = savedInstanceState.getInt("losses");
        rightGuess = savedInstanceState.getInt("rightGuess");
        wrongGuess = savedInstanceState.getInt("wrongGuess");
        displayWord = savedInstanceState.getCharArray("displayWord");
        wordsFromXmlFile = savedInstanceState.getStringArray("wordsFromXmlFile");
        hangmanWord = savedInstanceState.getString("hangmanWord");
        hangmanWordIndex = savedInstanceState.getInt("hangmanWordIndex");
        words = savedInstanceState.getStringArrayList("words");
        usedWords = savedInstanceState.getStringArrayList("usedWords");
        guessedLetters = savedInstanceState.getStringArrayList("guessedLetters");
        rightLetters = savedInstanceState.getStringArrayList("rightLetters");

        for(int i = 0; i < guessedLetters.size(); i++){
            for( int j = 0; j < buttons.length; j++) {
                if (guessedLetters.get(i).equals(buttons[j].getText().toString())) {
                    buttons[j].setClickable(false);
                    buttons[j].setTextColor(getResources().getColor(R.color.wrongLetter));
                }
            }
        }

        for(int i = 0; i < rightLetters.size(); i++){
            for(int j = 0; j < buttons.length; j++){
                if (rightLetters.get(i).equals(buttons[j].getText().toString())) {
                    buttons[j].setTextColor(getResources().getColor(R.color.rightLetter));
                }
            }
        }
        updateDisplayWord();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        //Save state
        savedInstanceState.putInt("hangmanRound", hangman.getRound());
        savedInstanceState.putInt("whatLanguage", whatLanguage);
        savedInstanceState.putStringArrayList("rightLetters", rightLetters);
        savedInstanceState.putStringArrayList("guessedLetters", guessedLetters);
        savedInstanceState.putInt("wins", wins);
        savedInstanceState.putInt("losses", losses);
        savedInstanceState.putInt("rightGuess", rightGuess);
        savedInstanceState.putInt("wrongGuess", wrongGuess);
        savedInstanceState.putCharArray("displayWord", displayWord);
        savedInstanceState.putStringArray("wordsFromXmlFile", wordsFromXmlFile);
        savedInstanceState.putString("hangmanWord", hangmanWord);
        savedInstanceState.putInt("hangmanWordIndex", hangmanWordIndex);
        savedInstanceState.putStringArrayList("words", words);
        savedInstanceState.putStringArrayList("usedWords", usedWords);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (item.getItemId()){
            case R.id.rulesactivity:
                Intent i = new Intent(this, RulesActivity.class);
                startActivity(i);
                return true;
            case R.id.languageactivity:
                Intent j = new Intent(this, LanguageActivity.class);
                startActivity(j);
                return true;
            case R.id.exit:
                super.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Fill words-array with words
    protected void fillWordArray(){

        wordsFromXmlFile = getResources().getStringArray(R.array.words);
        words = new ArrayList<>(Arrays.asList(wordsFromXmlFile));
    }

    //Generate buttons
    protected void generateButtons(){

        GridLayout layout = (GridLayout) findViewById(R.id.buttons);

        //Set rows and columns
        int column = 8;
        int row = buttons.length / column;
        layout.setUseDefaultMargins(true);
        layout.setColumnCount(column);
        layout.setRowCount(row);

        for (int i = 0; i < buttons.length; i++){
            final int index = i;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(55,55);

            buttons[i].setText(String.valueOf(letters[i]));
            buttons[i].setTextSize(10);
            buttons[i].setLayoutParams(params);
            buttons[i].setTextColor(getResources().getColor(R.color.buttonsText));
            buttons[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.button));

            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Disable button
                    buttons[index].setClickable(false);
                    guessedLetters.add(buttons[index].getText().toString());

                    //Check whether guessed letter is right or wrong
                    boolean correctOrWrong = guessLetter(buttons[index].getText().toString());
                    if (correctOrWrong) {
                        buttons[index].setTextColor(getResources().getColor(R.color.rightLetter));
                        rightLetters.add(buttons[index].getText().toString());

                    } else {
                        buttons[index].setTextColor(getResources().getColor(R.color.wrongLetter));
                        hangman.setRound();
                        hangman.reDraw();
                        wrongGuess++;
                    }

                    //Update word displayed on screen
                    updateDisplayWord();

                    //Win or loose conditions
                    if (rightGuess == hangmanWord.length() || wrongGuess == NUMBER_OF_ATTEMPTS) {

                        String winOrLoose = "";

                        for (int i = 0; i < buttons.length; i++) {
                            buttons[i].setTextColor(getResources().getColor(R.color.buttonsText));
                        }

                        guessedLetters.clear();
                        rightLetters.clear();
                        hangman.resetRound();
                        hangman.reDraw();

                        if (rightGuess == hangmanWord.length()) {
                            winOrLoose = getResources().getString(R.string.winGame);
                            wins++;
                        } else if (wrongGuess == NUMBER_OF_ATTEMPTS) {
                            winOrLoose = getResources().getString(R.string.loseGame);
                            losses++;
                        }

                        //Used word moved to usedWord ArrayList
                        usedWords.add(words.remove(hangmanWordIndex));

                        //A Toast telling user the outcome of the game
                        Toast.makeText(getApplicationContext(), winOrLoose, Toast.LENGTH_LONG).show();

                        //Generate new word
                        initiateGame();
                        updateDisplayWord();
                    }
                }
            });
            layout.addView(buttons[i]);
        }
    }

    //Adding buttons
    protected void addButtons(){

        if(checkLanguage() == 1){
            buttons = new Button[ENG_LANG];
        }
        else if(checkLanguage() == 0){
            buttons = new Button[NO_LANG];
        }

        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new Button(this);
        }
    }

    //Check what language is being used
    protected int checkLanguage(){

        if(Locale.getDefault().getLanguage().startsWith("en")){
           whatLanguage = 1;

        }
        else if(Locale.getDefault().getLanguage().startsWith("nb")){
            whatLanguage = 0;
        }
        return whatLanguage;
    }



    ///////////////////////////
    //                       //
    //     GAME METHODS      //
    //                       //
    ///////////////////////////


    //Check whether a letter is right or wrong
    protected boolean guessLetter(String w){

        char word[] = hangmanWord.toCharArray();
        char guessChar = w.charAt(0);
        boolean correct = false;

        for(int i = 0; i < displayWord.length; i++){
            if(guessChar == word[i]){
                displayWord[i] = guessChar;
                rightGuess++;
                correct = true;
            }
        }
        return correct;
    }

    //Update displayWord
    protected void updateDisplayWord(){
        textView.setText("");
        for(int i = 0; i < displayWord.length; i++){
            textView.append(displayWord[i] + "\t");
        }

        this.setTitle("Hangman " + getResources().getString(R.string.wins) + " " + wins + " " + getResources().getString(R.string.losses) + " " + losses);
    }

    //Fill displayWord-array with underscores and initiate hangmanWord
    protected void initiateGame(){

        if(words.size() == 0){
            finish();
            restartGameActivity();
        }
        else {
            textView = (TextView) findViewById(R.id.word);

            for(int i = 0; i < buttons.length; i++){
                buttons[i].setClickable(true);
            }

            textView.setText("");
            rightGuess = 0;
            wrongGuess = 0;

            hangmanWordIndex = (int) (Math.random() * words.size());
            hangmanWord = words.get(hangmanWordIndex);

            displayWord = new char[hangmanWord.length()];

            for (int i = 0; i < displayWord.length; i++) {
                displayWord[i] = '_';
                textView.append(String.valueOf(displayWord[i]) + "\t");

            }

            //Set initial drawing of hangman in ImageView

            FrameLayout frame = (FrameLayout) findViewById(R.id.hangman);
            hangman = new Hangman(this);
            frame.addView(hangman);
        }
    }

    //Restart GameActivity when all words have been played
    protected void restartGameActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);

        Toast.makeText(getApplicationContext(), getResources().getText(R.string.allWordsPlayed), Toast.LENGTH_SHORT).show();
    }
}
