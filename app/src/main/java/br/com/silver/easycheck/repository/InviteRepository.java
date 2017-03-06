package br.com.silver.easycheck.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.silver.easycheck.model.Invite;
import br.com.silver.easycheck.util.SilverSQLHelper;

/**
 * Created by silver on 28/04/16.
 */
public class InviteRepository extends SilverSQLHelper {

    public InviteRepository(Context ctx) {
        super(ctx);
    }

    public long insert(Invite invite){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, invite.name);
        cv.put(COLUMN_DATE, invite.date);
        cv.put(COLUMN_CODE, invite.code);
        cv.put(COLUMN_STATUS, invite.status);
        return getWritableDatabase().insert(TABLE_INVITE, null, cv);
    }

    public List<Invite> getAll() {
        String sql = "SELECT * FROM " + TABLE_INVITE;
        sql += " ORDER BY " + COLUMN_ID;
        Cursor cursor = getWritableDatabase().rawQuery(sql, null);
        List<Invite> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(
                new Invite(
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CODE)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS))
                )
            );
        }
        cursor.close();
        close();
        return list;
    }

    public int total(){
        String sql = "SELECT COUNT(*) FROM " + TABLE_INVITE;
        Cursor cursor = getWritableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public void deleteAll() {
        getWritableDatabase().delete(TABLE_INVITE, null, null);
    }
}
