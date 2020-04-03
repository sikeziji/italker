package com.wangj.factory.net;

import com.wangj.factory.modle.api.RspModel;
import com.wangj.factory.modle.api.account.AccountRspModel;
import com.wangj.factory.modle.api.account.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 网络请求的所有接口
 */
public interface RemoteService {

    /**
     * 网络请求的一个注册接口
     *
     * @param model 传入的是RegisterModel
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);
}
