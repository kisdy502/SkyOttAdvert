package cn.sdt.ottadvert.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.sdt.ottadvert.IoUtils;
import cn.sdt.ottadvert.db.AdvertDbHelper;
import cn.sdt.ottadvert.db.bean.DefaultMaterial;

/**
 * Created by SDT13411 on 2018/3/21.
 */

public class DefaultMaterialDao implements IDao<DefaultMaterial> {

    private final static String TAG = "MaterialDao";
    private final static String TBNAME = "DefaultMaterial";
    public final static String CREATE_SQL = String.format("create table %s (" +
            "id integer primary key autoincrement," +
            "width int not null," +
            "height int not null," +
            "materialUrl text not null," +
            "md5 text not null," +
            "spaceCode text not null," +
            "materialType int not null," +
            "duration int not null," +
            "materialPath text not null);", TBNAME);

    Context context;
    AdvertDbHelper dbHelper;

    public DefaultMaterialDao(Context context) {
        this.context = context;
        this.dbHelper = new AdvertDbHelper(context);
    }

    @Override
    public DefaultMaterial query(int materialId) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TBNAME, null, "id=?",
                    new String[]{String.valueOf(materialId)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                DefaultMaterial material = cursor2Bean(cursor);
                return material;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(cursor);
            IoUtils.close(sqLiteDatabase);
        }
        return null;
    }

    @Override
    public List<DefaultMaterial> queryAll() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TBNAME, null, null,
                    null,
                    null, null, null);
            List<DefaultMaterial> materialList = new ArrayList<>();
            DefaultMaterial defaultMaterial = null;
            while (cursor.moveToNext()) {
                defaultMaterial = cursor2Bean(cursor);
                materialList.add(defaultMaterial);
            }
            return materialList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(cursor);
            IoUtils.close(sqLiteDatabase);
        }
        return null;
    }

    @Override
    public long insert(DefaultMaterial material) {
        ContentValues values = bean2ContentValues(material);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        long rowid = sqLiteDatabase.insert(TBNAME, null, values);
        Log.d(TAG, "rowid:" + rowid);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        IoUtils.close(sqLiteDatabase);
        return rowid;
    }

    @Override
    public int update(DefaultMaterial material) {
        ContentValues values = bean2ContentValues(material);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        int result = sqLiteDatabase.update(TBNAME, values, "id=?",
                new String[]{String.valueOf(material.getId())});
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


    private ContentValues bean2ContentValues(DefaultMaterial defaultMaterial) {
        ContentValues values = new ContentValues();
        values.put("width", defaultMaterial.getWidth());
        values.put("height", defaultMaterial.getHeight());
        values.put("md5", defaultMaterial.getMd5());
        values.put("materialUrl", defaultMaterial.getMaterialUrl());
        values.put("spaceCode", defaultMaterial.getSpaceCode());
        values.put("materialType", defaultMaterial.getMaterialType());
        values.put("duration", defaultMaterial.getDuration());
        values.put("materialPath", defaultMaterial.getMaterialPath());
        return values;
    }

    private DefaultMaterial cursor2Bean(Cursor cursor) {
        int idxId = cursor.getColumnIndex("id");
        int idxWidth = cursor.getColumnIndex("width");
        int idxHeight = cursor.getColumnIndex("height");
        int idxMaterialUrl = cursor.getColumnIndex("materialUrl");
        int idxMd5 = cursor.getColumnIndex("md5");
        int idxSpaceCode = cursor.getColumnIndex("spaceCode");
        int idxMaterialType = cursor.getColumnIndex("materialType");
        int idxDuration = cursor.getColumnIndex("duration");
        int idxMaterialPath = cursor.getColumnIndex("materialPath");
        int id = cursor.getInt(idxId);
        int width = cursor.getInt(idxWidth);
        int height = cursor.getInt(idxHeight);
        String materialUrl = cursor.getString(idxMaterialUrl);
        String spaceCode = cursor.getString(idxSpaceCode);
        int materialType = cursor.getInt(idxMaterialType);
        int duration = cursor.getInt(idxDuration);
        String materialPath = cursor.getString(idxMaterialPath);
        String md5 = cursor.getString(idxMd5);
        DefaultMaterial material = new DefaultMaterial();
        material.setId(id);
        material.setWidth(width);
        material.setHeight(height);
        material.setMaterialUrl(materialUrl);
        material.setSpaceCode(spaceCode);
        material.setMd5(md5);
        material.setMaterialType(materialType);
        material.setDuration(duration);
        material.setMaterialPath(materialPath);
        return material;
    }
}
