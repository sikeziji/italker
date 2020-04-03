package com.wangj.common.factory.presenter;

import androidx.annotation.StringRes;

/**
 * MVP模式中公共的基本契约
 */
public interface BaseContract {


    interface View<T extends Presenter> {

        //显示一个字符串错误
        void showError(@StringRes int str);

        //显示进度条
        void showLoading();

        //设置Presenter
        void setPresenter(T presenter);
    }

    interface Presenter {

        //公用的开始
        void start();


        //公用的销毁
        void destroy();
    }
}
