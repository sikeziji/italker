package com.wangj.italker.activity;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.wangj.common.app.BaseActivity;
import com.wangj.common.app.BaseFragment;
import com.wangj.italker.R;
import com.wangj.italker.frags.user.UpdateInfoFragment;

public class UserActivity extends BaseActivity {

    private BaseFragment mCurrentFragment;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_user;
    }




    @Override
    protected void initWidget() {
        super.initWidget();

        mCurrentFragment = new UpdateInfoFragment();
        //显示更新
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurrentFragment)
                .commit();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCurrentFragment.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
