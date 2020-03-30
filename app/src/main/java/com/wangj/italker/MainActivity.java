package com.wangj.italker;

import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wangj.common.app.BaseActivity;
import com.wangj.common.widget.PortraitView;
import com.wangj.italker.frags.main.ActivityFragment;
import com.wangj.italker.frags.main.ContactFragment;
import com.wangj.italker.frags.main.GroupFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.appbar)
    View mLayAppbar;

    @BindView(R.id.im_portrait)
    PortraitView mPortraitView;

    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.lay_container)
    FrameLayout mContainer;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();


        // 添加对底部按钮点击的监听
        mNavigation.setOnNavigationItemSelectedListener(this);

        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new CustomViewTarget<View, Drawable>(mLayAppbar) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        this.view.setBackground(resource);
                    }

                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @Override
    protected void InitData() {
        super.InitData();
    }


    @OnClick(R.id.im_search)
    void onSearchMenuClick() {

    }


    @OnClick(R.id.btn_action)
    void onActionClick() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.action_home) {

            //更新Title
            mTitle.setText(menuItem.getTitle());

            ActivityFragment activityFragment = new ActivityFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.lay_container, activityFragment)
                    .commit();
        } else if (menuItem.getItemId() == R.id.action_group) {
            mTitle.setText(menuItem.getTitle());

            GroupFragment groupFragment = new GroupFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.lay_container, groupFragment)
                    .commit();
        } else if (menuItem.getItemId() == R.id.action_contact) {
            mTitle.setText(menuItem.getTitle());

            ContactFragment contactFragment = new ContactFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.lay_container, contactFragment)
                    .commit();
        }


        return true;
    }
}
