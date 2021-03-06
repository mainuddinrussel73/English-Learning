package com.example.myapplication.Quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.Vocabylary.DataBase.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;

import static com.example.myapplication.Quiz.quiz_result.wordBuck;

public class quiz_page extends AppCompatActivity {

    public static int score = 0;
    public static int correct = 0;
    public static int wrong = 0;
    public static int ignored = 0;
    public boolean isPause = false;
    public long total = 30000;
    public long temp = 0;
    SharedPreferences gamePrefs;
    RelativeLayout scrollView;
    CountDownTimer cTimer = null;
    String word,meaning;
    TextView timer;
    int size;
    int currnum = Quiz_confirm.questioncount;
    int max = Quiz_confirm.questioncount;
    CardView c1,c2,c3,c4;
    Button t1,t2,t3,t4;
    private Button btnDisplay, nxt, previousquiz;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    List<Button> optionn =  new ArrayList<>();
    private LinearLayout radioSexGroup;
    private LinearLayout radioSexButton;
    private TextView quess,textView, scoress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quiz_list);


        final Intent intent = getIntent();
        score = intent.getIntExtra("s", 0);
        scoress = findViewById(R.id.scores);
        scoress.setText("Current score : " + score);
        btnDisplay = findViewById(R.id.btnDisplay);
        btnDisplay.setEnabled(true);
        quess = findViewById(R.id.quiz_question);
        textView = findViewById(R.id.quiz_detail);
        textView.setMovementMethod(new ScrollingMovementMethod());

        scrollView = findViewById(R.id.scrollButtons);


        size = intent.getIntExtra("size", 0);


        double randomDouble = Math.random();
        randomDouble = randomDouble * Quiz_confirm.contactList.size() + 0;
        int randomInt = (int) randomDouble;

        word = Quiz_confirm.contactList.get(randomInt).getWORD();
        meaning = Quiz_confirm.contactList.get(randomInt).getMEANINGB();

        quess.setText("Question : " + (currnum) +" : ");
        textView.setText("What is the meaning of the word : " + word + "?");

        LinearLayout radioGroup = scrollView.findViewById(R.id.radioGroup);

        c1  = findViewById(R.id.radio0);
        c2  = findViewById(R.id.radio1);
        c3  = findViewById(R.id.radio2);
        c4  = findViewById(R.id.radio3);


        List<Integer> c  = new ArrayList<>();
        for (int i = 0; i <4 ; i++) {
            double randomDouble2 = Math.random();
            randomDouble2 = randomDouble2 * Quiz_confirm.contactList.size() + 0;
            int randomInt2 = (int) randomDouble2;
            if(!c.contains(randomInt2)){
                c.add(randomInt2);
            }
        }
        t1 =  findViewById(R.id.op1);
        t1.setText(Quiz_confirm.contactList.get(c.get(0)).getMEANINGB());
        t2 =  findViewById(R.id.op2);
        t2.setText(Quiz_confirm.contactList.get(c.get(1)).getMEANINGB());
        t3 =  findViewById(R.id.op3);
        t3.setText(Quiz_confirm.contactList.get(c.get(2)).getMEANINGB());
        t4 =  findViewById(R.id.op4);
        t4.setText(Quiz_confirm.contactList.get(c.get(3)).getMEANINGB());

        optionn.add(t1);
        optionn.add(t2);
        optionn.add(t3);
        optionn.add(t4);

        double randomDouble1 = Math.random();
        randomDouble1 = randomDouble1 * 4 + 0;
        int randomInt1 = (int) randomDouble1;
        optionn.get(randomInt1).setText(meaning);

        List<String> words = new ArrayList<>();

        for (int i = 0; i <4 ; i++) {
            double randomDouble2 = Math.random();
            randomDouble2 = randomDouble2 * Quiz_confirm.contactList.size() + 0;
            int randomInt2 = (int) randomDouble2;
            words.add(Quiz_confirm.contactList.get(randomInt2).getMEANINGB());
        }





        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicked");
                addListenerOnButton(t1);
                c1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.uou)));

            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListenerOnButton(t2);
                c2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.uou)));
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListenerOnButton(t3);
                c3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.uou)));
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListenerOnButton(t4);
                c4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.uou)));
            }
        });




        nxt = findViewById(R.id.nextquiz);

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordBuck.add(word);
                System.out.println("sssss" + wordBuck.toString());
                words.clear();
                cancelTimer();
                ignored++;
                score--;
                if (score < 0) score = 0;


                Intent myIntent = new Intent(view.getContext(), quiz_page.class);
                //String s = view.findViewById(R.id.subtitle).toString();
                //String s = (String) parent.getI;
                myIntent.putExtra("s", score);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
                check(view.getContext());


            }
        });


        RelativeLayout relativeLayout = findViewById(R.id.relative_quiz);
        CardView linearLayout = findViewById(R.id.question);

        LinearLayout linearLayout1 = findViewById(R.id.radioGroup);
        timer = findViewById(R.id.timer);

        SharedPreferences prefs = getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("isDark", false);
        RelativeLayout scrollView = findViewById(R.id.scrollButtons);
        if (isDark) {

            textView.setTextColor(Color.WHITE);
            quess.setTextColor(Color.WHITE);
            linearLayout.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.soft_dark)));
            relativeLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.bbb));
            linearLayout1.setBackgroundColor(ContextCompat.getColor(this,R.color.bbb));
            scrollView.setBackgroundColor(ContextCompat.getColor(this, R.color.bbb));
            c1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.soft_dark)));
            c2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.soft_dark)));
            c3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.soft_dark)));
            c4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.soft_dark)));
            t1.setTextColor(Color.WHITE);
            t2.setTextColor(Color.WHITE);
            t3.setTextColor(Color.WHITE);
            t4.setTextColor(Color.WHITE);
        } else {
            quess.setTextColor(Color.BLACK);
            linearLayout.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.soft_light)));
            textView.setTextColor(Color.BLACK);
            relativeLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            linearLayout1.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            scrollView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

            c1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.soft_light)));
            c2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.soft_light)));
            c3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.soft_light)));
            c4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.soft_light)));
            t1.setTextColor(Color.BLACK);
            t2.setTextColor(Color.BLACK);
            t3.setTextColor(Color.BLACK);
            t4.setTextColor(Color.BLACK);
        }

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(quiz_page.this,"Click an option.", Toast.LENGTH_LONG).show();
            }
        });
        Button button2 = findViewById(R.id.endquiz);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);

                if (score > prefs.getInt("highscore", 0)) {

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("highscore", score);
                    editor.commit();

                }

                cancelTimer();


                Intent myIntent = new Intent(view.getContext(), quiz_result.class);

                myIntent.putExtra("score", score);

                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });


        startTimer();


    }

    void startTimer() {
        cTimer = new CountDownTimer(total, 1000) {
            public void onTick(long millisUntilFinished) {
                if (!isPause) {
                    total = millisUntilFinished;
                    timer.setText("Time remaining: " + millisUntilFinished / 1000);
                } else {
                    total = temp;
                    cancelTimer();
                    startTimer();
                }
            }

            public void onFinish() {
                timer.setText("Finished!");

                ignored++;
                score--;
                if (score < 0) score = 0;
                wordBuck.add(word);


                Intent myIntent = new Intent(quiz_page.this, quiz_page.class);
                //String s = view.findViewById(R.id.subtitle).toString();
                //String s = (String) parent.getI;
                myIntent.putExtra("s", score);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
                cancelTimer();


                check(quiz_page.this);
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }


    @Override
    protected void onStart() {
        super.onStart();

        getWindow().setBackgroundDrawableResource(android.R.color.white);
    }

    public void addListenerOnButton(Button b) {

        radioSexGroup = findViewById(R.id.radioGroup);

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                btnDisplay.setEnabled(true);


                if (meaning.equals(b.getText())) {
                    Toasty.success(quiz_page.this,
                            "congrats", Toast.LENGTH_SHORT).show();



                    if(b.getId()==t1.getId()){
                        c1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.av_green)));
                    }
                    if(b.getId()==t2.getId()){
                        c2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.av_green)));
                    }
                    if(b.getId()==t3.getId()){
                        c3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.av_green)));
                    }
                    if(b.getId()==t4.getId()){
                        c4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.av_green)));
                    }
                    score++;
                    btnDisplay.setEnabled(false);
                    correct++;
                    scoress.setText("Current score : " + score);
                    Intent myIntent = new Intent(quiz_page.this, quiz_page.class);
                    myIntent.putExtra("s", score);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(myIntent, 0);
                    cancelTimer();
                    check(v.getContext());
                } else {
                    Toasty.error(quiz_page.this,
                            "Alas!", Toast.LENGTH_SHORT).show();
                    wordBuck.add(word);
                    if(b.getId()==t1.getId()){
                        c1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.av_red)));
                    }
                    if(b.getId()==t2.getId()){
                        c2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.av_red)));
                    }
                    if(b.getId()==t3.getId()){
                        c3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.av_red)));
                    }
                    if(b.getId()==t4.getId()){
                        c4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.av_red)));
                    }
                    if(t1.getText().toString().trim().equals(meaning)){
                        c1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.av_green)));
                    }
                    if(t2.getText().toString().trim().equals(meaning)){
                        c2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.av_green)));
                    }
                    if(t3.getText().toString().trim().equals(meaning)){
                        c3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.av_green)));
                    }
                    if(t4.getText().toString().trim().equals(meaning)){
                        c4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(quiz_page.this,R.color.av_green)));
                    }


                    score--;
                    wrong++;
                    if (score < 0) score = 0;
                    scoress.setText("Current score : " + score);
                    btnDisplay.setEnabled(false);
                    Intent myIntent = new Intent(quiz_page.this, quiz_page.class);
                    myIntent.putExtra("s", score);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(myIntent, 0);
                    cancelTimer();
                    check(v.getContext());

                }

                // find the radiobutton by returned id


            }

        });


    }




    @Override
    public void onBackPressed() {

    }

    @Override
    public void onPause() {
        super.onPause();
        isPause = true;
        temp = total;


    }

    @Override
    public void onResume() {

        super.onResume();
        isPause = false;
        temp = 0;
    }

    public void check(Context context) {
        Quiz_confirm.questioncount--;
        currnum = Quiz_confirm.questioncount;
        if (Quiz_confirm.questioncount <= 0) {
            SharedPreferences prefs = getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);

            if (score > prefs.getInt("highscore", 0)) {

                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("highscore", score);
                editor.commit();

            }

            cancelTimer();


            Intent myIntent1 = new Intent(context, quiz_result.class);

            myIntent1.putExtra("score", score);

            myIntent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(myIntent1, 0);
            finish();
        }
    }
}