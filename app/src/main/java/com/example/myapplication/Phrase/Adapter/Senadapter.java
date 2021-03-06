package com.example.myapplication.Phrase.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.myapplication.Phrase.Data.sentence;
import com.example.myapplication.Phrase.Tab2;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Senadapter extends BaseAdapter {

    private final Activity context;
    List<sentence> sentenceList;
    HashMap<String, Integer> alphaIndexer;

    String[] selections;
    // private final Integer[] imgid;

    public Senadapter(Activity context) {

        this.context = context;
        this.sentenceList = new ArrayList<sentence>();
        this.sentenceList.addAll(Tab2.sentenceList);


    }

    @Override
    public int getCount() {
        return Tab2.sentenceList.size();
    }

    @Override
    public sentence getItem(int i) {
        return Tab2.sentenceList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.senlist_item, null, true);

        TextView titleText = rowView.findViewById(R.id.title);
        TextView number = rowView.findViewById(R.id.num);


        //System.out.println("klkl"+MainActivity.contactList.size());


        String newString = Tab2.sentenceList.get(position).getSENTENCE().replaceAll(Tab2.current_word.toLowerCase().trim(), "<font color='red'>"+Tab2.current_word.toLowerCase().trim()+"</font>");
        titleText.setText(Html.fromHtml(newString));
        number.setText(Integer.toString(Tab2.sentenceList.get(position).getID()));

        LinearLayout listitem = rowView.findViewById(R.id.list_item);


        SharedPreferences prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("isDark", false);
        if (isDark) {

            //System.out.println("klklkl");
            // listitem.setBackgroundColor(Color.BLACK);


            titleText.setTextColor(Color.WHITE);
            number.setTextColor(Color.WHITE);

            listitem.setBackground(ContextCompat.getDrawable(context,R.drawable.background_card_dark));


        } else {

            titleText.setTextColor(Color.BLACK);
            number.setTextColor(Color.BLACK);
            listitem.setBackground(ContextCompat.getDrawable(context,R.drawable.background_card));
        }


        return rowView;

    }
}

