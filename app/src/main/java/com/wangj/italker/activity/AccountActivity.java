package com.wangj.italker.activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wangj.common.app.BaseActivity;
import com.wangj.common.app.BaseFragment;
import com.wangj.italker.R;
import com.wangj.italker.frags.account.UpdateInfoFragment;

public class AccountActivity extends BaseActivity {

    private BaseFragment mCurrentFragment;

    public static void show(Context context) {
        //显示Activity
        context.startActivity(new Intent(context, AccountActivity.class));
    }


    @Override
    protected int getContentLayoutId() {

        return R.layout.activity_account;
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


    //
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        mCurrentFragment.onActivityResult(requestCode, resultCode, data);
//    }
}
