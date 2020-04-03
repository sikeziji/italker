package com.wangj.factory.data.helper;

import com.wangj.common.factory.data.DataSource;
import com.wangj.factory.Factory;
import com.wangj.factory.R;
import com.wangj.factory.modle.api.RspModel;
import com.wangj.factory.modle.api.account.AccountRspModel;
import com.wangj.factory.modle.api.account.RegisterModel;
import com.wangj.factory.modle.db.User;
import com.wangj.factory.net.NetWork;
import com.wangj.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountHelper {


    /**
     * 注册的接口，
     *
     * @param model    传递一个注册的modle
     * @param callback 回调成功与失败的接口
     */
    public static void register(RegisterModel model, final DataSource.Callback<User> callback) {
        RemoteService service = NetWork.getRetrofit().create(RemoteService.class);

        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);

        call.enqueue(new Callback<RspModel<AccountRspModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
                //请求返回成功
                //从返回中得到我们全局的model ，内部是使用Gson解析
                RspModel<AccountRspModel> rspModel = response.body();
                //判断是否绑定设备
                if (rspModel.success()) {
                    AccountRspModel accountRspModel = rspModel.getResult();
                    if (accountRspModel.isBind()) {
                        //拿到实体
                        User user = accountRspModel.getUser();
                        //数据库的写入和缓存的绑定，然后返回
                        callback.onDataLoaded(user);
                    } else {
                        //绑定设备
                        bindPush(callback);

                    }
                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
                //请求返回失败
                callback.onDataNotAvailable(R.string.data_network_error);

            }
        });


    }


    /**
     * 对设备Id进行绑定的操作
     *
     * @param callback
     */
    public static void bindPush(final DataSource.Callback<User> callback) {
        //抛出一个错误
        callback.onDataNotAvailable(R.string.app_name);
    }
}
