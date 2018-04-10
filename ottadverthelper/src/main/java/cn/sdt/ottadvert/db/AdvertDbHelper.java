package cn.sdt.ottadvert.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.util.ArrayMap;

import cn.sdt.ottadvert.db.dao.DefaultMaterialDao;
import cn.sdt.ottadvert.db.dao.ErrorMD5MaterialDao;
import cn.sdt.ottadvert.db.dao.LastPlayDateDao;
import cn.sdt.ottadvert.db.dao.MaterialDao;
import cn.sdt.ottadvert.db.dao.TempRecordDao;
import cn.sdt.ottadvert.db.upgrade.Upgrade;
import cn.sdt.ottadvert.db.upgrade.VersionFactory;

/**
 * Created by SDT13411 on 2018/3/21.
 */

public class AdvertDbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "advertapp.db";
    private Context mContext;

    public AdvertDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DefaultMaterialDao.CREATE_SQL);
        db.execSQL(MaterialDao.CREATE_SQL);
        db.execSQL(TempRecordDao.CREATE_SQL);
        db.execSQL(LastPlayDateDao.CREATE_SQL);
        db.execSQL(ErrorMD5MaterialDao.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ArrayMap<Integer, Upgrade> upgradeList = VersionFactory.getUpgradeList(oldVersion, newVersion, mContext);
        //按照版本号从低到高执行升级操作
        int size = upgradeList.size();
        for (int i = 0; i < size; i++) {
            upgradeList.valueAt(i).update(db);
        }
    }


}
