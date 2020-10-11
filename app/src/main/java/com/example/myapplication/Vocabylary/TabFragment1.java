package com.example.myapplication.Vocabylary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.developer.kalert.KAlertDialog;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Vocabylary.Data.word;
import com.example.myapplication.Vocabylary.DataBase.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import es.dmoral.toasty.Toasty;

import static com.example.myapplication.MainActivity.kAlertDialog;

public class TabFragment1 extends Fragment {

    TextInputEditText word;
    TextInputLayout w;
    TextInputEditText meaningb;
    TextInputLayout mb;
    TextInputEditText meaninge;
    TextInputLayout me;
    TextInputEditText synonym;
    TextInputLayout sym;
    TextInputEditText antonym;
    TextInputLayout ant;

    Button update,delete,loadonline;

    ImageButton done;
    String current_word;
    private DatabaseHelper mDBHelper;
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Intent intent = getActivity().getIntent();
        done = getActivity().findViewById(R.id.dark2_button);

        int i = 1;
        word = getActivity().findViewById(R.id.word1);
        w = getActivity().findViewById(R.id.word);
        meaningb = getActivity().findViewById(R.id.meaningB1);
        mb = getActivity().findViewById(R.id.meaningB);
        meaninge = getActivity().findViewById(R.id.meaningE1);
        me = getActivity().findViewById(R.id.meaningE);
        synonym = getActivity().findViewById(R.id.synonyme1);
        sym = getActivity().findViewById(R.id.synonyme);
        antonym = getActivity().findViewById(R.id.antonyme1);
        ant = getActivity().findViewById(R.id.antonyme);


        update = getActivity().findViewById(R.id.update);
        delete = getActivity().findViewById(R.id.delete);
        loadonline = getActivity().findViewById(R.id.loadonline);

        mDBHelper = new DatabaseHelper(getContext());

        System.out.println("Word : " +intent.getStringExtra("message")+",");

        current_word = intent.getStringExtra("message");

        com.example.myapplication.Vocabylary.Data.word word1 = new word();
        final DatabaseHelper mDBHelper = new DatabaseHelper(getContext());
        final Cursor cursor = mDBHelper.getAllData2(current_word);
        System.out.println(cursor);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                i++;

                System.out.println(word1);

                if(!cursor.getString(3).isEmpty() && !cursor.getString(4).isEmpty()
                        && !cursor.getString(5).isEmpty()){
                    word1.setID(i);
                    word1.setWORD(cursor.getString(1));
                    word1.setMEANINGB(cursor.getString(2));
                    word1.setMEANINGE(cursor.getString(3));
                    word1.setSYNONYMS(cursor.getString(4));
                    word1.setANTONYMS(cursor.getString(5));
                }
                else if(cursor.getString(3).isEmpty()){

                    word1.setID(i);
                    word1.setWORD(cursor.getString(1));
                    word1.setMEANINGB(cursor.getString(2));
                    word1.setMEANINGE("None");
                    word1.setSYNONYMS(cursor.getString(4));
                    word1.setANTONYMS(cursor.getString(5));

                }else if(cursor.getString(4).isEmpty()){

                    word1.setID(i);
                    word1.setWORD(cursor.getString(1));
                    word1.setMEANINGB(cursor.getString(2));
                    word1.setMEANINGE(cursor.getString(3));
                    word1.setSYNONYMS("None");
                    word1.setANTONYMS(cursor.getString(5));

                }else if(cursor.getString(5).isEmpty()){

                    word1.setID(i);
                    word1.setWORD(cursor.getString(1));
                    word1.setMEANINGB(cursor.getString(2));
                    word1.setMEANINGE(cursor.getString(3));
                    word1.setSYNONYMS(cursor.getString(4));
                    word1.setANTONYMS("None");
                }





            }
        }

        word.setText(word1.getWORD());
        meaningb.setText(word1.getMEANINGB());
        meaninge.setText(word1.getMEANINGE());
        synonym.setText(word1.getSYNONYMS());
        antonym.setText(word1.getANTONYMS());
        word.setEnabled(false);
        meaningb.setEnabled(false);
        meaninge.setEnabled(false);
        synonym.setEnabled(false);
        antonym.setEnabled(false);
        final RelativeLayout additem = getActivity().findViewById(R.id.item_detail);
        SharedPreferences prefs = getActivity().getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("isDark", false);


        if (isDark) {
            additem.setBackgroundColor(Color.BLACK);
            word.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.edittextstyledark));
            word.setHintTextColor(Color.rgb(185, 185, 185));
            word.setTextColor(Color.WHITE);
            w.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.divider)));

            meaningb.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.edittextstyledark));
            meaningb.setHintTextColor(Color.rgb(185, 185, 185));
            meaningb.setTextColor(Color.WHITE);
            mb.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.divider)));

            meaninge.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.edittextstyledark));
            meaninge.setHintTextColor(Color.rgb(185, 185, 185));
            meaninge.setTextColor(Color.WHITE);
            me.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.divider)));

            synonym.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.edittextstyledark));
            synonym.setHintTextColor(Color.rgb(185, 185, 185));
            synonym.setTextColor(Color.WHITE);
            sym.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.divider)));

            antonym.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.edittextstyledark));
            antonym.setHintTextColor(Color.rgb(185, 185, 185));
            antonym.setTextColor(Color.WHITE);
            ant.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.divider)));
        } else {
            additem.setBackgroundColor(Color.WHITE);
            word.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.editextstyle));
            word.setHintTextColor(Color.BLACK);
            word.setTextColor(Color.BLACK);
            w.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.darkgray)));

            meaningb.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.editextstyle));
            meaningb.setHintTextColor(Color.BLACK);
            meaningb.setTextColor(Color.BLACK);
            mb.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.darkgray)));

            meaninge.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.editextstyle));
            meaninge.setHintTextColor(Color.BLACK);
            meaninge.setTextColor(Color.BLACK);
            me.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.darkgray)));

            synonym.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.editextstyle));
            synonym.setHintTextColor(Color.BLACK);
            synonym.setTextColor(Color.BLACK);
            sym.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.darkgray)));

            antonym.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.editextstyle));
            antonym.setHintTextColor(Color.BLACK);
            antonym.setTextColor(Color.BLACK);
            ant.setDefaultHintTextColor( ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.darkgray)));
        }

        update.setOnClickListener(new View.OnClickListener() {


            int i = 0;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                i++;


                if (i == 1) {
                    word.setEnabled(true);
                    meaningb.setEnabled(true);
                    meaninge.setEnabled(true);
                    synonym.setEnabled(true);
                    antonym.setEnabled(true);

                    word.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(word, InputMethodManager.SHOW_IMPLICIT);
                } else if (i == 2) {
                    //Double click
                    i = 0;
                    String words = word.getText().toString();
                    int id = intent.getExtras().getInt("id");
                    id++;
                    boolean b = mDBHelper.updateData(String.valueOf(id), intent.getStringExtra("message"), words, meaningb.getText().toString(), meaninge.getText().toString(),synonym.getText().toString(),antonym.getText().toString());
                    if (b == true) {
                        Toasty.success(getContext(), "Done.", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivityForResult(myIntent, 0);
                    } else {
                        Toasty.error(getContext(), "Opps.", Toast.LENGTH_SHORT).show();
                    }
                    word.setEnabled(false);
                    meaningb.setEnabled(false);
                    meaninge.setEnabled(false);
                    synonym.setEnabled(false);
                    antonym.setEnabled(false);
                }


            }
        });
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getActivity().getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
                boolean isDark = prefs.getBoolean("isDark", false);
                if (isDark) {

                    KAlertDialog.DARK_STYLE = true;
                    kAlertDialog =  new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
                    kAlertDialog
                            .setTitleText("Want to delete?")
                            .setContentText("If you delete, you will not be able to recover back")
                            .setCancelText("No, cancel!")
                            .setConfirmText("Yes, do!")
                            .showCancelButton(true)
                            .confirmButtonColor(R.drawable.btn_style,getContext())
                            .cancelButtonColor(R.drawable.btn_style,getContext())
                            .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    sDialog
                                            .setTitleText("Done!")
                                            .setContentText("Dark Mode Has been Set!")
                                            .changeAlertType(KAlertDialog.SUCCESS_TYPE);

                                    String words = word.getText().toString();
                                    int id = intent.getExtras().getInt("id");
                                    id++;
                                    int b = mDBHelper.deleteData(String.valueOf(id), intent.getStringExtra("message"), words, meaningb.getText().toString(),meaninge.getText().toString(), synonym.getText().toString(),antonym.getText().toString());
                                    if (b == 1) {
                                        Toasty.success(getContext(), "Done.", Toast.LENGTH_SHORT).show();
                                        Intent myIntent = new Intent(getContext(), MainActivity.class);
                                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivityForResult(myIntent, 0);
                                    } else {
                                        Toasty.success(getContext(), "Opps.", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            });





                } else {

                    KAlertDialog.DARK_STYLE = false;
                    kAlertDialog =  new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
                    kAlertDialog
                            .setTitleText("Want to delete?")
                            .setContentText("If you delete, you will not be able to recover back")
                            .setCancelText("No, cancel!")
                            .setConfirmText("Yes, do!")
                            .showCancelButton(true)
                            .confirmButtonColor(R.drawable.btn_style,getContext())
                            .cancelButtonColor(R.drawable.btn_style,getContext())
                            .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    sDialog
                                            .setTitleText("Done!")
                                            .setContentText("Dark Mode Has been Set!")
                                            .changeAlertType(KAlertDialog.SUCCESS_TYPE);

                                    String words = word.getText().toString();
                                    int id = intent.getExtras().getInt("id");
                                    id++;
                                    int b = mDBHelper.deleteData(String.valueOf(id), intent.getStringExtra("message"), words, meaningb.getText().toString(),meaninge.getText().toString(), synonym.getText().toString(),antonym.getText().toString());
                                    if (b == 1) {
                                        Toasty.success(getContext(), "Done.", Toast.LENGTH_SHORT).show();
                                        Intent myIntent = new Intent(getContext(), MainActivity.class);
                                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivityForResult(myIntent, 0);
                                    } else {
                                        Toasty.success(getContext(), "Opps.", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            });


                }


                kAlertDialog.show();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.word_with_detail_frag, container, false);
    }
}
