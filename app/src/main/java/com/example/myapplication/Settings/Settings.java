package com.example.myapplication.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import com.developer.kalert.KAlertDialog;
import com.example.myapplication.Backup;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.suke.widget.SwitchButton;

import es.dmoral.toasty.Toasty;

public class Settings extends AppCompatActivity   {
    public static SharedPreferences prefs;
    public static KAlertDialog kAlertDialog;
    SwitchButton toogledark;
    Button backup;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_settings);
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

        kAlertDialog = new KAlertDialog(this, KAlertDialog.WARNING_TYPE);
        prefs = getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        toogledark = findViewById(R.id.isdark);
        backup = findViewById(R.id.backup);

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Settings.this, Backup.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
            }
        });

        TextView one,two,three,four;
        RelativeLayout coordinate_j = findViewById(R.id.coordinate_j);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);

        boolean isDark = prefs.getBoolean("isDark",false);
        System.out.println("ss"+isDark);
        if (isDark){
            //toogledark.toggle(true);
            toogledark.toggle();
            coordinate_j.setBackgroundColor(Color.BLACK);
            one.setTextColor(Color.WHITE);
            two.setTextColor(Color.WHITE);
            three.setTextColor(ContextCompat.getColor(this,R.color.soft_light));
            four.setTextColor(Color.WHITE);



        }else{
            coordinate_j.setBackgroundColor(Color.WHITE);
            one.setTextColor(Color.BLACK);
            two.setTextColor(Color.BLACK);
            three.setTextColor(ContextCompat.getColor(this,R.color.soft_dark));
            four.setTextColor(Color.BLACK);


        }

        toogledark.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                System.out.println("click");
                System.out.println(isChecked);
                if (!isChecked) {



                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putBoolean("isDark", false);
                                    editor.commit();
                                    // linearLayout.setBackgroundColor(Color.WHITE);
                                    // linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.card_background));
                                    // list.setAdapter(adapter);

                                    Intent myIntent = new Intent(Settings.this, MainActivity.class);
                                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivityForResult(myIntent, 0);

                                    Toasty.success(Settings.this, "Disabled", Toast.LENGTH_SHORT).show();



                }
                else {




                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putBoolean("isDark", true);
                                    editor.commit();

                                    Intent myIntent = new Intent(Settings.this, MainActivity.class);
                                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivityForResult(myIntent, 0);

                                    Toasty.success(Settings.this, "Enabled", Toast.LENGTH_SHORT).show();




                }
            }
        });



    }





}