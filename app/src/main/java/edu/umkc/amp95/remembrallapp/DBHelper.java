package edu.umkc.amp95.remembrallapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alexandre on 3/10/2015.
 */
public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context)
    {
        super(context, "programs.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE movies (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "day TEXT," +
                "time TEXT," +
                "channel TEXT," +
                "notification TEXT)";

        db.execSQL(query);

        query = "CREATE TABLE series (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "day TEXT," +
                "time TEXT," +
                "channel TEXT," +
                "notification TEXT)";

        db.execSQL(query);

        query = "CREATE TABLE matches (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "day TEXT," +
                "time TEXT," +
                "channel TEXT," +
                "notification TEXT)";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS movies";
        db.execSQL(query);

        query = "DROP TABLE IF EXISTS series";
        db.execSQL(query);

         query = "DROP TABLE IF EXISTS matches";
        db.execSQL(query);

        onCreate(db);

    }

    public void insertSeries(HashMap<String, String> query){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", query.get("name"));
        values.put("time", query.get("time"));
        values.put("day", query.get("day"));
        values.put("notification", query.get("notification"));
        values.put("channel", query.get("channel"));

        db.insert("series", null, values);

        db.close();

    }

    public int updateSeries(HashMap<String, String> query){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", query.get("name"));
        values.put("time", query.get("time"));
        values.put("day", query.get("day"));
        values.put("notification", query.get("notification"));
        values.put("channel", query.get("channel"));

        return db.update("series", values,"id " + " = ?", new String[] { query.get("id") });

    }

    public void deleteSeries (String id){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM series WHERE id = '" + id + "'";

        db.execSQL(query);

        db.close();

    }

    public ArrayList<HashMap<String, String>> selectSeries(){

        ArrayList<HashMap<String, String>> series = new ArrayList<HashMap<String, String>>();

        String query = "SELECT * FROM series";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if(cursor.moveToFirst()){

            do {

                HashMap<String, String> row = new HashMap<String, String>();

                row.put("id", cursor.getString(0));
                row.put("name", cursor.getString(1));
                row.put("day", cursor.getString(2));
                row.put("time", cursor.getString(3));
                row.put("channel", cursor.getString(4));
                row.put("notification", cursor.getString(5));

                series.add(row);

            }while(cursor.moveToNext());

        }

        return series;
    }

    public HashMap<String, String> selectSeries(String id){

        HashMap<String, String> row = new HashMap<String, String>();

        String query = "SELECT * FROM series WHERE id = '" + id + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if(cursor.moveToFirst()){

            do {

                row.put("id", cursor.getString(0));
                row.put("name", cursor.getString(1));
                row.put("day", cursor.getString(2));
                row.put("time", cursor.getString(3));
                row.put("channel", cursor.getString(4));
                row.put("notification", cursor.getString(5));

            }while(cursor.moveToNext());
        }

        return row;
    }
}
