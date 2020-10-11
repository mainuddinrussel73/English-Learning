package com.example.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.example.myapplication.Parts.Partsofspeech;
import com.example.myapplication.Phrase.Data.phrase;
import com.example.myapplication.Phrase.DataBase.PhDatabase;
import com.example.myapplication.Phrase.Phrase;
import com.example.myapplication.Phrase.PhraseDetail;
import com.example.myapplication.Quiz.Quiz_confirm;
import com.example.myapplication.Settings.Settings;
import com.example.myapplication.Vocabylary.DataBase.DatabaseHelper;
import com.example.myapplication.Vocabylary.Vocabulary;
import com.example.myapplication.Vocabylary.Data.word;
import com.example.myapplication.Vocabylary.WordDetail;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    public static final int PICKFILE_RESULT_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static Activity mActivity;
    public static int score = 1111111;
    public static boolean isChecked = false;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    public static KAlertDialog kAlertDialog;

    ActionBarDrawerToggle toggle;
    public boolean isDark;
    public SharedPreferences prefs;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected double latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    String provider;
    Menu menu;
    private List<String> permissionsToRequest;
    private List<String> permissionsRejected = new ArrayList();
    DrawerLayout drawer;
    private List<String> permissions = new ArrayList();
    private Context mContext;
    NavigationView navigationView1;
    NavigationView navigationView;
    private PopupWindow mPopupWindow;
    private DrawerLayout mRelativeLayout;
    public static List<word> contactList = new ArrayList<word>();
    public static List<phrase> contactList1 = new ArrayList<phrase>();

    @Override
    protected void onStart() {
        super.onStart();

        getWindow().setBackgroundDrawableResource(android.R.color.white);
    }


    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);


        isDark = prefs.getBoolean("isDark", false);


        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        kAlertDialog = new KAlertDialog(this, KAlertDialog.WARNING_TYPE);

        mActivity = this;


        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions((ArrayList) permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }



        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {

                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission();
            }
        } else {

        }

      /*  FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), add_page.class);
                //String s = view.findViewById(R.id.subtitle).toString();
                //String s = (String) parent.getI;
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
            }
        });*/


        ///Intent intent=new Intent(MainActivity.this,quiz_page.class);
        //Intent intent = new Intent(Intent.ACTION_VIEW, null, MainActivity.this, quiz_page.class);


        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.appname);
        TextView toalword = hView.findViewById(R.id.wordcount);
        TextView scores = hView.findViewById(R.id.scores);
        TextView scores1 = hView.findViewById(R.id.scores1);


        SpannableString s = new SpannableString(navigationView.getMenu().findItem(R.id.datas).getTitle());
        SpannableString s1 = new SpannableString(navigationView.getMenu().findItem(R.id.datas1).getTitle());


        if (isDark) {
            hView.setBackgroundColor(ContextCompat.getColor(this, R.color.bbb));
            nav_user.setTextColor(Color.WHITE);
            toalword.setTextColor(Color.WHITE);
            scores.setTextColor(Color.WHITE);
            scores1.setTextColor(Color.WHITE);
            s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance45), 0, s.length(), 0);
            navigationView.getMenu().findItem(R.id.datas).setTitle(s);

            s1.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance45), 0, s1.length(), 0);
            navigationView.getMenu().findItem(R.id.datas1).setTitle(s1);


        } else {
            hView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            nav_user.setTextColor(Color.BLACK);
            toalword.setTextColor(Color.BLACK);
            scores.setTextColor(Color.BLACK);
            scores1.setTextColor(Color.BLACK);
            s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
            navigationView.getMenu().findItem(R.id.datas).setTitle(s);

            s1.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s1.length(), 0);
            navigationView.getMenu().findItem(R.id.datas1).setTitle(s1);


        }

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.scores);
        TextView navUsername1 = headerView.findViewById(R.id.scores1);
        TextView wordcount = headerView.findViewById(R.id.wordcount);


        //prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);


        navUsername.setText("Highest Score in multiple choice  : " + prefs.getInt("highscore", 0));
        navUsername1.setText("Highest Score in matching game  : " + prefs.getInt("highscore1", 0));


        navigationView.bringToFront();
        Menu menu = navigationView.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);

            menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    // TODO: switch on menuItem.getItemId()
                    return false;
                }
            });
        }


        /* */




        DatabaseHelper mDBHelper = new DatabaseHelper(this);
        PhDatabase phDatabase = new PhDatabase(this);


        contactList.clear();
        contactList1.clear();

        int i =0;

        final Cursor cursor = mDBHelper.getAllData();

        // looping through all rows and adding to list
        if (cursor.getCount() != 0) {
            // show message
            while (cursor.moveToNext()) {

                word word = new word();

                if (!cursor.getString(3).isEmpty() && !cursor.getString(4).isEmpty()
                        && !cursor.getString(5).isEmpty()) {
                    word.setID(i);
                    word.setWORD(cursor.getString(1));
                    word.setMEANINGB(cursor.getString(2));
                    word.setMEANINGE(cursor.getString(3));
                    word.setSYNONYMS(cursor.getString(4));
                    word.setANTONYMS(cursor.getString(5));
                } else if (cursor.getString(3).isEmpty()) {

                    word.setID(i);
                    word.setWORD(cursor.getString(1));
                    word.setMEANINGB(cursor.getString(2));
                    word.setMEANINGE("None");
                    word.setSYNONYMS(cursor.getString(4));
                    word.setANTONYMS(cursor.getString(5));

                } else if (cursor.getString(4).isEmpty()) {

                    word.setID(i);
                    word.setWORD(cursor.getString(1));
                    word.setMEANINGB(cursor.getString(2));
                    word.setMEANINGE(cursor.getString(3));
                    word.setSYNONYMS("None");
                    word.setANTONYMS(cursor.getString(5));

                } else if (cursor.getString(5).isEmpty()) {

                    word.setID(i);
                    word.setWORD(cursor.getString(1));
                    word.setMEANINGB(cursor.getString(2));
                    word.setMEANINGE(cursor.getString(3));
                    word.setSYNONYMS(cursor.getString(4));
                    word.setANTONYMS("None");
                }


                contactList.add(word);
                //   System.out.println(word.ANTONYMS);

                // maintitle.add(word.WORD);
                // subtitle.add(word.MEANING);

                i++;
            }
        } else {


        }


        TextView t1,t2,t3,t4;
        t1 = findViewById(R.id.word);
        t2 = findViewById(R.id.word1);
        t3 = findViewById(R.id.word2);
        t4 = findViewById(R.id.word3);
        TextView m1,m2,m3,m4;
        m1 = findViewById(R.id.body);
        m2 = findViewById(R.id.body3);
        m3 = findViewById(R.id.body4);
        m4 = findViewById(R.id.body5);

        List<Integer> c  = new ArrayList<>();
        for (int iq = 0; iq <4 ; iq++) {
            double randomDouble2 = Math.random();
            randomDouble2 = randomDouble2 * contactList.size() + 0;
            int randomInt2 = (int) randomDouble2;
            if(!c.contains(randomInt2)){
                c.add(randomInt2);
            }
        }
        if(!contactList.isEmpty())
        {
            t1.setText(contactList.get(c.get(0)).getWORD());
            t2.setText(contactList.get(c.get(1)).getWORD());
            t3.setText(contactList.get(c.get(2)).getWORD());
            t4.setText(contactList.get(c.get(3)).getWORD());

            m1.setText(contactList.get(c.get(0)).getMEANINGB());
            m2.setText(contactList.get(c.get(1)).getMEANINGB());
            m3.setText(contactList.get(c.get(2)).getMEANINGB());
            m4.setText(contactList.get(c.get(3)).getMEANINGB());

        }else {

        }

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), WordDetail.class);
                //String s = view.findViewById(R.id.subtitle).toString();
                //String s = (String) parent.getI;
                myIntent.putExtra("message", contactList.get(c.get(0)).getWORD());
                myIntent.putExtra("meaningb", contactList.get(c.get(0)).getMEANINGB());
                myIntent.putExtra("meaninge", contactList.get(c.get(0)).getMEANINGE());
                myIntent.putExtra("syn", contactList.get(c.get(0)).getSYNONYMS());
                myIntent.putExtra("ant", contactList.get(c.get(0)).getANTONYMS());
                myIntent.putExtra("id", c.get(0));
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), WordDetail.class);
                //String s = view.findViewById(R.id.subtitle).toString();
                //String s = (String) parent.getI;
                myIntent.putExtra("message", contactList.get(c.get(1)).getWORD());
                myIntent.putExtra("meaningb", contactList.get(c.get(1)).getMEANINGB());
                myIntent.putExtra("meaninge", contactList.get(c.get(1)).getMEANINGE());
                myIntent.putExtra("syn", contactList.get(c.get(1)).getSYNONYMS());
                myIntent.putExtra("ant", contactList.get(c.get(1)).getANTONYMS());
                myIntent.putExtra("id", c.get(1));
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), WordDetail.class);
                //String s = view.findViewById(R.id.subtitle).toString();
                //String s = (String) parent.getI;
                myIntent.putExtra("message", contactList.get(c.get(2)).getWORD());
                myIntent.putExtra("meaningb", contactList.get(c.get(2)).getMEANINGB());
                myIntent.putExtra("meaninge", contactList.get(c.get(2)).getMEANINGE());
                myIntent.putExtra("syn", contactList.get(c.get(2)).getSYNONYMS());
                myIntent.putExtra("ant", contactList.get(c.get(2)).getANTONYMS());
                myIntent.putExtra("id", c.get(2));
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), WordDetail.class);
                //String s = view.findViewById(R.id.subtitle).toString();
                //String s = (String) parent.getI;
                myIntent.putExtra("message", contactList.get(c.get(3)).getWORD());
                myIntent.putExtra("meaningb", contactList.get(c.get(3)).getMEANINGB());
                myIntent.putExtra("meaninge", contactList.get(c.get(3)).getMEANINGE());
                myIntent.putExtra("syn", contactList.get(c.get(3)).getSYNONYMS());
                myIntent.putExtra("ant", contactList.get(c.get(3)).getANTONYMS());
                myIntent.putExtra("id", c.get(3));
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
            }
        });


        wordcount.setText("Total Number Of Words : " + i);



        i =0;

        final Cursor cursor1 = phDatabase.getAllData();

        // looping through all rows and adding to list
        if (cursor1.getCount() != 0) {
            // show message
            while (cursor1.moveToNext()) {

                phrase word = new phrase();

                if(!cursor1.getString(3).isEmpty() && !cursor1.getString(4).isEmpty()){
                    word.setID(i);
                    word.setPHRASE(cursor1.getString(1));
                    word.setMEANINGB(cursor1.getString(2));
                    word.setMEANINGE(cursor1.getString(3));
                    word.setORIGINE(cursor1.getString(4));
                }
                else if(cursor1.getString(3).isEmpty()){

                    word.setID(i);
                    word.setPHRASE(cursor1.getString(1));
                    word.setMEANINGB(cursor1.getString(2));
                    word.setMEANINGE("None");
                    word.setORIGINE(cursor1.getString(4));


                } else if(cursor1.getString(4).isEmpty()){

                    word.setID(i);
                    word.setPHRASE(cursor1.getString(1));
                    word.setMEANINGB(cursor1.getString(2));
                    word.setMEANINGE(cursor1.getString(3));
                    word.setORIGINE("None");


                }

                contactList1.add(word);
                //   System.out.println(word.ANTONYMS);

                // maintitle.add(word.WORD);
                // subtitle.add(word.MEANING);

                i++;
            }
        } else {


        }

        TextView p1 = findViewById(R.id.phrase);

        TextView b1,o1;
        b1 = findViewById(R.id.body1);
        o1 = findViewById(R.id.origin);

        List<Integer> c1  = new ArrayList<>();
        for (int iq = 0; iq <1 ; iq++) {
            double randomDouble2 = Math.random();
            randomDouble2 = randomDouble2 * contactList1.size() + 0;
            int randomInt2 = (int) randomDouble2;
            if(!c1.contains(randomInt2)){
                c1.add(randomInt2);
            }
        }

        if(!contactList1.isEmpty())
        {
            p1.setText(contactList1.get(c1.get(0)).getPHRASE());

            b1.setText(contactList1.get(c1.get(0)).getMEANINGE());
            o1.setText(contactList1.get(c1.get(0)).getORIGINE());


        }else {

        }
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), PhraseDetail.class);
                //String s = view.findViewById(R.id.subtitle).toString();
                //String s = (String) parent.getI;
                myIntent.putExtra("message", contactList1.get(c1.get(0)).getPHRASE());
                myIntent.putExtra("meaningb", contactList1.get(c1.get(0)).getMEANINGB());
                myIntent.putExtra("meaninge", contactList1.get(c1.get(0)).getMEANINGE());
                myIntent.putExtra("id", c1.get(0));
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
            }
        });


        //   customAnalogClock.s(true);
        // customAnalogClock.setFace();

        /*
         */



        RelativeLayout constraintLayout1 = findViewById(R.id.content_main);
        // LinearLayout linearLayout1 = findViewById(R.id.listview);

        navigationView1 = findViewById(R.id.nav_view);





        RelativeLayout scrollView = findViewById(R.id.bbnb);
        RelativeLayout relativeLayout = findViewById(R.id.bass);
        RelativeLayout topcard = findViewById(R.id.topcard);
        RelativeLayout seccard = findViewById(R.id.seccard);

        if (isDark) {

            constraintLayout1.setBackgroundColor(Color.BLACK);
            // linearLayout.setBackgroundColor(Color.BLACK);
            navigationView1.setBackgroundColor(ContextCompat.getColor(this,R.color.bbb));
            topcard.setBackground(ContextCompat.getDrawable(this,R.drawable.background_card_dark));
            seccard.setBackground(ContextCompat.getDrawable(this,R.drawable.background_card_dark));

            m1.setTextColor(Color.WHITE);
            m2.setTextColor(Color.WHITE);
            m3.setTextColor(Color.WHITE);
            m4.setTextColor(Color.WHITE);

            b1.setTextColor(Color.WHITE);
            o1.setTextColor(Color.WHITE);

            Drawable icon = getResources().getDrawable(R.drawable.ic_notifications);
            icon.setTint(Color.WHITE);
            navigationView1.getMenu().getItem(0).setIcon(icon);


            // ColorStateList colorStateList = new ColorStateList(states, colors);
            //navigationView1.setItemIconTintList(colorStateList);


            navigationView1.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
            relativeLayout.setBackgroundColor(Color.BLACK);
            //if (contactList.size() != 0) list.setAdapter(adapter);

        } else {
            constraintLayout1.setBackgroundColor(Color.WHITE);
            m1.setTextColor(Color.BLACK);
            m2.setTextColor(Color.BLACK);
            m3.setTextColor(Color.BLACK);
            m4.setTextColor(Color.BLACK);

            b1.setTextColor(Color.BLACK);
            o1.setTextColor(Color.BLACK);
            topcard.setBackground(ContextCompat.getDrawable(this,R.drawable.background_card));
            seccard.setBackground(ContextCompat.getDrawable(this,R.drawable.background_card));

            navigationView1.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            navigationView1.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
            // linearLayout.setBackgroundColor(Color.WHITE);
            relativeLayout.setBackgroundColor(Color.WHITE);
            //if (contactList.size() != 0) list.setAdapter(adapter);
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isDark", isDark);
        editor.commit();

        /*ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        Intent intent1 = new Intent(this, add_page.class);

        intent1.setAction(Intent.ACTION_VIEW);
        ShortcutInfo shortcutInfo1 = new ShortcutInfo.Builder(MainActivity.this, "Shortcut_2")
                .setLongLabel("Add Word")
                .setShortLabel("Add Word")
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_add))
                .setIntent(intent1)
                .build();
        shortcutManager.addDynamicShortcuts(Arrays.asList(shortcutInfo1));*/






        /*AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Intent intenta = new Intent(this, daily_service.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intenta, 0);

        Calendar cal= Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 58);
        // alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);



        if (Build.VERSION.SDK_INT >= 23) {
            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    cal.getTimeInMillis(), pIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
        } else {
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pIntent);
        }


         */

    }

    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }




    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }





    public void showMessage(String title, String Message) {

        AlertDialog.Builder builder;
        if (isDark) {
            builder = new AlertDialog.Builder(this, R.style.DialogurDark);
        } else {
            builder = new AlertDialog.Builder(this, R.style.DialogueLight);
        }

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_voca) {
            // Handle the camera action
            //Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(MainActivity.this, Vocabulary.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(myIntent, 0);

        }
        else if(id == R.id.nav_phrase){
            Intent myIntent = new Intent(this, Phrase.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(myIntent, 0);
            return true;
        }

        else if (id == R.id.nav_parts) {
            Intent myIntent = new Intent(MainActivity.this, Partsofspeech.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(myIntent, 0);
        }
        else if (id == R.id.subscription) {

            if(isDark){
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this,R.style.my_dialog_theme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0,
                                new Intent(MainActivity.this, Mainservice.class),
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        Calendar calendar = Calendar.getInstance();
                        // set the triggered time to currentHour:08:00 for testing
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                        calendar.set(Calendar.SECOND, 0);

                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);


                        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), , pendingIntent);


                        Toasty.success(MainActivity.this, "Success! " + selectedHour + ":" + selectedMinute, Toast.LENGTH_SHORT, true).show();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }else{
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0,
                                new Intent(MainActivity.this, Mainservice.class),
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        Calendar calendar = Calendar.getInstance();
                        // set the triggered time to currentHour:08:00 for testing
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                        calendar.set(Calendar.SECOND, 0);

                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);


                        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), , pendingIntent);


                        Toasty.success(MainActivity.this, "Success! " + selectedHour + ":" + selectedMinute, Toast.LENGTH_SHORT, true).show();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }




        }
        else if (id == R.id.nav_quiz) {


            SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
            MainActivity.score = prefs.getInt("highscore", 0);
            Intent myIntent = new Intent(MainActivity.this, Quiz_confirm.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(myIntent, 0);


        }
        else if(id== R.id.nav_tools){

            Intent myIntent = new Intent(MainActivity.this, Settings.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(myIntent, 0);
        }

       /* else if(id == R.id.nav_back){

            Calendar mcurrentTime = Calendar.getInstance();
            final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            final int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                    Calendar calendar = Calendar.getInstance();
                    // set the triggered time to currentHour:08:00 for testing
                    calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar.set(Calendar.MINUTE, selectedMinute);
                    calendar.set(Calendar.SECOND, 0);

                    if (android.os.Build.VERSION.SDK_INT >= 23) {
                        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                                selectedHour, selectedMinute, 0);
                    } else {
                        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                                selectedHour, selectedMinute, 0);
                    }

                    AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    //creating a new intent specifying the broadcast receiver
                    Intent i = new Intent(MainActivity.this, receive_back.class);

                    //creating a pending intent using the intent
                    PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, i, 0);

                    //setting the repeating alarm that will be fired every day
                    am.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
                    Toasty.success(MainActivity.this, "schedule is set", Toast.LENGTH_SHORT).show();
                    //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), , pendingIntent);


                    //Toasty.success(MainActivity.this, "Success! " + selectedHour + ":" + selectedMinute, Toast.LENGTH_SHORT, true).show();
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time To Auto Backup.");
            mTimePicker.show();
        }

        else if (id == R.id.nav_tools) {
            mContext = getApplicationContext();
            mActivity = MainActivity.this;


            if (isDark ) {

                KAlertDialog.DARK_STYLE = true;
                kAlertDialog =  new KAlertDialog(this, KAlertDialog.WARNING_TYPE);
                kAlertDialog
                        .setTitleText("Dark Mode.")
                        .setContentText("Enable Dark Mode?")
                        .setCancelText("No, cancel!")
                        .setConfirmText("Yes, do!")
                        .showCancelButton(true)
                        .confirmButtonColor(R.drawable.btn_style,this)
                        .cancelButtonColor(R.drawable.btn_style,this)
                        .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {

                            RelativeLayout constraintLayout = findViewById(R.id.content_main);
                            // LinearLayout linearLayout = findViewById(R.id.listview);

                            NavigationView navigationView = findViewById(R.id.nav_view);

                            @Override
                            public void onClick(KAlertDialog  sDialog) {


                                sDialog
                                        .setTitleText("Done!")
                                        .setContentText("Dark Mode Has been Set!")
                                        .changeAlertType(KAlertDialog.SUCCESS_TYPE);


                                isDark = false;
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean("isDark", isDark);
                                editor.commit();
                                constraintLayout.setBackgroundColor(Color.WHITE);
                                navigationView.setBackgroundColor(Color.WHITE);
                                navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
                                // linearLayout.setBackgroundColor(Color.WHITE);
                                // linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.card_background));
                                // list.setAdapter(adapter);

                                Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivityForResult(myIntent, 0);

                                Toasty.success(mContext, "Disabled", Toast.LENGTH_SHORT).show();

                            }
                        });


                kAlertDialog.show();



            }
            else if (!isDark) {
                KAlertDialog.DARK_STYLE = false;
                kAlertDialog =  new KAlertDialog(this, KAlertDialog.WARNING_TYPE);
                kAlertDialog
                        .setTitleText("Dark Mode.")
                        .setContentText("Enable Dark Mode?")
                        .setCancelText("No, cancel!")
                        .setConfirmText("Yes, do!")
                        .showCancelButton(true)
                        .confirmButtonColor(R.drawable.btn_style,this)
                        .cancelButtonColor(R.drawable.btn_style,this)
                        .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {

                            RelativeLayout constraintLayout = findViewById(R.id.content_main);
                            // LinearLayout linearLayout = findViewById(R.id.listview);

                            NavigationView navigationView = findViewById(R.id.nav_view);

                            @Override
                            public void onClick(KAlertDialog  sDialog) {


                                sDialog
                                        .setTitleText("Done!")
                                        .setContentText("Dark Mode Has been Set!")
                                        .changeAlertType(KAlertDialog.SUCCESS_TYPE);


                                isDark = true;
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean("isDark", isDark);
                                editor.commit();
                                constraintLayout.setBackgroundColor(Color.BLACK);
                                // linearLayout.setBackgroundColor(Color.BLACK);
                                navigationView.setBackgroundColor(Color.BLACK);
                                navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));



                                Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivityForResult(myIntent, 0);

                                Toasty.success(mContext, "Enabled", Toast.LENGTH_SHORT).show();

                            }
                        });


                kAlertDialog.show();


            }



        }*/


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        finishAffinity();
        finish();

    }
    @Override
    protected void onResume() {
        super.onResume();


    }




    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            Toasty.info(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;

        }
    }
}