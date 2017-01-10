package com.jackfruit.mall.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.utils
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/10 9:55
 * @修改
 * @修改时期 2017/1/10 9:55
 */
public class V2Toast {

    public static void show(Context context, String msg, int length) {
        Toast.makeText(context, msg, length).show();
    }

    public static void showCenter(Context context, String msg, int length) {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setText(msg);
        toast.setDuration(length);
        toast.show();
    }
}
