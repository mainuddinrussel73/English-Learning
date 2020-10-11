package com.example.myapplication.Parts;

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

import com.example.myapplication.Parts.DataBase.partsDb;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import es.dmoral.toasty.Toasty;

public class Add_parts extends AppCompatActivity {


    TextInputEditText word;
    TextInputLayout w;
    TextInputEditText noun;
    TextInputLayout n;
    TextInputEditText verb;
    TextInputLayout v;
    TextInputEditText adj;
    TextInputLayout aj;
    TextInputEditText adv;
    TextInputLayout av;


    Button done,load;
    private partsDb mDBHelper;
    String meaningbs="",meaninges="",ns="",vs="",ajs = "", advs = "";
    String[] lines = new String[10000];

    boolean loadonline = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_parts);

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
        noun = findViewById(R.id.noun1);
        n = findViewById(R.id.noun);
        verb = findViewById(R.id.verb1);
        v = findViewById(R.id.verb);
        adj = findViewById(R.id.adjective1);
        aj = findViewById(R.id.adjective);
        adv = findViewById(R.id.adverb1);
        av = findViewById(R.id.adverb);


        mDBHelper = new partsDb(this);
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

            noun.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.edittextstyledark));
            noun.setHintTextColor(Color.rgb(185, 185, 185));
            noun.setTextColor(Color.WHITE);
            n.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.divider)));

            verb.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.edittextstyledark));
            verb.setHintTextColor(Color.rgb(185, 185, 185));
            verb.setTextColor(Color.WHITE);
            v.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.divider)));


            adj.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.edittextstyledark));
            adj.setHintTextColor(Color.rgb(185, 185, 185));
            adj.setTextColor(Color.WHITE);
            aj.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.divider)));

            adv.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.edittextstyledark));
            adv.setHintTextColor(Color.rgb(185, 185, 185));
            adv.setTextColor(Color.WHITE);
            av.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.divider)));


        } else {
            additem.setBackgroundColor(Color.WHITE);
            word.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.editextstyle));
            word.setHintTextColor(Color.BLACK);
            word.setTextColor(Color.BLACK);
            w.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkgray)));

            noun.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.editextstyle));
            noun.setHintTextColor(Color.BLACK);
            noun.setTextColor(Color.BLACK);
            n.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkgray)));

            verb.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.editextstyle));
            verb.setHintTextColor(Color.BLACK);
            verb.setTextColor(Color.BLACK);
            v.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkgray)));


            adj.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.editextstyle));
            adj.setHintTextColor(Color.BLACK);
            adj.setTextColor(Color.BLACK);
            aj.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkgray)));

            adv.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.editextstyle));
            adv.setHintTextColor(Color.BLACK);
            adv.setTextColor(Color.BLACK);
            av.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkgray)));

        }


        word.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                // TODO Auto-generated method stub

                if (!arg1) {
                    partsDb mDBHelper = new partsDb(Add_parts.this);
                    // SQLiteDatabase mDb;
                    String id = "-1";
                    //SQLiteDatabase db = mDBHelper.getWritableDatabase();
                    SQLiteDatabase db1 = mDBHelper.getReadableDatabase();
                    try {
                        Cursor re = db1.rawQuery("SELECT * FROM Parts_table WHERE WORD = ?; ", new String[]{word.getText().toString().trim()});
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
                        Toasty.warning(Add_parts.this, "Data already exist", Toasty.LENGTH_LONG).show();
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
                    }else {
                        boolean b = mDBHelper.insertData(word.getText().toString(), noun.getText().toString(),verb.getText().toString(),adj.getText().toString(),adv.getText().toString());
                        if (b == true) {
                            Toasty.success(getApplicationContext(), "Done.", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(v.getContext(), Partsofspeech.class);
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
                        //mDBHelper.insertData1(word.getText().toString(),s);
                    }

                   // boolean b = mDBHelper.insertData(word.getText().toString(), meaningb.getText().toString(),meaninge.getText().toString());
                  /*  if (b == true) {
                        Toasty.success(getApplicationContext(), "Done.", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(Add_parts.this, Partsofspeech.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivityForResult(myIntent, 0);
                    } else {
                        Toasty.error(getApplicationContext(), "opps.", Toast.LENGTH_SHORT).show();
                    }*/
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
                            new Add_parts.RetrieveFeedTask().execute("http://dictionary.studysite.org/Bengali-meaning-of-".concat(word.getText().toString().trim()));
                        }
                    }else{
                        Toasty.error(Add_parts.this,"No internet connection.", Toast.LENGTH_LONG).show();
                    }


                }


            }
        });
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        private Exception exception;


        protected Void doInBackground(String... urls) {
            try {
                String url = (urls[0]);


                Document doc =  Jsoup.connect(url).get();
                Element table = doc.select("table").get(0); //select the first table.
                Elements rows = table.select("tr");
                System.out.println(rows.size());
                if(rows.size()==8){
                    meaningbs = rows.get(1).select("td").get(1).wholeText();
                    meaninges =  rows.get(2).select("td").get(1).wholeText();
                    System.out.println("Pics : " + rows.get(3).select("td").get(0).select("img").first().attr("abs:src"));
                    rows.get(4).select("td").select("br").after("\n");
                    lines = rows.get(4).select("td").get(1).wholeText().split("\\r?\\n");
                    ns = rows.get(5).select("td").get(1).wholeText();
                    vs = rows.get(6).select("td").get(1).wholeText();
                }else if(rows.size()==7){
                    meaningbs = rows.get(1).select("td").get(1).wholeText();
                    meaninges =  rows.get(2).select("td").get(1).wholeText();
                    rows.get(3).select("td").select("br").after("\n");
                    lines = rows.get(3).select("td").get(1).wholeText().split("\\r?\\n");
                    ns = rows.get(4).select("td").get(1).wholeText();
                    vs = rows.get(5).select("td").get(1).wholeText();
                }
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
            noun.setText(meaningbs);
            verb.setText(meaninges);
            Toasty.success(Add_parts.this,"Done.",Toast.LENGTH_LONG).show();
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
}