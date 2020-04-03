package com.wangj.factory;

import androidx.annotation.StringRes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wangj.common.app.Application;
import com.wangj.common.factory.data.DataSource;
import com.wangj.factory.modle.api.RspModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Factory {

    private static final Factory instance;
    private final Executor mExecutor;
    private final Gson gson;


    static {
        instance = new Factory();
    }

    private Factory() {
        mExecutor = Executors.newFixedThreadPool(4);
        gson = new GsonBuilder()
                //设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                //TODO 设置一个过滤器，数据库级别的model不进行json的转换
//                .setExclusionStrategies()
                .create();
    }


    /**
     * 返回全局的Application
     *
     * @return Application
     */
    public static Application app() {
        return Application.getInstance();
    }

    public static void runOnAsync(Runnable runnable) {
        //拿到线程池异步执行
        instance.mExecutor.execute(runnable);
    }

    /**
     * 返回一个全局的Gson，在这里可以进行Gson的一些全局的初始化
     *
     * @return
     */
    public static Gson getGson() {
        return instance.gson;
    }

    /**
     * 进行错误Code的解析
     *
     * @param model
     * @param callback
     */
    public static void decodeRspCode(RspModel model, DataSource.FailedCallback callback) {
        if (model == null) {
            return;
        }
        switch (model.getCode()) {

            case RspModel.SUCCEED:
                return;
            case RspModel.ERROR_SERVICE:
                decodeRspCode(R.string.data_rsp_error_service, callback);
                break;
            case RspModel.ERROR_NOT_FOUND_USER:
                decodeRspCode(R.string.data_rsp_error_not_found_user, callback);
                break;
            case RspModel.ERROR_NOT_FOUND_GROUP:
                decodeRspCode(R.string.data_rsp_error_not_found_group, callback);
                break;
            case RspModel.ERROR_NOT_FOUND_GROUP_MEMBER:
                decodeRspCode(R.string.data_rsp_error_not_found_group_member, callback);
                break;
            case RspModel.ERROR_CREATE_USER:
                decodeRspCode(R.string.data_rsp_error_create_user, callback);
                break;
            case RspModel.ERROR_CREATE_GROUP:
                decodeRspCode(R.string.data_rsp_error_create_group, callback);
                break;
            case RspModel.ERROR_CREATE_MESSAGE:
                decodeRspCode(R.string.data_rsp_error_create_message, callback);
                break;
            case RspModel.ERROR_PARAMETERS:
                decodeRspCode(R.string.data_rsp_error_parameters, callback);
                break;
            case RspModel.ERROR_PARAMETERS_EXIST_ACCOUNT:
                decodeRspCode(R.string.data_rsp_error_parameters_exist_account, callback);
                break;
            case RspModel.ERROR_PARAMETERS_EXIST_NAME:
                decodeRspCode(R.string.data_rsp_error_parameters_exist_name, callback);
                break;
            case RspModel.ERROR_ACCOUNT_TOKEN:
                Application.showToast(R.string.data_rsp_error_account_token);
                instance.logout();
                break;
            case RspModel.ERROR_ACCOUNT_LOGIN:
                decodeRspCode(R.string.data_rsp_error_account_login, callback);
                break;
            case RspModel.ERROR_ACCOUNT_REGISTER:
                decodeRspCode(R.string.data_rsp_error_account_register, callback);
                break;
            case RspModel.ERROR_ACCOUNT_NO_PERMISSION:
                decodeRspCode(R.string.data_rsp_error_account_no_permission, callback);
                break;
            case RspModel.ERROR_UNKNOWN:
            default:
                decodeRspCode(R.string.data_rsp_error_unknown, callback);
                break;
        }
    }


    private static void decodeRspCode(@StringRes final int resId,
                                      final DataSource.FailedCallback callback) {
        if (callback != null)
            callback.onDataNotAvailable(resId);
    }


    /**
     * 收到账户退出的消息需要进行账户退出重新登录
     */
    private void logout() {

    }
}
