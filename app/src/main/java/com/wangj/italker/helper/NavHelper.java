package com.wangj.italker.helper;

import android.content.Context;
import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NavHelper<T> {


    //用于初始化的相关参数
    private final FragmentManager fragmentManager;

    private final int containerId;

    private final SparseArray<Tab<T>> tabs = new SparseArray();

    private final Context context;

    private final OnTabChangeListener<T> listener;

    //存储一个当前选中的Tab
    private Tab<T> currentTab;

    public NavHelper(Context context, int containerId,  FragmentManager fragmentManager,OnTabChangeListener<T> listener) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
        this.context = context;
        this.listener = listener;
    }


    /**
     * 添加Tab
     *
     * @param menuId Tab对应的菜单ID
     * @param tab
     */
    public NavHelper<T> add(int menuId, Tab<T> tab) {
        tabs.put(menuId, tab);
        return this;
    }

    /**
     * 获取当前显示的Tab
     *
     * @return
     */
    public Tab<T> getCurrentTab() {
        return currentTab;
    }


    /**
     * 执行点击菜单操作
     *
     * @param menuId 菜单的Id
     * @return 是否能够处理这个点击
     */
    public boolean performClickMenu(int menuId) {
        //集合中寻找点击的菜单对应的Tab
        //如果有则进行处理
        Tab<T> tab = tabs.get(menuId);
        if (tab != null) {
            doSelect(tab);
            return true;
        }

        return false;
    }

    private void doSelect(Tab<T> tab) {
        Tab<T> oldTab = null;

        if (currentTab != null) {
            oldTab = currentTab;
            if (oldTab == tab) {
                //如果当前tab是点击的tab
                //可以做刷新操作
                notifyTabReselect(tab);
                return;
            }
        }
        currentTab = tab;
    }

    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (oldTab != null && oldTab.fragment != null) {
            //从界面中移除，但是还在fragment的缓存空间中
            ft.detach(oldTab.fragment);
        }
        if (newTab != null) {
            if (newTab.fragment == null) {
                //首次新建
                Fragment fragment = fragmentManager.getFragmentFactory().instantiate(context.getClassLoader(), newTab.clx.getName());
                //缓存起来
                newTab.fragment = fragment;
                //提交fragmentManger
                ft.add(containerId,fragment,newTab.clx.getName());
            }else {
                ft.attach(newTab.fragment);
            }
            ft.commit();
            // 通知回调
            notifyTabSelect(newTab, oldTab);
        }
    }

    /**
     * 刷新切换tab
     * @param newTab
     * @param oldTab
     */
    private void notifyTabSelect(Tab<T> newTab, Tab<T> oldTab) {
        if (listener != null) {
            listener.onTabChanged(newTab,oldTab);
        }
    }

    /**
     * 刷新当前Tab
     * @param tab
     */
    private void notifyTabReselect(Tab<T> tab) {
        //TODO 二次点击tab所做的操作

    }

    public static class Tab<T> {

        public Tab(Class<?> clx, T extra) {
            this.clx = clx;
            this.extra = extra;
        }

        public Class<?> clx;

        //额外的字段，用户自己设定需要使用
        public T extra;

        //内部缓存的对应Fragment，
        Fragment fragment;


    }

    public interface OnTabChangeListener<T> {
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }
}
