package com.wangj.italker.frags.account;


import android.content.Context;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.wangj.common.app.PresenterFragment;
import com.wangj.factory.presenter.account.RegisterContract;
import com.wangj.factory.presenter.account.RegisterPresenter;
import com.wangj.italker.R;
import com.wangj.italker.activity.MainActivity;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册界面
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter>
        implements RegisterContract.View {
    private Accounttrigger mAccounttrigger;

    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_name)
    EditText mName;
    @BindView(R.id.edit_password)
    EditText mPassword;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //拿到Activity的引用
        mAccounttrigger = (Accounttrigger) context;


    }

    /**
     * 初始化Presenter
     *
     * @return
     */
    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }


    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String phone = mPhone.getText().toString();
        String name = mName.getText().toString();
        String password = mPassword.getText().toString();

        //调用P层进行注册
        mPresenter.register(phone, name, password);
    }


    @OnClick(R.id.txt_go_login)
    void OnShowLoginClick() {
        mAccounttrigger.triggerView();
    }


    @Override
    public void showLoading() {
        super.showLoading();

        //当注册时，界面不可输入
        mLoading.start();
        //让控件不可以输入
        mPhone.setEnabled(false);
        mName.setEnabled(false);
        mPassword.setEnabled(false);
        //提交按钮不可以点击
        mSubmit.setEnabled(false);
    }

    @Override
    public void showError(int str) {
        super.showError(str);
        //当需要显示错误时触发，一定是注册结束
        mLoading.stop();
        //让控件可以输入
        mPhone.setEnabled(true);
        mName.setEnabled(true);
        mPassword.setEnabled(true);
        //提交按钮可以点击
        mSubmit.setEnabled(true);
    }

    /**
     * 注册成功回调
     */
    @Override
    public void registerSuccess() {

        //MainActivity可以正常进行跳转
        //我们需要记性跳转到MainActivity界面
        MainActivity.show(getContext());

        //关闭当前界面
        getActivity().finish();
    }
}

