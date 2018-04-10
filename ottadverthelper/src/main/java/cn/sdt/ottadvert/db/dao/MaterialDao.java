package cn.sdt.ottadvert.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.sdt.ottadvert.IoUtils;
import cn.sdt.ottadvert.db.AdvertDbHelper;
import cn.sdt.ottadvert.db.bean.Material;

/**
 * Created by SDT13411 on 2018/3/21.
 */

public class MaterialDao implements IDao<Material> {

    private final static String TAG = "MaterialDao";
    final static String TBNAME = "Material";

    public final static String CREATE_SQL = String.format("CREATE TABLE %s(" +
            "id integer primary key autoincrement," +
            "width int not null," +
            "height int not null," +
            "materialUrl text not null," +
            "md5 text not null," +
            "spaceCode text not null," +
            "materialType int not null," +
            "duration int not null," +
            "materialPath text not null," +
            "scheduleId int not null," +
            "materialId int not null," +
            "actionType int not null," +
            "action text," +
            "monitorUrl text," +
            "startTime char(16)," +
            "endTime char(16)," +
            "orderNo char(8)," +
            "deliverySpeed int ," +
            "deliveryLimit int ," +
            "playCount int ," +
            "isCurrent char(8));", TBNAME);

    Context context;
    AdvertDbHelper dbHelper;

    public MaterialDao(Context context) {
        this.context = context;
        this.dbHelper = new AdvertDbHelper(context);
    }

    public Material query(int id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TBNAME, null, "id=?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                Material material = cursor2Bean(cursor);
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

    @Nullable
    @Override
    public List<Material> queryAll() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TBNAME, null, null,
                    null,
                    null, null, null);
            List<Material> materialList = new ArrayList<>();
            Material material = null;
            while (cursor.moveToNext()) {
                material = cursor2Bean(cursor);
                materialList.add(material);
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
    public long insert(@NonNull Material material) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        ContentValues values = bean2ContentValues(material);
        long rowid = sqLiteDatabase.insert(TBNAME, null, values);
        Log.d(TAG, "rowid:" + rowid);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        IoUtils.close(sqLiteDatabase);
        return rowid;
    }


    public int update(Material material) {
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

    public void incrementPlayCount(int id) {
        String sql = String.format("update %s set playCount=playCount+1 where id=?;", TBNAME);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        try {
            sqLiteDatabase.execSQL(sql, new String[]{String.valueOf(id)});
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(sqLiteDatabase);
        }
    }


    private ContentValues bean2ContentValues(Material material) {
        ContentValues values = new ContentValues();
        values.put("width", material.getWidth());
        values.put("height", material.getHeight());
        values.put("materialUrl", material.getMaterialUrl());
        values.put("md5", material.getMd5());
        values.put("spaceCode", material.getSpaceCode());
        values.put("materialType", material.getMaterialType());
        values.put("duration", material.getDuration());
        values.put("materialPath", material.getMaterialPath());
        values.put("scheduleId", material.getScheduleId());
        values.put("materialId", material.getMaterialId());
        values.put("actionType", material.getActionType());
        values.put("action", material.getAction());
        values.put("monitorUrl", material.getMonitorUrl());
        values.put("startTime", material.getStartTime());
        values.put("endTime", material.getEndTime());
        values.put("orderNo", material.getOrderNo());
        values.put("deliverySpeed", material.getDeliverySpeed());
        values.put("deliveryLimit", material.getDeliveryLimit());
        values.put("playCount", material.getPlayCount());
        values.put("isCurrent", material.getIsCurrent());
        return values;
    }

    private Material cursor2Bean(Cursor cursor) {
        int idxId = cursor.getColumnIndex("id");
        int idxWidth = cursor.getColumnIndex("width");
        int idxHeight = cursor.getColumnIndex("height");
        int idxMaterialUrl = cursor.getColumnIndex("materialUrl");
        int idxMd5 = cursor.getColumnIndex("md5");
        int idxSpaceCode = cursor.getColumnIndex("spaceCode");
        int idxMaterialType = cursor.getColumnIndex("materialType");
        int idxDuration = cursor.getColumnIndex("duration");
        int idxMaterialPath = cursor.getColumnIndex("materialPath");
        int idxScheduleId = cursor.getColumnIndex("scheduleId");
        int idxMaterialId = cursor.getColumnIndex("materialId");
        int idxActionType = cursor.getColumnIndex("actionType");
        int idxAction = cursor.getColumnIndex("action");
        int idxMonitorUrl = cursor.getColumnIndex("monitorUrl");
        int idxStartTime = cursor.getColumnIndex("startTime");
        int idxEndTime = cursor.getColumnIndex("endTime");
        int idxOrderNo = cursor.getColumnIndex("orderNo");
        int idxDeliverySpeed = cursor.getColumnIndex("deliverySpeed");
        int idxDeliveryLimit = cursor.getColumnIndex("deliveryLimit");
        int idxPlayCount = cursor.getColumnIndex("playCount");
        int idxIsCurrent = cursor.getColumnIndex("isCurrent");

        int id = cursor.getInt(idxId);
        int width = cursor.getInt(idxWidth);
        int height = cursor.getInt(idxHeight);
        String materialUrl = cursor.getString(idxMaterialUrl);
        String spaceCode = cursor.getString(idxSpaceCode);
        int materialType = cursor.getInt(idxMaterialType);
        int duration = cursor.getInt(idxDuration);
        String materialPath = cursor.getString(idxMaterialPath);
        String md5 = cursor.getString(idxMd5);
        int scheduleId = cursor.getInt(idxScheduleId);
        int materialId = cursor.getInt(idxMaterialId);
        int actionType = cursor.getInt(idxActionType);
        String action = cursor.getString(idxAction);
        String monitorUrl = cursor.getString(idxMonitorUrl);
        long startTime = cursor.getLong(idxStartTime);
        long endTime = cursor.getLong(idxEndTime);
        String orderNo = cursor.getString(idxOrderNo);
        int deliverySpeed = cursor.getInt(idxDeliverySpeed);
        int deliveryLimit = cursor.getInt(idxDeliveryLimit);
        int playCount = cursor.getInt(idxPlayCount);
        String isCurrent = cursor.getString(idxIsCurrent);


        Material material = new Material();
        material.setId(id);
        material.setWidth(width);
        material.setHeight(height);
        material.setMaterialUrl(materialUrl);
        material.setSpaceCode(spaceCode);
        material.setMd5(md5);
        material.setMaterialType(materialType);
        material.setDuration(duration);
        material.setMaterialPath(materialPath);
        material.setScheduleId(scheduleId);
        material.setMaterialId(materialId);
        material.setActionType(actionType);
        material.setAction(action);
        material.setMonitorUrl(monitorUrl);
        material.setStartTime(startTime);
        material.setEndTime(endTime);
        material.setOrderNo(orderNo);
        material.setDeliverySpeed(deliverySpeed);
        material.setDeliveryLimit(deliveryLimit);
        material.setPlayCount(playCount);
        material.setIsCurrent(isCurrent);

        return material;
    }


}
