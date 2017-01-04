package common.lib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @项目名称 JackFruit
 * @类：common.lib.base
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/4 17:55
 * @修改
 * @修改时期 2017/1/4 17:55
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getRootView() != 0) {
            setContentView(getRootView());
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    abstract int getRootView();

    abstract boolean isBindEventBusHere();


    protected void toNewActivity(Class clx) {
        startActivity(new Intent(this, clx));
    }

    protected void toNewActivity(Class clx, Intent intent) {
        intent.setClass(this, clx);
        startActivity(intent);
    }

    protected void toNewActivity(Class clx, Intent intent, boolean isFinish) {
        toNewActivity(clx, intent);
        if(isFinish) {
            finish();
        }
    }
}
