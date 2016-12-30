package com.jackfruit.mall.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jackfruit.mall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.ui
 * @描述 管理主界面底部导航Tab
 * @创建者 kh
 * @日期 2016/12/28 10:08
 * @修改
 * @修改时期 2016/12/28 10:08
 */
public class FragmentNavTabUtil implements BottomNavigationBar.OnTabSelectedListener{
    private FragmentManager fragmentManager;
    private List<Fragment> fragments;
    private String[] fragmentTags = {"home", "cate", "find", "cart", "info"};
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


    //导航栏名称
    private String[] tabTitles;
    private BottomNavigationBar bottomNavigationBar;
    //购物车提醒
    private BadgeItem cartBadgeItem;
    //当前显示tab
    private int currentTab = 0;
    private List<BottomNavigationItem> navigationItems;


    public FragmentNavTabUtil(FragmentManager fragmentManager, List<Fragment> fragments, BottomNavigationBar bottomNavigationBar, String[] tabTitles) {
        this.fragmentManager = fragmentManager;
        this.fragments = fragments;
        this.bottomNavigationBar = bottomNavigationBar;
        this.tabTitles = tabTitles;
        initView();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    //初始化底部tab
    private void initView() {

        //实例购物车Badge
        cartBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("99")
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
                .addItem(navigationItems.get(3).setBadgeItem(cartBadgeItem))
                .addItem(navigationItems.get(4))
                .setFirstSelectedPosition(0)
                .initialise();
    }

    @Override
    public void onTabSelected(int position) {
        Log.e("", "onTabSelected: " + position);
        //是否点击当前tab
        if(currentTab != position) {
            showTab(position);
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    //显示tab页
    public void showTab(int position) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //隐藏前一个显示Tab
        ft.hide(fragments.get(currentTab));

        //判断是否已经存在，不存在tags先加入
        //fragmentManager.findFragmentByTag(fragmentTags[position]) == null
        if(!fragments.get(position).isAdded()) {
            ft.add(R.id.container, fragments.get(position), fragmentTags[position]);
        } else {
            ft.show(fragments.get(position));
        }
        ft.commit();
        currentTab = position;
    }

    //得到当前tab位置
    public int getCurrentTab() {
        return currentTab;
    }

    //得到购物车BadgeItem
    public BadgeItem getCartBadgeItem() {
        return cartBadgeItem;
    }
}
