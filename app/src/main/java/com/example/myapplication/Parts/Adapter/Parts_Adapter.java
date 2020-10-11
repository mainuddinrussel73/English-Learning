package com.example.myapplication.Parts.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.myapplication.Parts.Data.parts;
import com.example.myapplication.Parts.Partsofspeech;
import com.example.myapplication.R;
import com.example.myapplication.Vocabylary.Data.word;
import com.example.myapplication.Vocabylary.DataBase.DatabaseHelper;
import com.example.myapplication.Vocabylary.Vocabulary;
import com.example.myapplication.Vocabylary.WordDetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import es.dmoral.toasty.Toasty;

public class Parts_Adapter  extends BaseAdapter implements SectionIndexer {
    private final Activity context;
    List<parts> contactList;
    HashMap<String, Integer> alphaIndexer;

    String[] selections;
    // private final Integer[] imgid;

    public Parts_Adapter(Activity context) {

        this.context = context;
        this.contactList = new ArrayList<parts>();
        this.contactList.addAll(Partsofspeech.contactList);
        this.alphaIndexer = new HashMap<String, Integer>();

        int size = this.contactList.size();

        for (int i = 0; i < size; i++) {
            String w = contactList.get(i).getWord();
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
        return Partsofspeech.contactList.size();
    }

    @Override
    public parts getItem(int i) {
        return Partsofspeech.contactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.parts_item, null, true);

        RelativeLayout listitm = rowView.findViewById(R.id.list_item);
        TextView titleText = rowView.findViewById(R.id.title1);
        TextView noun = rowView.findViewById(R.id.noun);
        TextView verb = rowView.findViewById(R.id.verb);
        TextView adject = rowView.findViewById(R.id.adjective);
        TextView adverb = rowView.findViewById(R.id.adverb);

        TextView number = rowView.findViewById(R.id.num);

        int i = 0;
        DatabaseHelper mDBHelper = new DatabaseHelper(context);
        final Cursor cursor = mDBHelper.getAllData();

        // looping through all rows and adding to list
        List<word> contactListq = new ArrayList<word>();
        if (cursor.getCount() != 0) {
            // show message
            while (cursor.moveToNext()) {

                word word = new word();

                if (!cursor.getString(3).isEmpty() && !cursor.getString(5).isEmpty()
                        && !cursor.getString(4).isEmpty()) {
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

                } else if (cursor.getString(5).isEmpty()) {

                    word.setID(i);
                    word.setWORD(cursor.getString(1));
                    word.setMEANINGB(cursor.getString(2));
                    word.setMEANINGE(cursor.getString(3));
                    word.setSYNONYMS("None");
                    word.setANTONYMS(cursor.getString(5));

                } else if (cursor.getString(6).isEmpty()) {

                    word.setID(i);
                    word.setWORD(cursor.getString(1));
                    word.setMEANINGB(cursor.getString(2));
                    word.setMEANINGE(cursor.getString(3));
                    word.setSYNONYMS(cursor.getString(4));
                    word.setANTONYMS("None");
                }


                contactListq.add(word);
                //   System.out.println(word.ANTONYMS);

                // maintitle.add(word.WORD);
                // subtitle.add(word.MEANING);

                i++;
            }
        }

        //System.out.println("klkl"+Vocabulary.contactList.size());
        word word1 = new word();
        for (word w :  contactListq) {
                if (w.getWORD().trim().toLowerCase().equals(Partsofspeech.contactList.get(position).getWord())){
                    word1 = w;
                }
        }

        SpannableString ss = new SpannableString(Partsofspeech.contactList.get(position).getWord());
        word finalWord = word1;
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                //startActivity(new Intent(MyActivity.this, NextActivity.class));

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan, 0 , Partsofspeech.contactList.get(position).getWord().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        titleText.setText(ss);
        titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalWord.getWORD()!=null){
                    System.out.println(finalWord);
                    Intent myIntent = new Intent(view.getContext(), WordDetail.class);
                    //String s = view.findViewById(R.id.subtitle).toString();
                    //String s = (String) parent.getI;
                    myIntent.putExtra("message", finalWord.getWORD());
                    myIntent.putExtra("meaningb", finalWord.getMEANINGB());
                    myIntent.putExtra("meaninge", finalWord.getMEANINGE());
                    myIntent.putExtra("syn", finalWord.getSYNONYMS());
                    myIntent.putExtra("ant", finalWord.getANTONYMS());
                    myIntent.putExtra("id", position);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivityForResult(myIntent, 0);
                }
                else{
                    Toasty.error(context, "Item not in the vocabulary", Toast.LENGTH_SHORT).show();
                }
            }
        });



        noun.setText(Partsofspeech.contactList.get(position).getNounform());
        verb.setText(Partsofspeech.contactList.get(position).getVerbform());
        adject.setText(Partsofspeech.contactList.get(position).getAdjectiveform());
        adverb.setText(Partsofspeech.contactList.get(position).getAdverbform());

        number.setText(Integer.toString(Partsofspeech.contactList.get(position).getID()));

        //LinearLayout listitem = rowView.findViewById(R.id.list_item);


        SharedPreferences prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("isDark", false);
        if (isDark) {

            //System.out.println("klklkl");
            // listitem.setBackgroundColor(Color.BLACK);


            titleText.setTextColor(Color.WHITE);
            noun.setTextColor(Color.WHITE);
            verb.setTextColor(Color.WHITE);
            adject.setTextColor(Color.WHITE);
            adverb.setTextColor(Color.WHITE);
            noun.setBackground(ContextCompat.getDrawable(context,R.drawable.border_allside_w));
            verb.setBackground(ContextCompat.getDrawable(context,R.drawable.border_allside_w));
            adject.setBackground(ContextCompat.getDrawable(context,R.drawable.border_allside_w));
            adverb.setBackground(ContextCompat.getDrawable(context,R.drawable.border_allside_w));

            number.setTextColor(ContextCompat.getColor(context,R.color.soft_light));
            listitm.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.background_card_dark));


        } else {

            titleText.setTextColor(Color.BLACK);
            noun.setTextColor(Color.BLACK);
            verb.setTextColor(Color.BLACK);
            adject.setTextColor(Color.BLACK);
            adverb.setTextColor(Color.BLACK);
            noun.setBackground(ContextCompat.getDrawable(context,R.drawable.border_allside));
            verb.setBackground(ContextCompat.getDrawable(context,R.drawable.border_allside));
            adject.setBackground(ContextCompat.getDrawable(context,R.drawable.border_allside));
            adverb.setBackground(ContextCompat.getDrawable(context,R.drawable.border_allside));
            number.setTextColor(ContextCompat.getColor(context,R.color.soft_dark));
            listitm.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.background_card));
        }


        return rowView;

    }


    public void filter(String charText) {
        charText = charText.toLowerCase();
        Partsofspeech.contactList.clear();
        // System.out.println(this.contactList.size());
        if (charText.length() == 0) {
            Partsofspeech.contactList.addAll(this.contactList);
        } else {
            for (parts wp : this.contactList) {
                if (wp.getWord().toLowerCase().contains(charText)) {
                    Partsofspeech.contactList.add(wp);
                }
            }
        }
        //SharedPreferences prefs = MyListAdapter.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        String type = Partsofspeech.prefs.getString("sort", "asc");
        if (type.equals("asc")) {
            Collections.sort(Partsofspeech.contactList);
        } else if (type.equals("des")) {
            Collections.sort(Partsofspeech.contactList, Collections.reverseOrder());
        } else if (type.equals("alp")) {
            Collections.sort(Partsofspeech.contactList,
                    new Comparator<parts>() {
                        public int compare(parts f1, parts f2) {
                            return f1.getWord().compareTo(f2.getWord());
                        }
                    });
        }
        // System.out.println(Vocabulary.contactList.size());
        notifyDataSetChanged();
    }

    public void filter1(String charText) {
        charText = charText.toLowerCase();
        Partsofspeech.contactList.clear();
        // System.out.println(this.contactList.size());
        if (charText.length() == 0) {
            Partsofspeech.contactList.addAll(this.contactList);
        } else {
            for (parts wp : this.contactList) {
                if (wp.getNounform().toLowerCase().contains(charText) || wp.getNounform().toLowerCase().contains(charText)) {
                    Partsofspeech.contactList.add(wp);
                }
            }
        }
        String type = Partsofspeech.prefs.getString("sort", "asc");
        if (type.equals("asc")) {
            Collections.sort(Partsofspeech.contactList);
        } else if (type.equals("des")) {
            Collections.sort(Partsofspeech.contactList, Collections.reverseOrder());
        } else if (type.equals("alp")) {
            Collections.sort(Partsofspeech.contactList,
                    new Comparator<parts>() {
                        public int compare(parts f1, parts f2) {
                            return f1.getWord().compareTo(f2.getWord());
                        }
                    });
        }
        // System.out.println(Vocabulary.contactList.size());
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
