package com.example.asamles.app.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.example.asamles.app/databases/";
    private static String DB_NAME = "animals.db";
    private SQLiteDatabase myDataBase;
    private final Context mContext;

    public DataBaseHelper(Context context, String dbName) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
//		this.DB_NAME = dbName;
    }


    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        SQLiteDatabase myDataBase;
        if (dbExist) {
        } else {
            myDataBase = this.getReadableDatabase();
            myDataBase.close();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw e;
            }
        }
    }


    private boolean checkDataBase() {

        String myPath = DB_PATH + DB_NAME;
        return mContext.getDatabasePath(myPath).exists();
    }

    private void copyDataBase() throws IOException {

        InputStream myInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}