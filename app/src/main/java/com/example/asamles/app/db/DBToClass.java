package com.example.asamles.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.asamles.app.R;

import java.io.IOException;
import java.util.ArrayList;


public class DBToClass {

    private ArrayList<Animals> animals;
    private DataBaseHelper myDbHelper;
    private Context context;
    private DBToClassListener callback;
    private SQLiteDatabase db;

    public interface DBToClassListener {
        public void onDBToClassListener(ArrayList<Animals> animals);
    }

    public DBToClass(final Context context) {
        this.context = context;
        myDbHelper = new DataBaseHelper(this.context, "animals.db");
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            Toast.makeText(context,
                    context.getString(R.string.error), Toast.LENGTH_SHORT).show();
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            Toast.makeText(context,
                    context.getString(R.string.error), Toast.LENGTH_SHORT).show();
            throw sqle;
        }

    }

    public void setDBToClassListener(DBToClassListener callback) {
        this.callback = callback;
    }

    public void getDataFromDB() {
        db = myDbHelper.getWritableDatabase();

        Cursor c = db.query("animals", null, null, null, null, null, null);

        animals = new ArrayList<Animals>();

        int idColIndex = c.getColumnIndex("_id");
        int nameColIndex = c.getColumnIndex("name");
        int typeColIndex = c.getColumnIndex("type");
        int contentColIndex = c.getColumnIndex("content");
        int imgColIndex = c.getColumnIndex("img");

        Animals animal = new Animals();
        if (c.moveToFirst()) {
            do {
                animal.setId(c.getString(idColIndex));
                animal.setName(c.getString(nameColIndex));
                animal.setContent(c.getString(contentColIndex));
                animal.setType(c.getInt(typeColIndex));
                animal.setImg(c.getString(imgColIndex));
                animals.add(animal);
                animal = new Animals();
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        callback.onDBToClassListener(animals);
    }
}