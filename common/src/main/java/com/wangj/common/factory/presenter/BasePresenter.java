package com.wangj.common.factory.presenter;

public class BasePresenter<T extends BaseContract.View>
        implements BaseContract.Presenter {
    private T mView;

    public BasePresenter(T view) {
        setView(view);
    }


    /**
     * 设置一个VIew，子类可以复写
     *
     * @param view
     */
    protected void setView(T view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * 给子类使用的获取View的操作
     * 不允许复写
     *
     * @return
     */
    protected final T getView() {
        return mView;
    }

    @Override
    public void start() {
        T view = mView;
        if (view != null) {
            view.showLoading();
        }

    }

    @Override
    public void destroy() {
        T view = mView;
        mView = null;
        if (view != null) {
            //设置Presenter为空
            view.setPresenter(null);
        }
    }
}
