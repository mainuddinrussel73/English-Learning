package com.example.myapplication.Phrase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Phrase.DataBase.PhDatabase;
import com.example.myapplication.R;
import com.example.myapplication.Phrase.Phrase;
import com.example.myapplication.Vocabylary.add_page;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Add_phrase extends AppCompatActivity {


    TextInputEditText word;
    TextInputLayout w;
    TextInputEditText meaningb;
    TextInputLayout mb;
    TextInputEditText meaninge;
    TextInputLayout me;

    TextInputEditText origin;
    TextInputLayout or;

    Button done,load;
    private PhDatabase mDBHelper;
    String meaningbs="",meaninges="",origins="";
    List<String> lines = new ArrayList<>();

    boolean loadonline = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_phrase);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        word = findViewById(R.id.word1);
        w = findViewById(R.id.word);
        meaningb = findViewById(R.id.meaningB1);
        mb = findViewById(R.id.meaningB);
        meaninge = findViewById(R.id.meaningE1);
        me = findViewById(R.id.meaningE);
        origin = findViewById(R.id.origin1);
        or = findViewById(R.id.origin);

        mDBHelper = new PhDatabase(this);
        done = findViewById(R.id.done);
        load = findViewById(R.id.loadonline);
        final RelativeLayout additem = findViewById(R.id.add_item);
        RelativeLayout wordg = findViewById(R.id.wordsec);

        SharedPreferences prefs = getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("isDark", false);


        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        if (isDark) {
            additem.setBackgroundColor(Color.BLACK);
            word.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.edittextstyledark));
            word.setHintTextColor(Color.rgb(185, 185, 185));
            word.setTextColor(Color.WHITE);
            w.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.divider)));

            meaningb.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.edittextstyledark));
            meaningb.setHintTextColor(Color.rgb(185, 185, 185));
            meaningb.setTextColor(Color.WHITE);
            mb.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.divider)));

            meaninge.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.edittextstyledark));
            meaninge.setHintTextColor(Color.rgb(185, 185, 185));
            meaninge.setTextColor(Color.WHITE);
            me.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.divider)));

            origin.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.edittextstyledark));
            origin.setHintTextColor(Color.rgb(185, 185, 185));
            origin.setTextColor(Color.WHITE);
            or.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.divider)));

        } else {
            additem.setBackgroundColor(Color.WHITE);
            word.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.editextstyle));
            word.setHintTextColor(Color.BLACK);
            word.setTextColor(Color.BLACK);
            w.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkgray)));

            meaningb.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.editextstyle));
            meaningb.setHintTextColor(Color.BLACK);
            meaningb.setTextColor(Color.BLACK);
            mb.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkgray)));

            meaninge.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.editextstyle));
            meaninge.setHintTextColor(Color.BLACK);
            meaninge.setTextColor(Color.BLACK);
            me.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkgray)));

            origin.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.editextstyle));
            origin.setHintTextColor(Color.BLACK);
            origin.setTextColor(Color.BLACK);
            or.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkgray)));

        }


        word.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                // TODO Auto-generated method stub

                if (!arg1) {
                    PhDatabase mDBHelper = new PhDatabase(Add_phrase.this);
                    // SQLiteDatabase mDb;
                    String id = "-1";
                    //SQLiteDatabase db = mDBHelper.getWritableDatabase();
                    SQLiteDatabase db1 = mDBHelper.getReadableDatabase();
                    try {
                        Cursor re = db1.rawQuery("SELECT * FROM Phrase_table WHERE PHRASE = ?; ", new String[]{word.getText().toString().trim()});
                        if (re.moveToFirst()) {
                            do {
                                System.out.println(re.getString(0));
                                id = re.getString(0);
                            } while (re.moveToNext());
                        }

                        re.close();
                        // System.out.println(re.getString(0));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    if (!id.equals("-1")) {
                        Toasty.warning(Add_phrase.this, "Data already exist", Toasty.LENGTH_LONG).show();
                        done.setEnabled(false);
                    } else {
                        done.setEnabled(true);
                    }
                }
            }
        });

        done.setOnClickListener(new View.OnClickListener() {

            int i = 0;

            @Override
            public void onClick(View v) {
                if(!loadonline){
                    if (word.getText().toString().isEmpty() || word.getText().toString().trim().length() <= 0) {
                        Toasty.error(getApplicationContext(), "No input.", Toast.LENGTH_SHORT).show();
                    } else if (meaningb.getText().toString().isEmpty() || meaningb.getText().toString().trim().length() <= 0) {
                        Toasty.error(getApplicationContext(), "No input.", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean b = mDBHelper.insertData(word.getText().toString(), meaningb.getText().toString(),meaninge.getText().toString(),origin.getText().toString());
                        if (b == true) {
                            Toasty.success(getApplicationContext(), "Done.", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(v.getContext(), Phrase.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivityForResult(myIntent, 0);
                        } else {
                            Toasty.error(getApplicationContext(), "opps.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    loadonline = false;
                    for (String s:
                            lines) {
                        mDBHelper.insertData1(word.getText().toString(),s);
                    }

                    boolean b = mDBHelper.insertData(word.getText().toString(), meaningb.getText().toString(),meaninge.getText().toString(),origin.getText().toString());
                    if (b == true) {
                        Toasty.success(getApplicationContext(), "Done.", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(Add_phrase.this, Phrase.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivityForResult(myIntent, 0);
                    } else {
                        Toasty.error(getApplicationContext(), "opps.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        load.setOnClickListener(new View.OnClickListener() {

            int i = 0;

            @Override
            public void onClick(View v) {
                ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

                if (word.getText().toString().isEmpty() || word.getText().toString().trim().length() <= 0) {
                    Toasty.error(getApplicationContext(), "No input.", Toast.LENGTH_SHORT).show();
                }else {

                    if (netInfo != null) {
                        if (netInfo.isConnected()) {

                            new RetrieveFeedTask().execute("https://www.theidioms.com/".concat(word.getText().toString().trim().replaceAll(" ","-")));
                        }
                    }else{
                        Toasty.error(Add_phrase.this,"No internet connection.", Toast.LENGTH_LONG).show();
                    }


                }


            }
        });
    }
    private void setUpperHintColor(int color) {
        try {
            Field field = w.getClass().getDeclaredField("mFocusedTextColor");
            field.setAccessible(true);
            int[][] states = new int[][]{
                    new int[]{}
            };
            int[] colors = new int[]{
                    color
            };
            ColorStateList myList = new ColorStateList(states, colors);
            field.set(w, myList);

            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            fDefaultTextColor.setAccessible(true);
            fDefaultTextColor.set(w, myList);

            Method method = w.getClass().getDeclaredMethod("updateLabelState", boolean.class);
            method.setAccessible(true);
            method.invoke(w, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void setUpperHintColor1(int color) {
        try {
            Field field = mb.getClass().getDeclaredField("mFocusedTextColor");
            field.setAccessible(true);
            int[][] states = new int[][]{
                    new int[]{}
            };
            int[] colors = new int[]{
                    color
            };
            ColorStateList myList = new ColorStateList(states, colors);
            field.set(mb, myList);

            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            fDefaultTextColor.setAccessible(true);
            fDefaultTextColor.set(mb, myList);

            Method method = mb.getClass().getDeclaredMethod("updateLabelState", boolean.class);
            method.setAccessible(true);
            method.invoke(mb, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //m.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, color)));
    }
    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        private Exception exception;


        protected Void doInBackground(String... urls) {
            try {
                String url = (urls[0]);


                Document doc =  Jsoup.connect(url).get();
                Element table = doc.select("div[class=article]").get(0); //select the first table.

                Element rows = table.select("ul").first();

                for (int i = 0; i <rows.select("li").size() ; i++) {
                    System.out.println(rows.select("li").get(i).wholeText());
                    meaninges += (rows.select("li").get(i).wholeText());

                }
                Element rows1 = table.select("ol").first();
                for (int i = 0; i <rows1.select("li").size() ; i++) {
                    System.out.println(rows1.select("li").get(i).wholeText());
                    lines.add(rows1.select("li").get(i).wholeText());
                }

                Element rows2 = table.select("p").get(1);
                System.out.println(rows2.wholeText());
                origins = rows2.wholeText();


            } catch (Exception e) {
                this.exception = e;
                System.out.println(e.getMessage());

                return null;
            }
            return null;
        }

        protected void onPostExecute(Void doc) {

            // In cas
            // e of any IO errors, we want the messages written to the console

            loadonline = true;
            meaningb.setText(meaningbs);
            meaninge.setText(meaninges);
            origin.setText(origins);
            Toasty.success(Add_phrase.this,"Done.",Toast.LENGTH_LONG).show();
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
}