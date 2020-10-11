package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Parts.DataBase.partsDb;
import com.example.myapplication.Phrase.DataBase.PhDatabase;
import com.example.myapplication.Vocabylary.DataBase.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import es.dmoral.toasty.Toasty;

public class Backup extends AppCompatActivity {

    public static final int PICKFILE_RESULT_CODE = 1;
    public static AtomicInteger number = new AtomicInteger(0);
    public boolean flag;
    public View v;
    RelativeLayout coordinatorLayout, mainlayout;
    TextView textView;
    private DatabaseHelper mDBHelper;
    private PhDatabase PhDatabase;
    private partsDb partsDb;
    private Button insert, backup, restore, restore1;
    Button ok;
    private EditText retext;
    private Uri fileUri;
    private String filePath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri = data.getData();
                    filePath = fileUri.getPath();
                    retext.setText(filePath);

                }

                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        try {

            setContentView(R.layout.activity_probackup);
            //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mDBHelper = new DatabaseHelper(this);
        PhDatabase = new PhDatabase(this);
        partsDb = new partsDb(this);

        backup = findViewById(R.id.backup);

        backup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                JSONArray jsonArray = new JSONArray();
                final Cursor cursor = mDBHelper.getAllData();

                // looping through all rows and adding to list
                if (cursor.getCount() != 0) {
                    // show message
                    while (cursor.moveToNext()) {

                        JSONObject word = new JSONObject();
                        try {
                            word.put("ID", Integer.parseInt(cursor.getString(0)));
                            word.put("WORD", cursor.getString(1));
                            word.put("MEANINGB", cursor.getString(2));
                            word.put("MEANINGE", cursor.getString(3));
                            word.put("SYNONYM", cursor.getString(4));
                            word.put("ANTONYM", cursor.getString(5));
                            System.out.println(word);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        jsonArray.put(word);
                    }

                    File root = android.os.Environment.getExternalStorageDirectory();
                    // http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
                    File dir = new File(root.getAbsolutePath() + "/wordstore");
                    dir.mkdirs();
                    File file = new File(dir, "backup_vocabulary.json");
                    try {
                        FileOutputStream f = new FileOutputStream(file);
                        PrintWriter pw = new PrintWriter(f);
                        pw.write(jsonArray.toString());
                        pw.flush();
                        pw.close();
                        f.close();
                        Toasty.success(getApplicationContext(), "Done.", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toasty.error(getApplicationContext(), "Opps.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toasty.error(getApplicationContext(), "Opps.", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toasty.error(getApplicationContext(), "Opps, Nothing found.", Toast.LENGTH_SHORT).show();
                }

                JSONArray jsonArray0 = new JSONArray();
                final Cursor cursor0 = mDBHelper.getAllData3();

                // looping through all rows and adding to list
                if (cursor0.getCount() != 0) {
                    // show message
                    while (cursor0.moveToNext()) {

                        JSONObject word = new JSONObject();
                        try {
                            word.put("ID", Integer.parseInt(cursor0.getString(0)));
                            word.put("WORD", cursor0.getString(1));
                            word.put("SENTENCE", cursor0.getString(2));
                            System.out.println(word);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        jsonArray0.put(word);
                    }

                    File root = android.os.Environment.getExternalStorageDirectory();
                    // http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
                    File dir = new File(root.getAbsolutePath() + "/wordstore");
                    dir.mkdirs();
                    File file = new File(dir, "backup_sentences.json");
                    try {
                        FileOutputStream f = new FileOutputStream(file);
                        PrintWriter pw = new PrintWriter(f);
                        pw.write(jsonArray0.toString());
                        pw.flush();
                        pw.close();
                        f.close();
                        Toasty.success(getApplicationContext(), "Done.", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toasty.error(getApplicationContext(), "Opps.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toasty.error(getApplicationContext(), "Opps.", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toasty.error(getApplicationContext(), "Opps, Nothing found.", Toast.LENGTH_SHORT).show();
                }


                JSONArray jsonArray1 = new JSONArray();
                final Cursor cursor1 = PhDatabase.getAllData();

                // looping through all rows and adding to list
                if (cursor1.getCount() != 0) {
                    // show message
                    while (cursor1.moveToNext()) {

                        JSONObject word = new JSONObject();
                        try {
                            word.put("ID", Integer.parseInt(cursor1.getString(0)));
                            word.put("PHRASE", cursor1.getString(1));
                            word.put("MEANINGB", cursor1.getString(2));
                            word.put("MEANINGE", cursor1.getString(3));
                            word.put("ORIGIN", cursor1.getString(4));
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        jsonArray1.put(word);
                    }

                    File root = android.os.Environment.getExternalStorageDirectory();
                    // http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
                    File dir = new File(root.getAbsolutePath() + "/wordstore");
                    dir.mkdirs();
                    File file = new File(dir, "backup_phrase.json");
                    try {
                        FileOutputStream f = new FileOutputStream(file);
                        PrintWriter pw = new PrintWriter(f);
                        pw.write(jsonArray1.toString());
                        pw.flush();
                        pw.close();
                        f.close();
                        //Toasty.success(context, "Done.", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        //Toasty.error(context, "Opps.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        //Toasty.error(context, "Opps.", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    // showMessage("Error", "Nothing found");
                }


                JSONArray jsonArray3 = new JSONArray();
                final Cursor cursor3 = PhDatabase.getAllData1();

                // looping through all rows and adding to list
                if (cursor3.getCount() != 0) {
                    // show message
                    while (cursor3.moveToNext()) {

                        JSONObject word = new JSONObject();
                        try {
                            word.put("ID", Integer.parseInt(cursor3.getString(0)));
                            word.put("PHRASE", cursor3.getString(1));
                            word.put("SENTENCE", cursor3.getString(2));
                            System.out.println(word);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        jsonArray3.put(word);
                    }

                    File root = android.os.Environment.getExternalStorageDirectory();
                    // http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
                    File dir = new File(root.getAbsolutePath() + "/wordstore");
                    dir.mkdirs();
                    File file = new File(dir, "backup_sentences_phrase.json");
                    try {
                        FileOutputStream f = new FileOutputStream(file);
                        PrintWriter pw = new PrintWriter(f);
                        pw.write(jsonArray3.toString());
                        pw.flush();
                        pw.close();
                        f.close();
                        Toasty.success(getApplicationContext(), "Done.", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toasty.error(getApplicationContext(), "Opps.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toasty.error(getApplicationContext(), "Opps.", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toasty.error(getApplicationContext(), "Opps, Nothing found.", Toast.LENGTH_SHORT).show();
                }


                JSONArray jsonArray2 = new JSONArray();
                final Cursor cursor2 = partsDb.getAllData();

                // looping through all rows and adding to list
                if (cursor2.getCount() != 0) {
                    // show message
                    while (cursor2.moveToNext()) {

                        JSONObject word = new JSONObject();


                        try {
                            word.put("ID", Integer.parseInt(cursor2.getString(0)));
                            word.put("WORD", cursor2.getString(1));
                            word.put("NOUNFORM", cursor2.getString(2));
                            word.put("VERBFORM", cursor2.getString(3));
                            word.put("ADJECTIVEFORM", cursor2.getString(4));
                            word.put("ADVERBFORM", cursor2.getString(5));
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }


                        jsonArray2.put(word);
                    }

                    File root = android.os.Environment.getExternalStorageDirectory();
                    // http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
                    File dir = new File(root.getAbsolutePath() + "/wordstore");
                    dir.mkdirs();
                    File file = new File(dir, "backup_parts_of_speech.json");
                    try {
                        FileOutputStream f = new FileOutputStream(file);
                        PrintWriter pw = new PrintWriter(f);
                        pw.write(jsonArray2.toString());
                        pw.flush();
                        pw.close();
                        f.close();
                        //Toasty.success(context, "Done.", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        //Toasty.error(context, "Opps.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        //Toasty.error(context, "Opps.", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    // showMessage("Error", "Nothing found");
                }


            }


        });

        restore = findViewById(R.id.restore);
        //restore1 = (Button) findViewById(R.id.restore1);
        ok = findViewById(R.id.click);
        retext = findViewById(R.id.retext);
        ok.setEnabled(false);
        restore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent chooseFile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);

                //  Toast.makeText(onNewIntent();)

                ok.setEnabled(true);


            }


        });


        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


              new MyTask().execute();



            }


        });
        mainlayout = findViewById(R.id.coordinate);
        coordinatorLayout = mainlayout.findViewById(R.id.coordinate_backup);
        SharedPreferences prefs = getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        RelativeLayout relativeLayout = findViewById(R.id.coordinate_j);
        boolean isDark = prefs.getBoolean("isDark", false);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        if (isDark) {
            retext.setBackgroundColor(Color.rgb(64, 64, 64));
            retext.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.card_background_dark));

            relativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.bbb));
            //c
            coordinatorLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.bbb));
            retext.setHintTextColor(Color.rgb(185, 185, 185));
            retext.setTextColor(Color.WHITE);
        } else {
            retext.setBackgroundColor(Color.WHITE);
            retext.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.card_background));

            relativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            //mainlayout.setBackgroundColor(Color.WHITE);
            coordinatorLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            retext.setHintTextColor(Color.rgb(64, 64, 64));
            retext.setTextColor(Color.BLACK);
        }
    }

    private int dothings() {
        String[] parts = fileUri.getLastPathSegment().split(":");
        String part2 = parts[1]; // 0

        {
            String retreivedDBPAth = Environment.getExternalStorageDirectory() + "/" + part2;
            File retrievedDB = new File(retreivedDBPAth);
            InputStream is = null;


            try {
                is = new FileInputStream(retrievedDB);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return 0;
            }
            InputStreamReader isr = new InputStreamReader(is);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            StringBuilder stringBuilder = new StringBuilder();
            JSONObject reader = new JSONObject();
            String json = null;
            try {
                //is = getActivity().getAssets().open("yourfilename.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                ex.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
            try {
                jsonArray = new JSONArray(json);
                mDBHelper.deleteAll();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = new JSONObject(jsonArray.get(i).toString());
                    if(jsonObject.has("MEANINGE") && jsonObject.has("MEANINGB") && jsonObject.has("SYNONYM") && jsonObject.has("ANTONYM")){
                        mDBHelper.insertData(jsonObject.get("WORD").toString(), jsonObject.get("MEANINGB").toString(),jsonObject.get("MEANINGE").toString(),jsonObject.get("SYNONYM").toString(),jsonObject.get("ANTONYM").toString());
                    }else{
                        mDBHelper.insertData(jsonObject.get("WORD").toString(), jsonObject.get("MEANINGB").toString(),"None","None","None");
                    }

                }
                // Toast.makeText(getApplicationContext(),"Done.",Toast.LENGTH_SHORT).show();
                return 1;


            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
    }

    private int dothings1() {
        String[] parts = fileUri.getLastPathSegment().split(":");
        String part2 = parts[1]; // 0


       {
            String retreivedDBPAth = Environment.getExternalStorageDirectory() + "/" +part2.replace("backup","backup_sentences");
            File retrievedDB = new File(retreivedDBPAth);
            InputStream is = null;


            try {
                is = new FileInputStream(retrievedDB);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
            InputStreamReader isr = new InputStreamReader(is);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            StringBuilder stringBuilder = new StringBuilder();
            JSONObject reader = new JSONObject();
            String json = null;
            try {
                //is = getActivity().getAssets().open("yourfilename.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                ex.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
            try {
                jsonArray = new JSONArray(json);
                mDBHelper.deleteAll1();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = new JSONObject(jsonArray.get(i).toString());

                    if(jsonObject.has("SENTENCE") && !jsonObject.get("SENTENCE").toString().trim().equals("")){
                        System.out.println(jsonObject.toString());
                        mDBHelper.insertData1(jsonObject.get("WORD").toString(), jsonObject.get("SENTENCE").toString());
                    }
                }
                // Toast.makeText(getApplicationContext(),"Done.",Toast.LENGTH_SHORT).show();
                return 1;


            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
    }

    private int dothings2() {
        String[] parts = fileUri.getLastPathSegment().split(":");
        String part2 = parts[1]; // 0


        {
            String retreivedDBPAth = Environment.getExternalStorageDirectory() + "/" +part2.replace("backup","backup_phrase");
            File retrievedDB = new File(retreivedDBPAth);
            InputStream is = null;


            try {
                is = new FileInputStream(retrievedDB);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
            InputStreamReader isr = new InputStreamReader(is);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            StringBuilder stringBuilder = new StringBuilder();
            JSONObject reader = new JSONObject();
            String json = null;
            try {
                //is = getActivity().getAssets().open("yourfilename.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                ex.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
            try {
                jsonArray = new JSONArray(json);
                PhDatabase.deleteAll();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = new JSONObject(jsonArray.get(i).toString());

                    if(jsonObject.has("MEANINGE") && jsonObject.has("MEANINGB") && jsonObject.has("ORIGIN")){
                        PhDatabase.insertData(jsonObject.get("PHRASE").toString(), jsonObject.get("MEANINGB").toString(),jsonObject.get("MEANINGE").toString(),jsonObject.get("ORIGIN").toString());
                    }else{
                        PhDatabase.insertData(jsonObject.get("PHRASE").toString(), jsonObject.get("MEANINGB").toString(),"None","None");
                    }
                }
                // Toast.makeText(getApplicationContext(),"Done.",Toast.LENGTH_SHORT).show();
                return 1;


            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
    }

    private int dothings0() {
        String[] parts = fileUri.getLastPathSegment().split(":");
        String part2 = parts[1]; // 0


        {
            String retreivedDBPAth = Environment.getExternalStorageDirectory() + "/" +part2.replace("backup","backup_sentences_phrase");
            File retrievedDB = new File(retreivedDBPAth);
            InputStream is = null;


            try {
                is = new FileInputStream(retrievedDB);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
            InputStreamReader isr = new InputStreamReader(is);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            StringBuilder stringBuilder = new StringBuilder();
            JSONObject reader = new JSONObject();
            String json = null;
            try {
                //is = getActivity().getAssets().open("yourfilename.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                ex.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
            try {
                jsonArray = new JSONArray(json);
                PhDatabase.deleteAll1();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = new JSONObject(jsonArray.get(i).toString());

                    if(jsonObject.has("SENTENCE") && !jsonObject.get("SENTENCE").toString().trim().equals("")){
                        System.out.println(jsonObject.toString());
                        PhDatabase.insertData1(jsonObject.get("PHRASE").toString(), jsonObject.get("SENTENCE").toString());
                    }
                }
                // Toast.makeText(getApplicationContext(),"Done.",Toast.LENGTH_SHORT).show();
                return 1;


            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
    }


    private int dothings3() {
        String[] parts = fileUri.getLastPathSegment().split(":");
        String part2 = parts[1]; // 0


        {
            String retreivedDBPAth = Environment.getExternalStorageDirectory() + "/" +part2.replace("backup","backup_parts_of_speech");
            File retrievedDB = new File(retreivedDBPAth);
            InputStream is = null;


            try {
                is = new FileInputStream(retrievedDB);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
            InputStreamReader isr = new InputStreamReader(is);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            StringBuilder stringBuilder = new StringBuilder();
            JSONObject reader = new JSONObject();
            String json = null;
            try {
                //is = getActivity().getAssets().open("yourfilename.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                ex.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
            try {
                jsonArray = new JSONArray(json);
                partsDb.deleteAll();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = new JSONObject(jsonArray.get(i).toString());
                    if(jsonObject.has("NOUNFORM") && jsonObject.has("VERBFORM") && jsonObject.has("ADJECTIVEFORM") && jsonObject.has("ADVERBFORM")){
                        partsDb.insertData(jsonObject.get("WORD").toString(), jsonObject.get("NOUNFORM").toString(),jsonObject.get("VERBFORM").toString(),jsonObject.get("ADJECTIVEFORM").toString(),jsonObject.get("ADVERBFORM").toString());
                    }else{
                        partsDb.insertData(jsonObject.get("WORD").toString(),"None","None","None","None");
                    }

                }
                // Toast.makeText(getApplicationContext(),"Done.",Toast.LENGTH_SHORT).show();
                return 1;


            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Opps.",Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
    }


    class MyTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
           try{
               dothings();
               dothings1();
               dothings2();
               dothings0();
               dothings3();
               return true;
           }catch (Exception e){
               return false;
           }


        }
        @Override
        protected void onPostExecute(Boolean b){

            if(b){
                Toasty.success(Backup.this,"Done",Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(Backup.this, MainActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
            }else{
                Toasty.success(Backup.this,"Failed.",Toast.LENGTH_LONG).show();
            }
        }
    }


}
