package com.jackfruit.mall.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jackfruit.mall.JFApplication;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.utils.db
 * @描述 describe
 * @创建者 kh
 * @日期 2016/12/29 10:39
 * @修改
 * @修改时期 2016/12/29 10:39
 */

public class SqliteUtil {
    private Context context;
    private static SqliteUtil instance;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase db;

    public SqliteUtil() {
        context = JFApplication.getApp().getApplicationContext();
        sqLiteOpenHelper = new SqliteHelper(context);
    }

    private void establishDB() {
        if(db == null) {
            db = sqLiteOpenHelper.getWritableDatabase();
        }
    }

    public SQLiteDatabase getDB() {
        establishDB();
        return db;
    }
}
