package com.wangj.common.app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在界面未初始化之前，调用的初始化窗口
        initWindows();

        if (initArgs(getIntent().getExtras())) {
            //获取到界面的Id，并设置到Activity界面中去
            int layId = getContentLayoutId();
            setContentView(layId);
            initBefore();
            initWidget();
            initData();
        } else {
            finish();
        }

    }

    protected void initData() {
    }


    protected void initWidget() {
        ButterKnife.bind(this);
    }

    protected void initBefore() {
    }


    /**
     * 获取当前界面的资源ID
     *
     * @return 返回资源ID
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化相关参数
     *
     * @param extras 参数Bundle
     * @return 如果参数正确返回true ，否则返回false
     */
    protected boolean initArgs(Bundle extras) {
        return true;

    }

    /**
     * 初始化窗口
     */
    protected void initWindows() {
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
