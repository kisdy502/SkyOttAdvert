package cn.sdt.ottadvert.db.upgrade;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.sdt.ottadvert.db.dao.MaterialDao;

/**
 * Created by SDT13411 on 2018/3/22.
 */
@DbVersionCode(2)
public class Version2Upgrade extends Upgrade {
    @Override
    public void update(SQLiteDatabase db) {
        Log.d("v2", "db version 2 upgrade");

    }
}
