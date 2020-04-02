package com.wangj.italker.frags.main;

import com.wangj.common.app.BaseFragment;
import com.wangj.italker.R;

public class ActivityFragment extends BaseFragment {


    @Override
    protected int getContentLayoutId() {

        System.out.println("加载界面");

        return R.layout.fragment_activity;
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
