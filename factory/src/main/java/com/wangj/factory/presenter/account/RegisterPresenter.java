package com.wangj.factory.presenter.account;

import android.text.TextUtils;

import com.wangj.common.Common;
import com.wangj.common.factory.data.DataSource;
import com.wangj.common.factory.presenter.BasePresenter;
import com.wangj.factory.R;
import com.wangj.factory.data.helper.AccountHelper;
import com.wangj.factory.modle.api.account.RegisterModel;
import com.wangj.factory.modle.db.User;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter, DataSource.Callback<User> {


    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String password) {
        //调用开始方法，在Start中默认启动了loading
        start();

        //得到View接口
        RegisterContract.View view = getView();

        //检查参数是否合法

        if (!checkMobile(phone)) {
            //提示
            view.showError(R.string.data_account_register_invalid_parameter_mobile);
        } else if (name.length() < 2) {
            //名称需要大于2位
            view.showError(R.string.data_account_register_invalid_parameter_name);
        } else if (password.length() < 6) {
            //密码需要大于6位
            view.showError(R.string.data_account_register_invalid_parameter_password);
        } else {//进行网络请求

            //构造Model，进行请求调用
            RegisterModel model = new RegisterModel(phone, password, name);
            //进行网络请求并设置回调借口给自己
            AccountHelper.register(model, this);
        }
    }

    /**
     * 检查手机号是否合法
     *
     * @param phone 手机号码
     * @return 合法为True
     */
    @Override
    public boolean checkMobile(String phone) {
        return !TextUtils.isEmpty(phone) && Pattern.matches(Common.Constance.REGEXX_MOBILE, phone);
    }

    @Override
    public void onDataLoaded(User user) {
        //当网络请求成功，注册好了，回调用户信息
        //通知界面注册成功
        final RegisterContract.View view = getView();
        if (view == null) {
            return;
        }
        //当从网络中回调到此接口，并不能保证是在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //调用注册成功
                view.registerSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        //请求网络注册失败
        final RegisterContract.View view = getView();
        if (view == null) {
            return;
        }
        //当从网络中回调到此接口，并不能保证是在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //调用注册显示失败
                view.showError(strRes);
            }
        });
    }
}
