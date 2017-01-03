package com.jackfruit.mall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jackfruit.mall.R;
import com.jackfruit.mall.bean.DemoBean;
import com.jackfruit.mall.bean.DemoResult;
import com.jackfruit.mall.http.RetrofitManager;
import com.jackfruit.mall.ui.fragment.CartFragment;
import com.jackfruit.mall.ui.fragment.CategroyFragment;
import com.jackfruit.mall.ui.fragment.FindFragment;
import com.jackfruit.mall.ui.fragment.HomePageFragment;
import com.jackfruit.mall.ui.fragment.MineFragment;
import com.jackfruit.mall.utils.DateUtils;
import com.jackfruit.mall.utils.FragmentNavTabUtil;
import com.jackfruit.mall.utils.LogUtils;
import com.jackfruit.mall.utils.permission.PermissionHelper;
import com.jackfruit.mall.utils.permission.PermissionsManager;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DemoActivity extends AppCompatActivity  implements BottomNavigationBar.OnTabSelectedListener {

    String TAG = "";
    private static final int REQUEST_MULTI_PERMISSION = 0x0101;//申请多个权限
    //权限拒绝后重复申请次数
    private static int permissionRequestCount = 0;
    //导航栏选中icon
    private int[] selectTabIcons = {
            R.mipmap.ic_home_page_selected,
            R.mipmap.ic_home_category_select,
            R.mipmap.ic_home_find_selected,
            R.mipmap.ic_home_cart_selected,
            R.mipmap.ic_home_mine_selected};
    //导航栏未选中icon
    private int[] defaultTabIcons = {
            R.mipmap.ic_home_page_default,
            R.mipmap.ic_home_category_default,
            R.mipmap.ic_home_find_default,
            R.mipmap.ic_home_cart_default,
            R.mipmap.ic_home_mine_default};
    //购物车提醒
    private BadgeItem cartBadgeItem;
    //当前显示tab
    private int currentTab = 0;
    private List<BottomNavigationItem> navigationItems;
    //导航栏名称
    private String[] tabTitles;
    //显示内容
    @BindView(R.id.container)
    FrameLayout homeContentFl;
    //底部导航栏
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private List<Fragment> fragments;
    //tab管理
    private FragmentNavTabUtil fragmentTabUtil;

    private HomePageFragment homeFragment;
    private CategroyFragment categoryFragment;
    private FindFragment findFragment;
    private CartFragment cartFragment;
    private MineFragment mineFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(savedInstanceState != null) {
            currentTab = savedInstanceState.getInt("currentTab", 0);
        }
        initFragments();

        initBottomView();
        //申请存储、相机、定位权限
        PermissionsManager.get().requestMultiPermissionsIfNecessary(this, REQUEST_MULTI_PERMISSION);
        LogUtils.init().writeLog(new LogUtils.AppLog("MA", "oC", DateUtils.getDatetime(), "").toString());
        //new IntentIntegrator(this).initiateScan(IntentIntegrator.QR_CODE_TYPES);
        startActivityForResult(new Intent(this, CaptureActivity.class), 10);
    }

    //初始化底部tab
    private void initBottomView() {
        tabTitles = getResources().getStringArray(R.array.homepage_tab_titles);
        //实例购物车Badge
        cartBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                //.setText("99")
                .setHideOnSelect(false);
        //初始化底部tab五个BottomNavigationItem
        navigationItems = new ArrayList<BottomNavigationItem>();
        navigationItems.add(new BottomNavigationItem(selectTabIcons[0], tabTitles[0]).setInactiveIconResource(defaultTabIcons[0])
                .setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.colorAccent));
        navigationItems.add(new BottomNavigationItem(selectTabIcons[1], tabTitles[1]).setInactiveIconResource(defaultTabIcons[1])
                .setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.colorAccent));
        navigationItems.add(new BottomNavigationItem(selectTabIcons[2], tabTitles[2]).setInactiveIconResource(defaultTabIcons[2])
                .setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.colorAccent));
        navigationItems.add(new BottomNavigationItem(selectTabIcons[3], tabTitles[3]).setInactiveIconResource(defaultTabIcons[3])
                .setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.colorAccent));
        navigationItems.add(new BottomNavigationItem(selectTabIcons[4], tabTitles[4]).setInactiveIconResource(defaultTabIcons[4])
                .setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.colorAccent));
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .addItem(navigationItems.get(0))
                .addItem(navigationItems.get(1))
                .addItem(navigationItems.get(2))
                .addItem(navigationItems.get(3))
                .addItem(navigationItems.get(4))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    private void initFragments() {
        LogUtils.init().writeLog(new LogUtils.AppLog("MA", "iF", DateUtils.getDatetime(), "").toString());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideTab(ft);
        homeFragment = HomePageFragment.newInstance("");
        ft.add(R.id.container, homeFragment);
        ft.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_MULTI_PERMISSION) {
            if(permissionRequestCount < 1) {
                PermissionsManager.get().requestMultiPermissionsIfNecessary(this, REQUEST_MULTI_PERMISSION);
                permissionRequestCount ++;
            } else {
                PermissionHelper.getInstance(this).locationPermissionCheck();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == 10) {
                if(null != data) {
                    Bundle bundle = data.getExtras();
                    if(bundle == null) {

                    } else {
                        if(bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                            String contents = bundle.getString(CodeUtils.RESULT_STRING);
                            Toast.makeText(this, contents, Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onActivityResult: " + contents);
                        }
                    }
                }

            }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        LogUtils.init().writeLog(new LogUtils.AppLog("MA", "onAttachFragment", DateUtils.getDatetime(), "绑定").toString());
        if(homeFragment == null && fragment instanceof HomePageFragment) {
            homeFragment = (HomePageFragment) fragment;
        }
        if(categoryFragment == null && fragment instanceof CategroyFragment) {
            categoryFragment = (CategroyFragment) fragment;
        }
        if(findFragment == null && fragment instanceof FindFragment) {
            findFragment = (FindFragment) fragment;
        }
        if(cartFragment == null && fragment instanceof CartFragment) {
            cartFragment = (CartFragment) fragment;
        }
        if(mineFragment == null && fragment instanceof MineFragment) {
            mineFragment = (MineFragment) fragment;
        }

    }

    @Override
    public void onTabSelected(int position) {
        Log.e("", "onTabSelected: " + position);
        //是否点击当前tab
        if(currentTab != position) {
            setSelection(position);
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
    private void setSelection(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideTab(transaction);
        switch (index) {
            case 0:
                if(homeFragment == null) {
                    homeFragment = HomePageFragment.newInstance("");
                    transaction.add(R.id.container, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if(categoryFragment == null) {
                    categoryFragment = CategroyFragment.newInstance("");
                    transaction.add(R.id.container, categoryFragment);
                } else {
                    transaction.show(categoryFragment);
                }
                break;
            case 2:
                if(findFragment == null) {
                    findFragment = FindFragment.newInstance("");
                    transaction.add(R.id.container, findFragment);
                } else {
                    transaction.show(findFragment);
                }
                break;
            case 3:
                if(cartFragment == null) {
                    cartFragment = CartFragment.newInstance("");
                    transaction.add(R.id.container, cartFragment);
                } else {
                    transaction.show(cartFragment);
                }
                break;
            case 4:
                if(mineFragment == null) {
                    mineFragment = MineFragment.newInstance("");
                    transaction.add(R.id.container, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commit();
        currentTab = index;
    }

    private void hideTab(FragmentTransaction ft) {
        if(homeFragment != null) {
            ft.hide(homeFragment);
        }
        if(categoryFragment != null) {
            ft.hide(categoryFragment);
        }
        if(findFragment != null) {
            ft.hide(findFragment);
        }
        if(cartFragment != null) {
            ft.hide(cartFragment);
        }
        if(mineFragment != null) {
            ft.hide(mineFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.init().writeLog(new LogUtils.AppLog("MA", "oD", DateUtils.getDatetime(), "销毁").toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentTab", currentTab);
        LogUtils.init().writeLog(new LogUtils.AppLog("MA", "oS", DateUtils.getDatetime(), "保存").toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: ********");
        currentTab = savedInstanceState.getInt("currentTab", 0);
        LogUtils.init().writeLog(new LogUtils.AppLog("MA", "oR", DateUtils.getDatetime(), "恢复").toString());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);

    }
}
