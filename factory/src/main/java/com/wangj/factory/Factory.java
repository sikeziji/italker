package com.wangj.factory;

import com.wangj.common.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Factory {

    private static final Factory instance;
    private final Executor mExecutor;


    static {
        instance = new Factory();
    }

    private Factory() {
        mExecutor = Executors.newFixedThreadPool(4);
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
}
