package edu.umkc.amp95.remembrallapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alexandre on 3/10/2015.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static String TAG = ActivitySplash.class.getName();

    public DBHelper(Context context)
    {
        super(context, "showsDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE show (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "time TEXT," +
                "channel TEXT," +
                "notification TEXT)";

        db.execSQL(query);

        query = "CREATE TABLE days (" +
                "id INTEGER," +
                "day TEXT," +
                "FOREIGN KEY(id) REFERENCES show(id) ON DELETE CASCADE)";
        try
        {
            db.execSQL(query);
        }
        catch (SQLiteException e)
        {
            Log.e(TAG, e.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS show";

        try
        {
            db.execSQL(query);
        }
        catch (SQLiteException e)
        {
            Log.e(TAG, e.getMessage());
        }

        query = "DROP TABLE IF EXISTS day";

        try
        {
            db.execSQL(query);
        }
        catch (SQLiteException e)
        {
            Log.e(TAG, e.getMessage());
        }

        onCreate(db);

    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            try
            {
                db.execSQL("PRAGMA foreign_keys=ON;");
            }
            catch (SQLiteException e)
            {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public void insertShow(HashMap<String, String> query){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", query.get("name"));
        values.put("time", query.get("time"));
        values.put("notification", query.get("notification"));
        values.put("channel", query.get("channel"));

        try
        {
            db.insert("show", null, values);
        }
        catch (SQLiteException e)
        {
            Log.e(TAG, e.getMessage());
        }

        db.close();


    }

    public void insertDays(String id, ArrayList<String> days){

        SQLiteDatabase db = this.getWritableDatabase();

        for(String day : days)
        {
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("day", day);

            try
            {
                db.insert("days", null, values);
            }
            catch (SQLiteException e)
            {
                Log.e(TAG, e.getMessage());
            }
        }

        db.close();

    }

    public void updateDays(String id, ArrayList<String> days){

        String query = "DELETE FROM days WHERE id = " + id;

        SQLiteDatabase db = this.getWritableDatabase();

        try
        {
            db.execSQL(query);
        }
        catch (SQLiteException e)
        {
            Log.e(TAG, e.getMessage());
        }

        for(String day : days)
        {
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("day", day);

            try
            {
                db.insert("days", null, values);
            }
            catch (SQLiteException e)
            {
                Log.e(TAG, e.getMessage());
            }
        }

        db.close();

    }

    public int updateShow(HashMap<String, String> query){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", query.get("name"));
        values.put("time", query.get("time"));
        values.put("notification", query.get("notification"));
        values.put("channel", query.get("channel"));

        return db.update("show", values,"id " + " = ?", new String[] { query.get("id") });

    }

    public void deleteShow(String id){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM show WHERE id = " + id;

        try
        {
            db.execSQL(query);
        }
        catch (SQLiteException e)
        {
            Log.e(TAG, e.getMessage());
        }

        query = "DELETE FROM days WHERE id = " + id;

        try
        {
            db.execSQL(query);
        }
        catch (SQLiteException e)
        {
            Log.e(TAG, e.getMessage());
        }

        db.close();

    }

    public ArrayList<HashMap<String, String>> selectShows(){

        ArrayList<HashMap<String, String>> shows = new ArrayList<HashMap<String, String>>();

        String query = "SELECT * FROM show";
        String days = new String();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if(cursor.moveToFirst())
        {

            do
            {

                query = "SELECT day FROM days where id = " + cursor.getString(0);

                Cursor daysCursor = db.rawQuery(query, null);

                if(daysCursor.moveToFirst())
                {

                    days = "";
                    do
                    {
                        if(daysCursor.getString(0).equals(new String("Sunday")))
                        {
                            days+="Su ";
                        }
                        if(daysCursor.getString(0).equals(new String("Monday")))
                        {
                            days+="Mo ";
                        }
                        if(daysCursor.getString(0).equals(new String("Tuesday")))
                        {
                            days+="Tu ";
                        }
                        if(daysCursor.getString(0).equals(new String("Wednesday")))
                        {
                            days+="We ";
                        }
                        if(daysCursor.getString(0).equals(new String("Thursday")))
                        {
                            days+="Th ";
                        }
                        if(daysCursor.getString(0).equals(new String("Friday")))
                        {
                            days+="Fr ";
                        }
                        if(daysCursor.getString(0).equals(new String("Saturday")))
                        {
                            days+="Sa ";
                        }
                    }while(daysCursor.moveToNext());
                }

                HashMap<String, String> row = new HashMap<String, String>();

                row.put("id", cursor.getString(0));
                row.put("name", cursor.getString(1));
                row.put("time", cursor.getString(2));
                row.put("channel", cursor.getString(3));
                row.put("notification", cursor.getString(4));
                row.put("days", days);

                shows.add(row);

            }while(cursor.moveToNext());

        }

        return shows;
    }

    public HashMap<String, String> selectShow(String id){

        HashMap<String, String> row = new HashMap<String, String>();

        String query = "SELECT * FROM show WHERE id = " + id;
        String days = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if(cursor.moveToFirst()){

            String idx = cursor.getString(0);

            query = "SELECT day FROM days where id = " + idx;

            Cursor daysCursor = db.rawQuery(query, null);

            if(daysCursor.moveToFirst())
            {
                do
                {
                    if(daysCursor.getString(0).equals(new String("Sunday")))
                    {
                        days+="Su ";
                    }
                    if(daysCursor.getString(0).equals(new String("Monday")))
                    {
                        days+="Mo ";
                    }
                    if(daysCursor.getString(0).equals(new String("Tuesday")))
                    {
                        days+="Tu ";
                    }
                    if(daysCursor.getString(0).equals(new String("Wednesday")))
                    {
                        days+="We ";
                    }
                    if(daysCursor.getString(0).equals(new String("Thursday")))
                    {
                        days+="Th ";
                    }
                    if(daysCursor.getString(0).equals(new String("Friday")))
                    {
                        days+="Fr ";
                    }
                    if(daysCursor.getString(0).equals(new String("Saturday")))
                    {
                        days+="Sa ";
                    }
                }while(daysCursor.moveToNext());
            }

            do {

                row.put("id", cursor.getString(0));
                row.put("name", cursor.getString(1));
                row.put("time", cursor.getString(2));
                row.put("channel", cursor.getString(3));
                row.put("notification", cursor.getString(4));
                row.put("days", days);

            }while(cursor.moveToNext());
        }

        return row;
    }

    public HashMap<String, String> selectLastShowId(){

        HashMap<String, String> row = new HashMap<String, String>();

        String query = "SELECT * FROM show WHERE id = (SELECT max(id) FROM show)";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if(cursor.moveToFirst()){

            do {

                row.put("id", cursor.getString(0));
                row.put("name", cursor.getString(1));
                row.put("time", cursor.getString(2));
                row.put("channel", cursor.getString(3));
                row.put("notification", cursor.getString(4));

            }while(cursor.moveToNext());
        }

        return row;
    }

    public ArrayList<String> selectDays(String id){

        ArrayList<String> days = new ArrayList<String>();

        String query = "SELECT day FROM days WHERE id = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if(cursor.moveToFirst()){

            do {
                days.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }

        return days;
    }
}
