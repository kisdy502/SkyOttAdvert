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
import cn.sdt.ottadvert.db.bean.TempRecord;

/**
 * Created by Administrator on 2018/3/26.
 */

public class TempRecordDao implements IDao<TempRecord> {

    private final static String TAG = "TempRecordDao";
    final static String TBNAME = "TempRecord";

    public final static String CREATE_SQL = String.format("CREATE TABLE [%s](\n" +
            "  [id] integer PRIMARY KEY AUTOINCREMENT, \n" +
            "  [regionId] char(8) NOT NULL, \n" +
            "  [cityId] char(8) NULL, \n" +
            "  [channelType] char(20) NOT NULL, \n" +
            "  [channelId] char(20) NOT NULL, \n" +
            "  [modelId] char(20) NOT NULL, \n" +
            "  [version] char(20) NOT NULL, \n" +
            "  [scheduleId] int NOT NULL, \n" +
            "  [materialId] int NOT NULL, \n" +
            "  [orderNo] char(20) NOT NULL, \n" +
            "  [spaceCode] char(20) NOT NULL, \n" +
            "  [type] char(10) NOT NULL, \n" +
            "  [mac] char(30) NOT NULL, \n" +
            "  [time] long NOT NULL, \n" +
            "  FOREIGN KEY([materialId]) REFERENCES [Material]([materialId]));", TBNAME);

    Context context;
    AdvertDbHelper dbHelper;

    public TempRecordDao(Context context) {
        this.context = context;
        this.dbHelper = new AdvertDbHelper(context);
    }

    @Nullable
    @Override
    public TempRecord query(int id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TBNAME, null, "id=?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                TempRecord tempRecord = cursor2Bean(cursor);
                return tempRecord;
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
    public List<TempRecord> queryAll() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TBNAME, null, null,
                    null,
                    null, null, null);
            List<TempRecord> tempRecords = new ArrayList<>();
            TempRecord tempRecord = null;
            while (cursor.moveToNext()) {
                tempRecord = cursor2Bean(cursor);
                tempRecords.add(tempRecord);
            }
            return tempRecords;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(cursor);
            IoUtils.close(sqLiteDatabase);
        }
        return null;
    }

    @Override
    public long insert(@NonNull TempRecord tempRecord) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = bean2ContentValues(tempRecord);
        long rowid = db.insert(TBNAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        IoUtils.close(db);
        return rowid;
    }

    @Override
    public int update(@NonNull TempRecord tempRecord) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = bean2ContentValues(tempRecord);
        int result = db.update(TBNAME, values, "id=?", new String[]{String.valueOf(tempRecord.getId())});
        db.setTransactionSuccessful();
        db.endTransaction();
        IoUtils.close(db);
        return result;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(TBNAME, "id=?", new String[]{String.valueOf(id)});
        IoUtils.close(db);
        return result;
    }

    @Override
    public int deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(TBNAME, null, null);
        IoUtils.close(db);
        return result;
    }

    private ContentValues bean2ContentValues(TempRecord tempRecord) {
        ContentValues values = new ContentValues();
        values.put("regionId", tempRecord.getRegionId());
        values.put("cityId", tempRecord.getCityId());
        values.put("channelType", tempRecord.getChannelType());
        values.put("channelId", tempRecord.getChannelId());
        values.put("modelId", tempRecord.getSpaceCode());
        values.put("version", tempRecord.getVersion());
        values.put("orderNo", tempRecord.getOrderNo());
        values.put("spaceCode", tempRecord.getSpaceCode());
        values.put("type", tempRecord.getType());
        values.put("mac", tempRecord.getMac());
        values.put("scheduleId", tempRecord.getScheduleId());
        values.put("time", tempRecord.getTime());
        return values;
    }

    private TempRecord cursor2Bean(Cursor cursor) {
        int idxId = cursor.getColumnIndex("id");
        int idxRegionId = cursor.getColumnIndex("regionId");
        int idxCityId = cursor.getColumnIndex("cityId");
        int idxChannelType = cursor.getColumnIndex("channelType");
        int idxChannelId = cursor.getColumnIndex("channelId");
        int idxModelId = cursor.getColumnIndex("modelId");
        int idxVersion = cursor.getColumnIndex("version");
        int idxMaterialId = cursor.getColumnIndex("materialId");
        int idxOrderNo = cursor.getColumnIndex("orderNo");
        int idxSpaceCode = cursor.getColumnIndex("spaceCode");
        int idxType = cursor.getColumnIndex("type");
        int idxMac = cursor.getColumnIndex("mac");
        int idxScheduleId = cursor.getColumnIndex("scheduleId");
        int idxTime = cursor.getColumnIndex("time");

        int id = cursor.getInt(idxId);
        String regionId = cursor.getString(idxRegionId);
        String cityId = cursor.getString(idxCityId);
        String channelType = cursor.getString(idxChannelType);
        String channelId = cursor.getString(idxChannelId);
        String modelId = cursor.getString(idxModelId);
        String version = cursor.getString(idxVersion);
        int materialId = cursor.getInt(idxMaterialId);
        String orderNo = cursor.getString(idxOrderNo);
        String spaceCode = cursor.getString(idxSpaceCode);
        String type = cursor.getString(idxType);
        String mac = cursor.getString(idxMac);
        int scheduleId = cursor.getInt(idxScheduleId);
        long time = cursor.getLong(idxTime);

        TempRecord material = new TempRecord();
        material.setId(id);
        material.setRegionId(regionId);
        material.setCityId(cityId);
        material.setChannelType(channelType);
        material.setModelId(modelId);
        material.setChannelId(channelId);
        material.setVersion(version);
        material.setMaterialId(materialId);
        material.setOrderNo(orderNo);
        material.setSpaceCode(spaceCode);
        material.setType(type);
        material.setMac(mac);
        material.setScheduleId(scheduleId);
        material.setTime(time);

        return material;
    }
}
