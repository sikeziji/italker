package com.wangj.italker.frags.account;

import android.content.Context;

import androidx.annotation.NonNull;

import com.wangj.common.app.BaseFragment;
import com.wangj.italker.R;

public class LoginFragment extends BaseFragment {

    private Accounttrigger mAccounttrigger;

    @Override
    protected int getContentLayoutId() {

        return R.layout.fragment_login;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //拿到Activity的引用
        mAccounttrigger = (Accounttrigger) context;
    }

    @Override
    public void onResume() {
        super.onResume();

        //进行一次切换，默认切换为注册界面
        mAccounttrigger.triggerView();


    }
}
