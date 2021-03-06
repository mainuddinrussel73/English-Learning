package com.example.myapplication.Quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Vocabylary.Data.word;
import com.example.myapplication.Vocabylary.DataBase.DatabaseHelper;

import java.util.Locale;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

import static com.example.myapplication.Quiz.Quiz_confirm.contactList;
import static com.example.myapplication.Quiz.quiz_result.wordBuck;

public class quiz_spelling extends AppCompatActivity {

    public static TextView scoress;
    public  static  int score2 = 0;
    RecyclerView listView;
    ExtendedEditText word;
    RelativeLayout texth;
    TextView ttt;
    Button d,n;
    TextView scores;
    word curr = null;
    ImageButton listed;
    int pos = 0;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private TextToSpeech textToSpeech;

    public static String replaceCharAt(String s, int pos, char c) {
        return s.substring(0,pos) + c + s.substring(pos+1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quiz_spelling);



        d = findViewById(R.id.btnDisplay);
        n = findViewById(R.id.nextquiz);
        word = findViewById(R.id.word);

        texth = findViewById(R.id.texth);
        ttt = findViewById(R.id.wordh);
        scores = findViewById(R.id.scores);

        pos =getRandomNumberInRange(0, Quiz_confirm.contactList.size()-1);

        System.out.println(pos);

        while (curr==null) {
            curr = contactList.get(pos);
        }


        listView = findViewById(R.id.learnlist);

        int i = curr.getWORD().length();
        String s = curr.getWORD().trim();
        System.out.println(s);

        for (int j = 0; j <i ; j++) {
            int y = getRandomNumberInRange(0,i-1);
            System.out.println(y);
            try{
                s = replaceCharAt(s,y,'_');}
            catch (Exception e) {

            }
        }

        ttt.setText(s);

        spell_adapter spell_adapter = new spell_adapter(curr);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setAdapter(spell_adapter);


        SharedPreferences prefs = getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("isDark", false);
        RelativeLayout relativeLayout =findViewById(R.id.backup);
        if(isDark) {
            word.setHintTextColor(Color.rgb(185, 185, 185));
            word.setTextColor(Color.WHITE);
            word.setBackground(ContextCompat.getDrawable(this,R.drawable.card_background_dark));
            texth.setBackground(ContextCompat.getDrawable(this,R.drawable.card_background_dark));

            relativeLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.bbb));
            listView.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.card_background_dark));
        }else {

            word.setHintTextColor(Color.BLACK);
            word.setTextColor(Color.BLACK);
            word.setBackground(ContextCompat.getDrawable(this,R.drawable.card_background));
            texth.setBackground(ContextCompat.getDrawable(this,R.drawable.card_background));
            relativeLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            listView.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.card_background));
        }

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.UK);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    // Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textToSpeech.setSpeechRate(0.5f);

        Button button2 = findViewById(R.id.extpage);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);

                if (score2 > prefs.getInt("highscore2", 0)) {

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("highscore1", score2);
                    editor.commit();

                }

                Intent myIntent = new Intent(view.getContext(), quiz_result.class);

                myIntent.putExtra("score2", score2);

                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });

    }


    public void onDisplay(View view) {
        if(word.getText().toString().trim().toLowerCase().equals(curr.getWORD().trim().toLowerCase())){
            score2++;
            // mCheckView.check();
            System.out.println("correct");
            Toasty.success(this,"Correct.", Toast.LENGTH_LONG).show();
            ttt.setText(curr.getWORD());
            ttt.setTextColor(Color.GREEN);
            scores.setText(Integer.toString(score2));


        }else{
            score2--;
            // mCheckView.uncheck();
            System.out.println("incorrect");
            Toasty.error(this,"Incorrect.", Toast.LENGTH_LONG).show();

            ttt.setText(curr.getWORD());
            ttt.setTextColor(Color.RED);
            wordBuck.add(curr.getWORD());
            scores.setText(Integer.toString(score2));
        }


    }


    public void onNxt(View view) {

        curr = contactList.get(getRandomNumberInRange(0, Quiz_confirm.contactList.size()-1));

        try {
            int i = curr.getWORD().length();

            String s = curr.getWORD().trim();

            for (int j = 0; j < i; j++) {
                int y = getRandomNumberInRange(0, i - 1);
                try {
                    s = replaceCharAt(s, y, '_');
                } catch (Exception e) {

                }
            }

            word.setText("");


            ttt.setText(s);

            spell_adapter spell_adapter = new spell_adapter(curr);
            listView.setAdapter(spell_adapter);
        }catch ( Exception e){

        }

    }

    public void onListen(View view) {
        int speechStatus = textToSpeech.speak(curr.getWORD(), TextToSpeech.QUEUE_FLUSH, null);

        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }

    }

    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}