package com.example.myapplication.Phrase.Adapter;

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

import com.example.myapplication.Phrase.Data.phrase;
import com.example.myapplication.Phrase.Phrase;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PhBaseAdapter extends BaseAdapter implements SectionIndexer {

    private final Activity context;
    List<phrase> contactList;
    HashMap<String, Integer> alphaIndexer;

    String[] selections;
    // private final Integer[] imgid;

    public PhBaseAdapter(Activity context) {

        this.context = context;
        this.contactList = new ArrayList<phrase>();
        this.contactList.addAll(Phrase.contactList);
        this.alphaIndexer = new HashMap<String, Integer>();

        int size = this.contactList.size();

        for (int i = 0; i < size; i++) {
            String w = contactList.get(i).getPHRASE();
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
        return Phrase.contactList.size();
    }

    @Override
    public phrase getItem(int i) {
        return Phrase.contactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.phrase_item, null, true);

        RelativeLayout listitm = rowView.findViewById(R.id.list_item);
        TextView titleText = rowView.findViewById(R.id.title);
        TextView bodyText = rowView.findViewById(R.id.body);
        TextView originText = rowView.findViewById(R.id.origin);
        TextView number = rowView.findViewById(R.id.num);

        TextView ori = rowView.findViewById(R.id.ori);
        TextView mean = rowView.findViewById(R.id.mean);


        //System.out.println("klkl"+Phrase.contactList.size());
        titleText.setText(Phrase.contactList.get(position).getPHRASE());
        bodyText.setText(Phrase.contactList.get(position).getMEANINGB()+"... More.");
        originText.setText(Phrase.contactList.get(position).getORIGINE());
        number.setText(Integer.toString(Phrase.contactList.get(position).getID()));

        //LinearLayout listitem = rowView.findViewById(R.id.list_item);


        SharedPreferences prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("isDark", false);
        if (isDark) {

            //System.out.println("klklkl");
            // listitem.setBackgroundColor(Color.BLACK);


            bodyText.setTextColor(Color.WHITE);
            ori.setTextColor(Color.WHITE);
            mean.setTextColor(Color.WHITE);
            originText.setTextColor(Color.WHITE);
            number.setTextColor(ContextCompat.getColor(context,R.color.soft_light));
            listitm.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.background_card_dark));


        } else {

            bodyText.setTextColor(Color.BLACK);
            originText.setTextColor(Color.BLACK);
            ori.setTextColor(Color.BLACK);
            mean.setTextColor(Color.BLACK);
            number.setTextColor(ContextCompat.getColor(context,R.color.soft_dark));
            listitm.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.background_card));
        }


        return rowView;

    }


    public void filter(String charText) {
        charText = charText.toLowerCase();
        Phrase.contactList.clear();
        // System.out.println(this.contactList.size());
        if (charText.length() == 0) {
            Phrase.contactList.addAll(this.contactList);
        } else {
            for (phrase wp : this.contactList) {
                if (wp.getPHRASE().toLowerCase().contains(charText)) {
                    Phrase.contactList.add(wp);
                }
            }
        }
        //SharedPreferences prefs = MyListAdapter.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        String type = Phrase.prefs.getString("sort", "asc");
        if (type.equals("asc")) {
            Collections.sort(Phrase.contactList);
        } else if (type.equals("des")) {
            Collections.sort(Phrase.contactList, Collections.reverseOrder());
        } else if (type.equals("alp")) {
            Collections.sort(Phrase.contactList,
                    new Comparator<phrase>() {
                        public int compare(phrase f1, phrase f2) {
                            return f1.getPHRASE().compareTo(f2.getPHRASE());
                        }
                    });
        }
        // System.out.println(Phrase.contactList.size());
        notifyDataSetChanged();
    }

    public void filter1(String charText) {
        charText = charText.toLowerCase();
        Phrase.contactList.clear();
        // System.out.println(this.contactList.size());
        if (charText.length() == 0) {
            Phrase.contactList.addAll(this.contactList);
        } else {
            for (phrase wp : this.contactList) {
                if (wp.getMEANINGB().toLowerCase().contains(charText) || wp.getMEANINGE().toLowerCase().contains(charText)) {
                    Phrase.contactList.add(wp);
                }
            }
        }
        String type = Phrase.prefs.getString("sort", "asc");
        if (type.equals("asc")) {
            Collections.sort(Phrase.contactList);
        } else if (type.equals("des")) {
            Collections.sort(Phrase.contactList, Collections.reverseOrder());
        } else if (type.equals("alp")) {
            Collections.sort(Phrase.contactList,
                    new Comparator<phrase>() {
                        public int compare(phrase f1, phrase f2) {
                            return f1.getPHRASE().compareTo(f2.getPHRASE());
                        }
                    });
        }
        // System.out.println(Phrase.contactList.size());
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

