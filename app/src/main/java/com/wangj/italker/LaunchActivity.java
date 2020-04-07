package com.wangj.italker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;

import com.wangj.common.app.BaseActivity;
import com.wangj.factory.presenter.Account;
import com.wangj.italker.activity.MainActivity;
import com.wangj.italker.assist.PermissionsFragment;

import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.compat.UiCompat;

public class LaunchActivity extends BaseActivity {

    private ColorDrawable mBgDrawable;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        //拿到根布局
        View root = findViewById(R.id.activity_launch);
        //获取颜色
        int color = UiCompat.getColor(getResources(), R.color.colorPrimary);
        //创建一个Drawable
        ColorDrawable drawable = new ColorDrawable(color);
        //设置背景
        root.setBackground(drawable);

        mBgDrawable = drawable;
    }

    @Override
    protected void initData() {
        super.initData();

        //动画进入到50%等待PushId获取到
        startAnim(0.5f, () -> waitPushReceiverId());
    }

    /**
     * 等待个推框架对我们的PushId设置好值
     */
    private void waitPushReceiverId() {

        if (!TextUtils.isEmpty(Account.getPushId())) {
            //跳转
            skip();
            return;
        }
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                waitPushReceiverId();
            }
        }, 500);

    }

    /**
     * 跳转界面
     */
    private void skip() {

        startAnim(1f, new Runnable() {
            @Override
            public void run() {
                reallySkip();
            }
        });
    }


    private void reallySkip() {
        //权限检测跳转
        if (PermissionsFragment.haveAll(this, getSupportFragmentManager())) {

            MainActivity.show(this);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


//        if (PermissionsFragment.haveAll(this, getSupportFragmentManager())) {
//            MainActivity.show(this);
//            finish();
//        }
    }


    /**
     * 给背景设置一个动画
     *
     * @param endProgress 动画的结束进度
     * @param endCallback 动画结束时触发
     */
    private void startAnim(float endProgress, final Runnable endCallback) {
        //获取一个结束的颜色
        int finalColor = Resource.Color.WHITE;//

        //运算当前进度的颜色
        ArgbEvaluator enaluator = new ArgbEvaluator();
        int endColor = (int) enaluator.evaluate(endProgress, mBgDrawable.getColor(), finalColor);

        //构建一个属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofObject(this, mProperty, enaluator, endColor);
        //设置时间为1.5秒
        valueAnimator.setDuration(1500);
        //设置开始结束的值
        valueAnimator.setIntValues(mBgDrawable.getColor(), endColor);

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                endCallback.run();
            }
        });

        valueAnimator.start();

    }


    private Property<LaunchActivity, Object> mProperty = new Property<LaunchActivity, Object>(Object.class, "color") {
        @Override
        public Object get(LaunchActivity launchActivity) {
            return launchActivity.mBgDrawable.getColor();
        }

        @Override
        public void set(LaunchActivity object, Object value) {
            object.mBgDrawable.setColor((Integer) value);
        }
    };
}
