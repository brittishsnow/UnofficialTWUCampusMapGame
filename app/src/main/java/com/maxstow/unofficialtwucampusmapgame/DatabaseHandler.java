package com.maxstow.unofficialtwucampusmapgame;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "locationsDB.db";
    public static final String TABLE_LOCATIONS = "locations";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LOCATIONNAME = "locationname";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    //We need to pass database information along to superclass
    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_LOCATIONS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LOCATIONNAME + " TEXT " +
                COLUMN_LATITUDE + " TEXT " +
                COLUMN_LONGITUDE + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        onCreate(db);
    }

    //Add a new row to the database
    public void addLocation(Locations locations){
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATIONNAME, locations.get_locationname());
        values.put(COLUMN_LATITUDE, String.valueOf(locations.get_latitude()));
        values.put(COLUMN_LONGITUDE, String.valueOf(locations.get_longitude()));
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LOCATIONS, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deleteLocation(String locationName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_LOCATIONS + " WHERE " + COLUMN_LOCATIONNAME + "=\"" + locationName + "\";");
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_LOCATIONS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("locationname")) != null) {
                dbString += c.getString(c.getColumnIndex("locationname"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

}