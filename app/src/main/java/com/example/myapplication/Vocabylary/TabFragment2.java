package com.example.myapplication.Vocabylary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Vocabylary.Adapter.Senadapter;
import com.example.myapplication.Vocabylary.Data.sentence;
import com.example.myapplication.Vocabylary.DataBase.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class TabFragment2 extends Fragment {


    ExtendedEditText extendedEditText;
    ImageButton done;
    public static String current_word;
    private DatabaseHelper mDBHelper;
    ListView list;
    Senadapter adapter;

    public  static List<sentence> sentenceList = new ArrayList<>();

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Intent intent = getActivity().getIntent();

        extendedEditText = getActivity().findViewById(R.id.text_field_boxes3);
        done = getActivity().findViewById(R.id.dark2_button);
        mDBHelper = new DatabaseHelper(getContext());



        sentenceList.clear();
        int i = 1;

        adapter = new Senadapter(getActivity());
        list = getActivity().findViewById(R.id.list);

        System.out.println("Word : " +intent.getStringExtra("message")+",");

        current_word = intent.getStringExtra("message");

        final Cursor cursor = mDBHelper.getAllData1(current_word);

        // looping through all rows and adding to list
        if (cursor.getCount() != 0) {
            // show message
            while (cursor.moveToNext()) {

                sentence sentence = new sentence();
                sentence.setID(i);
                sentence.setWORD(cursor.getString(1));
                sentence.setSENTENCE(cursor.getString(2));
                System.out.println(sentence.toString());

                i++;
                try {
                    if (sentence.getSENTENCE().trim().length() != 0)
                        sentenceList.add(sentence);
                }catch (Exception e){

                }


                // maintitle.add(word.WORD);
                // subtitle.add(word.MEANING);

            }


            // size = contactList.size();


            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub
                    // System.out.println(position);
                    final int pos = parent.getPositionForView(view);
                    // Toast.makeText(getContext(),pos+"",Toast.LENGTH_SHORT).show();

                    Context wrapper = new ContextThemeWrapper(getContext(), R.style.YOURSTYLE1);
                    SharedPreferences prefs = getContext().getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
                    boolean isDark = prefs.getBoolean("isDark", false);
                    if (isDark) {
                        wrapper = new ContextThemeWrapper(getContext(), R.style.YOURSTYLE);

                    } else {
                        wrapper = new ContextThemeWrapper(getContext(), R.style.YOURSTYLE1);
                    }

                    PopupMenu popup = new PopupMenu(wrapper, view);
                    popup.getMenuInflater().inflate(R.menu.popup_delete, popup.getMenu());
                    //
                    popup.show();


                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getTitle().equals("Delete")){
                                int b = mDBHelper.deleteData2(Integer.toString(sentenceList.get(pos).getID()),sentenceList.get(pos).getWORD(),sentenceList.get(pos).getSENTENCE());
                                if (b == 1) {
                                    Toasty.success(getContext(), "Done.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toasty.error(getContext(), "opps.", Toast.LENGTH_SHORT).show();
                                }
                                sentenceList.remove(pos);
                                list.setAdapter(adapter);
                            }
                            return true;
                        }
                    });


                }
            });
        } else {

            Toasty.error(getContext(), "Error  Nothing found", Toasty.LENGTH_SHORT);
        }

        LinearLayout additem = getActivity().findViewById(R.id.sen_frag);
        LinearLayout listfragg = getActivity().findViewById(R.id.textlayout);
        SharedPreferences prefs = getActivity().getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("isDark", false);

        if (isDark) {
            additem.setBackgroundColor(Color.BLACK);

            if (isDark && sentenceList.size() != 0) {

                list.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.bbb));
                list.setAdapter(adapter);
            }
            else if (isDark && sentenceList.size() == 0) {


                list.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.bbb));
            }
            listfragg.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_card_dark));
            extendedEditText.setBackgroundColor(Color.BLACK);
            extendedEditText.setTextColor(Color.WHITE);
            extendedEditText.setHintTextColor(Color.rgb(185, 185, 185));

        } else {

            if (!isDark && sentenceList.size() != 0) {



                list.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                list.setAdapter(adapter);

            } else if (!isDark && sentenceList.size() == 0) {

                list.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));



            }
            listfragg.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_card));
            extendedEditText.setBackgroundColor(Color.WHITE);
            extendedEditText.setTextColor(Color.BLACK);
            extendedEditText.setHintTextColor(Color.BLACK);
            additem.setBackgroundColor(Color.WHITE);

        }

        done.setOnClickListener(new View.OnClickListener() {

            int i = 0;

            @Override
            public void onClick(View v) {
                if (extendedEditText.getText().toString().isEmpty() || extendedEditText.getText().toString().trim().length() <= 0) {
                    Toasty.error(getContext(), "No input.", Toast.LENGTH_SHORT).show();
                }else {
                    System.out.println("Word : " +intent.getStringExtra("message")+","+extendedEditText.getText().toString());
                    boolean b = mDBHelper.insertData1(intent.getStringExtra("message"),extendedEditText.getText().toString());
                    if (b == true) {
                        Toasty.success(getContext(), "Done.", Toast.LENGTH_SHORT).show();
                        sentence sentence = new sentence(sentenceList.size()+1,intent.getStringExtra("message"),extendedEditText.getText().toString());
                        sentenceList.add(sentence);
                        list.setAdapter(adapter);

                    } else {
                        Toasty.error(getContext(), "opps.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.word_with_sen_frag, container, false);
    }
}
