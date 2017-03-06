package br.com.silver.easycheck.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by silver on 28/04/16.
 */
public class SilverSQLHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_easycheck";
    private static final int  DB_VERSION = 1;

    public static final String TABLE_INVITE = "invite";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_STATUS = "status";

    public SilverSQLHelper(Context ctx){
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("SilverSQLHelper", "Criando base de dados");
        createTableInvite(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTableInvite(SQLiteDatabase db){
        String sql = "CREATE TABLE " + TABLE_INVITE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_DATE + " TEXT," +
                COLUMN_CODE + " TEXT," +
                COLUMN_STATUS + " INTEGER )";
        db.execSQL(sql);
        Log.i("SilverSQLHelper", sql);
    }
}
