package com.example.myapplication.Parts.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class partsDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "parts.db";
    public static final String TABLE_NAME = "Parts_table";
    public static final String ID = "ID";
    public static final String WORD = "WORD";
    public static final String NOUNFORM = "NOUNFORM";
    public static final String VERBFORM = "VERBFORM";
    public static final String ADJECTIVEFORM = "ADJECTIVEFORM";
    public static final String ADVERBFORM = "ADVERBFORM";


    static String DB_PATH = "";
    private SQLiteDatabase mDataBase;
    private Context mContext;
    private boolean mNeedUpdate = false;

    public partsDb(Context context) {
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
                + WORD + " TEXT,"
                + NOUNFORM + " TEXT,"
                + VERBFORM + " TEXT,"
                + ADJECTIVEFORM + " TEXT,"
                + ADVERBFORM + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String phrase, String noun,String verb,String adject, String adverb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORD, phrase);
        contentValues.put(NOUNFORM, noun);
        contentValues.put(VERBFORM, verb);
        contentValues.put(ADJECTIVEFORM, adject);
        contentValues.put(ADVERBFORM, adverb);
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }


    public Cursor getAllData2(String old_word) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM Parts_table WHERE WORD = ?; ", new String[]{old_word});
        return res;
    }

    public boolean updateData(String id, String old_phrase, String phrase,String noun,String verb,String adject, String adverb) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db1 = this.getReadableDatabase();
        try {
            Cursor re = db1.rawQuery("SELECT * FROM Parts_table WHERE WORD = ?; ", new String[]{old_phrase});
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
        contentValues.put(WORD, phrase);
        contentValues.put(NOUNFORM, noun);
        contentValues.put(VERBFORM, verb);
        contentValues.put(ADJECTIVEFORM, adject);
        contentValues.put(ADVERBFORM, adverb);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id, String old_phrase, String phrase, String noun,String verb,String adject, String adverb) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db1 = this.getReadableDatabase();
        try {
            Cursor re = db1.rawQuery("SELECT * FROM Parts_table WHERE WORD = ?; ", new String[]{old_phrase});
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
        contentValues.put(WORD, phrase);
        contentValues.put(NOUNFORM, noun);
        contentValues.put(VERBFORM, verb);
        contentValues.put(ADJECTIVEFORM, adject);
        contentValues.put(ADVERBFORM, adverb);
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }




    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }



}
