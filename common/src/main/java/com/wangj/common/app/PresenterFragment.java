package com.wangj.common.app;

import android.content.Context;

import androidx.annotation.NonNull;

import com.wangj.common.factory.presenter.BaseContract;

public abstract class PresenterFragment<Presenter extends BaseContract.Presenter>
        extends BaseFragment implements BaseContract.View<Presenter> {

    protected Presenter mPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //在界面onAttach之后出发初始化Presenter
        initPresenter();
    }

    protected abstract Presenter initPresenter();

    @Override
    public void showError(int str) {
        Application.showToast(str);
    }

    @Override
    public void showLoading() {

        //TODO 封装完成后实现Loading
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }
}
