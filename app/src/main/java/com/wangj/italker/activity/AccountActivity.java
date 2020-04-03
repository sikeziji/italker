package com.wangj.italker.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.wangj.common.app.BaseActivity;
import com.wangj.common.app.BaseFragment;
import com.wangj.italker.R;
import com.wangj.italker.frags.account.Accounttrigger;
import com.wangj.italker.frags.account.LoginFragment;
import com.wangj.italker.frags.account.RegisterFragment;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;

public class AccountActivity extends BaseActivity implements Accounttrigger {


    private BaseFragment mCurrentFragment;
    private BaseFragment mLoginFragment;
    private BaseFragment mRegisterFragmnet;

    @BindView(R.id.im_bg)
    ImageView mBg;

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

        mCurrentFragment = mLoginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurrentFragment)
                .commit();

        //初始化背景
        Glide.with(this)
                .load(R.drawable.bg_src_tianjin)
                .centerCrop()
                .into(new DrawableImageViewTarget(mBg){
                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        //判断resource是否为空
                        if (resource == null) {
                            super.setResource(resource);
                            return;
                        }
                        // 使用适配类进行包装
                        Drawable drawable = DrawableCompat.wrap(resource);
                        drawable.setColorFilter(UiCompat.getColor(getResources(), R.color.colorAccent),
                                PorterDuff.Mode.SCREEN); // 设置着色的效果和颜色，蒙板模式
                        // 设置给ImageView
                        super.setResource(drawable);
                    }
                });

    }

    @Override
    public void triggerView() {
        BaseFragment fragment;
        if (mCurrentFragment == mLoginFragment) {
            if (mRegisterFragmnet == null) {
                //默认情况下为null
                //第一次后就不为空
                mRegisterFragmnet = new RegisterFragment();
            }
            fragment = mRegisterFragmnet;
        } else {
            //因为默认请求下MLoginFragment 已经复制，无需判断null
            fragment = mLoginFragment;
        }

        //重新赋值当前正在显示的Fragment
        mCurrentFragment = fragment;


        //切换显示
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_container, fragment)
                .commit();

    }
}
