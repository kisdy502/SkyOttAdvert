package cn.sdt.ottadvert.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.sdt.ottadvert.IoUtils;
import cn.sdt.ottadvert.db.AdvertDbHelper;
import cn.sdt.ottadvert.db.bean.LastPlayDate;

/**
 * Created by Administrator on 2018/3/26.
 */

public class LastPlayDateDao implements IDao<LastPlayDate> {
    private final static String TAG = "LastPlayDateDao";
    final static String TBNAME = "LastPlayDate";

    public final static String CREATE_SQL = String.format("CREATE TABLE [%s](\n" +
            "  [id] integer PRIMARY KEY AUTOINCREMENT, \n" +
            "  [scheduleId] int NOT NULL, \n" +
            "  [lastPlayTimestamp] long NOT NULL);", TBNAME);

    Context context;
    AdvertDbHelper dbHelper;

    public LastPlayDateDao(Context context) {
        this.context = context;
        this.dbHelper = new AdvertDbHelper(context);
    }

    @Nullable
    @Override
    public LastPlayDate query(int id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TBNAME, null, "id=?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                LastPlayDate lastPlayDate = cursor2Bean(cursor);
                return lastPlayDate;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(cursor);
            IoUtils.close(sqLiteDatabase);
        }
        return null;
    }

    @Nullable
    @Override
    public List<LastPlayDate> queryAll() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TBNAME, null, null,
                    null,
                    null, null, null);
            List<LastPlayDate> lastPlayDateList = new ArrayList<>();
            LastPlayDate lastPlayDate = null;
            while (cursor.moveToNext()) {
                lastPlayDate = cursor2Bean(cursor);
                lastPlayDateList.add(lastPlayDate);
            }
            return lastPlayDateList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(cursor);
            IoUtils.close(sqLiteDatabase);
        }
        return null;
    }

    @Override
    public long insert(@NonNull LastPlayDate lastPlayDate) {
        ContentValues values = bean2ContentValues(lastPlayDate);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        long rowid = sqLiteDatabase.insert(TBNAME, null, values);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        IoUtils.close(sqLiteDatabase);
        return rowid;
    }

    @Override
    public int update(@NonNull LastPlayDate lastPlayDate) {
        ContentValues values = bean2ContentValues(lastPlayDate);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        int result = sqLiteDatabase.update(TBNAME, values, "id=?",
                new String[]{String.valueOf(lastPlayDate.getId())});
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        IoUtils.close(sqLiteDatabase);
        return result;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        int result = sqLiteDatabase.delete(TBNAME, "id=?", new String[]{String.valueOf(id)});
        IoUtils.close(sqLiteDatabase);
        return result;
    }

    @Override
    public int deleteAll() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        int result = sqLiteDatabase.delete(TBNAME, null, null);
        IoUtils.close(sqLiteDatabase);
        return result;
    }


    private ContentValues bean2ContentValues(LastPlayDate lastPlayDate) {
        ContentValues values = new ContentValues();
        values.put("lastPlayTimestamp", lastPlayDate.getLastPlayTimestamp());
        values.put("scheduleId", lastPlayDate.getScheduleId());
        return values;
    }

    private LastPlayDate cursor2Bean(Cursor cursor) {
        int idxId = cursor.getColumnIndex("id");
        int idxScheduleId = cursor.getColumnIndex("lastPlayTimestamp");
        int idxLastPlayTimestamp = cursor.getColumnIndex("scheduleId");
        int id = cursor.getInt(idxId);
        int scheduleId = cursor.getInt(idxScheduleId);
        long lastime = cursor.getLong(idxLastPlayTimestamp);
        LastPlayDate lastPlayDate = new LastPlayDate();
        lastPlayDate.setId(id);
        lastPlayDate.setScheduleId(scheduleId);
        lastPlayDate.setLastPlayTimestamp(lastime);
        return lastPlayDate;

    }
}
