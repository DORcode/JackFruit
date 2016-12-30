package com.jackfruit.mall.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jackfruit.mall.utils.Constants;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.utils.db
 * @描述 describe
 * @创建者 kh
 * @日期 2016/12/29 10:34
 * @修改
 * @修改时期 2016/12/29 10:34
 */

public class SqliteHelper extends SQLiteOpenHelper {

    public SqliteHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
