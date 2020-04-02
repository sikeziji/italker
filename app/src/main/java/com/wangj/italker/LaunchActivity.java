package com.wangj.italker;

import com.wangj.common.app.BaseActivity;
import com.wangj.italker.activity.MainActivity;
import com.wangj.italker.assist.PermissionsFragment;

public class LaunchActivity extends BaseActivity {
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (PermissionsFragment.haveAll(this, getSupportFragmentManager())) {
            MainActivity.show(this);
            finish();
        }
    }
}
