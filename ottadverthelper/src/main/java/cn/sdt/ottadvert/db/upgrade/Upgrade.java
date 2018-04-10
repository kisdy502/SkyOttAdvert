package cn.sdt.ottadvert.db.upgrade;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by SDT13411 on 2018/3/22.
 */

public abstract class Upgrade {
    public abstract void update(SQLiteDatabase db);
}
