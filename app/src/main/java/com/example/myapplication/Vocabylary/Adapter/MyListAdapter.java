package com.example.myapplication.Vocabylary.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Vocabylary.Data.word;
import com.example.myapplication.Vocabylary.Vocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyListAdapter extends BaseAdapter implements SectionIndexer {

    private final Activity context;
    List<word> contactList;
    HashMap<String, Integer> alphaIndexer;

    String[] selections;
    // private final Integer[] imgid;

    public MyListAdapter(Activity context) {

        this.context = context;
        this.contactList = new ArrayList<word>();
        this.contactList.addAll(MainActivity.contactList);
        this.alphaIndexer = new HashMap<String, Integer>();

        int size = this.contactList.size();

        for (int i = 0; i < size; i++) {
            String w = contactList.get(i).getWORD();
            if (w.length() == 0) continue;
            String ch = w.substring(0, 1);
            ch = ch.toLowerCase();
            if (!alphaIndexer.containsKey(ch)) {
                alphaIndexer.put(ch, i);
            }
        }
        Set<String> selectionLetters = alphaIndexer.keySet();
        ArrayList<String> sectionList = new ArrayList<>(selectionLetters);
        Collections.sort(sectionList);
        selections = new String[sectionList.size()];
        sectionList.toArray(selections);

    }

    @Override
    public int getCount() {
        return MainActivity.contactList.size();
    }

    @Override
    public word getItem(int i) {
        return MainActivity.contactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);

        RelativeLayout listitm = rowView.findViewById(R.id.list_item);
        TextView titleText = rowView.findViewById(R.id.title);
        TextView number = rowView.findViewById(R.id.num);


        //System.out.println("klkl"+Vocabulary.contactList.size());
        titleText.setText(MainActivity.contactList.get(position).getWORD());
        number.setText(Integer.toString(MainActivity.contactList.get(position).getID()));

        //LinearLayout listitem = rowView.findViewById(R.id.list_item);


        SharedPreferences prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("isDark", false);
        if (isDark) {

            //System.out.println("klklkl");
            // listitem.setBackgroundColor(Color.BLACK);


            titleText.setTextColor(Color.WHITE);
            number.setTextColor(ContextCompat.getColor(context,R.color.soft_light));
            listitm.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.background_card_dark));


        } else {

            titleText.setTextColor(Color.BLACK);
            number.setTextColor(ContextCompat.getColor(context,R.color.soft_dark));
            listitm.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.background_card));
        }


        return rowView;

    }


    public void filter(String charText) {
        charText = charText.toLowerCase();
        MainActivity.contactList.clear();
        // System.out.println(this.contactList.size());
        if (charText.length() == 0) {
            MainActivity.contactList.addAll(this.contactList);
        } else {
            for (word wp : this.contactList) {
                if (wp.getWORD().toLowerCase().contains(charText)) {
                    MainActivity.contactList.add(wp);
                }
            }
        }
        //SharedPreferences prefs = MyListAdapter.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        String type = Vocabulary.prefs.getString("sort", "asc");
        if (type.equals("asc")) {
            Collections.sort(MainActivity.contactList);
        } else if (type.equals("des")) {
            Collections.sort(MainActivity.contactList, Collections.reverseOrder());
        } else if (type.equals("alp")) {
            Collections.sort(MainActivity.contactList,
                    new Comparator<word>() {
                        public int compare(word f1, word f2) {
                            return f1.getWORD().compareTo(f2.getWORD());
                        }
                    });
        }
        // System.out.println(MainActivity.contactList.size());
        notifyDataSetChanged();
    }

    public void filter1(String charText) {
        charText = charText.toLowerCase();
        MainActivity.contactList.clear();
        // System.out.println(this.contactList.size());
        if (charText.length() == 0) {
            MainActivity.contactList.addAll(this.contactList);
        } else {
            for (word wp : this.contactList) {
                if (wp.getMEANINGB().toLowerCase().contains(charText) || wp.getMEANINGE().toLowerCase().contains(charText)) {
                    MainActivity.contactList.add(wp);
                }
            }
        }
        String type = Vocabulary.prefs.getString("sort", "asc");
        if (type.equals("asc")) {
            Collections.sort(MainActivity.contactList);
        } else if (type.equals("des")) {
            Collections.sort(MainActivity.contactList, Collections.reverseOrder());
        } else if (type.equals("alp")) {
            Collections.sort(MainActivity.contactList,
                    new Comparator<word>() {
                        public int compare(word f1, word f2) {
                            return f1.getWORD().compareTo(f2.getWORD());
                        }
                    });
        }
        // System.out.println(MainActivity.contactList.size());
        notifyDataSetChanged();
    }

    @Override
    public Object[] getSections() {
        return selections;
    }

    @Override
    public int getPositionForSection(int i) {
        return alphaIndexer.get(this.selections[i]);
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }
}

