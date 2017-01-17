package com.jackfruit.mall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jackfruit.mall.JFApplication;
import com.jackfruit.mall.R;
import com.jackfruit.mall.bean.Demo;
import com.jackfruit.mall.bean.DemoBean;
import com.jackfruit.mall.bean.DemoDao;
import com.jackfruit.mall.bean.DemoResult;
import com.jackfruit.mall.http.RetrofitManager;
import com.jackfruit.mall.ui.fragment.MineFragment;
import com.jackfruit.mall.ui.fragment.CartFragment;
import com.jackfruit.mall.ui.fragment.CategroyFragment;
import com.jackfruit.mall.ui.fragment.FindFragment;
import com.jackfruit.mall.ui.fragment.HomePageFragment;
import com.jackfruit.mall.utils.FragmentNavTabUtil;
import com.jackfruit.mall.utils.V2Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.lib.rx.BaseSubscriber;
import common.lib.utils.ExceptionHandle;
import common.lib.utils.permission.PermissionHelper;
import common.lib.utils.permission.PermissionsManager;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    private static final String TAG = "MainActivity";
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
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        if(savedInstanceState != null) {
            currentTab = savedInstanceState.getInt("currentTab", 0);
            initFragments(false);
        } else {
            //第一次运行
            initFragments(true);
        }
        String aaa = JFApplication.getDaoSession().getDemoDao().load("111111").toString();
        QueryBuilder<Demo> queryBuilder = JFApplication.getDaoSession().getDemoDao().queryBuilder();
        Log.d(TAG, "onCreate: " + aaa);
        queryBuilder.join(Demo.class, DemoDao.Properties.Id);
        //initFragments();
        /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideTab(ft);
        homeFragment = HomePageFragment.newInstance("");
        ft.add(R.id.container, homeFragment);
        ft.commit();*/
        initBottomView();
        //申请存储、相机、定位权限
        PermissionsManager.get().requestMultiPermissionsIfNecessary(this, REQUEST_MULTI_PERMISSION);
        
        Subscription subscription = RetrofitManager.getRetrofitManager().getLoginService().getDemoResult()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<DemoResult<DemoBean>>() {
                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(DemoResult<DemoBean> bean) {
                        V2Log.d(TAG, "onNext: " + bean.getCode() + bean.getData().getVersionCode() + bean.getData().getUrl());
                        Toast.makeText(MainActivity.this, bean.getData().getUrl(), Toast.LENGTH_LONG).show();
                    }
                });
        CompositeSubscription compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(subscription);
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

    private void initFragments(boolean isInit) {
        fragments = new ArrayList<Fragment>();
        if(isInit) {
            homeFragment = HomePageFragment.newInstance("");
            categoryFragment = CategroyFragment.newInstance("");
            findFragment = FindFragment.newInstance("");
            cartFragment = CartFragment.newInstance("");
            mineFragment = MineFragment.newInstance("");
            fragments.add(homeFragment);
            fragments.add(categoryFragment);
            fragments.add(findFragment);
            fragments.add(cartFragment);
            fragments.add(mineFragment);
            getSupportFragmentManager().beginTransaction().add(R.id.container, homeFragment, homeFragment.getClass().getName()).add(R.id.container, categoryFragment, categoryFragment.getClass().getName())
                    .add(R.id.container, findFragment, findFragment.getClass().getName()).add(R.id.container, cartFragment, cartFragment.getClass().getName())
                    .add(R.id.container, mineFragment, mineFragment.getClass().getName())
                    .hide(homeFragment).hide(categoryFragment).hide(findFragment)
                    .hide(cartFragment).hide(mineFragment)
                    .show(homeFragment).commit();
         }else {
            homeFragment = (HomePageFragment) getSupportFragmentManager().findFragmentByTag(homeFragment.getClass().getName());
            categoryFragment = (CategroyFragment) getSupportFragmentManager().findFragmentByTag(categoryFragment.getClass().getName());
            findFragment = (FindFragment) getSupportFragmentManager().findFragmentByTag(findFragment.getClass().getName());
            cartFragment = (CartFragment) getSupportFragmentManager().findFragmentByTag(cartFragment.getClass().getName());
            mineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag(mineFragment.getClass().getName());

            fragments.add(homeFragment);
            fragments.add(categoryFragment);
            fragments.add(findFragment);
            fragments.add(cartFragment);
            fragments.add(mineFragment);
            getSupportFragmentManager().beginTransaction().hide(homeFragment).hide(categoryFragment)
                    .hide(findFragment).hide(cartFragment).hide(mineFragment).show(fragments.get(currentTab)).commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            String contents = getIntent().getStringExtra("SCAN_RESULT");//图像内容
            String format = getIntent().getStringExtra("SCAN_RESULT_FROMAT");//图像格式
            Toast.makeText(this, contents, Toast.LENGTH_LONG).show();
        }
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
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
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
        V2Log.e("", "onTabSelected: " + position);
        //是否点击当前tab
        if(currentTab != position) {
            switchTab(position);
            //setSelection(position);
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

    //显示tab页
    public void switchTab(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //隐藏前一个显示Tab
        ft.hide(fragments.get(currentTab));
        //判断是否已经存在，不存在tags先加入
        if(!fragments.get(position).isAdded()) {
            ft.add(R.id.container, fragments.get(position), fragments.get(position).getClass().getName());
            //ft.show(fragments.get(position));
        } else {
            ft.show(fragments.get(position));
        }
        ft.commit();
        currentTab = position;
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentTab", currentTab);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        V2Log.i(TAG, "onRestoreInstanceState: ********");
        currentTab = savedInstanceState.getInt("currentTab", 0);
    }
}
