package com.wangj.factory.presenter;

public class Account {

    //设备的推送ID
    private static String pushId = "test";


    public static String getPushId() {
        return pushId;
    }

    public static void setPushId(String pushId) {
        Account.pushId = pushId;
    }
}
