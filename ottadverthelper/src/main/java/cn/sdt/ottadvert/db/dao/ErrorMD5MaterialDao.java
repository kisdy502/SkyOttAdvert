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
import cn.sdt.ottadvert.db.bean.ErrorMD5Material;

/**
 * Created by Administrator on 2018/3/26.
 */

public class ErrorMD5MaterialDao implements IDao<ErrorMD5Material> {

    private final static String TAG = "ErrorMD5MaterialDao";
    final static String TBNAME = "ErrorMD5Material";

    public final static String CREATE_SQL = String.format("CREATE TABLE [%s](\n" +
            "  [id] integer PRIMARY KEY AUTOINCREMENT, \n" +
            "  [scheduleId] int NOT NULL, \n" +
            "  [materialId] int NOT NULL, \n" +
            "  [materialUrl] text NOT NULL, \n" +
            "  [md5] text NOT NULL, \n" +
            "  [createTimestamp] long NOT NULL);\n", TBNAME);

    Context context;
    AdvertDbHelper dbHelper;

    public ErrorMD5MaterialDao(Context context) {
        this.context = context;
        this.dbHelper = new AdvertDbHelper(context);
    }

    @Nullable
    @Override
    public ErrorMD5Material query(int id) {
        return null;
    }

    @Nullable
    @Override
    public List<ErrorMD5Material> queryAll() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TBNAME, null, null,
                    null,
                    null, null, null);
            List<ErrorMD5Material> eorroMaterials = new ArrayList<>();
            ErrorMD5Material eorroMaterial = null;
            while (cursor.moveToNext()) {
                eorroMaterial = cursor2Bean(cursor);
                eorroMaterials.add(eorroMaterial);
            }
            return eorroMaterials;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(cursor);
            IoUtils.close(sqLiteDatabase);
        }
        return null;
    }

    @Override
    public long insert(@NonNull ErrorMD5Material eorroMaterial) {
        ContentValues values = bean2ContentValues(eorroMaterial);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        long rowid = sqLiteDatabase.insert(TBNAME, null, values);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        IoUtils.close(sqLiteDatabase);
        return rowid;
    }

    @Override
    public int update(@NonNull ErrorMD5Material eorroMaterial) {
        ContentValues values = bean2ContentValues(eorroMaterial);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        int result = sqLiteDatabase.update(TBNAME, values, "id=?",
                new String[]{String.valueOf(eorroMaterial.getId())});
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        IoUtils.close(sqLiteDatabase);
        return result;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int deleteAll() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        int result = sqLiteDatabase.delete(TBNAME, null, null);
        IoUtils.close(sqLiteDatabase);
        return result;
    }

    private ContentValues bean2ContentValues(ErrorMD5Material eorroMaterial) {
        ContentValues values = new ContentValues();
        values.put("scheduleId", eorroMaterial.getScheduleId());
        values.put("materialId", eorroMaterial.getMaterialId());
        values.put("md5", eorroMaterial.getMd5());
        values.put("materialUrl", eorroMaterial.getMaterialUrl());
        values.put("createTimestamp", eorroMaterial.getCreateTimestamp());
        return values;
    }

    private ErrorMD5Material cursor2Bean(Cursor cursor) {
        int idxId = cursor.getColumnIndex("id");
        int idxScheduleId = cursor.getColumnIndex("scheduleId");
        int idxMaterialId = cursor.getColumnIndex("materialId");
        int idxMd5 = cursor.getColumnIndex("md5");
        int idxMaterialUrl = cursor.getColumnIndex("materialUrl");
        int idxCreateTimestamp = cursor.getColumnIndex("createTimestamp");

        int id = cursor.getInt(idxId);
        int scheduleId = cursor.getInt(idxScheduleId);
        int materialId = cursor.getInt(idxMaterialId);
        String md5 = cursor.getString(idxMd5);
        String materialUrl = cursor.getString(idxMaterialUrl);
        long createTimestamp = cursor.getLong(idxCreateTimestamp);

        ErrorMD5Material eorroMaterial = new ErrorMD5Material();
        eorroMaterial.setId(id);
        eorroMaterial.setScheduleId(scheduleId);
        eorroMaterial.setMaterialId(materialId);
        eorroMaterial.setMd5(md5);
        eorroMaterial.setMaterialUrl(materialUrl);
        eorroMaterial.setCreateTimestamp(createTimestamp);
        return eorroMaterial;

    }
}
