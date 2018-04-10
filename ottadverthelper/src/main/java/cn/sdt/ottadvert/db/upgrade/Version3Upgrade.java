package cn.sdt.ottadvert.db.upgrade;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.sdt.ottadvert.db.bean.ErrorMD5Material;
import cn.sdt.ottadvert.db.dao.ErrorMD5MaterialDao;
import cn.sdt.ottadvert.db.dao.LastPlayDateDao;
import cn.sdt.ottadvert.db.dao.TempRecordDao;

/**
 * Created by SDT13411 on 2018/3/22.
 */
@DbVersionCode(3)
public class Version3Upgrade extends Upgrade {
    @Override
    public void update(SQLiteDatabase db) {
        Log.d("v2", "db version 2 upgrade");
    }
}
