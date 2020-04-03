package com.wangj.common;

public class Common {

    /**
     * 一些不可变的参数，通常用于一些配置
     */
    public interface Constance {

        String REGEXX_MOBILE = "[1][3,4,5,7,8][0-9]{9}$";

        //基础的网络请求地址
        String API_URL = "http://192.168.31.160:8080/api/";

    }
}
