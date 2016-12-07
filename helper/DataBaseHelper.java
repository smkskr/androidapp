package com.example.android.thenextbigthing.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.thenextbigthing.utils.Riddles;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by smk on 11/1/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "riddles.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableDetails.TABLE_NAME + " (" +
                    TableDetails._ID + " INTEGER PRIMARY KEY," +
                    TableDetails.COLUMN_NAME_QUESTION + TEXT_TYPE + COMMA_SEP +
                    TableDetails.COLUMN_NAME_ANSWER + TEXT_TYPE + COMMA_SEP +
                    TableDetails._COUNT + " INTEGER );";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TableDetails.TABLE_NAME;
    private int id;

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    //Add riddles
    public void addRiddles(Riddles riddles){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableDetails.COLUMN_NAME_QUESTION,riddles.getQuestion());
        contentValues.put(TableDetails.COLUMN_NAME_ANSWER,riddles.getAnswer());
        sqLiteDatabase.insert(TableDetails.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    //Getting list of riddles
    public List<Riddles> getRiddles(){

        List<Riddles> riddles = new ArrayList<Riddles>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TableDetails.TABLE_NAME;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Riddles riddle = new Riddles();
                riddle.set_id(cursor.getInt(0));
                riddle.setQuestion(cursor.getString(1));
                riddle.setAnswer(cursor.getString(2));

                // Adding riddle to list
                riddles.add(riddle);
            } while (cursor.moveToNext());
        }

        // return riddle list
        return riddles;

    }
    /**
     * Get a random riddle which has not been repeated
     * @return Riddle
     */
    public Riddles getRandomRiddle() {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        String queryRiddle = "SELECT * FROM " + TableDetails.TABLE_NAME + " ORDER BY RANDOM() LIMIT 1 ";


        Cursor cursorRiddle = sqLiteDatabase.rawQuery(queryRiddle, null);

        int rowCount = getRowCount();
        if(cursorRiddle.getCount() == 0){
            Riddles riddle = new Riddles();
            riddle.set_count(0);
            return riddle;
        }

           if (cursorRiddle.getCount() > 0) {
               cursorRiddle.moveToFirst();
               Riddles riddle = new Riddles();
               riddle = new Riddles(cursorRiddle.getString(1), cursorRiddle.getString(2));
               riddle.set_count(rowCount);
               id = cursorRiddle.getInt(0);
               sqLiteDatabase.execSQL("DELETE FROM " + TableDetails.TABLE_NAME + " WHERE " + TableDetails._ID + " = " + id );
               cursorRiddle.close();
               sqLiteDatabase.close();
               return riddle;
           }


        return null;
    }

    /**
     * get count of rows
     * @return
     */
    public int getRowCount(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TableDetails.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        return count;

    }

    //Delete table entries
    public void deleteTableEntries(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+TableDetails.TABLE_NAME);
        sqLiteDatabase.close();
    }



}
