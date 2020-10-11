package com.example.myapplication.Phrase.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Vocabylary.Data.word;

import java.util.ArrayList;
import java.util.List;

public class PhDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "phrase.db";
    public static final String TABLE_NAME = "Phrase_table";
    public static final String ID = "ID";
    public static final String PHRASE = "PHRASE";
    public static final String MEANINGB = "MEANINGB";
    public static final String MEANINGE = "MEANINGE";
    public static final String ORIGIN = "ORIGIN";

    public static final String TABLE_NAME2 = "Sentence_table";
    public static final String ID1 = "ID1";
    public static final String PHRASE1 = "PHRASE1";
    public static final String SENTENCE1 = "SENTENCE1";

    static String DB_PATH = "";
    private SQLiteDatabase mDataBase;
    private Context mContext;
    private boolean mNeedUpdate = false;

    public PhDatabase(Context context) {
        super(context, DATABASE_NAME, null, 2);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;


        this.getReadableDatabase();
    }


    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY,"
                + PHRASE + " TEXT,"
                + MEANINGB + " TEXT,"
                + MEANINGE + " TEXT,"
                + ORIGIN + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        String TASK_TABLE_CREATE = "CREATE TABLE "
                + TABLE_NAME2 + " ("
                + ID1 + " integer primary key, "
                + PHRASE1 + " text, "
                + SENTENCE1 + " TEXT" + ")";

        db.execSQL(TASK_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    public boolean insertData(String phrase, String meaningB,String meaningE,String origin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PHRASE, phrase);
        contentValues.put(MEANINGB, meaningB);
        contentValues.put(MEANINGE, meaningE);
        contentValues.put(ORIGIN, origin);
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public boolean insertData1(String phrase, String sentence){
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(PHRASE1, phrase);
        contentValues1.put(SENTENCE1, sentence);
        long result1 = db1.insert(TABLE_NAME2, null, contentValues1);
        return result1!=-1;
    }
    public boolean exists(String old_word) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db1 = this.getReadableDatabase();
        String id = null;
        try {
            Cursor re = db1.rawQuery("SELECT * FROM Sentence_table WHERE PHRASE1 = ?; ", new String[]{old_word});
            if (re.moveToFirst()) {
                do {
                    System.out.println(re.getString(0));
                    id = re.getString(0);
                } while (re.moveToNext());
            }

            re.close();
            // System.out.println(re.getString(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(id==null){
            return true;
        }else return false;


    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
    public Cursor getAllData1() {
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor res = db1.rawQuery("select * from " + TABLE_NAME2, null);
        return res;
    }

    public Cursor getAllData0(String old_word) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor res = db1.rawQuery("SELECT * FROM Sentence_table WHERE PHRASE1 = ?; ", new String[]{old_word});
        return res;
    }

    public Cursor getAllData2(String old_word) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM Phrase_table WHERE PHRASE = ?; ", new String[]{old_word});
        return res;
    }

    public boolean updateData(String id, String old_phrase, String phrase, String meainingB,String meainingE,String origin) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db1 = this.getReadableDatabase();
        try {
            Cursor re = db1.rawQuery("SELECT * FROM Phrase_table WHERE PHRASE = ?; ", new String[]{old_phrase});
            if (re.moveToFirst()) {
                do {
                    System.out.println(re.getString(0));
                    id = re.getString(0);
                } while (re.moveToNext());
            }

            re.close();
            // System.out.println(re.getString(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(PHRASE, phrase);
        contentValues.put(MEANINGB, meainingB);
        contentValues.put(MEANINGE, meainingE);
        contentValues.put(ORIGIN, origin);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id, String old_phrase, String phrase, String meainingB,String meainingE,String origin ) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db1 = this.getReadableDatabase();
        try {
            Cursor re = db1.rawQuery("SELECT * FROM Phrase_table WHERE PHRASE = ?; ", new String[]{old_phrase});
            if (re.moveToFirst()) {
                do {
                    System.out.println(re.getString(0));
                    id = re.getString(0);
                } while (re.moveToNext());
            }

            re.close();
            // System.out.println(re.getString(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(PHRASE, phrase);
        contentValues.put(MEANINGB, meainingB);
        contentValues.put(MEANINGE, meainingE);
        contentValues.put(ORIGIN, origin);
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }


    public Integer deleteData2(String id,String phrase, String sentence) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db1 = this.getReadableDatabase();
        try {
            Cursor re = db1.rawQuery("SELECT * FROM Sentence_table WHERE SENTENCE1 = ?; ", new String[]{sentence});
            if (re.moveToFirst()) {
                do {
                    System.out.println(re.getString(0));
                    id = re.getString(0);
                } while (re.moveToNext());
            }

            re.close();
            // System.out.println(re.getString(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID1, id);
        contentValues.put(PHRASE1, phrase);
        contentValues.put(SENTENCE1, sentence);
        return db.delete(TABLE_NAME2, "ID1 = ?", new String[]{id});
    }

    public Integer deleteData1(String id, String old_phrase, String phrase, String sentence) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db1 = this.getReadableDatabase();
        try {
            Cursor re = db1.rawQuery("SELECT * FROM Sentence_table WHERE PHRASE1 = ?; ", new String[]{old_phrase});
            if (re.moveToFirst()) {
                do {
                    System.out.println(re.getString(0));
                    id = re.getString(0);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ID1, id);
                    contentValues.put(PHRASE1, phrase);
                    contentValues.put(SENTENCE1, sentence);
                    db.delete(TABLE_NAME2, "ID1 = ?", new String[]{id});
                } while (re.moveToNext());
            }

            re.close();
            return 1;
            // System.out.println(re.getString(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;

    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }
    public void deleteAll1() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME2);
    }


}
