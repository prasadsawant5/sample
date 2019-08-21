package storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import constants.ApplicationConstants;
import models.GeneralInformation;

/**
 * Created by prasadsawant on 11/18/16.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ApplicationConstants.CREATE_GENERAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ApplicationConstants.DROP_GENERAL_TABLE);
        onCreate(db);
    }


    public void setGeneralInformation(GeneralInformation generalInformation) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ApplicationConstants.HOME_FRAGMENT_COLUMN_VALUE, generalInformation.getSunrise());
        contentValues.put(ApplicationConstants.HOME_FRAGMENT_COLUMN_VALUE, generalInformation.getSunset());
        contentValues.put(ApplicationConstants.HOME_FRAGMENT_COLUMN_VALUE, generalInformation.getCity());

        db.insert(ApplicationConstants.HOME_FRAGMENT_TABLE_GENERAL, null, contentValues);
        db.close();
    }
}
