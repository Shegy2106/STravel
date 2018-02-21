package com.example.darko.stravel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Darko on 13.1.2018..
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "STravel";

    // Table Names
    private static final String TABLE_PLACES = "Places";
    private static final String TABLE_EVENTS = "Events";

    // Common column names
    private static final String KEY_ID_PLACE = "ID_place";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_NAME = "name";

    // PLACES Table - column names
    private static final String KEY_REVIEW = "review";
    private static final String KEY_TYPE = "type";
    private static final String KEY_SUBTYPE = "subtype";
    private static final String KEY_WORK_TIME_BEGIN = "work_time_begin";
    private static final String KEY_WORK_TIME_END = "work_time_end";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_TELEPHONE_NUMBER = "telephone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_WEBSITE = "website";

    // EVENTS Table - column names
    private static final String KEY_ID_EVENT = "ID_event";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";


    // Table Create Statements
    // PLACES table create statement
    private static final String CREATE_TABLE_PLACES = "CREATE TABLE "
            + TABLE_PLACES + "(" + KEY_ID_PLACE + " INTEGER PRIMARY KEY," + KEY_REVIEW
            + " REAL," + KEY_NAME + " TEXT," + KEY_TYPE +" TEXT," + KEY_SUBTYPE +" TEXT,"
            + KEY_DESCRIPTION +" TEXT," + KEY_WORK_TIME_BEGIN + " TEXT," + KEY_WORK_TIME_END + " TEXT,"
            + KEY_ADDRESS + " TEXT," + KEY_TELEPHONE_NUMBER + " TEXT," + KEY_EMAIL + " TEXT," + KEY_WEBSITE
            + " TEXT" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_EVENTS
            + "(" + KEY_ID_EVENT + " INTEGER PRIMARY KEY," + KEY_ID_PLACE + " INTEGER FOREIGN KEY REFERENCES "
            + TABLE_PLACES + "(" + KEY_ID_PLACE +")," + KEY_NAME +" TEXT," + KEY_START_TIME +" TEXT,"
            + KEY_END_TIME + " TEXT," + KEY_DESCRIPTION + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_PLACES);
        db.execSQL(CREATE_TABLE_EVENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);

        // create new tables
        onCreate(db);
    }


    public List<TablePlaces> getAllPlaces() {
        List<TablePlaces> places = new ArrayList<TablePlaces>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLACES +";";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                TablePlaces pl = new TablePlaces();
                pl.setID_place(c.getInt((c.getColumnIndex(KEY_ID_PLACE))));
                pl.setReview(c.getFloat(c.getColumnIndex(KEY_REVIEW)));
                pl.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                pl.setType((c.getString(c.getColumnIndex(KEY_TYPE))));
                pl.setSubtype((c.getString(c.getColumnIndex(KEY_SUBTYPE))));
                pl.setDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
                pl.setWork_time_begin((c.getString(c.getColumnIndex(KEY_WORK_TIME_BEGIN))));
                pl.setWork_time_end((c.getString(c.getColumnIndex(KEY_WORK_TIME_END))));
                pl.setAddress((c.getString(c.getColumnIndex(KEY_ADDRESS))));
                pl.setTelephone_number((c.getString(c.getColumnIndex(KEY_TELEPHONE_NUMBER))));
                pl.setEmail((c.getString(c.getColumnIndex(KEY_EMAIL))));
                pl.setWebsite((c.getString(c.getColumnIndex(KEY_WEBSITE))));

                places.add(pl);
            } while (c.moveToNext());
        }

        return places;
    }


    public List<TableEvents> getAllEvents() {
        List<TableEvents> events = new ArrayList<TableEvents>();
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS + ";";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                TableEvents ev = new TableEvents();
                ev.setID_event(c.getInt((c.getColumnIndex(KEY_ID_EVENT))));
                ev.setID_place(c.getInt((c.getColumnIndex(KEY_ID_PLACE))));
                ev.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                ev.setStart_time(c.getString(c.getColumnIndex(KEY_START_TIME)));
                ev.setEnd_time(c.getString(c.getColumnIndex(KEY_END_TIME)));
                ev.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));

                events.add(ev);
            } while (c.moveToNext());
        }

        return events;
    }

    /*
    * get single place
    * */
    public TablePlaces getPlace(long id_place) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PLACES + " WHERE "
                + KEY_ID_PLACE + " = " + id_place + ";";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        TablePlaces pl = new TablePlaces();
        pl.setID_place(c.getInt((c.getColumnIndex(KEY_ID_PLACE))));
        pl.setReview(c.getFloat(c.getColumnIndex(KEY_REVIEW)));
        pl.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        pl.setType((c.getString(c.getColumnIndex(KEY_TYPE))));
        pl.setSubtype((c.getString(c.getColumnIndex(KEY_SUBTYPE))));
        pl.setDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
        pl.setWork_time_begin((c.getString(c.getColumnIndex(KEY_WORK_TIME_BEGIN))));
        pl.setWork_time_end((c.getString(c.getColumnIndex(KEY_WORK_TIME_END))));
        pl.setAddress((c.getString(c.getColumnIndex(KEY_ADDRESS))));
        pl.setTelephone_number((c.getString(c.getColumnIndex(KEY_TELEPHONE_NUMBER))));
        pl.setEmail((c.getString(c.getColumnIndex(KEY_EMAIL))));
        pl.setWebsite((c.getString(c.getColumnIndex(KEY_WEBSITE))));

        return pl;
    }

    /*
    * getting all places under single subtype
    * */
    public List<TablePlaces> getAllPlacesBySubtype(String subtype) {
        List<TablePlaces> places = new ArrayList<TablePlaces>();

        String selectQuery = "SELECT  * FROM " + TABLE_PLACES + " pl "
                + "WHERE pl." + KEY_SUBTYPE + " = '" + subtype + "' ;";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                TablePlaces pl = new TablePlaces();
                pl.setID_place(c.getInt((c.getColumnIndex(KEY_ID_PLACE))));
                pl.setReview(c.getFloat(c.getColumnIndex(KEY_REVIEW)));
                pl.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                pl.setType((c.getString(c.getColumnIndex(KEY_TYPE))));
                pl.setSubtype((c.getString(c.getColumnIndex(KEY_SUBTYPE))));
                pl.setDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
                pl.setWork_time_begin((c.getString(c.getColumnIndex(KEY_WORK_TIME_BEGIN))));
                pl.setWork_time_end((c.getString(c.getColumnIndex(KEY_WORK_TIME_END))));
                pl.setAddress((c.getString(c.getColumnIndex(KEY_ADDRESS))));
                pl.setTelephone_number((c.getString(c.getColumnIndex(KEY_TELEPHONE_NUMBER))));
                pl.setEmail((c.getString(c.getColumnIndex(KEY_EMAIL))));
                pl.setWebsite((c.getString(c.getColumnIndex(KEY_WEBSITE))));

                places.add(pl);
            } while (c.moveToNext());
        }

        return places;
    }


    /*
    * getting place where event happens
    * */
    public TablePlaces getPlaceWhereEventHappens(int ID_place) {
        TablePlaces pl = new TablePlaces();

        String selectQuery = "SELECT  * FROM " + TABLE_PLACES + " pl, "
                + TABLE_EVENTS + " ev " + " WHERE pl." + KEY_ID_PLACE
                + " = ev." + KEY_ID_PLACE + ";";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        pl.setID_place(c.getInt((c.getColumnIndex(KEY_ID_PLACE))));
        pl.setReview(c.getFloat(c.getColumnIndex(KEY_REVIEW)));
        pl.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        pl.setType((c.getString(c.getColumnIndex(KEY_TYPE))));
        pl.setSubtype((c.getString(c.getColumnIndex(KEY_SUBTYPE))));
        pl.setDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
        pl.setWork_time_begin((c.getString(c.getColumnIndex(KEY_WORK_TIME_BEGIN))));
        pl.setWork_time_end((c.getString(c.getColumnIndex(KEY_WORK_TIME_END))));
        pl.setAddress((c.getString(c.getColumnIndex(KEY_ADDRESS))));
        pl.setTelephone_number((c.getString(c.getColumnIndex(KEY_TELEPHONE_NUMBER))));
        pl.setEmail((c.getString(c.getColumnIndex(KEY_EMAIL))));
        pl.setWebsite((c.getString(c.getColumnIndex(KEY_WEBSITE))));

        return pl;
    }






}
