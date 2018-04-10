package cn.sdt.ottadvert.db.upgrade;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by SDT13411 on 2018/3/22.
 */
@DbVersionCode(1)
public class Version1Upgrade extends Upgrade {

    @Override
    public void update(SQLiteDatabase db) {
        Log.d("v1", "db version 1 upgrade");
    }
}
