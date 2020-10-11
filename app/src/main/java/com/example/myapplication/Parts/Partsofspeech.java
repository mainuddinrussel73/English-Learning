package com.example.myapplication.Parts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;


import com.example.myapplication.Parts.Adapter.Parts_Adapter;
import com.example.myapplication.Parts.Data.parts;
import com.example.myapplication.Parts.DataBase.partsDb;
import com.example.myapplication.Phrase.Add_phrase;
import com.example.myapplication.R;
import com.example.myapplication.WalkThrough;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.dmoral.toasty.Toasty;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

public class Partsofspeech  extends AppCompatActivity {

    public static boolean isDark;
    public static SharedPreferences prefs;
    public static int size = 0;
    public static List<parts> contactList = new ArrayList<parts>();
    public static SharedPreferences sizee;
    Parts_Adapter adapter;
    ListView list;
    Button sort;
    SearchView searchView;
    SearchView searchView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_parts);


        prefs = getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);


        isDark = prefs.getBoolean("isDark", false);





        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RelativeLayout homelay = findViewById(R.id.homelayoyt);
        RelativeLayout header = findViewById(R.id.header);
        TextView t1,t2,t3,t4;
        t1 = findViewById(R.id.one);
        t2 = findViewById(R.id.two);
        t3 = findViewById(R.id.three);
        t4 = findViewById(R.id.four);

        if(isDark){
            homelay.setBackgroundColor(Color.BLACK);
            toolbar.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_style));
            header.setBackground(ContextCompat.getDrawable(this,R.drawable.background_card_dark));
            t1.setTextColor(Color.WHITE);
            t2.setTextColor(Color.WHITE);
            t3.setTextColor(Color.WHITE);
            t4.setTextColor(Color.WHITE);

        }else{
            homelay.setBackgroundColor(Color.WHITE);
            toolbar.setBackground(ContextCompat.getDrawable(this,R.drawable.btn_style));
            header.setBackground(ContextCompat.getDrawable(this,R.drawable.background_card));
            t1.setTextColor(Color.BLACK);
            t2.setTextColor(Color.BLACK);
            t3.setTextColor(Color.BLACK);
            t4.setTextColor(Color.BLACK);
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Add_parts.class);
                //String s = view.findViewById(R.id.subtitle).toString();
                //String s = (String) parent.getI;
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
            }
        });

        partsDb mDBHelper = new partsDb(this);


        contactList.clear();

        int i =0;

        final Cursor cursor = mDBHelper.getAllData();

        // looping through all rows and adding to list
        if (cursor.getCount() != 0) {
            // show message
            while (cursor.moveToNext()) {

                parts word = new parts();

                if(!cursor.getString(2).isEmpty() && !cursor.getString(3).isEmpty()
                        && !cursor.getString(4).isEmpty() && !cursor.getString(5).isEmpty()){
                    word.setID(i);
                    word.setWord(cursor.getString(1));
                    word.setNounform(cursor.getString(2));
                    word.setVerbform(cursor.getString(3));
                    word.setAdjectiveform(cursor.getString(4));
                    word.setAdverbform(cursor.getString(5));
                }
                else if(cursor.getString(2).isEmpty()){

                    word.setID(i);
                    word.setWord(cursor.getString(1));
                    word.setNounform("None");
                    word.setVerbform(cursor.getString(3));
                    word.setAdjectiveform(cursor.getString(4));
                    word.setAdverbform(cursor.getString(5));

                }
                else if(cursor.getString(3).isEmpty()){

                    word.setID(i);
                    word.setWord(cursor.getString(1));
                    word.setNounform(cursor.getString(2));
                    word.setVerbform("None");
                    word.setAdjectiveform(cursor.getString(4));
                    word.setAdverbform(cursor.getString(5));

                }
                else if(cursor.getString(4).isEmpty()){

                    word.setID(i);
                    word.setWord(cursor.getString(1));
                    word.setNounform(cursor.getString(2));
                    word.setVerbform(cursor.getString(3));
                    word.setAdjectiveform("None");
                    word.setAdverbform(cursor.getString(5));

                }
                else if(cursor.getString(5).isEmpty()){

                    word.setID(i);
                    word.setWord(cursor.getString(1));
                    word.setNounform(cursor.getString(2));
                    word.setVerbform(cursor.getString(3));
                    word.setAdjectiveform(cursor.getString(4));
                    word.setVerbform("None");

                }




                contactList.add(word);
               // System.out.println(word);

                // maintitle.add(word.WORD);
                // subtitle.add(word.MEANING);

                i++;
            }


            // size = contactList.size();
            adapter = new Parts_Adapter(this);
            list = findViewById(R.id.list);

            String type = prefs.getString("sort", "asc");
            if (type.equals("asc")) {
                Collections.sort(contactList);
            } else if (type.equals("des")) {
                Collections.sort(contactList, Collections.reverseOrder());
            } else if (type.equals("alp")) {
                Collections.sort(contactList,
                        new Comparator<parts>() {
                            public int compare(parts f1, parts f2) {
                                return f1.getWord().compareTo(f2.getWord());
                            }
                        });
            }

            //list.setFastScrollEnabled(true);
            list.setAdapter(adapter);

            //textView = findViewById(R.id.board);
            //final Intent intent = getIntent();
            //bestscore = (intent.getIntExtra("sb",0)> bestscore)?intent.getIntExtra("sb",0) :bestscore;
//            textView.setText("Total Words : \n"+String.valueOf(contactList.size())+"\nCurrent score : \n"+String.valueOf(bestscore));



        } else {

            Toasty.info(Partsofspeech.this,"Error Nothing found");
        }

        size = contactList.size();

        sizee = this.getSharedPreferences("ok", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sizee.edit();
        editor.putInt("size", size);
        editor.commit();



        System.out.println(Partsofspeech.sizee.getInt("size", 0));

        sort = findViewById(R.id.sort);
        sort.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                //PopupMenu popup = new PopupMenu(MainActivity.this, sort);
                //Inflating the Popup using xml file
                Context wrapper = new ContextThemeWrapper(Partsofspeech.this, R.style.YOURSTYLE1);
                if (isDark) {
                    wrapper = new ContextThemeWrapper(Partsofspeech.this, R.style.YOURSTYLE);

                } else {
                    wrapper = new ContextThemeWrapper(Partsofspeech.this, R.style.YOURSTYLE1);
                }

                PopupMenu popup = new PopupMenu(wrapper, sort);
                popup.getMenuInflater().inflate(R.menu.pop_up_menu, popup.getMenu());


                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Ascending")) {
                            Collections.sort(contactList);
                            //  adapter = new MyListAdapter(getParent());
                            list = findViewById(R.id.list);
                            list.setAdapter(adapter);

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("sort", "asc");
                            editor.commit();

                        } else if (item.getTitle().equals("Descending")) {
                            Collections.sort(contactList, Collections.reverseOrder());
                            //adapter = new MyListAdapter(getParent());
                            list = findViewById(R.id.list);
                            list.setAdapter(adapter);

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("sort", "des");
                            editor.commit();

                        } else if (item.getTitle().equals("Alphabetically")) {
                            Collections.sort(contactList,
                                    new Comparator<parts>() {
                                        public int compare(parts f1, parts f2) {
                                            return f1.getWord().compareTo(f2.getWord());
                                        }
                                    });
                            list = findViewById(R.id.list);
                            list.setAdapter(adapter);

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("sort", "alp");
                            editor.commit();

                        } else {

                        }

                        Toasty.info(Partsofspeech.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

        RelativeLayout linearLayout1 = findViewById(R.id.listview);

        if (isDark) {
            linearLayout1.setBackgroundColor(Color.BLACK);
            if (contactList.size() != 0) list.setAdapter(adapter);

        } else {
            linearLayout1.setBackgroundColor(Color.WHITE);
            if (contactList.size() != 0) list.setAdapter(adapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.other_menu, menu);


        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView v = searchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.ic_search);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }

        });


        if (WalkThrough.isfirst) {
            WalkThrough.isfirst = false;
            new MaterialTapTargetSequence()
                    .addPrompt(new MaterialTapTargetPrompt.Builder(Partsofspeech.this)
                            .setTarget(findViewById(R.id.fab))
                            .setPrimaryText("Step 1")
                            .setSecondaryText("Add Word")
                            .setSecondaryTextSize(R.dimen.helper_text_size)
                            .setFocalPadding(R.dimen.dp40)
                            .create(), 4000)
                    .addPrompt(new MaterialTapTargetPrompt.Builder(this)
                            .setPrimaryText("Step 2")
                            .setSecondaryText("Sort Words")
                            .setSecondaryTextSize(R.dimen.helper_text_size)
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setMaxTextWidth(R.dimen.dp40)
                            .setIcon(R.drawable.ic_sort_by_alpha)
                            .setTarget(R.id.sort)
                            .create(), 4000)
                    .addPrompt(new MaterialTapTargetPrompt.Builder(this)
                            .setPrimaryText("Step 3")
                            .setSecondaryText("Search by word")
                            .setSecondaryTextSize(R.dimen.helper_text_size)
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setMaxTextWidth(R.dimen.dp40)
                            .setIcon(R.drawable.ic_searchw)
                            .setTarget(searchView)
                            .create(), 4000)
                    .addPrompt(new MaterialTapTargetPrompt.Builder(Partsofspeech.this)
                            .setTarget(findViewById(R.id.app_bar_search1))
                            .setPrimaryText("Step 4")
                            .setSecondaryText("Search by meaning")
                            .setSecondaryTextSize(R.dimen.helper_text_size)
                            .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                            .setFocalPadding(R.dimen.dp40)
                            .setTarget(searchView1)
                            .setIcon(R.drawable.ic_searchm)
                            .create(), 4000)

                    .show();
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
        }
        return super.onCreateOptionsMenu(menu);
    }
}
