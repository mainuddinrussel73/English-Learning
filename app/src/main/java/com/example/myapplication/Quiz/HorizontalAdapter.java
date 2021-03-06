package com.example.myapplication.Quiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Vocabylary.Vocabulary;
import com.example.myapplication.Vocabylary.WordDetail;

import java.util.List;

import static com.example.myapplication.Quiz.Quiz_confirm.prefs;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

    Context context;
    private List<String> horizontalList;
    boolean isDark = false;

    public HorizontalAdapter(List<String> horizontalList) {
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.learnlistitem, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtView.setText(horizontalList.get(position));

        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,holder.txtView.getText().toString(),Toast.LENGTH_SHORT).show();

                TextView textView = v.findViewById(R.id.word);
                String text = textView.getText().toString();

                int i = 0;
                for (i = 0; i < MainActivity.contactList.size(); i++) {
                    if (MainActivity.contactList.get(i).getWORD().equals(text)) {
                        System.out.println(MainActivity.contactList.get(i).getWORD());
                        break;
                    }
                }
                System.out.println(i);
                Intent myIntent = new Intent(v.getContext(), WordDetail.class);

                myIntent.putExtra("message", MainActivity.contactList.get(i).getWORD());
                myIntent.putExtra("meaningb", MainActivity.contactList.get(i).getMEANINGB());
                myIntent.putExtra("meaninge", MainActivity.contactList.get(i).getMEANINGE());
                myIntent.putExtra("syn", MainActivity.contactList.get(i).getSYNONYMS());
                myIntent.putExtra("ant", MainActivity.contactList.get(i).getANTONYMS());

                myIntent.putExtra("id", i);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ((Activity) context).startActivityForResult(myIntent, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtView;

        public MyViewHolder(View view) {
            super(view);
            txtView = view.findViewById(R.id.word);
            isDark = prefs.getBoolean("isDark", false);

            if (isDark){
                txtView.setTextColor(Color.WHITE);
                view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.card_background_dark));
            }
            else{ txtView.setTextColor(Color.BLACK);
                view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.card_background));
            }
            context = view.getContext();


        }
    }
}
